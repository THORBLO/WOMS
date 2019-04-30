package algorithm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import db_actions.InsertAssignment;


public class demo0 {
	
	
	
	public static void main(String[] args) {
		
		/**
		List<Map<String, String>> a = GetOrder.getOrder();
		Map<String, Double> b = GetOrder.measureArea(a);
		Map<String, List> c = OBGateUtility.gateMap(b);
		Map<Integer, Map<String, String>> d = FIFO.getDate(a, c);
		
		List<List<String>> e = FIFO.allFifo(d);
		InsertAssignment.addAssignment(e);
		PreInform.inform();
		**/
		
		
		/**
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间 
        sdf.applyPattern("yyMMddHHmmss");  
        Date date = new Date();// 获取当前时间 
        
        System.out.println(sdf.format(date));
        **/
		
		//改进OBG算法，gateOrder最开始先根据value排序，LIFO算法也可以借用这个模块
		
		
	}
}
