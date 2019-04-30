package algorithm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetOrder {
	
	static Map<String, Double> measureArea (List<Map<String, String>> inputList) {//匹配订单号和订单面积
		
		Map<String, Double> outputMap = new HashMap<String, Double>();
		
		for (int i = 0; i < inputList.size(); i++) {
			outputMap.put(inputList.get(i).get("id"), Double.valueOf(inputList.get(i).get("area")));
		}
		
		return outputMap;
		
	}
	
	static List<Map<String, String>> getOrder() {//得到每个订单的订单号，订单面积和下单时间
		
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
			
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
			String sql = "SELECT * FROM order_info";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Map<String, String> demo = new HashMap<String, String>();
				demo.put("id", rs.getString(1));
				demo.put("area", rs.getString(3));
				demo.put("datetime", rs.getString(5));
				list.add(demo);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
}
