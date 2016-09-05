package com.madcoding.smile.helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.madcoding.smile.domain.Photo;
import com.madcoding.smile.domain.RankedPhoto;
import com.madcoding.smile.store.PhotoStore;
import com.madcoding.smile.store.VoteStore;

public class RankedPhotoHelper {
	private static RankedPhotoHelper INSTANCE = null;
	private RankedPhotoHelper(){
		
	}
	
	public static RankedPhotoHelper getInstance(){
		if(INSTANCE == null){
			INSTANCE = new RankedPhotoHelper();
		}
		return INSTANCE;
	}
	
	
	
}
