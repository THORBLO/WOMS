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
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.laf.table.WebTable;

import algorithm.OutBoundFlow;

public class mGetOrder {
	
	private static int countOrd() {
		String startTime = UnprocessedOrder.getstartDate();
		String endTime = UnprocessedOrder.getendDate();
		int count = 0;
		
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
			String sql = "SELECT COUNT(*) FROM order_info WHERE order_time >= '" + startTime + "' AND order_time <= '" + endTime + "' AND order_status = 1";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
		
	}
	
	public static WebPanel getOrder(WebPanel nordPanel, WebTabbedPane tabbedPane) {
		WebPanel content = new WebPanel();
		content.setLayout(new BorderLayout(0, 0));
		
		JPanel titlePanel = new JPanel();
		content.add(titlePanel, BorderLayout.NORTH);
		
		
		SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间 
        sdf.applyPattern("yyyy年MM月dd日");
        Date date = new Date();
        int count = countOrd();
		JLabel cLabel = new JLabel("WOMS提醒您：今天是" + sdf.format(date) + "，待处理订单共有" + count + "件，请适时处理。");
		cLabel.setFont(new Font("等线", Font.BOLD, 20));
		
		WebButton cButton = new WebButton("立即处理");
		cButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		cButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OutBoundFlow.flowDetail(content);
				String startTime = UnprocessedOrder.getstartDate();
				String endTime = UnprocessedOrder.getendDate();
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
					String sql = "UPDATE order_info SET order_status = 2 WHERE order_time >= '" + startTime + "' AND order_time <= '" + endTime + "' AND order_status = 1";
					stmt.execute(sql);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				nordPanel.removeAll();
				nordPanel.invalidate();
				WebPanel uPanel = mGetOrder.getOrder(nordPanel, tabbedPane);
				nordPanel.add(uPanel, BorderLayout.CENTER);
				nordPanel.validate();
				
			}
		});
		GroupLayout gl_titlePanel = new GroupLayout(titlePanel);
		gl_titlePanel.setHorizontalGroup(
			gl_titlePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_titlePanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(cLabel, GroupLayout.PREFERRED_SIZE, 900, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
					.addComponent(cButton, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
					.addGap(31))
		);
		gl_titlePanel.setVerticalGroup(
			gl_titlePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_titlePanel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_titlePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(cLabel, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addComponent(cButton, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		titlePanel.setLayout(gl_titlePanel);
		
		
		
		//表格设置
		//ordTable = new WebTable();
		WebTable upotable = UnprocessedOrder.createTable();
		upotable.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(WebLabel.CENTER);
		upotable.setRowHeight(25);
		upotable.setDefaultRenderer(Object.class, dtcr);
		JTableHeader head = upotable.getTableHeader();
		head.setPreferredSize(new Dimension(head.getWidth(), 35));
		head.setFont(new Font("微软雅黑", Font.BOLD, 18));

		upotable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				/**
				// TODO Auto-generated method stub
				int[] rowid = table.getSelectedRows();
				for (int i = 0; i < rowid.length; i++) {
					String order = table.getValueAt(rowid[i], 0).toString();
					//String store = table.getValueAt(rowid[i], 1).toString();
					//double area = Double.valueOf(table.getValueAt(rowid[i], 2).toString());
					//String datetime = table.getValueAt(rowid[i], 3).toString();
					
				}
				**/
				
				int rowid = upotable.getSelectedRow();
				String order = upotable.getValueAt(rowid, 0).toString();
				WebTable detailTable = OrderDetail.createDetail(order);
				detailTable.setFont(new Font("微软雅黑", Font.PLAIN, 14));
				detailTable.setBackground(new Color(240, 248, 255));
				DefaultTableCellRenderer dt = new DefaultTableCellRenderer();
				dt.setHorizontalAlignment(WebLabel.CENTER);
				detailTable.setRowHeight(25);
				detailTable.setDefaultRenderer(Object.class, dt);
				JTableHeader hed = detailTable.getTableHeader();
				hed.setPreferredSize(new Dimension(hed.getWidth(), 35));
				hed.setFont(new Font("微软雅黑", Font.BOLD|Font.ITALIC, 16));
				JScrollPane ocsPanel = new JScrollPane(detailTable);
				content.add(ocsPanel, BorderLayout.SOUTH);
				content.invalidate();
				content.validate();
			}
		});
		
		JScrollPane ordsPanel = new JScrollPane(upotable);
		content.add(ordsPanel, BorderLayout.CENTER);
		
		
		
		
		JPopupMenu nordPopMenu = new JPopupMenu();
		addPopup(nordPanel, nordPopMenu);
		
		JMenuItem menuItem_cls = new JMenuItem("关闭当前页面");
		menuItem_cls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.remove(nordPanel);
			}
		});
		nordPopMenu.add(menuItem_cls);
		
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
}
