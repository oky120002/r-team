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

import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseDialog;
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

        // 中部卡箱
        HBaseBox box = HBaseBox.createHorizontalBaseBox();
        add(box, BorderLayout.CENTER);
        box.adds(new JScrollPane(this.cardBoxChangeList), HBaseBox.EmptyHorizontal, new JScrollPane(this.cardBoxStoreList));

        // 下部命令区域
        HBaseBox bottomBox = HBaseBox.createHorizontalBaseBox();
        add(bottomBox, BorderLayout.SOUTH);
        bottomBox.adds(this.oneRandomAllCardButton);
    }

    /**
     * 初始化卡箱信息
     * 
     * @param cardboxChange
     *            换卡箱
     * @param cardboxStore
     *            储藏箱
     */
    public void initCardBox(Collection<CardBox> cardboxChange, Collection<CardBox> cardboxStore) {
        this.cardBoxChangeListModel.clear();
        addCardBoxToChange(cardboxChange);

        this.cardBoxStoreListModel.clear();
        for (CardBox cardBox : cardboxStore) {
            this.cardBoxStoreListModel.addElement(new CardBoxListItem(cardBox));
        }
        this.cardBoxStoreList.repaint();
    }

    /** 交换卡片 */
    public void changeCardBox(CardBoxListItem cardBoxListItem, int destSlot) {
        // 先移除此卡箱项,再添加到另外的卡箱中
        if (0 == cardBoxListItem.getCardBoxType()) { // 换卡箱事件
            this.cardBoxChangeListModel.removeElement(cardBoxListItem);
            cardBoxListItem.setCardBoxType(1);
            cardBoxListItem.setSlot(destSlot);
            this.cardBoxStoreListModel.addElement(cardBoxListItem);
        } else { // 保险箱事件
            this.cardBoxStoreListModel.removeElement(cardBoxListItem);
            cardBoxListItem.setCardBoxType(0);
            cardBoxListItem.setSlot(destSlot);
            this.cardBoxChangeListModel.addElement(cardBoxListItem);
        }
    }

    /** 向换卡箱添加卡片 */
    public void addCardBoxToChange(Collection<CardBox> cardBoxs) {
        for (CardBox cardBox : cardBoxs) {
            this.cardBoxChangeListModel.addElement(new CardBoxListItem(cardBox));
        }
        this.cardBoxChangeList.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int clickCount = e.getClickCount();
        Object source = e.getSource();
        if (clickCount >= 2) { // 双击
            if (this.cardBoxChangeList.equals(source)) { // 换卡箱
                int index = this.cardBoxChangeList.locationToIndex(e.getPoint());
                CardBoxListItem cardBoxListItem = this.cardBoxChangeListModel.getElementAt(index);
                this.notify.notifyEvent(Event.box$交换卡片, cardBoxListItem);
            }
            if (this.cardBoxStoreList.equals(source)) { // 保险箱
                int index = this.cardBoxStoreList.locationToIndex(e.getPoint());
                CardBoxListItem cardBoxListItem = this.cardBoxStoreListModel.getElementAt(index);
                this.notify.notifyEvent(Event.box$交换卡片, cardBoxListItem);
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
    }

}
