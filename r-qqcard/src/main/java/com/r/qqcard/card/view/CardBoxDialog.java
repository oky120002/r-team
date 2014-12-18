/**
 * 
 */
package com.r.qqcard.card.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import org.apache.commons.collections.CollectionUtils;

import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseDialog;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.TaskUtil;
import com.r.qqcard.card.bean.CardBoxListItem;
import com.r.qqcard.card.model.CardBox;
import com.r.qqcard.notify.context.NotifyContext;
import com.r.qqcard.notify.handler.Event;

/**
 * 卡片箱子对话框
 * 
 * @author rain
 *
 */
public class CardBoxDialog extends HBaseDialog implements MouseListener, ActionListener {
    private static final long serialVersionUID = 5268486548680474881L;
    private static final Logger logger = LoggerFactory.getLogger(CardBoxDialog.class);
    /** 换卡箱列表数据源 */
    private DefaultListModel<CardBoxListItem> cardBoxChangeListModel = new DefaultListModel<CardBoxListItem>();
    /** 储藏箱列表数据源 */
    private DefaultListModel<CardBoxListItem> cardBoxStoreListModel = new DefaultListModel<CardBoxListItem>();

    /** 消息容器 */
    private NotifyContext notify;
    /** 换卡箱列表 */
    private JList<CardBoxListItem> cardBoxChangeList = new JList<CardBoxListItem>();
    /** 储藏箱列表 */
    private JList<CardBoxListItem> cardBoxStoreList = new JList<CardBoxListItem>();

    /** 一键抽卡 */
    private JButton oneRandomAllCardButton = new JButton("一键抽卡");
    /** 整理卡片.尽量把价值超过10金币的卡片放到保险箱 */
    private JButton tidyCardButton = new JButton("整理卡片");

    /** 构造一个卡片箱子 */
    public CardBoxDialog(NotifyContext notify, String title) {
        super((JFrame) null, title, false);
        this.notify = notify;

        // 窗口属性
        this.setSize(450, 350); // 调整窗口大小
        this.setLocationRelativeTo(null); // 全屏居中
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE); // 关闭对话框,则隐藏窗口
        this.setResizable(false);// 不能调整对话框大小
        this.setLayout(new BorderLayout()); // 设置页面面板布局类型

        // 子控件属性
        this.cardBoxChangeList.setBorder(BorderFactory.createTitledBorder("换卡箱"));
        this.cardBoxChangeList.setModel(this.cardBoxChangeListModel);
        this.cardBoxChangeList.setSize(200, 300);
        this.cardBoxChangeList.addMouseListener(this);
        this.cardBoxStoreList.setBorder(BorderFactory.createTitledBorder("保险箱"));
        this.cardBoxStoreList.setModel(this.cardBoxStoreListModel);
        this.cardBoxStoreList.setSize(200, 300);
        this.cardBoxStoreList.addMouseListener(this);
        this.oneRandomAllCardButton.addActionListener(this);
        this.tidyCardButton.setToolTipText("尽最大可能把金币超过10的卡片放到保险箱");
        this.tidyCardButton.addActionListener(this);

        // 中部卡箱
        HBaseBox box = HBaseBox.createHorizontalBaseBox();
        add(box, BorderLayout.CENTER);
        box.adds(new JScrollPane(this.cardBoxChangeList), HBaseBox.EmptyHorizontal, new JScrollPane(this.cardBoxStoreList));

