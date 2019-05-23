package panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.table.WebTable;

public class StoreInform {
	
	protected static WebTable createTable(String storeID) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
		
		
		Vector<String> column = new Vector<String>();
		column.add("订单编号");
		column.add("取货门区");
		column.add("取货时间");
		
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
			String sql = "SELECT * FROM obin_detail WHERE store_id = '" + storeID + "' AND obin_inform >'" + sdf.format(date) + "'";
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Vector<Object> row = new Vector<Object>();
				row.add(rs.getString(3));
				row.add(rs.getString(4));
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
	
	public static WebPanel creatInform(String storeID) {
		WebPanel content = new WebPanel();
		content.setLayout(new BorderLayout(0, 0));
		
		
		
		WebTable cartTable = createTable(storeID);
		JScrollPane scrollPane = new JScrollPane(cartTable);
		content.add(scrollPane, BorderLayout.CENTER);
		
		return content;
	}
}
