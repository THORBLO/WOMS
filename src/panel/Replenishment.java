package panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.table.WebTable;

import algorithm.InBoundFlow;

public class Replenishment {
	
	protected static WebTable createTable() {
		Vector<String> column = new Vector<String>();
		column.add("补货订单编号");
		column.add("供应商编号");
		column.add("产品编号");
		column.add("补货数量");
		column.add("供应商报价");
		column.add("订单生成时间");
		
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
			String sql = "SELECT * FROM replenishment WHERE rp_status = 1";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Vector<Object> row = new Vector<Object>();
				row.add(rs.getString(1));
				row.add(rs.getString(2));
				row.add(rs.getString(3));
				row.add(rs.getString(4));
				row.add(rs.getString(5));
				row.add(rs.getString(7));
				
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
			String sql = "SELECT COUNT(*) FROM replenishment WHERE rp_status = 1";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}

	
	public static WebPanel createRPanel(WebPanel rpPanel) {
		WebPanel content = new WebPanel();
		content.setLayout(new BorderLayout(0, 0));
		WebPanel title = new WebPanel();
		content.add(title, BorderLayout.NORTH);
		
		int count = num();
		SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间 
        sdf.applyPattern("yyyy年MM月dd日");
        Date date = new Date();
		
		JLabel label = new JLabel("WOMS提醒您：今天是" + sdf.format(date) + "，待处理的补货订单共" + count + "条，请适时处理！");
		label.setFont(new Font("等线", Font.BOLD, 20));
		WebButton Button = new WebButton("立即处理");
		Button.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				InBoundFlow.flowDetail(content);
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
					String sql = "UPDATE replenishment SET rp_status = 2 WHERE rp_status = 1";
					stmt.execute(sql);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				rpPanel.removeAll();
				rpPanel.invalidate();
				WebPanel uPanel = Replenishment.createRPanel(rpPanel);
				rpPanel.add(uPanel);
				rpPanel.validate();
			}
		});
		
		GroupLayout gl_title = new GroupLayout(title);
		gl_title.setHorizontalGroup(
			gl_title.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_title.createSequentialGroup()
					.addContainerGap()
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 900, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
					.addComponent(Button, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
					.addGap(31))
		);
		gl_title.setVerticalGroup(
			gl_title.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_title.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_title.createParallelGroup(Alignment.BASELINE)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addComponent(Button, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		title.setLayout(gl_title);
		
		WebTable rpTable = createTable();
		JScrollPane scrollPane = new JScrollPane(rpTable);
		content.add(scrollPane, BorderLayout.CENTER);
		
		/**
		
		supiTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int irowid = supiTable.getSelectedRow();
				String inid = supiTable.getValueAt(irowid, 0).toString();
				int i = WebOptionPane.showConfirmDialog(supiTable, "确认接受该要约邀请？", "确认接受", WebOptionPane.YES_NO_OPTION);
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
						String sql = "UPDATE sup_invite SET invite_status = 2 WHERE invite_id = '" + inid + "'";
						stmt.execute(sql);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
					double iprice = Double.valueOf(WebOptionPane.showInputDialog(supiTable, "请输入参考价格："));
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
						String sql = "INSERT INTO sup_traceback(tb_inid, tb_price) VALUES('" + inid + "', '" + iprice + "')";
						stmt.execute(sql);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
					WebOptionPane.showMessageDialog(supiTable, "反馈成功！");
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
						String sql = "UPDATE sup_invite SET invite_status = 3 WHERE invite_id = '" + inid + "'";
						stmt.execute(sql);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
					WebOptionPane.showMessageDialog(supiTable, "反馈成功！");
				}
				
				invitePanel.removeAll();
				invitePanel.invalidate();
				WebPanel uPanel = supInvitePanel(supid, invitePanel);
				invitePanel.add(uPanel);
				invitePanel.validate();
				
				
			}
		});
		**/
		
		return content;
	}
}
