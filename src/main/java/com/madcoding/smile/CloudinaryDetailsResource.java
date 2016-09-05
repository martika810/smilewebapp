package com.madcoding.smile;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.madcoding.smile.domain.CloudinaryDetails;
import com.madcoding.smile.images.CloudinaryDetailsInstance;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/cloudinarydetails")
public class CloudinaryDetailsResource {
	
	@GET
	public Response load(){
		CloudinaryDetails details = CloudinaryDetailsInstance.getInstance().getDetails();
		return Response.ok(new Gson().toJson(details)).build();
	}

}
