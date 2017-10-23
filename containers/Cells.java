package com.hyj.containers;

import java.util.ArrayList;
import java.util.HashMap;

//��װ��containers_v0... begin
public	class Cells{
	ArrayList<HashMap<String, Object>> cells = new ArrayList<HashMap<String,Object>>();

	public void clear(){
	
		cells.clear();
	}
	public void init(String[] sargKey,int[] intentID, int intentIconID, String[] textList){
		clear();
		int k = textList.length;

		for (int i = 0; i < k; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put(sargKey[0], intentID[i]);
			map.put(sargKey[1], intentIconID);
			map.put(sargKey[2], textList[i]);
			cells.add(map);
		}
	}
	public void init(String[] sargKey,int[] intentID, int[] iconID,String[] textList){
		clear();
		int k = iconID.length;
		
		for (int i = 0; i < k; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put(sargKey[0], intentID[i]);
			map.put(sargKey[1], iconID[i]);
			map.put(sargKey[2], textList[i]);
			cells.add(map);
		}
	}
	
	public ArrayList<HashMap<String, Object>> getMapList(){
		
		return cells;
	}	
	
}
//��װ��containers_v0... end