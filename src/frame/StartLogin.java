package frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;
import javax.swing.plaf.FontUIResource;

import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.GroupingType;
import com.alee.extended.window.TestFrame;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.radiobutton.WebRadioButton;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextField;

public class StartLogin extends WebFrame {
	

	
	public StartLogin () {
		
		this.setTitle("WOMS"); // new窗体		
		this.setLocationRelativeTo(null); // 初始化窗体坐标
		this.setSize(400,300);  // 初始化窗体大小
		this.setLayout(null);
		
		WebPanel loginPanel = new WebPanel(new GridLayout(2, 2, 20, 10));
		loginPanel.setBounds(0, 80, 300, 70);
		WebLabel nameLabel = new WebLabel("用户名：");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		WebTextField nameText = new WebTextField(13);
		loginPanel.add(nameLabel);
		loginPanel.add(nameText);
		
		WebLabel pwLabel = new WebLabel("密码：");
		pwLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		WebPasswordField pwField = new WebPasswordField(13);
		loginPanel.add(pwLabel);
		loginPanel.add(pwField);
		
		WebPanel buttonPanel = new WebPanel(new GridLayout(1, 2, 40, 10));
		buttonPanel.setBounds(90, 200, 200, 30);
		WebButton loginButton = new WebButton("登录");// new按钮
		WebButton registButton = new WebButton("注册");
		buttonPanel.add(loginButton);
		buttonPanel.add(registButton);
		
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
		

		//loginButton.setBounds(50, 50, 50, 50); // 初始化按钮
		
		loginButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
            	dispose();            	
            	new Operator();
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
		
		WebLookAndFeel.globalControlFont  = new FontUIResource("黑体", 0, 16);
		//WebLookAndFeel.globalTooltipFont = new FontUIResource("黑体", 0 ,16);
		//WebLookAndFeel.globalAlertFont = new FontUIResource("黑体", 0 ,16);
		//WebLookAndFeel.globalTextFont = new FontUIResource("黑体", 0 ,16);
		//WebLookAndFeel.globalTitleFont = new FontUIResource("黑体", 0 ,16);
		
		WebLookAndFeel.install();
		new StartLogin(); 
	}

}
