package algorithm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PreInform {
	
	static void inform () {
		
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间 
        sdf.applyPattern("yyyy-MM-dd");  
        Date date = new Date();// 获取当前时间
        String underOperate = null;
		
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
			String sql = "SELECT * FROM ob_plan WHERE plan_date = '" + sdf.format(date) + "'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				underOperate = rs.getString(3);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Gson gson = new Gson();
		List<List<String>> list = gson.fromJson(underOperate, new TypeToken<List<List<String>>>(){}.getType());
		
		addToDB(list);
		
	}
	
	static void addToDB (List<List<String>> list) {
		
		Date date = getDate();
		long time = 30*60*1000;
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间 
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss"); 
		
		for (int i = 0; i < list.size(); i++) {
			
			List<String> singleList = list.get(i);
			
			for (int j = 0; j < singleList.size(); j++) {
				
				Date predate = new Date(date.getTime() + time*j);
				insertToDB(singleList.get(j), i+1, sdf.format(predate));			
			}
			
		}
		
	}
	
	static void insertToDB (String order, int gate, String predate) {
		
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
			String sql = "INSERT INTO ob_inform(obin_order, obin_gate, obin_predate) VALUES('" + order + "', '" + gate + "', '" + predate + "')";
			stmt.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	static String getStore (String order) {
		
		String store = null;
		
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
			String sql = "SELECT * FROM order_info WHERE order_id = '" + order + "'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				store = rs.getString(2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return store;
	}
	
	static Date getDate () {
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        
        Date date = calendar.getTime();
        

        
        return date;
        
	}
	
	

}
