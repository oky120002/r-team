/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-2-27
 * <修改描述:>
 */
package com.r.core.desktop;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class JListDemo {
    @SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
    public static void main(String args[]) {
        JFrame f = new JFrame("JList");

        try {
            // 这里直接使用类路径字符串,而不是用com.sun.java.swing.plaf.windows.WindowsLookAndFeel.class.getNamt().是因为,在此虚拟机里面可能不存在
            // com.sun.java.swing.plaf.windows.WindowsLookAndFeel类,那么就通不过编译
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(f); // 注意
        } catch (Exception ex) {
        }
        
        
        Container contentPane = f.getContentPane();
        contentPane.setLayout(new GridLayout(1, 2));
        String[] s = { "美国", "日本", "大陆", "英国", "法国" };
        Vector v = new Vector();
        v.addElement("Nokia 8850");
        v.addElement("Nokia 8250");
        v.addElement("Motorola V8088");
        v.addElement("Motorola V3688x");
        v.addElement("Panasonic GD92");
        v.addElement("其他");
        
        
        JList list1 = new JList(s);
        list1.setBorder(BorderFactory.createTitledBorder("您最喜欢到哪个国家玩呢？"));
        
        JList list2 = new JList(v);
        list2.setBorder(BorderFactory.createTitledBorder("您最喜欢哪一种手机？"));
        
        contentPane.add(new JScrollPane(list1));
        contentPane.add(new JScrollPane(list2));
        f.pack();
        f.show();
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}