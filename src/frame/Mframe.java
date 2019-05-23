package frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.alee.laf.button.WebButton;
import com.alee.laf.desktoppane.WebInternalFrame;
import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.table.WebTableHeaderUI;
import com.alee.laf.tree.WebTree;

import algorithm.OutBoundFlow;
import panel.OrderDetail;
import panel.Replenishment;
import panel.StoreAllTraceback;
import panel.UnprocessedOrder;
import panel.WaitoImp;
import panel.createStaticTable;
import panel.mGatePanel;
import panel.mGetOrder;

import java.awt.Color;
import java.awt.Font;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import java.awt.SystemColor;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.FlowLayout;
import java.awt.GridLayout;

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
	    
	    leaf = new DefaultMutableTreeNode("补货订单");
	    category.add(leaf);
	    
	    category = new DefaultMutableTreeNode("门区");
	    top.add(category);
	    
	    leaf = new DefaultMutableTreeNode("出库门区");
	    category.add(leaf);
	    
	    leaf = new DefaultMutableTreeNode("入库门区");
	    category.add(leaf);
	    
	    category = new DefaultMutableTreeNode("库位");
	    top.add(category);
	    
	    category = new DefaultMutableTreeNode("托盘");
	    top.add(category);
	    
	    category = new DefaultMutableTreeNode("任务");
	    top.add(category);
	    
	}
	
	
	private JPanel contentPane;
	
	/**
	 * Create the frame.
	 */
	public Mframe(String mName) {
		/***logo，菜单，slogan****/
		setIconImage(Toolkit.getDefaultToolkit().getImage("F:\\InstallPackages\\logo\\icon.jpg"));
		setTitle("WOMS - 管理员");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		
		WebMenuBar menuBar = new WebMenuBar();
		setJMenuBar(menuBar);
		
		WebMenu mnuser = new WebMenu("用户");
		mnuser.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		menuBar.add(mnuser);
		
		WebMenuItem menuItem = new WebMenuItem("门店");
		menuItem.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		mnuser.add(menuItem);
		
		WebMenu menu_exit = new WebMenu("退出");
		menu_exit.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		menuBar.add(menu_exit);
		
		JMenuItem menuItem_login = new JMenuItem("返回登录界面");

		menuItem_login.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		menuItem_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = WebOptionPane.showConfirmDialog(contentPane, "确认返回登录界面？", "返回登录界面", WebOptionPane.YES_NO_OPTION);
				if (i == 0) {
					dispose();
					Login login = new Login();
					login.setVisible(true);
				}
			}
		});
		
		menu_exit.add(menuItem_login);
		
		JMenuItem menuItem_close = new JMenuItem("关闭WOMS");
		menuItem_close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = WebOptionPane.showConfirmDialog(contentPane, "确认关闭WOMS系统？", "关闭WOMS", WebOptionPane.YES_NO_OPTION);
				if (i == 0) {
					System.exit(0);
				}
			}
		});
		menuItem_close.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		menu_exit.add(menuItem_close);
		
		
		/********/
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		/****左侧功能列表树****/
		WebTree<DefaultMutableTreeNode> tree;
		DefaultMutableTreeNode top =
		        new DefaultMutableTreeNode("WOMS");
		createNodes(top);
		tree = new WebTree<DefaultMutableTreeNode>(top);
		tree.setBackground(Color.WHITE);
		

		tree.setFont(new Font("微软雅黑", Font.PLAIN, 15));

		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(tree, BorderLayout.WEST);
		/****************/
		
		/*******展示页***********/
		WebTabbedPane tabbedPane = new WebTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.WHITE);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("F:\\InstallPackages\\logo\\slogan.jpg"));
		contentPane.add(label, BorderLayout.NORTH);
		
		
		/******今日订单页*********/
		WebPanel nordPanel = new WebPanel();
		//tabbedPane.addTab("今日订单", null, nordPanel, null);
		
		JPanel content = mGetOrder.getOrder(nordPanel, tabbedPane);
		nordPanel.add(content, BorderLayout.CENTER);
		
		
		/**********************/
		
		/**********亟待补货页*********/
		WebPanel waitoimpPanel = new WebPanel();
		WebPanel wContent = new WebPanel();
		waitoimpPanel.add(wContent);
		
		JPanel wTitle = new JPanel();
		wContent.add(wTitle, BorderLayout.NORTH);
		
		JLabel wLabel = new JLabel("WOMS提醒您：");
		wLabel.setFont(new Font("等线", Font.BOLD, 20));
		GroupLayout gl_wTitle = new GroupLayout(wTitle);
		gl_wTitle.setHorizontalGroup(
			gl_wTitle.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_wTitle.createSequentialGroup()
					.addContainerGap()
					.addComponent(wLabel, GroupLayout.PREFERRED_SIZE, 900, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(251, Short.MAX_VALUE))
		);
		gl_wTitle.setVerticalGroup(
			gl_wTitle.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_wTitle.createSequentialGroup()
					.addContainerGap()
					.addComponent(wLabel, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
					.addContainerGap())
		);
		wTitle.setLayout(gl_wTitle);
		
		//表格设置
		WebTable imptable = WaitoImp.createImptable();
		imptable.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();
		dtcr1.setHorizontalAlignment(WebLabel.CENTER);
		imptable.setRowHeight(25);
		imptable.setDefaultRenderer(Object.class, dtcr1);
		JTableHeader head1 = imptable.getTableHeader();
		head1.setPreferredSize(new Dimension(head1.getWidth(), 35));
		head1.setFont(new Font("微软雅黑", Font.BOLD, 18));
		

		
		
		JScrollPane wscrollPane = new JScrollPane(imptable);
		wContent.add(wscrollPane, BorderLayout.CENTER);
		
		//tabbedPane.addTab("亟待补货", null, waitoimpPanel, null);

		
		/*****************************/
		//tabbedPane.remove(panel);
		
		JPanel pordPanel = new JPanel();
		//tabbedPane.addTab("历史订单", null, pordPanel, null);
		/**********要约反馈***********/
		
		WebPanel tbPanel = new WebPanel();
		WebPanel tbContent = StoreAllTraceback.storeAllTraceback(tbPanel);
		tbPanel.add(tbContent, BorderLayout.CENTER);
		
		
		/************补货订单***********/
		WebPanel rpPanel = new WebPanel();
		WebPanel rpContent = Replenishment.createRPanel(rpPanel);
		rpPanel.add(rpContent, BorderLayout.CENTER);
		/************入库门区***********/
		
		JPanel inGatePanel = new JPanel();
		WebPanel gatePanel = mGatePanel.createGatePanel("B");
		inGatePanel.add(gatePanel, BorderLayout.CENTER);
		//tabbedPane.addTab("进货门区", null, inGatePanel, null);
		
		/**********出库门区*************/
		
		JPanel obGatePanel = new JPanel();
		WebPanel ogatePanel = mGatePanel.createGatePanel("A");
		obGatePanel.add(ogatePanel, BorderLayout.CENTER);
		
		
		WebPanel storagePanel = new WebPanel();
		WebPanel storageTable = createStaticTable.storageTable();
		storagePanel.add(storageTable, BorderLayout.CENTER);
		
		WebPanel packagePanel = new WebPanel();
		WebPanel packageTable = createStaticTable.packageTable();
		packagePanel.add(packageTable, BorderLayout.CENTER);
		
		WebPanel assignPanel = new WebPanel();
		WebPanel assignTable = createStaticTable.assignTable();
		assignPanel.add(assignTable, BorderLayout.CENTER);
		
		/*********************/
		
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent arg0) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (node == null)
					return;
				String object = node.getUserObject().toString();
				//TreeNode user = (TreeNode) object;
				//System.out.println(object);
				if (object == "今日订单") {
					tabbedPane.addTab("今日订单", null, nordPanel, null);
					tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
					//repaint();
				} else if(object == "历史订单") {
					tabbedPane.addTab("历史订单", null, pordPanel, null);
					tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
				} else if(object == "亟待补货") {
					tabbedPane.addTab("亟待补货", null, waitoimpPanel, null);
					tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
				} else if(object == "要约反馈") {
					tabbedPane.addTab("要约反馈", null, tbPanel, null);
					tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
				} else if(object == "补货订单") {
					rpPanel.removeAll();
					rpPanel.invalidate();
					rpPanel.add(Replenishment.createRPanel(rpPanel));
					rpPanel.validate();
					tabbedPane.addTab("补货订单", null, rpPanel, null);
					tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
				} else if(object == "入库门区") {
					inGatePanel.removeAll();
					inGatePanel.invalidate();
					inGatePanel.add(mGatePanel.createGatePanel("B"));
					inGatePanel.validate();
					tabbedPane.addTab("入库门区", null, inGatePanel, null);
					tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
				} else if(object == "出库门区") {
					obGatePanel.removeAll();
					obGatePanel.invalidate();
					obGatePanel.add(mGatePanel.createGatePanel("A"));
					obGatePanel.validate();
					tabbedPane.addTab("出库门区", null, obGatePanel, null);
					tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
				} else if(object == "库位") {
					storagePanel.removeAll();
					storagePanel.invalidate();
					storagePanel.add(createStaticTable.storageTable());
					storagePanel.validate();
					tabbedPane.addTab("库位", null, storagePanel, null);
					tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
				} else if(object == "托盘") {
					packagePanel.removeAll();
					packagePanel.invalidate();
					packagePanel.add(createStaticTable.packageTable());
					packagePanel.validate();
					tabbedPane.addTab("托盘", null, packagePanel, null);
					tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
				} else if(object == "任务") {
					assignPanel.removeAll();
					assignPanel.invalidate();
					assignPanel.add(createStaticTable.assignTable());
					assignPanel.validate();
					tabbedPane.addTab("任务", null, assignPanel, null);
					tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
				}
			}
		});
		
		
		/***
		imptable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {

				int wrowid = imptable.getSelectedRow();
				String wcoid = imptable.getValueAt(wrowid, 0).toString();
				String wconame = imptable.getValueAt(wrowid, 1).toString();
				String wsupid = imptable.getValueAt(wrowid, 2).toString();
				String wsafe = imptable.getValueAt(wrowid, 3).toString();
				String wrest = imptable.getValueAt(wrowid, 4).toString();
				OpenInvite openInvite = new OpenInvite(wcoid, wconame, wsupid, wsafe, wrest);
				openInvite.setVisible(true);
				repaint();
				
			}
		});
		**/
		
		imptable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int wrowid = imptable.getSelectedRow();
				String wcoid = imptable.getValueAt(wrowid, 0).toString();
				String wconame = imptable.getValueAt(wrowid, 1).toString();
				String wsupid = imptable.getValueAt(wrowid, 2).toString();
				String wsafe = imptable.getValueAt(wrowid, 3).toString();
				String wrest = imptable.getValueAt(wrowid, 4).toString();
				//if (true) {
					OpenInvite openInvite = new OpenInvite(wcoid, wconame, wsupid, wsafe, wrest);
					openInvite.setVisible(true);
				//} else {
				//	WebOptionPane.showConfirmDialog(contentPane, "近期已经发送过补货要约，请耐心等待供应商反馈！", "WOMS - 提醒", WebOptionPane.OK_OPTION);
				//}
				
				
				repaint();
			}
		});
		
		
		
		
	}
}
