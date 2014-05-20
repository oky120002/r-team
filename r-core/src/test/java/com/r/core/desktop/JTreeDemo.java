/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-2-27
 * <修改描述:>
 */
package com.r.core.desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/** 
* Description: File Explorer <BR> 
* Copyright: Copyright (c) 2001<BR> 
* Email to <a href=mailto:SunKingEMail@163.net>Sunking</a><BR> 
* @author Sunking 
* @version 1.0 
*/
@SuppressWarnings({ "rawtypes", "unchecked", "deprecation", "serial" })
public class JTreeDemo extends JFrame implements TreeExpansionListener,
        TreeSelectionListener, ActionListener {
    private static final long serialVersionUID = -6277928934060989928L;
    
    final JTree tree = new JTree(createTreeModel());
    
    final JPanel pSub = new JPanel(new GridLayout(100, 3));
    
    final JSplitPane split;
    
    final JPanel statusbar = new JPanel(new BorderLayout());
    
    final JLabel lbStatus = new JLabel(" ");
    
    public JTreeDemo() {
        Dimension dimension = getToolkit().getScreenSize();
        int i = (dimension.width - 640) / 2;
        int j = (dimension.height - 480) / 2;
        setBounds(i, j, 640, 480);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowevent) {
                System.exit(0);
            }
        });
        
        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(
                tree), new JScrollPane(pSub));
        split.setLastDividerLocation(200);
        getContentPane().add(split);
        pSub.setBackground(Color.white);
        tree.addTreeExpansionListener(this);
        tree.addTreeSelectionListener(this);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ex) {
        }
        getContentPane().add(statusbar, BorderLayout.SOUTH);
        statusbar.add(lbStatus, BorderLayout.WEST);
    }
    
    public void treeCollapsed(TreeExpansionEvent e) {
    }
    
    public void treeExpanded(TreeExpansionEvent e) {
        if (tree.getLastSelectedPathComponent() == null)
            return;
        if (tree.getLastSelectedPathComponent()
                .toString()
                .trim()
                .equals("Local"))
            return;
        TreePath path = e.getPath();
        FileNode node = (FileNode) path.getLastPathComponent();
        if (!node.isExplored()) {
            DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
            node.explore();
            model.nodeStructureChanged(node);
        }
    }
    
    public void valueChanged(TreeSelectionEvent e) {
        try {
            if (tree.getLastSelectedPathComponent() == null)
                return;
            pSub.removeAll();
            if (tree.getLastSelectedPathComponent()
                    .toString()
                    .trim()
                    .equals("Local")) {
                File roots[] = File.listRoots();
                for (int i = 1; i < roots.length; i++) {
                    String DiskName = roots[i].toString();
                    DiskName = DiskName.substring(0, DiskName.indexOf(":") + 1);
                    addButton(DiskName, "");
                }
            } else {
                Object[] path = e.getPath().getPath();
                String ss = "";
                for (int i = 1; i < path.length; i++)
                    ss += File.separator + path[i].toString();
                File f = new File(ss.substring(1));
                lbStatus.setText(f.toString());
                String[] list = f.list();
                Vector vFile = new Vector(), vDir = new Vector();
                for (int i = 0; i < list.length; i++) {
                    if ((new File(ss + File.separator + list[i])).isDirectory())
                        vDir.addElement(list[i]);
                    else
                        vFile.addElement(list[i]);
                }
                sortElements(vFile);
                sortElements(vDir);
                for (int i = 0; i < vDir.size(); i++)
                    addButton((String) (vDir.elementAt(i)), ss);
                for (int i = 0; i < vFile.size(); i++)
                    addButton((String) (vFile.elementAt(i)), ss);
            }
            pSub.doLayout();
            pSub.repaint();
        } catch (Exception ee) {
        }
    }
    
    public void sortElements(Vector v) {
        for (int i = 0; i < v.size(); i++) {
            int k = i;
            for (int j = i + 1; j < v.size(); j++)
                if (((String) (v.elementAt(j))).toLowerCase()
                        .compareTo(((String) (v.elementAt(k))).toLowerCase()) < 0)
                    k = j;
            if (k != i)
                swap(k, i, v);
        }
    }
    
    private void swap(int loc1, int loc2, Vector v) {
        Object tmp = v.elementAt(loc1);
        v.setElementAt(v.elementAt(loc2), loc1);
        v.setElementAt(tmp, loc2);
    }
    
    private void addButton(String fileName, String filePath) {
        JButton btt = new JButton(fileName);
        btt.setBorder(null);
        btt.setHorizontalAlignment(SwingConstants.LEFT);
        btt.setBackground(Color.white);
        if ((new File(filePath + File.separator + fileName)).isDirectory())
            btt.setIcon(UIManager.getIcon("Tree.closedIcon"));
        else
            btt.setIcon(UIManager.getIcon("Tree.leafIcon"));
        pSub.add(btt);
        btt.addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e) {
        try {
            TreePath p = tree.getLeadSelectionPath();
            String text = ((JButton) (e.getSource())).getText();
            Object[] path = p.getPath();
            String ss = "";
            for (int i = 1; i < path.length; i++)
                ss += File.separator + path[i].toString();
            ss = ss.substring(1);
            File f = new File(ss + File.separator + text);
            lbStatus.setText(f.toString());
            if (f.isDirectory()) {
                int index = tree.getRowForPath(p);
                tree.expandRow(index);
                while (!(tree.getLastSelectedPathComponent().toString().trim().equals(text)))
                    tree.setSelectionRow(index++);
                tree.expandRow(index - 1);
            } else {
                String postfix = text.toUpperCase();
                if (postfix.indexOf(".TXT") != -1
                        || postfix.indexOf(".JAVA") != -1
                        || postfix.indexOf(".HTM") != -1
                        || postfix.indexOf(".LOG") != -1)
                    Runtime.getRuntime().exec("NotePad.exe " + ss
                            + File.separator + text);
            }
        } catch (Exception ee) {
        }
    }
    
    private DefaultMutableTreeNode createTreeModel() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Local");
        File[] rootPath = File.listRoots();
        for (int i = 1; i < rootPath.length; i++) {
            FileNode Node = new FileNode(rootPath[i]);
            Node.explore();
            rootNode.add(Node);
        }
        return rootNode;
    }
    
    public static void main(String args[]) {
        new JTreeDemo().show();
    }
    
    class FileNode extends javax.swing.tree.DefaultMutableTreeNode {
        private boolean explored = false;
        
        public FileNode(File file) {
            setUserObject(file);
        }
        
        public boolean getAllowsChildren() {
            return isDirectory();
        }
        
        public boolean isLeaf() {
            return !isDirectory();
        }
        
        public File getFile() {
            return (File) getUserObject();
        }
        
        public boolean isExplored() {
            return explored;
        }
        
        public boolean isDirectory() {
            return getFile().isDirectory();
        }
        
        public String toString() {
            File file = (File) getUserObject();
            String filename = file.toString();
            int index = filename.lastIndexOf(File.separator);
            return (index != -1 && index != filename.length() - 1) ? filename.substring(index + 1)
                    : filename;
        }
        
        public void explore() {
            if (!isDirectory())
                return;
            if (!isExplored()) {
                File file = getFile();
                File[] children = file.listFiles();
                for (int i = 0; i < children.length; ++i) {
                    File f = children[i];
                    if (f.isDirectory())
                        add(new FileNode(children[i]));
                }
                explored = true;
            }
        }
    }
    
}