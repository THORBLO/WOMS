package panel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.alee.laf.table.WebTable;

public class UnprocessedOrder {
	
	public static String getstartDate() {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间 
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");  
        
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        
        Date date = calendar.getTime();
        
        return sdf.format(date);
        
	}
	
	public static String getendDate() {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间 
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");  
        
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        
        Date date = calendar.getTime();
        
        return sdf.format(date);
        
	}
	
	
	public static WebTable createTable() {
		Vector<String> cloumn = new Vector<String>();
		cloumn.add("订单号");
		cloumn.add("门店编号");
		cloumn.add("占用面积");
		cloumn.add("订购时间");
		
		Vector<Vector<Object>> table = new Vector<Vector<Object>>();
		String startTime = getstartDate();
		String endTime = getendDate();
		
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
			String sql = "SELECT * FROM order_info WHERE order_time >= '" + startTime + "' AND order_time <= '" + endTime + "' AND order_status = 1";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Vector<Object> row = new Vector<Object>();
				row.add(rs.getString(1));
				row.add(rs.getString(2));
				row.add(rs.getDouble(3));
				row.add(rs.getString(5));
				
				table.add(row);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//System.out.println(table.toString());
		WebTable unprocessedTable = new WebTable(table, cloumn);
		return unprocessedTable;
	}
	
	
}
