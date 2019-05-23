package algorithm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class demo0 {
	
	
	
	public static void main(String[] args) {
		/****
		//List<Map<String, String>> a = GetOrder.getRp();
		List<Map<String, String>> a = GetOrder.getOrder();
		Map<String, Double> b = GetOrder.measureArea(a);
		Map<String, List> c = OBGateUtility.gateMap(b);
		Map<Integer, Map<String, String>> d = FIFO.getDate(a, c);
		
		List<List<String>> e = FIFO.allFifo(d, "ob_plan");
		//System.out.println(e.toString());
		InsertAssignment.addAssignment(e);
		//PreInform.inform();
		
		
		/**
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间 
        sdf.applyPattern("yyMMddHHmmss");  
        Date date = new Date();// 获取当前时间 
        
        System.out.println(sdf.format(date));
        **/
		
		//改进OBG算法，gateOrder最开始先根据value排序，LIFO算法也可以借用这个模块
		/**
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.MINUTE, -550);
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy年MM月dd日HH时mm分ss秒");
		System.out.println(sdf.format(date));
		**/
		
		for (int i = 1; i <= 50 ; i++) {
			for (int j = 1; j <= 20; j++) {
				for (int k = 1; k <= 5; k++) {
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
						String sql = "INSERT INTO storage(x, y, z) VALUES(" + i + ", " + j + ", " + k + ")";
						stmt.execute(sql);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
}
