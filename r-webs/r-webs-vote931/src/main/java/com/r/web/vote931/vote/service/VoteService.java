/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.web.vote931.vote.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.web.vote931.support.abs.AbstractDao;
import com.r.web.vote931.support.abs.AbstractService;
import com.r.web.vote931.support.bean.KeyValue;
import com.r.web.vote931.vote.dao.CompletionVoteItemDao;
import com.r.web.vote931.vote.dao.MultipleOptionVoteItemDao;
import com.r.web.vote931.vote.dao.SingleOptionVoteItemDao;
import com.r.web.vote931.vote.dao.YesOrNoVoteItemDao;
import com.r.web.vote931.vote.model.AbsVoteItem;

/**
 * 用户Service<br />
 * 
 * @author rain
 */
@Service("vote.service.vote")
public class VoteService extends AbstractService {

	@Resource(name = "mysqlSequence")
	private DataFieldMaxValueIncrementer incrementer;

	@Resource(name = "vote.dao.completionVoteItem")
	private CompletionVoteItemDao completionVoteItemDao;

	@Resource(name = "vote.dao.multipleOptionVoteItem")
	private MultipleOptionVoteItemDao multipleOptionVoteItemDao;

	@Resource(name = "vote.dao.singleOptionVoteItem")
	private SingleOptionVoteItemDao singleOptionVoteItemDao;

	@Resource(name = "vote.dao.yesOrNoVoteItem")
	private YesOrNoVoteItemDao yesOrNoVoteItemDao;

	@Resource(name = "support.dao.default")
	private AbstractDao<AbsVoteItem> abstractDao;

	/** 查询所有数据 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
	public List<AbsVoteItem> queryAll() {
		return abstractDao.queryAll();
	}

	/** 创建问卷项 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public <T extends AbsVoteItem> T create(T voteItem) {
		voteItem.setNo(incrementer.nextStringValue());
		voteItem.checkVoteItemContext();
		abstractDao.create(voteItem);
		return voteItem;
	}

	/** 删除所有问卷项数据 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public int deleteAll() {
		return abstractDao.deleteAll();
	}

	/**
	 * 根据ID查找问卷项
	 * 
	 * @param id
	 *            问卷项ID
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
	public AbsVoteItem findById(String id) {
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
	public AbsVoteItem findByNo(String no) {
		// 这里可以使用AbsVoteItem的确定子类来确定性的查询.如果使用AbsVoteItem实体来查询.则会把所有的子类都join进来后再进行查询.
		List<AbsVoteItem> queryByHql = abstractDao.queryByHql(" from " + AbsVoteItem.class.getName() + " where no = :no", KeyValue.kv("no", no));
		if (CollectionUtils.isEmpty(queryByHql)) {
			return null;
		} else {
			return queryByHql.get(0);
		}
	}
}
