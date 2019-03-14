package frame;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.rootpane.WebFrame;

public class Operator extends WebFrame{
	
	public Operator () {
		WebFrame operatorFrame = new WebFrame("Operator Stage");
		
		operatorFrame.setLocation(100, 100);
		operatorFrame.setSize(400,300);
		
		operatorFrame.setLayout(null);
		
		
		setDefaultCloseOperation(WebFrame.EXIT_ON_CLOSE);
		
		WebLabel label = new WebLabel("This is operator stage.");
		label.setBounds(50, 50, 200, 30);
		
		operatorFrame.add(label);
		
		operatorFrame.setVisible(true);
	}
	
}
