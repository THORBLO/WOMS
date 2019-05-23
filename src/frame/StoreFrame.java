package frame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.mysql.cj.exceptions.RSAException;

import panel.StoreCart;
import panel.StoreInform;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.CardLayout;
import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class StoreFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 * @return 
	 */
	private static boolean checkIF(String store, String coid) {
		boolean result = false;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (
				Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://127.0.0.1:3306/woms?useSSL=false&serverTimezone=UTC",
                        "root", "admin");
				Statement stmt = conn.createStatement();) 
		{
			String sql = "SELECT * FROM cart WHERE store = '" + store + "' AND commodity = '" + coid + "'";
			ResultSet rs = stmt.executeQuery(sql);
			result = rs.next();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	private WebPanel createPanel(WebPanel goodsPanel, String img, String name, String price, String storeName, String co_id) {//创建单独的商品Panel
		WebPanel panel = new WebPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		WebLabel imgLabel = new WebLabel("");
		imgLabel.setIcon(new ImageIcon(img));
		panel.add(imgLabel, BorderLayout.NORTH);
		
		WebPanel inPanel = new WebPanel();
		inPanel.setBackground(Color.WHITE);
		WebLabel nameLabel = new WebLabel("  " + name);
		nameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		nameLabel.setForeground(Color.GRAY);
		inPanel.add(nameLabel, BorderLayout.SOUTH);
		WebLabel priceLabel = new WebLabel("  ¥ " + price);
		inPanel.add(priceLabel, BorderLayout.NORTH);
		priceLabel.setForeground(new Color(255,69,0));
		priceLabel.setFont(new Font("Calibri", Font.BOLD, 24));
		
		panel.add(inPanel, BorderLayout.CENTER);
		
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int i = WebOptionPane.showConfirmDialog(panel, "确认订购" + nameLabel.getText()+"?", "确认订单", WebOptionPane.YES_NO_OPTION);
				if (i == 0) {
					int num = Integer.parseInt(WebOptionPane.showInputDialog(panel, "请输入订购数量："));
					int j = WebOptionPane.showConfirmDialog(panel, "是否立即下单订购？", "立即订购", WebOptionPane.YES_NO_OPTION);
					String ord = getOrd();
					if (j == 0) {
						insertOrd(ord, storeName);
				        insertOC(ord, co_id, num);
				        insertValue(ord);
						WebOptionPane.showMessageDialog(panel, "您已成功下单订购" + num + "件" + name + "，WOMS正在安排货物出仓，请耐心等待！");
					} else {
						if (checkIF(storeName, co_id)) {
							try {
								Class.forName("com.mysql.cj.jdbc.Driver");
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
							
							try (
									Connection conn = DriverManager.getConnection(
					                        "jdbc:mysql://127.0.0.1:3306/woms?useSSL=false&serverTimezone=UTC",
					                        "root", "admin");
									Statement stmt = conn.createStatement();) 
							{
								String sql = "UPDATE cart SET piece = piece +'" + num +"' WHERE store = '" + storeName + "' AND commodity = '" + co_id + "'";
								stmt.execute(sql);

							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else {
							try {
								Class.forName("com.mysql.cj.jdbc.Driver");
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
							
							try (
									Connection conn = DriverManager.getConnection(
					                        "jdbc:mysql://127.0.0.1:3306/woms?useSSL=false&serverTimezone=UTC",
					                        "root", "admin");
									Statement stmt = conn.createStatement();) 
							{
								String sql = "INSERT INTO cart VALUES ('" + storeName + "', '" + co_id + "', " + num + ")";
								stmt.execute(sql);

							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						
						WebOptionPane.showMessageDialog(panel, "已加入购物车，详情请在购物车界面查看！");
					}
				}
			}
		});
		
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				
				panel.setBorder(new LineBorder(new Color(255, 0, 0)));
			}
		});
		
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent arg0) {
				panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
			}
		});
		
		return panel;
	}
	
	public static void insertOC(String ord, String commodity, int num) {//插入单条产品信息关联表
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (
				Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://127.0.0.1:3306/woms?useSSL=false&serverTimezone=UTC",
                        "root", "admin");
				Statement stmt = conn.createStatement();) 
		{
			String sql = "INSERT INTO oc_combined VALUES ('" + ord + "', '" + commodity + "', " + num + ")";
			stmt.execute(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void insertOrd(String ord, String store) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (
				Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://127.0.0.1:3306/woms?useSSL=false&serverTimezone=UTC",
                        "root", "admin");
				Statement stmt = conn.createStatement();) 
		{
			String sql = "INSERT INTO order_info(order_id, store_id) VALUES ('" + ord + "', '" + store + "')";
			stmt.execute(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void insertValue(String ord) {
		double area = 0.00;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (
				Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://127.0.0.1:3306/woms?useSSL=false&serverTimezone=UTC",
                        "root", "admin");
				Statement stmt = conn.createStatement();) 
		{
			String sql = "SELECT A.co_area, B.oc_piece FROM commodity A, oc_combined B where B.oc_order = '" + ord + "' and A.co_id = B.oc_commodity";
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				area += rs.getDouble(1) * rs.getInt(2);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (
				Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://127.0.0.1:3306/woms?useSSL=false&serverTimezone=UTC",
                        "root", "admin");
				Statement stmt = conn.createStatement();) 
		{
			String sql = "UPDATE order_info SET order_area = '" + area + "' WHERE order_id = '" + ord + "'";
			stmt.execute(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static String getOrd() {//生成订单号
		
		SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间 
        sdf.applyPattern("yyMMddHHmmss");  
        Date date = new Date();// 获取当前时间
        String ord = sdf.format(date);
		return ord;		
	}
	
	private void display(WebPanel originalPanel, String storeName) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (
				Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://127.0.0.1:3306/woms?useSSL=false&serverTimezone=UTC",
                        "root", "admin");
				Statement stmt = conn.createStatement();) 
		{
			String sql = "SELECT * FROM commodity";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String img = rs.getString(7);
				String name = rs.getString(2);
				String id = rs.getString(1);
				String price = String.valueOf(rs.getDouble(8));
				WebPanel sigPanel = createPanel(originalPanel, img, name, price, storeName, id);
				originalPanel.add(sigPanel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public StoreFrame(String storeName) {
		/****图标，菜单****/
		setIconImage(Toolkit.getDefaultToolkit().getImage("./lib/icon/icon.jpg"));
		setTitle("WOMS - 门店");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		
		WebMenuBar menuBar = new WebMenuBar();
		menuBar.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		setJMenuBar(menuBar);
		
		WebMenu menu_goods = new WebMenu("产品主页");
		
		menu_goods.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		menuBar.add(menu_goods);
		
		
		WebMenu menu_cart = new WebMenu("我的购物车");

		menu_cart.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		menuBar.add(menu_cart);
		
		WebMenu menu_ord = new WebMenu("我的订单");
		menu_ord.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		menuBar.add(menu_ord);
		
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
		
		
		/****slogan和展示Panel****/
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
		CardLayout cl_cardPanel = new CardLayout(0, 0);
		cardPanel.setLayout(cl_cardPanel);
		
		
		/*商品展示页*/
		JPanel goodsPanel = new JPanel();
		cardPanel.add(goodsPanel, "主页");
		goodsPanel.setLayout(new BorderLayout(0, 0));
		
		
		JScrollPane gscrollPane = new JScrollPane();
		goodsPanel.add(gscrollPane, BorderLayout.CENTER);
		
		WebPanel goods_main = new WebPanel();
		goods_main.setMargin(new Insets(20, 0, 0, 0));
		goods_main.setBackground(Color.WHITE);
		gscrollPane.setViewportView(goods_main);
		goods_main.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 10));
		
		display(goods_main, storeName);
		
		
		/*购物车展示页*/
		JPanel cartPanel = new JPanel();
		cardPanel.add(cartPanel, "购物车");	
		cartPanel.setLayout(new BorderLayout(0, 0));
		WebPanel cartTable = StoreCart.createCart(storeName, cartPanel);
		cartPanel.add(cartTable, BorderLayout.CENTER);
		
		JPanel informPanel = new JPanel();
		cardPanel.add(informPanel, "我的订单");	
		informPanel.setLayout(new BorderLayout(0, 0));
		WebPanel informTable = StoreInform.creatInform(storeName);
		informPanel.add(informTable, BorderLayout.CENTER);
		
		
		menu_goods.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cl_cardPanel.show(cardPanel, "主页");
			}
		});
		
		menu_ord.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cl_cardPanel.show(cardPanel, "我的订单");
			}
		});
		
		menu_cart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				cartPanel.removeAll();
				cartPanel.invalidate();
				cartPanel.add(StoreCart.createCart(storeName, cartPanel));
				cartPanel.validate();
				cl_cardPanel.show(cardPanel, "购物车");
			}
		});
		
		
	}
}
