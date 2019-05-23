package panel;

import java.awt.BorderLayout;
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

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout.Alignment;

import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.table.WebTable;

public class SupInvitePanel {
	
	protected static int num(String supid) {
		
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
			String sql = "SELECT COUNT(*) FROM sup_invite WHERE invite_sup = '" + supid + "' AND invite_status = 1";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}

	
	public static WebPanel supInvitePanel(String supid, JPanel invitePanel) {
		WebPanel content = new WebPanel();
		content.setLayout(new BorderLayout(0, 0));
		WebPanel title = new WebPanel();
		content.add(title, BorderLayout.NORTH);
		
		int count = num(supid);
		SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间 
        sdf.applyPattern("yyyy年MM月dd日");
        Date date = new Date();
		
		JLabel label = new JLabel("WOMS提醒您：今天是" + sdf.format(date) + "，待查看的要约邀请共" + count + "条，请及时反馈！");
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
		
		WebTable supiTable = WaitoImp.supinviteTable(supid);
		JScrollPane iscrollPane = new JScrollPane(supiTable);
		content.add(iscrollPane, BorderLayout.CENTER);
		
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
		
		return content;
	}
	
}
