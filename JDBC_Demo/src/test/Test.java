package test;

import java.lang.reflect.InvocationTargetException;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

import model.User;

public class Test {

	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// 集合复习
		//1 list
//		List<Integer> list = new ArrayList<>();
//		list.add(10);
//		list.add(20);
//		list.add(30);
//		
//		System.out.println(list.get(0));
//		//list.remove(0);
//		System.out.println(list.size());
//		for (int i = 0; i <list.size(); i++) {
//			System.out.println(list.get(i));
//		}
		
		//2 Map
		/*
		Map<String, Integer> map=new HashMap<String, Integer>();
		map.put("小黑", 22);
		map.put("小红", 21);
		map.put("小黄", 23);
		System.out.println(map.get("小黑"));
		for(Entry<String, Integer> entry : map.entrySet()){
			System.out.println(entry.getKey()+entry.getValue());
		}*/
		
		//3，beanutils的测试
		/*
		User user = new User();
		System.out.println(user);
		BeanUtils.setProperty(user, "logname", "like");
		System.out.println(BeanUtils.getProperty(user, "logname"));
		*/

	}
	

}