        // 下部命令区域
        HBaseBox bottomBox = HBaseBox.createHorizontalBaseBox();
        add(bottomBox, BorderLayout.SOUTH);
        bottomBox.adds(this.oneRandomAllCardButton, HBaseBox.EmptyHorizontal, this.tidyCardButton);
    }

    /**
     * 初始化卡箱信息
     * 
     * @param cardboxChange
     *            换卡箱
     * @param cardboxStore
     *            储藏箱
     */
    public void initCardBox(Collection<CardBox> cardboxs) {
        this.cardBoxChangeListModel.clear();
        this.cardBoxStoreListModel.clear();

        addCardBox(cardboxs);
    }

    /**
     * 交换卡片<br/>
     * 可以通过cardBox和oldSlot推算出原来卡片所在位置
     * 
     * @param oldSlot
     *            卡片在原来卡箱所在位置
     * @param cardBox
     *            卡片交换后的卡片箱子信息
     */
    public void changeCardBox(int oldSlot, CardBox cardBox) {
        CardBoxListItem item = new CardBoxListItem(cardBox);
        // 先移除此卡箱项,再添加到另外的卡箱中
        if (0 == cardBox.getCardBoxType()) { // 换卡箱事件
            this.cardBoxChangeListModel.addElement(item);
            this.cardBoxStoreListModel.removeElement(item);
        } else { // 保险箱事件
            this.cardBoxStoreListModel.addElement(item);
            this.cardBoxChangeListModel.removeElement(item);
        }
    }

    /** 增加卡片箱子卡片 */
    public void addCardBox(Collection<CardBox> cardBoxs) {
        if (CollectionUtils.isNotEmpty(cardBoxs)) {
            for (CardBox cardBox : cardBoxs) {
                CardBoxListItem item = new CardBoxListItem(cardBox);
                if (cardBox.getCardBoxType() == 0) {
                    this.cardBoxChangeListModel.addElement(item);
                } else if (cardBox.getCardBoxType() == 1) {
                    this.cardBoxStoreListModel.addElement(item);
                } else {
                    logger.info("从卡片箱子中添加卡片时遇见未知的卡片箱子类型,请同步卡片数据");
                    logger.error("从卡片箱子中添加卡片时遇见未知的卡片箱子类型,id:{}", cardBox.getId());
                }
                TaskUtil.sleep(10);
            }
        }
    }

    /** 移除卡片箱子卡片 */
    public void removeCardBox(Collection<CardBox> cardBoxs) {
        if (CollectionUtils.isNotEmpty(cardBoxs)) {
            for (CardBox cardBox : cardBoxs) {
                if (cardBox.getCardBoxType() == 0) {
                    this.cardBoxChangeListModel.removeElement(new CardBoxListItem(cardBox));
                } else if (cardBox.getCardBoxType() == 1) {
                    this.cardBoxStoreListModel.removeElement(new CardBoxListItem(cardBox));
                } else {
                    logger.info("从卡片箱子中移除卡片时遇见未知的卡片箱子类型,请同步卡片数据");
                    logger.error("从卡片箱子中移除卡片时遇见未知的卡片箱子类型,id:{}", cardBox.getId());
                }
                TaskUtil.sleep(5);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int clickCount = e.getClickCount();
        Object source = e.getSource();
        if (this.cardBoxChangeList.equals(source)) { // 换卡箱
            CardBoxListItem item = this.cardBoxChangeList.getSelectedValue();
            if (item != null) {
                this.notify.notifyEvent(Event.cardimage$显示卡片图片, Integer.valueOf(item.getCardid()));
                if (clickCount >= 2) { // 双击
                    this.notify.notifyEvent(Event.box$交换卡片, item.getCardBoxid());
                }
            }
        }
        if (this.cardBoxStoreList.equals(source)) { // 保险箱
            CardBoxListItem item = this.cardBoxStoreList.getSelectedValue();
            if (item != null) {
                this.notify.notifyEvent(Event.cardimage$显示卡片图片, Integer.valueOf(item.getCardid()));
                if (clickCount >= 2) { // 双击
                    this.notify.notifyEvent(Event.box$交换卡片, item.getCardBoxid());
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(this.oneRandomAllCardButton)) { // 一键抽卡
            this.notify.notifyEvent(Event.box$一键抽卡);
        }
        if (source.equals(this.tidyCardButton)) { // 整理卡片
            this.notify.notifyEvent(Event.box$整理卡片);
        }
    }
}
