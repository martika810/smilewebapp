package com.madcoding.smile.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.madcoding.smile.domain.Identifiable;

public class CollectionUtils {
	
	public static <E> List<E> convertSetToList(Set<E> set){
		List<E> list = new ArrayList<E>();
		if(set == null){
			return list;
		}
		list.addAll(set);
		return list;
	}
	
	public static <E extends Identifiable> Map<String,E> convertListToMap(List<E> list){
		Map<String,E> map = new HashMap<String,E>();
		if(list == null){
			return map;
		}
		for(E item:list){
			map.put(item.getId(), item);
		}
		return map;
	}

}
