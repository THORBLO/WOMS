package frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.alee.laf.desktoppane.WebInternalFrame;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.tree.WebTree;

import java.awt.Color;
import java.awt.Font;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.Toolkit;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import java.awt.SystemColor;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Mframe extends WebFrame {

	private void createNodes(DefaultMutableTreeNode top) {
	    DefaultMutableTreeNode category = null;
	    DefaultMutableTreeNode leaf = null;
	    
	    category = new DefaultMutableTreeNode("订单");
	    top.add(category);
	    
	    //original Tutorial
	    leaf = new DefaultMutableTreeNode("今日订单");
	    category.add(leaf);
	    
	    //Tutorial Continued
	    leaf = new DefaultMutableTreeNode("历史订单");
	    category.add(leaf);
	    
	 
	    category = new DefaultMutableTreeNode("补货");
	    top.add(category);
	 
	    //VM
	    leaf = new DefaultMutableTreeNode("亟待补货");
	    category.add(leaf);
	 
	    //Language Spec
	    leaf = new DefaultMutableTreeNode("要约反馈");
	    category.add(leaf);
	}
	
	
	
	private JPanel contentPane;
	private JTable table;
	
	
	
	
	/**
	 * Create the frame.
	 */
	public Mframe(String mName) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("F:\\InstallPackages\\logo\\icon.jpg"));
		setTitle("WOMS - 管理员");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		
		WebMenuBar menuBar = new WebMenuBar();
		setJMenuBar(menuBar);
		
		WebMenu mnuser = new WebMenu("用户");
		mnuser.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		menuBar.add(mnuser);
		
		WebMenuItem menuItem = new WebMenuItem("门店");
		menuItem.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		mnuser.add(menuItem);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		WebTree tree;
		DefaultMutableTreeNode top =
		        new DefaultMutableTreeNode("WOMS");
		createNodes(top);
		tree = new WebTree(top);

		tree.setFont(new Font("微软雅黑", Font.PLAIN, 15));

		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(tree, BorderLayout.WEST);
		
		JLabel label = new JLabel("");
		contentPane.add(label, BorderLayout.NORTH);
		label.setIcon(new ImageIcon("F:\\InstallPackages\\logo\\slogan.jpg"));
		
		WebTabbedPane tabbedPane = new WebTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		WebPanel nordPanel = new WebPanel();
		tabbedPane.addTab("今日订单", null, nordPanel, null);
		
		
		table = new WebTable();
		nordPanel.add(table);
		
		JPopupMenu nordPopMenu = new JPopupMenu();
		addPopup(table, nordPopMenu);
		
		JMenuItem menuItem_close = new JMenuItem("关闭当前页面");
		menuItem_close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.remove(nordPanel);
			}
		});
		nordPopMenu.add(menuItem_close);
		
		//tabbedPane.remove(panel);
		
		JPanel pordPanel = new JPanel();
		tabbedPane.addTab("历史订单", null, pordPanel, null);
		
		
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
