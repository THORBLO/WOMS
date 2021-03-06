package frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.alee.laf.button.WebButton;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.radiobutton.WebRadioButton;
import com.alee.laf.rootpane.WebFrame;

import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.Toolkit;




public class Login extends WebFrame {
	
	
	protected boolean loginStatus (String name, String pw, String role) {
		
		boolean status = false;
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
			String sql = "SELECT * FROM " + role + " WHERE id = '" + name + "' AND password = '" + pw + "'";
			ResultSet rs = stmt.executeQuery(sql);
			
			status = rs.next();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return status;
	}

	private JPanel contentPane;
	private JTextField textField_id;
	private JPasswordField textField_pw;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("F:\\InstallPackages\\logo\\icon.jpg"));
		setResizable(false);
		setTitle("WOMS - 登录");
		setFont(new Font("微软雅黑", Font.PLAIN, 12));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel_iptbox = new JPanel();
		panel_iptbox.setBackground(new Color(255, 255, 255));
		
		JLabel label_id = new JLabel("账 号：");
		label_id.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JLabel label_pw = new JLabel("密 码：");
		label_pw.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		textField_id = new JTextField();
		textField_id.setToolTipText("");
		textField_id.setForeground(Color.BLACK);
		textField_id.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textField_id.setColumns(10);
		
		textField_pw = new JPasswordField();
		textField_pw.setToolTipText("");
		textField_pw.setForeground(Color.BLACK);
		textField_pw.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textField_pw.setColumns(10);
		GroupLayout gl_panel_iptbox = new GroupLayout(panel_iptbox);
		gl_panel_iptbox.setHorizontalGroup(
			gl_panel_iptbox.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_iptbox.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_iptbox.createParallelGroup(Alignment.LEADING, false)
						.addComponent(label_pw, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(label_id, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_iptbox.createParallelGroup(Alignment.LEADING, false)
						.addComponent(textField_pw)
						.addComponent(textField_id, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
					.addContainerGap(33, Short.MAX_VALUE))
		);
		gl_panel_iptbox.setVerticalGroup(
			gl_panel_iptbox.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_iptbox.createSequentialGroup()
					.addGap(23)
					.addGroup(gl_panel_iptbox.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_id, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_id, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_iptbox.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_pw)
						.addComponent(textField_pw, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)))
		);
		panel_iptbox.setLayout(gl_panel_iptbox);
		
		JPanel panel_lgbtn = new JPanel();
		panel_lgbtn.setBackground(new Color(255, 255, 255));
		
		JLabel WOMS_Icon = new JLabel("");
		WOMS_Icon.setIcon(new ImageIcon("F:\\InstallPackages\\logo\\logo.jpg"));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(63)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel_lgbtn, GroupLayout.PREFERRED_SIZE, 315, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(WOMS_Icon, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
								.addComponent(panel_iptbox, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE))
							.addGap(56))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(25)
					.addComponent(WOMS_Icon, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
					.addGap(27)
					.addComponent(panel_iptbox, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_lgbtn, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
					.addGap(22))
		);
		
		WebRadioButton managerRadio = new WebRadioButton("管理员");
		managerRadio.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		managerRadio.setSelected(true);
		managerRadio.setBackground(Color.WHITE);
		
		WebRadioButton storeRadio = new WebRadioButton("门店");
		storeRadio.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		storeRadio.setBackground(Color.WHITE);
		
		WebRadioButton supplierRadio = new WebRadioButton("供应商");
		supplierRadio.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		supplierRadio.setBackground(Color.WHITE);
		
		WebRadioButton staffRadio = new WebRadioButton("员工");
		staffRadio.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		staffRadio.setBackground(Color.WHITE);
		
		ButtonGroup role = new ButtonGroup();
		role.add(managerRadio);
		role.add(storeRadio);
		role.add(supplierRadio);
		role.add(staffRadio);
		
		WebButton btn_lg = new WebButton("登录");
		btn_lg.setText("登    录");
		btn_lg.setBottomBgColor(new Color(245, 245, 245));
		btn_lg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
        		String name = textField_id.getText();
        		char[] password = textField_pw.getPassword();
        		String pw = String.valueOf(password);
        		String role = null;
            	
            	if (name.equals("") || pw.equals(""))
            		WebOptionPane.showMessageDialog(contentPane, "用户名和密码不能为空！");
            	else {
            		if (managerRadio.isSelected()) {
            			role = "manager";
                    	if(loginStatus(name, pw, role)) {
                        	//dispose();
                        	Mframe mframe = new Mframe(name);
                        	mframe.setVisible(true);
                    	} else {
                    		WebOptionPane.showMessageDialog(contentPane, "用户名或密码错误！");
    					}
            		} 
            		else if (storeRadio.isSelected()) {
            			role = "store";
                    	if(loginStatus(name, pw, role)) {
                        	//dispose();
                        	StoreFrame storeframe = new StoreFrame(name);
                        	storeframe.setVisible(true);
                    	} else {
                    		WebOptionPane.showMessageDialog(contentPane, "用户名或密码错误！");
    					}
            		}
            		else if (supplierRadio.isSelected()) {
            			role = "supplier";
            			if (loginStatus(name, pw, role)) {
            				//dispose();
                        	SupplierFrame supplierframe = new SupplierFrame(name);
                        	supplierframe.setVisible(true);
            			} else {
                    		WebOptionPane.showMessageDialog(contentPane, "用户名或密码错误！");
    					}
            		}
            		else if (staffRadio.isSelected()) {
            			role = "operator";
            			if (loginStatus(name, pw, role)) {
            				//dispose();
                        	OperatorFrame operatorFrame = new OperatorFrame();
                        	operatorFrame.setVisible(true);
            			} else {
                    		WebOptionPane.showMessageDialog(contentPane, "用户名或密码错误！");
    					}
            		}
            	}
			}
		});
		btn_lg.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		

		GroupLayout gl_panel_lgbtn = new GroupLayout(panel_lgbtn);
		gl_panel_lgbtn.setHorizontalGroup(
			gl_panel_lgbtn.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_lgbtn.createSequentialGroup()
					.addGroup(gl_panel_lgbtn.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_lgbtn.createSequentialGroup()
							.addContainerGap()
							.addComponent(managerRadio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(storeRadio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(supplierRadio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(staffRadio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_lgbtn.createSequentialGroup()
							.addGap(68)
							.addComponent(btn_lg, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(29, Short.MAX_VALUE))
		);
		gl_panel_lgbtn.setVerticalGroup(
			gl_panel_lgbtn.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_lgbtn.createSequentialGroup()
					.addGroup(gl_panel_lgbtn.createParallelGroup(Alignment.BASELINE)
						.addComponent(managerRadio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(storeRadio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(supplierRadio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(staffRadio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(20)
					.addComponent(btn_lg, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
		);
		panel_lgbtn.setLayout(gl_panel_lgbtn);
		contentPane.setLayout(gl_contentPane);
	}
}
