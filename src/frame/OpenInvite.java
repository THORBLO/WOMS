package frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.alee.laf.button.WebButton;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.text.WebTextField;

import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class OpenInvite extends JFrame {

	private JPanel contentPane;
	
	public static boolean judgeInvite(String commodity) {
		boolean judge = false;
		
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
			String sql = "SELECT";
			stmt.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return judge;
	}

	public static void main(String[] args) {
		
	}
	/**
	 * Create the frame.
	 */
	public OpenInvite(String cid, String cname, String sid, String safe, String rest) {
		setTitle("WOMS - 要约邀请");
		setIconImage(Toolkit.getDefaultToolkit().getImage("F:\\InstallPackages\\logo\\icon.jpg"));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel img = new JLabel("");
		img.setIcon(new ImageIcon("F:\\InstallPackages\\logo\\logo.jpg"));
		
		JPanel cidPanel = new JPanel();
		cidPanel.setBackground(Color.WHITE);
		
		JPanel cnamePanel = new JPanel();
		cnamePanel.setBackground(Color.WHITE);
		cnamePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel cnameLabel = new JLabel("产品名称：");
		cnamePanel.add(cnameLabel, BorderLayout.WEST);
		cnameLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
		
		JLabel cnameL = new JLabel(cname);
		cnameL.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		cnamePanel.add(cnameL, BorderLayout.EAST);
		
		JPanel sidPanel = new JPanel();
		sidPanel.setBackground(Color.WHITE);
		sidPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel sidLabel = new JLabel("供应商ID：");
		sidPanel.add(sidLabel, BorderLayout.WEST);
		sidLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
		
		JLabel sidL = new JLabel(sid);
		sidL.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		sidPanel.add(sidL, BorderLayout.EAST);
		
		JPanel safePanel = new JPanel();
		safePanel.setBackground(Color.WHITE);
		safePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel safeLabel = new JLabel("安全库存：");
		safePanel.add(safeLabel, BorderLayout.WEST);
		safeLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
		

		JLabel safeL = new JLabel(safe);
		safeL.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		safePanel.add(safeL, BorderLayout.EAST);
		
		JPanel restPanel = new JPanel();
		restPanel.setBackground(Color.WHITE);
		restPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel restLabel = new JLabel("在库存量：");
		restPanel.add(restLabel, BorderLayout.WEST);
		restLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
		
		JLabel restL = new JLabel(rest);
		restL.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		restPanel.add(restL, BorderLayout.EAST);
		
		JPanel numPanel = new JPanel();
		numPanel.setBackground(Color.WHITE);
		numPanel.setLayout(new BorderLayout(0, 0));
		
		WebTextField numT = new WebTextField();
		numT.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		numPanel.add(numT, BorderLayout.EAST);
		numT.setColumns(5);
		cidPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel idLabel = new JLabel("产品编号：");
		cidPanel.add(idLabel, BorderLayout.WEST);
		idLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
		
		JLabel idL = new JLabel(cid);
		cidPanel.add(idL, BorderLayout.EAST);
		idL.setFont(new Font("微软雅黑", Font.PLAIN, 15));

		
		JLabel numLabel = new JLabel("补货数量：");
		numPanel.add(numLabel, BorderLayout.WEST);
		numLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
		
		WebButton cfmButton = new WebButton("发 送 要 约");
		cfmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int num =  Integer.parseInt(numT.getText());
				
				int i = WebOptionPane.showConfirmDialog(contentPane, "确认发送补货要约" + num + "件" + cname + "给供应商" + sid + "？", "发送要约", WebOptionPane.YES_NO_OPTION);
				
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
						String sql = "INSERT INTO sup_invite(invite_sup, invite_coid, invite_num) VALUES('" + sid + "', '" + cid + "', '" + num + "')";
						stmt.execute(sql);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					WebOptionPane.showMessageDialog(contentPane, "要约发送成功，请耐心等待供应商反馈！");
					
					dispose();
				}
				
				
			}
		});
		cfmButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(67)
							.addComponent(img))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(26)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(cidPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(sidPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(restPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(32)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(numPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(Alignment.TRAILING, gl_contentPane.createParallelGroup(Alignment.LEADING, false)
									.addComponent(safePanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(cnamePanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(151)
							.addComponent(cfmButton, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(22, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(img, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addGap(36)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(cidPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(26))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(cnamePanel, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(sidPanel, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addComponent(safePanel, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addGap(21)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(numPanel, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addComponent(restPanel, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
					.addComponent(cfmButton, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		contentPane.setLayout(gl_contentPane);
	}
}
