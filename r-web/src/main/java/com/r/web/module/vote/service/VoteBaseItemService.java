/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.web.module.vote.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.core.util.RandomUtil;
import com.r.web.component.incrementer.context.IncrementerContext;
import com.r.web.module.vote.exception.VoteItemContextErrorException;
import com.r.web.module.vote.model.Vote;
import com.r.web.module.vote.model.VoteItem;
import com.r.web.module.vote.model.base.VoteBaseItemImpl;
import com.r.web.support.abs.AbstractDao;
import com.r.web.support.abs.AbstractService;
import com.r.web.support.bean.KeyValue;

/**
 * 用户Service<br />
 * 
 * @author rain
 */
@Service("vote.service.votebaseitem")
public class VoteBaseItemService extends AbstractService {

    /** 基础问卷项问卷项自增长器类型 */
    public static final String VOTEBASEITEM_INCREMENTER_TYPE = "votebaseitem";
    /** 问卷项自增长器类型 */
    public static final String VOTEITEM_INCREMENTER_TYPE = "voteitem";

    @Resource(name = "incrementer")
    private IncrementerContext incrementer;

    @Resource(name = "support.dao.default")
    private AbstractDao<VoteBaseItemImpl> abstractDao;

    @Resource(name = "vote.service.vote")
    private VoteService voteService;

    @Resource(name = "vote.service.voteitem")
    private VoteItemService voteItemService;

    /**
     * 根据ID查找问卷项
     * 
     * @param id
     *            问卷项ID
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public VoteBaseItemImpl findById(String id) {
        // 这里只能使用 AbsVoteItem 实体的DAO来查询.因为只有AbsVoteItem才有ID主键
        // 子类实体没有主键ID所以不能使用对应的DAO.find(id)方法查询
        return abstractDao.find(id);
    }

    /**
     * 根据问卷项编号查询问卷项
     * 
     * @param yesno
     * 
     * @param no
     *            问卷项编号
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public VoteBaseItemImpl findByNo(Integer no) {
        // 这里可以使用AbsVoteItem的确定子类来确定性的查询.如果使用AbsVoteItem实体来查询.则会把所有的子类都join进来后再进行查询.
        List<VoteBaseItemImpl> queryByHql = abstractDao.queryByHql(" from " + VoteBaseItemImpl.class.getName() + " where no = :no", KeyValue.kv("no", no));
        if (CollectionUtils.isEmpty(queryByHql)) {
            return null;
        } else {
            return queryByHql.get(0);
        }
    }

    /** 查询所有数据 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public List<VoteBaseItemImpl> queryAll() {
        return abstractDao.queryAll(Order.desc("createDate"), Order.desc("no"));
    }

    /** 分页查询数据 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public List<VoteBaseItemImpl> queryByPage(int curPage, int pageSize) {
        return abstractDao.query((curPage - 1) * pageSize, pageSize);
    }

    /** 随机查询问卷项 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public List<VoteItem> queryByRandom(Vote vote, int visize) {
        // 获取最大的问卷项编号
        VoteBaseItemImpl maxNoVoteItem = abstractDao.query(0, 1, Order.desc("no")).get(0);
        int maxNo = Integer.valueOf(maxNoVoteItem.getNo());

        List<VoteItem> voteItems = new ArrayList<VoteItem>();
        return queryByRandom(vote, voteItems, visize, maxNo);
    }

    /** 统计问卷项总数 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public long countVoteItem() {
        return abstractDao.queryAllSize();
    }

    /**
     * 保存问卷项<br />
     * 如果存在id则更新，如果不存在id则新增
     * 
     * @param voteItem
     *            问卷项
     * @return
     * @throws VoteItemContextErrorException
     *             如果校验不通过.抛出VoteItemContextErrorException异常
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public <T extends VoteBaseItemImpl> T save(T voteItem) throws VoteItemContextErrorException {
        if (StringUtils.isBlank(voteItem.getId())) { // 新建
            voteItem.setNo(-1); // 新建时这里设置成-1,是为了让自增长编号不要断号过多
        }
        voteItem.checkVoteItemContext();
        if (StringUtils.isBlank(voteItem.getId())) { // 新建
            voteItem.setNo(incrementer.getIncrementer().nextIntValue(VoteBaseItemService.VOTEBASEITEM_INCREMENTER_TYPE));
            voteItem.setCreateDate(new Date());
        }
        abstractDao.save(voteItem);
        return voteItem;
    }

    /**
     * 递归随机查询问卷项<br/>
     * 地方法为递归方法
     * 
     * @param vote
     *            问卷项
     * @param voteItems
     *            问卷项集合
     * @param visize
     *            随机生成问卷项的数目
     * @param maxNo
     *            最大生成的问卷项数目
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    private List<VoteItem> queryByRandom(Vote vote, List<VoteItem> voteItems, int visize, int maxNo) {
        int size = voteItems.size();
        if (size == visize) { // 达到目标则返回对应数目的问卷项
            return voteItems;
        }

        maxNo++; // 这里加一,是因为下面random数字时,不会包含最大值本身,所以加一,用来包含最大值本身
        int diff = visize - size;
        List<Integer> nos = new ArrayList<Integer>();
        for (int i = 0; i < diff; i++) {
            nos.add(Integer.valueOf(RandomUtil.randomInteger(1, maxNo + 1)));
        }
        maxNo--; // 执行完毕后,减去一

        StringBuilder hql = new StringBuilder();
        hql.append(" from ").append(VoteBaseItemImpl.class.getName()).append(" vi ");
        hql.append(" where ").append(" vi.no in ( ").append(StringUtils.join(nos, ',')).append(" ) ");
        logger.debug(hql.toString());
        List<VoteBaseItemImpl> queryByHql = abstractDao.queryByHql(hql);
        for (VoteBaseItemImpl voteBaseItemImpl : queryByHql) {
            VoteItem vi = new VoteItem();
            if (CollectionUtils.isEmpty(voteItems)) {
                vi.setNo(1);
            } else {
                vi.setNo(voteItems.get(voteItems.size() - 1).getNo() + 1);
            }
            vi.setVote(vote);
            vi.setVoteBaseItem(voteBaseItemImpl);
//            voteItemService.create(vi);
            voteItems.add(vi);
        }

        return queryByRandom(vote, voteItems, visize, maxNo);
    }
}
