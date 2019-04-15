package frame;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.rootpane.WebFrame;

public class Operator extends WebFrame{
	
	public Operator () {
		WebFrame operatorFrame = new WebFrame("Operator Stage");
		
		// 最大化界面
		operatorFrame.setExtendedState(this.getExtendedState()|WebFrame.MAXIMIZED_BOTH);
		
		operatorFrame.setLayout(null);
		
		WebMenu operatorMenu = new WebMenu();
		
		
		
		operatorFrame.setDefaultCloseOperation(WebFrame.EXIT_ON_CLOSE);
		
		
		operatorFrame.setVisible(true);
	}
	
}
