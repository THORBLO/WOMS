package frame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.alee.laf.button.WebButton;

import panel.OpAssign;
import panel.OpInboundPanel;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;

public class OperatorFrame extends JFrame {
	
	public static List<String> getInboundAssign() {
		List<String> inboundAssignList = new ArrayList<String>();
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
			String sql = "SELECT * FROM assignment WHERE ass_status = 1";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			inboundAssignList.add(rs.getString(1));
			inboundAssignList.add(rs.getString(2));
			inboundAssignList.add(rs.getString(3));
			inboundAssignList.add(rs.getString(4));
			inboundAssignList.add(rs.getString(5));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return inboundAssignList;
	}

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public OperatorFrame() {

		setIconImage(Toolkit.getDefaultToolkit().getImage("F:\\InstallPackages\\logo\\icon.jpg"));
		setTitle("WOMS - 管理员");
			
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("F:\\InstallPackages\\logo\\slogan.jpg"));
		contentPane.add(label, BorderLayout.NORTH);
		
		JPanel cardPanel = new JPanel();
		contentPane.add(cardPanel, BorderLayout.CENTER);
		CardLayout cl_cardPanel = new CardLayout(0, 0);
		cardPanel.setLayout(cl_cardPanel);
		
		JPanel startPanel = new JPanel();
		cardPanel.add(startPanel, "开始");
		JPanel inBoundPanel = new JPanel();
		cardPanel.add(inBoundPanel, "入库任务");
		inBoundPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel outBoundPanel = new JPanel();
		cardPanel.add(outBoundPanel, "出库任务");
		outBoundPanel.setLayout(new BorderLayout(0, 0));
		
		WebButton startButton = new WebButton("开始操作作业");
		
		
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				inBoundPanel.add(OpInboundPanel.createInboundPanel(cl_cardPanel,cardPanel, inBoundPanel, outBoundPanel), BorderLayout.CENTER);
				cl_cardPanel.show(cardPanel, "入库任务");
				
			}
		});
		startButton.setFont(new Font("黑体", Font.BOLD, 46));
		GroupLayout gl_startPanel = new GroupLayout(startPanel);
		gl_startPanel.setHorizontalGroup(
			gl_startPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_startPanel.createSequentialGroup()
					.addContainerGap(408, Short.MAX_VALUE)
					.addComponent(startButton, GroupLayout.PREFERRED_SIZE, 453, GroupLayout.PREFERRED_SIZE)
					.addGap(391))
		);
		gl_startPanel.setVerticalGroup(
			gl_startPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_startPanel.createSequentialGroup()
					.addGap(179)
					.addComponent(startButton, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(212, Short.MAX_VALUE))
		);
		startPanel.setLayout(gl_startPanel);
		
		
		
		
		
		
		
		/* 两个card相互跳
		 * 抓住一个任务，改变任务状态，按num数量循环，取出托盘ID，找出空库位，提示，插入该库位以托盘ID, 循环内改动托盘状态，循环外改变任务状态
		 * 		
		 * 
		 */
		
	}
}
