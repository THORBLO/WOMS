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
	
	WebFrame loginFrame = new WebFrame("WOMS");
	
	public StartLogin () {
		
		
		loginFrame.setLocation(100, 100);
		loginFrame.setSize(400,300);
		
		loginFrame.setLayout(null);
		
		
		setDefaultCloseOperation(WebFrame.EXIT_ON_CLOSE);
		
		loginButton = new WebButton("Login");
		loginButton.setBounds(150, 150, 100, 100);
		
		loginButton.addActionListener(this);
		loginButton.setActionCommand("successfullyLogin");
		
		loginFrame.add(loginButton);
		
		loginFrame.setVisible(true);
	} 
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if (cmd.equals("successfullyLogin")) {
			
			dispose();
			new Operator();
			
			
		}
	}
	
	public static void main (String[] args) {
        
		WebLookAndFeel.install();
		new StartLogin();
	}
}
