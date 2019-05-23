package panel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.alee.laf.button.WebButton;
import com.alee.laf.optionpane.WebOptionPane;

import frame.OperatorFrame;

public class OpOutboundPanel {
	
	public static List<String> getOutboundAssign() {
		List<String> outboundAssignList = new ArrayList<String>();
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
			String sql = "SELECT * FROM assignment WHERE ass_status = 5";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			outboundAssignList.add(rs.getString(1));
			outboundAssignList.add(rs.getString(2));
			outboundAssignList.add(rs.getString(3));
			outboundAssignList.add(rs.getString(4));
			outboundAssignList.add(rs.getString(5));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return outboundAssignList;
	}
	
	protected static String findPack(String coid) {
		String packID = null;
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
			String sql = "SELECT * FROM package WHERE commodity = '" + coid + "' AND status = 2";
			//System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			packID = rs.getString(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return packID;
	}
	
	protected static void dropPositon(String x, String y, String z) {
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
			String sql = "UPDATE storage SET package_id = '' WHERE x = '" + x + "' AND y = '" + y + "' AND z = '" + z + "'";
			stmt.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected static List<String> findPositon(String packID) {
		List<String> positionList = new ArrayList<String>();
		
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
			String sql = "SELECT x, y, z FROM storage WHERE package_id = '" + packID + "'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			positionList.add(rs.getString(1));
			positionList.add(rs.getString(2));
			positionList.add(rs.getString(3));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return positionList;
	}
	
	public static JPanel createOutboundPanel(CardLayout gl_cardPanel, JPanel cardPanel, JPanel outboundPanel, JPanel inboundPanel) {
		
		JPanel content = new JPanel();
		
		JLabel ititile = new JLabel("今天是");
		ititile.setFont(new Font("等线", Font.BOLD, 24));
		
		JLabel imessage = new JLabel("接下来要进行的是一项出库操作任务：");
		imessage.setFont(new Font("等线", Font.BOLD, 24));
		

		List<String> outassignList = getOutboundAssign();
		OpAssign.assignStatusMove(outassignList.get(0));
		JLabel icontent = new JLabel("- 请载取门店订单" + outassignList.get(1) + "的货物" +outassignList.get(2) + "共" + outassignList.get(3) + "件前往A" + outassignList.get(4) + "出库门区，任务ID：" + outassignList.get(0));
		icontent.setFont(new Font("微软雅黑", Font.BOLD, 25));
		
		WebButton startButton = new WebButton("开始");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OpAssign.assignStatusMove(outassignList.get(0));
				for (int i = 0; i < Integer.parseInt(outassignList.get(3)); i++) {
					String packageID = findPack(outassignList.get(2));
					List<String> position = findPositon(packageID);
					OpAssign.packStatusMove(packageID);
					WebOptionPane.showConfirmDialog(content, "请于行：" + position.get(0) + "，列：" + position.get(1) + "，高：" + position.get(2) + "的位置将托盘号为" + packageID + "的货物取下放在叉车上并于操作结束时确认！");
					dropPositon(position.get(0), position.get(1), position.get(2));
					OpAssign.packStatusMove(packageID);
					WebOptionPane.showMessageDialog(content, "已完成该任务第" + (i + 1) + "托货物的出库操作，准备进行下一托货物操作！");
				}
				OpAssign.assignStatusMove(outassignList.get(0));
				WebOptionPane.showMessageDialog(content, "出库任务" + outassignList.get(0) + "已完成，下一条为出库任务，请准备！");
				outboundPanel.removeAll();
				outboundPanel.invalidate();
				outboundPanel.validate();
				inboundPanel.add(OpInboundPanel.createInboundPanel(gl_cardPanel, cardPanel, inboundPanel, outboundPanel), BorderLayout.CENTER);
				gl_cardPanel.show(cardPanel, "入库任务");
				
				
			}
		});
		startButton.setFont(new Font("等线", Font.BOLD, 20));
		
		WebButton quitButton = new WebButton("退出");
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Login login = new Login();
				//login.setVisible(true);
			}
		});
		quitButton.setFont(new Font("等线", Font.BOLD, 20));
		GroupLayout gl_content = new GroupLayout(content);
		gl_content.setHorizontalGroup(
			gl_content.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_content.createSequentialGroup()
					.addGap(136)
					.addGroup(gl_content.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_content.createSequentialGroup()
							.addComponent(startButton, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 673, Short.MAX_VALUE)
							.addComponent(quitButton, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
							.addGap(133))
						.addGroup(gl_content.createSequentialGroup()
							.addGroup(gl_content.createParallelGroup(Alignment.LEADING)
								.addComponent(icontent)
								.addComponent(imessage)
								.addComponent(ititile))
							.addContainerGap(708, Short.MAX_VALUE))))
		);
		gl_content.setVerticalGroup(
			gl_content.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_content.createSequentialGroup()
					.addGap(108)
					.addComponent(ititile)
					.addGap(18)
					.addComponent(imessage)
					.addGap(75)
					.addComponent(icontent)
					.addPreferredGap(ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
					.addGroup(gl_content.createParallelGroup(Alignment.LEADING)
						.addComponent(quitButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
						.addComponent(startButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
					.addGap(96))
		);
		content.setLayout(gl_content);
		
		return content;
		
	}
}
