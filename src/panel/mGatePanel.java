package panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.table.WebTable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class mGatePanel {
	
	protected static void addPack(String coid, int num) {
		
		for (int i = 0; i < num; i++) {
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
				String sql = "INSERT INTO package(commodity) VALUES('" + coid + "')";
				stmt.execute(sql);
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private static WebPanel createSingleGate(String gate, String listString, String inORob) {
		WebPanel singleGate = new WebPanel();
		
		
		Gson gson = new Gson();
		List<String> gateList = gson.fromJson(listString, new TypeToken<List<String>>() {}.getType());
		
		Vector<String> column = new Vector<String>();
		column.add(gate);
		Vector<Vector<String>> table = new Vector<Vector<String>>();
		if (gateList == null || gateList.size() == 0) {
			Vector<String> row = new Vector<String>();
			row.add("");
			table.add(row);
		} else {
			for (int i = 0; i < gateList.size(); i++) {
				Vector<String> row = new Vector<String>();
				row.add(gateList.get(i));
				table.add(row);
			}
		}	
		WebTable singleTable = new WebTable(table, column);
		singleTable.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(WebLabel.CENTER);
		singleTable.setRowHeight(25);
		singleTable.setDefaultRenderer(Object.class, dtcr);
		JTableHeader head = singleTable.getTableHeader();
		head.setPreferredSize(new Dimension(head.getWidth(), 35));
		head.setFont(new Font("微软雅黑", Font.BOLD, 18));
		FitTableColumns(singleTable);
		JScrollPane jScrollPane = new JScrollPane(singleTable);
		singleGate.add(jScrollPane);
		GroupLayout gl_panel_1 = new GroupLayout(singleGate);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(jScrollPane, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(jScrollPane, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		singleGate.setLayout(gl_panel_1);
		
		if (inORob == "B") {
			singleTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					int i = WebOptionPane.showConfirmDialog(singleGate, "确认到货？");
					if (i == 0) {
						int rowid = singleTable.getSelectedColumn();
						String rpid = singleTable.getValueAt(rowid, 0).toString();
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
							String sql = "SELECT rp_coid, rp_num FROM replenishment WHERE rp_id = '" + rpid + "'";
							ResultSet rs = stmt.executeQuery(sql);
							rs.next();
							String coid = rs.getString(1);
							int num = rs.getInt(2);
							addPack(coid, num);
							
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
							String sql = "UPDATE replenishment SET rp_status = 3 WHERE rp_id = '" + rpid + "'";
							stmt.execute(sql);
							
						} catch (SQLException e) {
							e.printStackTrace();
						}
						
						WebOptionPane.showMessageDialog(singleGate, "已确认到货并生成托盘ID，请尽快安排入库操作！");
					}
					
				}
			});
		}
		
		return singleGate;
	}
	
	public static WebPanel createGatePanel (String inORob) {
		
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd");
		Date date = new Date();
		
		WebPanel flowContent= new WebPanel();
		flowContent.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
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
			String sql = "SELECT " + inORob + "1, " + inORob + "2, " + inORob + "3, " + inORob + "4, " + inORob + "5 FROM gate_info WHERE g_date = '" + sdf.format(date) +"'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			for (int i = 1; i <= 5; i++) {
				String gate = inORob + i;
				String singleList = rs.getString(i);
				WebPanel singlePanel = createSingleGate(gate, singleList, inORob);
				flowContent.add(singlePanel);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flowContent;
	}
	
	public static void FitTableColumns(WebTable myTable) {
	    JTableHeader header = myTable.getTableHeader();
	    int rowCount = myTable.getRowCount();

	    Enumeration columns = myTable.getColumnModel().getColumns();
	    while (columns.hasMoreElements()) {
	        TableColumn column = (TableColumn) columns.nextElement();
	        int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
	        int width = (int) myTable.getTableHeader().getDefaultRenderer()
	                .getTableCellRendererComponent(myTable, column.getIdentifier(), false, false, -1, col)
	                .getPreferredSize().getWidth();
	        for (int row = 0; row < rowCount; row++) {
	            int preferedWidth = (int) myTable.getCellRenderer(row, col)
	                    .getTableCellRendererComponent(myTable, myTable.getValueAt(row, col), false, false, row, col)
	                    .getPreferredSize().getWidth();
	            width = Math.max(width, preferedWidth);
	        }
	        header.setResizingColumn(column);
	        column.setWidth(width + myTable.getIntercellSpacing().width + 10);
	    }
	}

	
}
