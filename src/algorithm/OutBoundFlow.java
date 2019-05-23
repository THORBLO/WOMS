package algorithm;

import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;

public class OutBoundFlow {
	
	public static void flowDetail(JPanel content) {
		List<Map<String, String>> orderList = GetOrder.getOrder();
		Map<String, Double> orderArea = GetOrder.measureArea(orderList);
		Map<String, List> optimizeOA = OBGateUtility.gateMap(orderArea);
		Map<Integer, Map<String, String>> orderDate = FIFO.getDate(orderList, optimizeOA);
		
		List<List<String>> finaList = FIFO.allFifo(orderDate, "ob_plan");
		InsertAssignment.addAssignment(finaList);
		PreInform.inform("ob_plan");
		WebOptionPane.showMessageDialog(content, "门店订单已分配至对应门区/ 出库任务已生成/ 已通知门店取货时间，请立即安排操作员工完成出库任务！");
	}
}
