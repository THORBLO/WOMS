package algorithm;

import java.util.List;
import java.util.Map;


public class demo0 {
	
	
	
	public static void main(String[] args) {
		
		GetOrder demo = new GetOrder();		
		List<Map<String, String>> a = demo.getOrder();
		Map<String, Double> b = demo.measureArea(a);
		Map c = OBGateUtility.gateMap(b);
		List demo0 = (List) c.get(1);
		
		System.out.println(demo0);
		
		//改进OBG算法，gateOrder最开始先根据value排序，LIFO算法也可以借用这个模块
		
		
	}
}
