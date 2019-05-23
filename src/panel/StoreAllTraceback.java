package panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.table.WebTable;

public class StoreAllTraceback {
	
	public static WebTable createTable() {
		Vector<String> column = new Vector<String>();
		column.add("反馈编号");
		column.add("供应商编号");
		column.add("供应商名");
		column.add("产品编号");
		column.add("产品名");
		column.add("反馈报价");
		column.add("补货数量");
		column.add("反馈时间");
		
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
			String sql = "SELECT * FROM all_traceback";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Vector<Object> row = new Vector<Object>();
				row.add(rs.getString(1));
				row.add(rs.getString(2));
				row.add(rs.getString(3));
				row.add(rs.getString(4));
				row.add(rs.getString(5));
				row.add(rs.getString(6));
				row.add(rs.getString(7));
				row.add(rs.getString(8));
				
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
	
	protected static int num() {
		
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
			String sql = "SELECT COUNT(*) FROM all_traceback";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	public static WebPanel storeAllTraceback(WebPanel tbPanel) {
		
		WebPanel content = new WebPanel();
		content.setLayout(new BorderLayout(0, 0));
		WebPanel title = new WebPanel();
		content.add(title, BorderLayout.NORTH);
		
		int count = num();
		SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间 
        sdf.applyPattern("yyyy年MM月dd日");
        Date date = new Date();
		
		JLabel label = new JLabel("WOMS提醒您：今天是" + sdf.format(date) + "，待处理的要约反馈共" + count + "条，请及时处理！");
		label.setFont(new Font("等线", Font.BOLD, 20));
		GroupLayout gltitle = new GroupLayout(title);
		gltitle.setHorizontalGroup(
			gltitle.createParallelGroup(Alignment.LEADING)
				.addGroup(gltitle.createSequentialGroup()
					.addContainerGap()
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 900, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(251, Short.MAX_VALUE))
		);
		gltitle.setVerticalGroup(
			gltitle.createParallelGroup(Alignment.LEADING)
				.addGroup(gltitle.createSequentialGroup()
					.addContainerGap()
					.addComponent(label, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
					.addContainerGap())
		);
		title.setLayout(gltitle);
		
		WebTable tbTable = createTable();
		JScrollPane scrollPane = new JScrollPane(tbTable);
		content.add(scrollPane, BorderLayout.CENTER);
		
		tbTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int rowid = tbTable.getSelectedRow();
				String tbid = tbTable.getValueAt(rowid, 0).toString();
				String supid = tbTable.getValueAt(rowid, 1).toString();
				String coid = tbTable.getValueAt(rowid, 3).toString();
				String conum = tbTable.getValueAt(rowid, 6).toString();
				String iprice = tbTable.getValueAt(rowid, 5).toString();
				int i = WebOptionPane.showConfirmDialog(tbTable, "确认接受该要约反馈？", "确认接受", WebOptionPane.YES_NO_OPTION);
				if (i == 0) {
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
						String sql = "UPDATE sup_traceback SET tb_status = 2 WHERE tb_id = '" + tbid + "'";
						stmt.execute(sql);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
						
					sdf.applyPattern("yyMMddHHmmss");
					Date dtn = new Date();
					String rpid = "RP" + sdf.format(dtn);
					
					try (
							Connection conn = DriverManager.getConnection(
			                        "jdbc:mysql://127.0.0.1:3306/woms?useSSL=false&serverTimezone=UTC",
			                        "root", "admin");
							Statement stmt = conn.createStatement();) 
					{
						String sql = "INSERT INTO replenishment(rp_id, rp_supid, rp_coid, rp_num, rp_iprice) VALUES('" + rpid + "', '" + supid + "', '" + coid + "', '" + conum + "', '" + iprice + "')";
						stmt.execute(sql);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					WebOptionPane.showMessageDialog(tbTable, "已生成补货订单，请积累足够补货订单后于15时进行入库门区规划！");
				} else if (i == 1) {
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
						String sql = "UPDATE sup_traceback SET tb_status = 3 WHERE tb_id = '" + tbid + "'";
						stmt.execute(sql);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
					WebOptionPane.showMessageDialog(tbTable, "要约已取消！");
				}
				
				tbPanel.removeAll();
				tbPanel.invalidate();
				WebPanel uPanel = storeAllTraceback(tbPanel);
				tbPanel.add(uPanel);
				tbPanel.validate();
				
				
			}
		});
		
		
		return content;
	}
}
