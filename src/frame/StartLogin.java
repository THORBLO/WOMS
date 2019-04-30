package frame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;
import javax.swing.plaf.FontUIResource;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.radiobutton.WebRadioButton;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextField;

public class StartLogin extends WebFrame {
	
	// 检查登录方法
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
	
	// 弹出对话框
	protected void message () {
		WebOptionPane.showMessageDialog(this, "用户名和密码不能为空！");
	}
	
	// 弹出对话框
	protected void messageLogin () {
		WebOptionPane.showMessageDialog(this, "用户名或密码错误！");
	}

	// 构造方法
	public StartLogin () {
		
		// 设置窗体
		this.setTitle("WOMS"); // new窗体		
		this.setLocationRelativeTo(null); // 初始化窗体坐标
		this.setSize(400,300);  // 初始化窗体大小
		this.setLayout(null);
		
		// 设置输入框
		WebPanel loginPanel = new WebPanel(new GridLayout(2, 2, 20, 10));
		loginPanel.setBounds(0, 80, 300, 70);
		WebLabel nameLabel = new WebLabel("用户名：");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		WebTextField nameText = new WebTextField("");
		loginPanel.add(nameLabel);
		loginPanel.add(nameText);
		
		WebLabel pwLabel = new WebLabel("密码：");
		pwLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		WebPasswordField pwField = new WebPasswordField("");
		loginPanel.add(pwLabel);
		loginPanel.add(pwField);
		
		// 设置按钮
		WebPanel buttonPanel = new WebPanel(new GridLayout(1, 2, 40, 10));
		buttonPanel.setBounds(90, 200, 200, 30);
		WebButton loginButton = new WebButton("登录");// new按钮
		WebButton registButton = new WebButton("注册");
		buttonPanel.add(loginButton);
		buttonPanel.add(registButton);
		
		// 设置单选框
		WebPanel radioPanel = new WebPanel(new GridLayout(1, 3, 20, 10));
		radioPanel.setBounds(60, 160, 260, 30);
		WebRadioButton managerButton = new WebRadioButton("管理员");
		WebRadioButton storeButton = new WebRadioButton("集散点");
		WebRadioButton supplierButton = new WebRadioButton("供应商");
		ButtonGroup role = new ButtonGroup();
		role.add(managerButton);
		role.add(storeButton);
		role.add(supplierButton);
		radioPanel.add(managerButton);
		radioPanel.add(storeButton);
		radioPanel.add(supplierButton);
		managerButton.setSelected(true);
		

		//loginButton.setBounds(50, 50, 50, 50); // 初始化按钮
		
		// 设置登录按钮监听事件
		loginButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
            	
        		String name = nameText.getText();
        		char[] password = pwField.getPassword();
        		String pw = String.valueOf(password);
        		String role; 
            	
            	if (name.equals("") || pw.equals("")) 
            		message();
            	else {
                	if (managerButton.isSelected()) {
                		role = "manager";
                		if(loginStatus(name, pw, role)) {
                    		dispose();
                    		//new Operator();
                		} else {
							messageLogin();
						}

                	} else if (storeButton.isSelected()) {
						// 集散点登录
					} else {
						// 供应商登录
					}
            	}

            }
        });
		// 监听StartLogin对象
		
		this.add(loginPanel);
		this.add(radioPanel);
		this.add(buttonPanel);
		//loginFrame.add(namePanel);
		//loginFrame.add(nameText);
		this.setDefaultCloseOperation(WebFrame.EXIT_ON_CLOSE); // 设置窗体关闭后结束进程
		this.setVisible(true); // 可视化
	} 
	
	
	public static void main (String[] args) {
		
		WebLookAndFeel.globalControlFont  = new FontUIResource("黑体", 0, 17);
		//WebLookAndFeel.globalTooltipFont = new FontUIResource("黑体", 0 ,16);
		//WebLookAndFeel.globalAlertFont = new FontUIResource("黑体", 0 ,16);
		//WebLookAndFeel.globalTextFont = new FontUIResource("黑体", 0 ,16);
		//WebLookAndFeel.globalTitleFont = new FontUIResource("黑体", 0 ,16);
		
		WebLookAndFeel.install();
		new StartLogin(); 
	}

}
