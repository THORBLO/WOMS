package algorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class demo0 {
	
	
	
	public static void main(String[] args) {
		
		GetOrder demo = new GetOrder();		
		List<Map<String, String>> a = demo.getOrder();
		Map<String, Double> b = demo.measureArea(a);
		Map<String, List> c = OBGateUtility.gateMap(b);
		FIFO fifo = new FIFO();
		Map<Integer, Map<String, String>> d = fifo.getDate(a, c);
		
		//System.out.println(c);
		
		//fifo.allFifo(d);
		PreInform.inform();
		

		
		//改进OBG算法，gateOrder最开始先根据value排序，LIFO算法也可以借用这个模块
		
		
	}
}
