package com.madcoding.smile.files;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


@XmlRootElement
public class UploadFile {
	
	private String name;
	private long size;
	private String url;
	private String thumbnailUrl;
	private String deleteUrl;
	private String deleteType = "DELETE";
	
	public UploadFile(String name, long size, String url, String thumbnailUrl, String deleteUrl) {
		super();
		this.name = name;
		this.size = size;
		this.url = url;
		this.thumbnailUrl = thumbnailUrl;
		this.deleteUrl = deleteUrl;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getDeleteUrl() {
		return deleteUrl;
	}

	public void setDeleteUrl(String deleteUrl) {
		this.deleteUrl = deleteUrl;
	}

	public String getDeleteType() {
		return deleteType;
	}

	public void setDeleteType(String deleteType) {
		this.deleteType = deleteType;
	}
	
	public JSONObject toJson () throws JSONException{
		JSONObject obj = new JSONObject();
		obj.put("name", name);
		obj.put("size", size);
		obj.put("url", url);
		obj.put("thumbnailUrl", thumbnailUrl);
		obj.put("deleteUrl", deleteUrl);
		obj.put("deleteType", deleteType);
		return obj;
	}

}
