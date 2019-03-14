package frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.rootpane.WebFrame;

public class StartLogin extends WebFrame implements ActionListener {
	
	private WebButton loginButton;
	
	WebFrame loginFrame = new WebFrame("WOMS"); // new窗体
	
	public StartLogin () {
		
		
		loginFrame.setLocation(100, 100); // 初始化窗体坐标
		loginFrame.setSize(400,300);  // 初始化窗体大小
		
		loginFrame.setLayout(null); // 固定窗体内组件
		
		
		setDefaultCloseOperation(WebFrame.EXIT_ON_CLOSE); // 设置窗体关闭后结束进程
		
		loginButton = new WebButton("Login"); // new按钮
		loginButton.setBounds(150, 150, 100, 100); // 初始化按钮
		
		loginButton.addActionListener(this); // 监听StartLogin对象
		loginButton.setActionCommand("successfullyLogin"); // 设置口令
		
		loginFrame.add(loginButton); // 添加按钮进窗体
		
		loginFrame.setVisible(true); // 可视化
	} 
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if (cmd.equals("successfullyLogin")) { // 触发监听器时
			
			loginFrame.dispose(); // 关闭当前窗口
			new Operator(); // new管理员窗口
			
			
		}
	}
	
	public static void main (String[] args) {
        
		WebLookAndFeel.install();
		new StartLogin(); 
	}
}
