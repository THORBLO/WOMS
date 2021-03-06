/**
 *  此类时出货门区优化利用算法
 *  过程：先按占用面积分配门区，再按时间先后排布各门区内部顺序
 *  目前只实现按面积分配门区
 *  类方法起点：gateMap
 */

package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class OBGateUtility {
	
	 static List gateOrder(Map inputOrder) { // 对输入的字典内订单进行符合要素的筛选，挑出满足一个门区的订单
		double gateArea = 2.0; // 设定单位门区面积
		Set set = inputOrder.entrySet();
		Iterator it = set.iterator(); // 设置迭代器
		List<String> orderList = new ArrayList<String>(); // 空列表
		while (it.hasNext()) { //对每个字典内的键值对进行匹配
			Map.Entry e = (Map.Entry)it.next();
			double singleArea = (double)e.getValue(); // 取出一个键的值（一个订单的占地面积）
			String orderNum = (String)e.getKey(); // 取出一个键
			if (singleArea <= gateArea) { // 判断条件，占地面积小于门区面积
				gateArea -= singleArea;	// 门区面积减去单个订单占地面积		
				orderList.add(orderNum); //添加键，即订单号
			} else { // 否则，跳出该循环进行下一个循环
				continue;
			}
		}
		
		return orderList;
	}
	 
	 static Map<String, List> gateMap(Map<String, Double> allOrder) {
		Map gateState = new HashMap();
		List<Entry<String, Double>> list = new ArrayList<Entry<String, Double>>(allOrder.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
				return (int)(o2.getValue() - o1.getValue());
			}
		});		
		Map<String, Double> allMap = new LinkedHashMap<String, Double>();
		for (int i = 0; i < list.size(); i++) {
			allMap.put(list.get(i).getKey(), list.get(i).getValue());
		}
		int count = 1; // 初始化门区号
		do { // 对字典内所有订单进行inputOrder方法，直到空
			List singleOrder = gateOrder(allMap); // 得到一个门区的list
			gateState.put(count, singleOrder); // 门区：list 键值对
			for (int i =0; i < singleOrder.size(); i++) {
				allMap.remove(singleOrder.get(i)); //取出已经排好的门区内订单
			}
			count++; //门区号+1
		}while(allMap.size() > 0); // 循环条件，字典不为空
		return gateState;
		 
	 }
}
