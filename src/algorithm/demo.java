package algorithm;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class demo {
	
	public static void main(String args[]) {
		int randNum = (int) (Math.random()*10);
		double randArea; // 随机订单数
		DecimalFormat format = new DecimalFormat("0.00"); // 小数点后二位
		Map demoMap = new HashMap(); // 订单：面积键值对
		double[] temp = new double[randNum];
		for (int i = 0; i < randNum; i++) {
			randArea = Double.valueOf(format.format(Math.random()*10));
			temp[i]= randArea;
		}
		Arrays.sort(temp);
		List aTemp = new ArrayList();
		for (int i = 0; i < randNum; i++) {
			aTemp.add(temp[i]);
		}
		Collections.reverse(aTemp); // 降序排列
		for (int i = 1; i <= randNum; i++) {			
			demoMap.put(Integer.toString(i), aTemp.get(i-1));
		}
		System.out.println("门区面积为10.00");
		System.out.println("各订单和对应占用面积：");
		System.out.println(demoMap.toString());
		Map demoResult = new HashMap();
		demoResult = OBGateUtility.gateMap(demoMap);
		System.out.println("根据算法得出最优订单分配门区情况");
		System.out.println(demoResult.toString());
		//先随机数插入数组，sort数组，再放进map
	}

}
