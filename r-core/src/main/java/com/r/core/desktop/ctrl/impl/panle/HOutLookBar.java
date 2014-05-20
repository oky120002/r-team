package com.r.core.desktop.ctrl.impl.panle;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JButton;

import com.r.core.desktop.ctrl.HBasePanel;

/**
 * 
  * <类型2000版QQ抽屉窗口>
  * 
  * @author  rain
  * @version  [版本号, 2013-2-28]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public class HOutLookBar extends HBasePanel implements ActionListener {
    private static final long serialVersionUID = 1295195233247106295L;
    
    /**顶部面板：包含抽屉窗口顶部显示的按钮*/
    private HBasePanel topPanel = new HBasePanel(new GridLayout(1, 1));
    
    /**底部面板：包含抽屉窗口底部显示的按钮*/
    private HBasePanel bottomPanel = new HBasePanel(new GridLayout(1, 1));
    
    /**装抽屉的LinkedHashMap*/
    private Map<String, BarInfo> bars = new LinkedHashMap<String, BarInfo>();
    
    /**当前显示中的抽屉 (从零开始的索引)*/
    private int visibleBar = 0;
    
    /**当前显示的抽屉*/
    private HBasePanel visiblePanel = null;
    
    /**创建一个抽屉窗口*/
    public HOutLookBar() {
        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }
    
    /**
     * 
      * <向抽屉窗口添加抽屉>
      * @param name 抽屉名称
      * @param panel 抽屉内容
      * 
      * @author  rain
     */
    public void addBar(String name, HBasePanel panel) {
        BarInfo barInfo = new BarInfo(name, panel);
        barInfo.getButton().addActionListener(this);
        addEventShow(panel);
        this.bars.put(name, barInfo);
        render();
    }
    
    /**
       * Adds the specified component to the JOutlookBar and sets the bar's name
       * 
       * @param name      The name of the outlook bar
       * @param icon      An icon to display in the outlook bar
       * @param componenet   The component to add to the bar
       */
    public void addBar(String name, Icon icon, HBasePanel panel) {
        BarInfo barInfo = new BarInfo(name, icon, panel);
        barInfo.getButton().addActionListener(this);
        addEventShow(panel);
        this.bars.put(name, barInfo);
        render();
    }
    
    /**
       * Removes the specified bar from the JOutlookBar
       * 
       * @param name The name of the bar to remove
       */
    public void removeBar(String name) {
        removeEventShow(this.bars.get(name).getPanel());
        this.bars.remove(name);
        render();
    }
    
    /**
       * Returns the index of the currently visible bar (zero-based)
       * 
       * @return The index of the currently visible bar
       */
    public int getVisibleBar() {
        return this.visibleBar;
    }
    
    /**
       * Programmatically sets the currently visible bar; the visible bar
       * index must be in the range of 0 to size() - 1
       * 
       * @param visibleBar   The zero-based index of the component to make visible
       */
    public void setVisibleBar(int visibleBar) {
        if (visibleBar > 0 && visibleBar < this.bars.size() - 1) {
            this.visibleBar = visibleBar;
            render();
        }
    }
    
    /**重构抽屉窗口,重新建立抽屉窗口上部和下部按钮,同事显示当前抽屉的内容面板*/
    public void render() {
        // Compute how many bars we are going to have where
        int totalBars = this.bars.size();
        int topBars = this.visibleBar + 1;
        int bottomBars = totalBars - topBars;
        
        // Get an iterator to walk through out bars with
        Iterator<String> itr = this.bars.keySet().iterator();
        
        // Render the top bars: remove all components, reset the GridLayout to
        // hold to correct number of bars, add the bars, and "validate" it to
        // cause it to re-layout its components
        this.topPanel.removeAll();
        GridLayout topLayout = (GridLayout) this.topPanel.getLayout();
        topLayout.setRows(topBars);
        BarInfo barInfo = null;
        for (int i = 0; i < topBars; i++) {
            String barName = (String) itr.next();
            barInfo = (BarInfo) this.bars.get(barName);
            this.topPanel.add(barInfo.getButton());
        }
        this.topPanel.validate();
        
        // Render the center component: remove the current component (if there
        // is one) and then put the visible component in the center of this panel
        if (this.visiblePanel != null) {
            this.remove(this.visiblePanel);
        }
        this.visiblePanel = barInfo.getPanel();
        this.add(visiblePanel, BorderLayout.CENTER);
        
        // Render the bottom bars: remove all components, reset the GridLayout to
        // hold to correct number of bars, add the bars, and "validate" it to
        // cause it to re-layout its components
        this.bottomPanel.removeAll();
        GridLayout bottomLayout = (GridLayout) this.bottomPanel.getLayout();
        bottomLayout.setRows(bottomBars);
        for (int i = 0; i < bottomBars; i++) {
            String barName = (String) itr.next();
            barInfo = (BarInfo) this.bars.get(barName);
            this.bottomPanel.add(barInfo.getButton());
        }
        this.bottomPanel.validate();
        
        // Validate all of our components: cause this container to re-layout its subcomponents
        this.validate();
    }
    
    /**抽屉被选中时执行的事件*/
    public void actionPerformed(ActionEvent e) {
        int currentBar = 0;
        for (Iterator<String> i = this.bars.keySet().iterator(); i.hasNext();) {
            String barName = (String) i.next();
            BarInfo barInfo = (BarInfo) this.bars.get(barName);
            if (barInfo.getButton() == e.getSource()) {
                // 找到被选中的按钮
                this.visibleBar = currentBar;
                render();
                return;
            }
            currentBar++;
        }
    }
    
    //    /**
    //       * Debug, dummy method
    //       */
    //    public static BasePanel getDummyPanel(String name) {
    //        BasePanel panel = new BasePanel(new BorderLayout());
    //        panel.add(new JLabel(name, JLabel.CENTER));
    //        return panel;
    //    }
    //    
    //    /**
    //       * Debug test...
    //       */
    //    public static void main(String[] args) {
    //        try {
    //            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    //        } catch (Exception ex) {
    //        }
    //        JFrame frame = new JFrame("JOutlookBar Test");
    //        JOutLookBar outlookBar = new JOutLookBar();
    //        outlookBar.addBar("One", getDummyPanel("One"));
    //        outlookBar.addBar("Two", getDummyPanel("Two"));
    //        outlookBar.addBar("Three", getDummyPanel("Three"));
    //        outlookBar.addBar("Four", getDummyPanel("Four"));
    //        outlookBar.addBar("Five", getDummyPanel("Five"));
    //        outlookBar.setVisibleBar(2);
    //        frame.getContentPane().add(outlookBar);
    //        frame.setSize(200, 600);
    //        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    //        frame.setLocation(d.width / 2 - 400, d.height / 2 - 300);
    //        
    //        frame.setVisible(true);
    //        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //    }
    
    /**
     * 
      * <抽屉信息> </br>
      * <此类保持着每个抽屉自己的信息>
      * 
      * @version  [版本号, 2013-3-4]
     */
    class BarInfo {
        /**抽屉上的标题*/
        private String name;
        
        /**抽屉上的按钮*/
        private JButton button;
        
        /**抽屉对应的内容*/
        private HBasePanel panel;
        
        /**
         * 创建一个抽屉
         * 
         * @param name 抽屉标题
         * @param panel 抽屉的内容面板
         */
        public BarInfo(String name, HBasePanel panel) {
            this.name = name;
            this.panel = panel;
            this.button = new JButton(name);
        }
        
        /**
         * 创建一个抽屉
         * 
         * @param name 抽屉上的标题
         * @param icon 抽屉图标
         * @param panel 抽屉的内容面板
         */
        public BarInfo(String name, Icon icon, HBasePanel panel) {
            this.name = name;
            this.panel = panel;
            this.button = new JButton(name, icon);
        }
        
        /**获得抽屉上的标题*/
        public String getName() {
            return this.name;
        }
        
        /**获得抽屉上的按钮*/
        public JButton getButton() {
            return this.button;
        }
        
        /**获得抽屉里面的内容面板*/
        public HBasePanel getPanel() {
            return this.panel;
        }
    }
}