package frame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;

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

public class StoreFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 * @return 
	 */
	
	private WebPanel createPanel(String img, String name, String price, String storeName, String co_id) {//创建单独的商品Panel
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
				int i = WebOptionPane.showConfirmDialog(panel, "确认订购"+nameLabel.getText()+"?", "确认订单",WebOptionPane.YES_NO_OPTION);
				if (i == 0) {
					String ord = getOrd();
					SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间 
			        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");  
			        Date date = new Date();// 获取当前时间
			        String datetime = sdf.format(date);
			        insertOrd(ord, storeName, datetime);
			        insertOC(ord, co_id);
			        insertValue(ord);
					WebOptionPane.showMessageDialog(panel, "订购成功！");
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
	
	private void insertOC(String ord, String commodity) {//插入单条产品信息关联表
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
			String sql = "INSERT INTO oc_combined VALUES ('" + ord + "', '" + commodity + "', 1)";
			stmt.execute(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	private void insertOrd(String ord, String store, String datetime) {
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
			String sql = "INSERT INTO order_info(order_id, store_id, order_time) VALUES ('" + ord + "', '" + store + "', '" + datetime + "')";
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
			String sql = "SELECT A.co_area FROM commodity A, oc_combined B where B.oc_order = '" + ord + "' and A.co_id = B.oc_commodity";
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				area += rs.getDouble(1);
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
	
	private String getOrd() {//生成订单号
		
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
				WebPanel sigPanel = createPanel(img, name, price, storeName, id);
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		
		JScrollPane scrollPane = new JScrollPane();
		goodsPanel.add(scrollPane, BorderLayout.CENTER);
		
		WebPanel goods_main = new WebPanel();
		goods_main.setMargin(new Insets(20, 0, 0, 0));
		goods_main.setBackground(Color.WHITE);
		scrollPane.setViewportView(goods_main);
		goods_main.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
		
		display(goods_main, storeName);
		
		/*购物车展示页*/
		JPanel cartPanel = new JPanel();
		cartPanel.setLayout(new BorderLayout(0, 0));
		WebLabel nameLabel = new WebLabel("111111");
		cartPanel.add(nameLabel, BorderLayout.CENTER);
		cardPanel.add(cartPanel, "购物车");
		
		
		menu_goods.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cl_cardPanel.show(cardPanel, "主页");
			}
		});
		
		menu_cart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cl_cardPanel.show(cardPanel, "购物车");
			}
		});
		
		
	}

}
