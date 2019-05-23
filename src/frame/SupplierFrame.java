package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.table.WebTable;

import panel.SupInvitePanel;
import panel.WaitoImp;

import javax.swing.JMenuItem;
import java.awt.CardLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

public class SupplierFrame extends JFrame {

	private JPanel contentPane;
	WebTable supiTable;
	JScrollPane iscrollPane;

	/**
	 * Create the frame.
	 */
	public SupplierFrame(String supid) {

		setIconImage(Toolkit.getDefaultToolkit().getImage("./lib/icon/icon.jpg"));
		setTitle("WOMS - 供应商");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		
		WebMenuBar menuBar = new WebMenuBar();
		menuBar.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		setJMenuBar(menuBar);
		
		WebMenu menu_invite = new WebMenu("产品主页");		
		menu_invite.setText("新的要约");
		menu_invite.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		menuBar.add(menu_invite);
		
		
		WebMenu menu_waitogo = new WebMenu("我的购物车");
		menu_waitogo.setText("待发货订单");
		menu_waitogo.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		menuBar.add(menu_waitogo);
		
		WebMenu menu_sended = new WebMenu("我的订单");
		menu_sended.setText("已发货订单");
		menu_sended.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		menuBar.add(menu_sended);
		
		JMenuItem menuItem_onway = new JMenuItem("在途订单");
		menuItem_onway.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		menu_sended.add(menuItem_onway);
		
		JMenuItem menuItem_received = new JMenuItem("已接收订单");
		menuItem_received.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		menu_sended.add(menuItem_received);
		
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
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel slogan = new JLabel("");
		slogan.setBackground(Color.WHITE);
		slogan.setIcon(new ImageIcon("./lib/icon/slogan.jpg"));
		contentPane.add(slogan, BorderLayout.NORTH);
		
		JPanel cardPanel = new JPanel();
		contentPane.add(cardPanel, BorderLayout.CENTER);
		CardLayout clt =  new CardLayout(0, 0);
		cardPanel.setLayout(clt);
		
		JPanel invitePanel = new JPanel();
		cardPanel.add(invitePanel, "新的要约");
		invitePanel.setLayout(new BorderLayout(0, 0));
		
		JPanel iPanel = SupInvitePanel.supInvitePanel(supid, invitePanel);
		invitePanel.add(iPanel, BorderLayout.CENTER);


		JPanel waitogoPanel = new JPanel();
		cardPanel.add(waitogoPanel, "待发货订单");
		
		JPanel onwayPanel = new JPanel();
		cardPanel.add(onwayPanel, "在途订单");
		
		JPanel receivedPanel = new JPanel();
		cardPanel.add(receivedPanel, "已接收订单");
		
		menu_invite.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clt.show(cardPanel, "新的要约");
			}
		});
		
		menu_waitogo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clt.show(cardPanel, "待发货订单");
			}
		});
	}

}
