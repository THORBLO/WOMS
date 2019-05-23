package panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.table.WebTable;

import frame.StoreFrame;

public class StoreCart {
	
	protected static void payBill(String storeID, JPanel cartPanel) {
		double totalArea = 0.0;
		String orderID = StoreFrame.getOrd();
		StoreFrame.insertOrd(orderID, storeID);
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
			String sql = "SELECT co_id, area, piece FROM cart_detail WHERE store = '" + storeID + "'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String coID = rs.getString(1);
				totalArea += rs.getDouble(2);
				int num = rs.getInt(3);
				StoreFrame.insertOC(orderID, coID, num);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try (
				Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://127.0.0.1:3306/woms?useSSL=false&serverTimezone=UTC",
                        "root", "admin");
				Statement stmt = conn.createStatement();) 
		{
			String sql = "UPDATE order_info SET order_area = '" + totalArea + "'WHERE order_id = '" + orderID + "'";
			stmt.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try (
				Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://127.0.0.1:3306/woms?useSSL=false&serverTimezone=UTC",
                        "root", "admin");
				Statement stmt = conn.createStatement();) 
		{
			String sql = "DELETE FROM cart WHERE store = '" + storeID + "'";
			stmt.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		WebOptionPane.showMessageDialog(cartPanel, "结算成功，WOMS正在安排货物出仓，请耐心等待！");
		
		
	}
	
	protected static double allMoney(String storeID) {
		double all = 0.0;
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
			String sql = "SELECT SUM(total) FROM cart_detail WHERE store = '" + storeID + "' GROUP BY store";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			all = rs.getDouble(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return all;
	}
	
	protected static WebTable createTable(String storeID) {
		Vector<String> column = new Vector<String>();
		column.add("产品编号");
		column.add("产品信息");
		column.add("单价");
		column.add("数量");
		column.add("金额");
		
		Vector<Vector<Object>> table = new Vector<Vector<Object>>();
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
			String sql = "SELECT * FROM cart_detail WHERE store = '" + storeID + "'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Vector<Object> row = new Vector<Object>();
				row.add(rs.getString(2));
				row.add(rs.getString(3));
				row.add(rs.getString(4));
				row.add(rs.getString(5));
				row.add(rs.getString(6));
				
				table.add(row);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		WebTable webTable = new WebTable(table, column);
		webTable.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(WebLabel.CENTER);
		webTable.setRowHeight(25);
		webTable.setDefaultRenderer(Object.class, dtcr);
		JTableHeader head = webTable.getTableHeader();
		head.setPreferredSize(new Dimension(head.getWidth(), 35));
		head.setFont(new Font("微软雅黑", Font.BOLD, 15));
		return webTable;
		
	}
	
	protected static void dropCart(String storeID, String coID) {
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
			String sql = "DELETE FROM cart WHERE store = '" + storeID + "' AND commodity = '" + coID + "'";
			stmt.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static WebPanel createCart(String storeID, JPanel cartPanel) {
		
		WebPanel content = new WebPanel();
		content.setLayout(new BorderLayout(0, 0));
		
		
		
		WebTable cartTable = createTable(storeID);
		hideTableColumn(cartTable, 0);
		JScrollPane scrollPane = new JScrollPane(cartTable);
		content.add(scrollPane, BorderLayout.CENTER);
		
		
		
		JPopupMenu nordPopMenu = new JPopupMenu();
		addPopup(cartTable, nordPopMenu);
		
		JMenuItem menuItem_cls = new JMenuItem("移除该产品");
		menuItem_cls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int rowid = cartTable.getSelectedRow();
				String id = cartTable.getValueAt(rowid, 0).toString();
				dropCart(storeID, id);
				WebOptionPane.showMessageDialog(cartPanel, "移除成功！");
				cartPanel.removeAll();;
				cartPanel.invalidate();
				WebPanel uPanel = createCart(storeID, cartPanel);
				cartPanel.add(uPanel);
				cartPanel.validate();
				
			}
		});
		nordPopMenu.add(menuItem_cls);
		
		JPanel bottomPanel = new JPanel();
		content.add(bottomPanel, BorderLayout.SOUTH);
		
		WebButton submitButton = new WebButton("结 算 全 部");
		submitButton.setBottomBgColor(new Color(255, 99, 71));
		submitButton.setTopBgColor(new Color(255, 69, 0));
		submitButton.setInnerShadeColor(new Color(255, 255, 255));
		submitButton.setBackground(new Color(255, 69, 0));
		submitButton.setForeground(Color.WHITE);
		submitButton.setFont(new Font("等线", Font.BOLD, 22));
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				payBill(storeID, cartPanel);
				cartPanel.removeAll();;
				cartPanel.invalidate();
				WebPanel uPanel = createCart(storeID, cartPanel);
				cartPanel.add(uPanel);
				cartPanel.validate();
			}
		});
		
		double all = allMoney(storeID);
		
		JLabel priceLabel = new JLabel("¥ " + all);
		priceLabel.setFont(new Font("等线", Font.BOLD, 26));
		priceLabel.setForeground(new Color(255, 69, 0));
		
		JLabel totalLabel = new JLabel("合计:");
		totalLabel.setForeground(new Color(0, 0, 0));
		totalLabel.setFont(new Font("等线", Font.BOLD, 20));
		GroupLayout gl_bottomPanel = new GroupLayout(bottomPanel);
		gl_bottomPanel.setHorizontalGroup(
			gl_bottomPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_bottomPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(totalLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(priceLabel, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 892, Short.MAX_VALUE)
					.addComponent(submitButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_bottomPanel.setVerticalGroup(
			gl_bottomPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_bottomPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_bottomPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(submitButton, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
						.addComponent(totalLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addComponent(priceLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		bottomPanel.setLayout(gl_bottomPanel);
		
		return content;
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
	
	private static void hideTableColumn(WebTable table, int column)
	{    
	    TableColumn tc = table.getColumnModel().getColumn(column); 
	    tc.setMinWidth(0);   
	    tc.setMaxWidth(0);
	}

	
}
