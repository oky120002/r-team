package com.r.app.taobaoshua.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.r.app.taobaoshua.TaoBaoApp;
import com.r.core.desktop.ctrl.HBaseFrame;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * 淘宝刷 登陆窗口
 * 
 * @author oky
 * 
 */
public class TaoBaoLoginFrame extends HBaseFrame implements ActionListener {
    private static final long serialVersionUID = -7265881767861403989L;
    private static final Logger logger = LoggerFactory.getLogger(TaoBaoLoginFrame.class);
    
    
    public TaoBaoLoginFrame() {
        super("淘宝刷-登陆");
        logger.info("MainDesktop newInstance .............");
        initStyle();
        initComponents();
        initListeners();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    private void initStyle() {
        setSize(new Dimension(350, 220));// 设置窗口大小
        setResizable(false);
        setLocationRelativeTo(null); // 移动到屏幕中部(上下左右)
        try {
            setIconImage(TaoBaoApp.getDefaultIco());
        } catch (Exception e) {
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }



    private void initComponents() {
        
    }



    private void initListeners() {
        
    }
}
