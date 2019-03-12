/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.simpleservice.svc;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mycompany.simpleservice.evt.Publisher;
import com.mycompany.simpleservice.svc.dto.NewUserRequestDto;

/**
 *
 * @author darin
 */
@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserService {
 
	@EJB
	private Publisher publisher;
	
    @Path("")
    @GET
    public Response getSimple() {
        return Response.ok().entity("oh hai there from user service!").build();
    }
    
    @Path("/count")
    @GET
    public Response getCount() {
    	int count = 0;
    	return Response.ok().entity(count).build();
    }
    
    @Path("")
    @POST
    public void publishMessage(NewUserRequestDto newUser) {
    	
    	//publish a new user alert
    	publisher.publish(newUser.toString());
    }
    
}
