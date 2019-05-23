package algorithm;

import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import com.alee.laf.optionpane.WebOptionPane;

public class InBoundFlow {
	
	public static void flowDetail(JPanel content) {
		List<Map<String, String>> orderList = GetOrder.getRp();
		Map<String, Double> orderArea = GetOrder.measureArea(orderList);
		Map<String, List> optimizeOA = OBGateUtility.gateMap(orderArea);
		Map<Integer, Map<String, String>> orderDate = FIFO.getDate(orderList, optimizeOA);
		
		List<List<String>> finaList = FIFO.allFifo(orderDate, "in_plan");
		InsertAssignment.addAssignmentIN(finaList);
		PreInform.inform("in_plan");
		WebOptionPane.showMessageDialog(content, "补货订单已分配至对应门区/ 入库任务已生成/ 已通知供应商发货时间，请立即安排操作员工完成入库任务！");
	}
}
