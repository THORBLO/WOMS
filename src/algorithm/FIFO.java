package algorithm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;

public class FIFO {
	
	static Map<Integer, Map<String, String>> getDate (List<Map<String, String>> origin, Map<String, List> opstart) {
		
		Map<Integer, Map<String, String>> backDateMap = new HashMap<Integer, Map<String,String>>();
		
		for (int i = 1; i <= opstart.size(); i++) {
			List<String> list = opstart.get(i);
			Map<String, String> dateMap = new HashMap<String, String>();
			for (int j = 0; j < list.size(); j++) {
				for (int k = 0; k < origin.size(); k++) {
					if (origin.get(k).get("id").equals(list.get(j))) {
						dateMap.put(list.get(j), origin.get(k).get("datetime"));
						break;
					} else {
						continue;
					}
				}
			}
			backDateMap.put(i, dateMap);
		}
		
		return backDateMap;
	}
	
	static List<String> fifoList (Map<String, String> map) {
		
		List<Entry<String, String>> list = new ArrayList<Entry<String, String>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
				return (int)(o1.getValue().compareTo(o2.getValue()));
			}
		});
		
		List<String> backList = new ArrayList<String>();
		
		for (int i = 0; i < list.size(); i++) {
			backList.add(list.get(i).getKey());
		}
		
		return backList;
	}
	
	static void allFifo (Map<Integer, Map<String, String>> map) {
		
		List<List<String>> list = new ArrayList<List<String>>();
		
		for (int i = 1; i <= map.size(); i++) {
			list.add(fifoList(map.get(i)));
		}
		
		Gson gson = new Gson();
		String finalList = gson.toJson(list);
		
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间 
        sdf.applyPattern("yyyy-MM-dd");  
        Date date = new Date();// 获取当前时间 
        
		
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
			String sql = "INSERT INTO ob_plan(plan_date, plan_prim) VALUES('" + sdf.format(date) + "', '" + finalList + "')";
			stmt.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(list);
		
	}
	
}
