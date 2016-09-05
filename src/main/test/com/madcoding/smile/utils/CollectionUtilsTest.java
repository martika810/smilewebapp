package com.madcoding.smile.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.madcoding.smile.domain.Photo;

public class CollectionUtilsTest {
	public final static String URL ="www.google.com";
	public final static String OWNER1 = "121";
	public final static String OWNER2 = "122";
	public final static String OWNER3 = "123";
	Photo photo1 = Photo.makeInstance("1",URL, OWNER1,0,"");
	Photo photo2 = Photo.makeInstance("2",URL, OWNER2,0,"");
	Photo photo3 = Photo.makeInstance("3",URL, OWNER3,0,"");

	@Test
	public void testConvertSetToList() {
		
		Set<Photo> set = new HashSet<Photo>();
		set.add(photo1);
		set.add(photo2);
		set.add(photo3);
		List<Photo> list = new ArrayList<Photo>();
		list = CollectionUtils.convertSetToList(set);
		List<Photo> emptyList = CollectionUtils.convertSetToList(null);
		assertSame(set.size(), list.size());
		assertTrue(list.contains(photo1));
		assertTrue(list.contains(photo2));
		assertTrue(list.contains(photo3));
		assertTrue(emptyList.isEmpty());
	}
	
	@Test
	public void testConvertListToMap(){
		List<Photo> list = new ArrayList<Photo>();
		list.add(photo1);
		list.add(photo2);
		list.add(photo3);
		Map<String,Photo> map = CollectionUtils.convertListToMap(list);
		Map<String,Photo> emptyMap = CollectionUtils.convertListToMap(null);
		assertSame(map.values().size(),list.size());
		assertTrue(map.containsValue(photo1));
		assertTrue(map.containsValue(photo2));
		assertTrue(map.containsValue(photo3));
		assertTrue(emptyMap.isEmpty());
	}

}
