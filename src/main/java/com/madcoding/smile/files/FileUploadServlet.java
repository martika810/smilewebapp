package com.madcoding.smile.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;
import com.madcoding.smile.conf.SmileProperties;
import com.madcoding.smile.images.CloudinarySignGenerator;

/**
 * A Java servlet that handles file upload from client.
 *
 * @author www.codejava.net
 */
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// location to store file uploaded
	private static final String UPLOAD_DIRECTORY = "upload";

	// upload settings
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	/**
	 * Upon receiving file upload submission, parses the request to read upload
	 * data and saves the file on disk.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		// checks if the request actually contains upload file
		if (!ServletFileUpload.isMultipartContent(request)) {
			// if not, we stop here
			PrintWriter writer = response.getWriter();
			writer.println("Error: Form must has enctype=multipart/form-data.");
			writer.flush();
			return;
		}

		// configures upload settings
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// sets memory threshold - beyond which files are stored in disk
		factory.setSizeThreshold(MEMORY_THRESHOLD);
		// sets temporary location to store files
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		ServletFileUpload upload = new ServletFileUpload(factory);

		// sets maximum size of upload file
		upload.setFileSizeMax(MAX_FILE_SIZE);

		// sets maximum size of request (include file + form data)
		upload.setSizeMax(MAX_REQUEST_SIZE);

		// constructs the directory path to store upload file
		// this path is relative to application's directory
		//String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
		String uploadPath = SmileProperties.getInstance().getProperty("root_directory")+ File.separator + UPLOAD_DIRECTORY;

		// creates the directory if it does not exist
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");

		try {
			// parses the request's content to extract file data
			@SuppressWarnings("unchecked")
			List<FileItem> formItems = upload.parseRequest(request);

			List<File> savedFiles = saveFiles(formItems, uploadPath);
			JSONObject jsonFileArray = convertToJsonAndUploadCloudinary(savedFiles);

			request.setAttribute("message", "Upload has been done successfully!");

			PrintWriter out = response.getWriter();
			out.print(jsonFileArray);
			out.flush();
			out.close();

		} catch (Exception ex) {
			ex.printStackTrace();
			request.setAttribute("message", "error: " + ex.getMessage());
		}

	}

	private JSONObject convertToJsonAndUploadCloudinary(List<File> files) throws IOException, JSONException {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonFileArray = new JSONObject();

		for (File file : files) {

			String weburl = SmileProperties.getInstance().getProperty("base_url") + "/upload/" + file.getName();
			Map uplodedFile = sendFileToCloudinary(file);
			if (uplodedFile != null) {
				weburl = (String) uplodedFile.get("secure_url");
			}
			UploadFile newFile = new UploadFile(file.getName(), file.length(), weburl, weburl, weburl);

			jsonArray.put(newFile.toJson());
		}
		jsonFileArray.put("files", jsonArray);
		return jsonFileArray;
	}

	private List<File> saveFiles(List<FileItem> formItems, String uploadPath) throws IOException {
		File uploads = new File(uploadPath);
		List<File> listFiles = new ArrayList<File>();

		if (formItems != null && formItems.size() > 0) {
			for (FileItem item : formItems) {
				if (!item.isFormField()) {
					String fileName = new File(item.getName()).getName();
					String filePath = uploadPath + File.separator + fileName;
					// File storeFile = new File(filePath);
					File file = new File(uploads, fileName);

					// saves the file on disk
					InputStream input = item.getInputStream();
					Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
					listFiles.add(file);
				}

			}

		}
		return listFiles;
	}

	private Map sendFileToCloudinary(File file) throws IOException {
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", "dwckrucnb", "api_key",
				"471338927618672"));

		Map uploadResult = cloudinary.uploader().unsignedUpload(file, "icojw4de", ObjectUtils.emptyMap());

		return uploadResult;
	}
}
