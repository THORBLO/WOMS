package panel;

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

public class OpInboundPanel {
	
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
			String sql = "SELECT * FROM package WHERE commodity = '" + coid + "' AND status = 0";
			//System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			packID = rs.getString(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return packID;
	}
	
	protected static void insertPositon(String x, String y, String z, String packID) {
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
			String sql = "UPDATE storage SET package_id = '" + packID + "' WHERE x = '" + x + "' AND y = '" + y + "' AND z = '" + z + "'";
			stmt.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected static List<String> findPositon() {
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
			String sql = "SELECT x, y, z FROM storage WHERE package_id = ''";
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
	
	public static JPanel createInboundPanel(CardLayout gl_cardPanel, JPanel cardPanel, JPanel inboundPanel, JPanel outboundPanel) {
		
		JPanel content = new JPanel();
		
		JLabel ititile = new JLabel("今天是");
		ititile.setFont(new Font("等线", Font.BOLD, 24));
		
		JLabel imessage = new JLabel("接下来要进行的是一项入库操作任务：");
		imessage.setFont(new Font("等线", Font.BOLD, 24));
		

		List<String> inassignList = OperatorFrame.getInboundAssign();
		OpAssign.assignStatusMove(inassignList.get(0));
		JLabel icontent = new JLabel("- 请前往B" + inassignList.get(4) + "入库门区载取补货订单" + inassignList.get(1) + "的货物" +inassignList.get(2) + "共" + inassignList.get(3) + "件，任务ID：" + inassignList.get(0));
		icontent.setFont(new Font("微软雅黑", Font.BOLD, 25));
		
		WebButton istartButton = new WebButton("开始");
		istartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OpAssign.assignStatusMove(inassignList.get(0));
				for (int i = 0; i < Integer.parseInt(inassignList.get(3)); i++) {
					String packageID = findPack(inassignList.get(2));
					List<String> position = findPositon();
					OpAssign.packStatusMove(packageID);
					WebOptionPane.showConfirmDialog(content, "请将托盘号为" + packageID + "的货物放置于行：" + position.get(0) + "，列：" + position.get(1) + "，高：" + position.get(2) + "的位置并于操作结束时确认！");
					insertPositon(position.get(0), position.get(1), position.get(2), packageID);
					OpAssign.packStatusMove(packageID);
					WebOptionPane.showMessageDialog(content, "已完成该任务第" + (i + 1) + "托货物的入库操作，准备进行下一托货物操作！");
				}
				OpAssign.assignStatusMove(inassignList.get(0));
				WebOptionPane.showMessageDialog(content, "入库任务" + inassignList.get(0) + "已完成，下一条为出库任务，请准备！");
				inboundPanel.removeAll();
				inboundPanel.invalidate();
				inboundPanel.validate();
				outboundPanel.add(OpOutboundPanel.createOutboundPanel(gl_cardPanel, cardPanel, outboundPanel, inboundPanel));
				gl_cardPanel.show(cardPanel, "出库任务");
				
				
			}
		});
		istartButton.setFont(new Font("等线", Font.BOLD, 20));
		
		WebButton iquitButton = new WebButton("退出");
		iquitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Login login = new Login();
				//login.setVisible(true);
			}
		});
		iquitButton.setFont(new Font("等线", Font.BOLD, 20));
		GroupLayout gl_content = new GroupLayout(content);
		gl_content.setHorizontalGroup(
			gl_content.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_content.createSequentialGroup()
					.addGap(136)
					.addGroup(gl_content.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_content.createSequentialGroup()
							.addComponent(istartButton, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 673, Short.MAX_VALUE)
							.addComponent(iquitButton, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
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
						.addComponent(iquitButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
						.addComponent(istartButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
					.addGap(96))
		);
		content.setLayout(gl_content);
		
		return content;
		
	}

}
