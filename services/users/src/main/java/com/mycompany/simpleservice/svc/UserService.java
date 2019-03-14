/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.simpleservice.svc;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mycompany.simpleservice.evt.Publisher;
import com.mycompany.simpleservice.svc.dto.UserRequestDto;
import com.mycompany.simpleservice.svc.entity.User;
import com.mycompany.simpleservice.svc.facade.UserFacade;
import com.mycompany.simpleservice.util.PasswordUtil;

import util.rest.TokenUtil;

/**
 *
 * @author darin
 */
@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserService {
 
	@EJB
	private UserFacade userFacade;
	
	@EJB
	private Publisher publisher;
	
    @Path("")
    @GET
    public Response getSimple() {
        return Response.ok().entity(userFacade.findAll()).build();
    }
    
    @Path("/count")
    @GET
    public Response getCount() {
    	int count = userFacade.findAll().size();
    	return Response.ok().entity(count).build();
    }
    
    @Path("")
    @POST
    public void createUser(UserRequestDto newUser) {
    	//create new user TODO: perform checks, validation, etc.
    	userFacade.createUser(newUser.getUsername(), newUser.getPassword());
    	
    	//publish a new user alert
    	publisher.publish(newUser.toString());
    }
    
    @Path("/login")
    @POST
    public Response login(UserRequestDto user) {
    	try {
        	User authenticatedUser = userFacade.getByUserName(user.getUsername());
        	String expectedHash = authenticatedUser.getPasswordHash();
        	String actualHash = PasswordUtil.hash(user.getPassword(), authenticatedUser.getPasswordSalt());
        	if (expectedHash.equals(actualHash)) {
        		String accessToken = TokenUtil.issueToken(user.getUsername(), new String[] {}, 30);
        		String refreshToken = TokenUtil.issueToken(user.getUsername(), new String[] {}, 60*30);
        		Map<String, String> returnValues = new HashMap<>();
        		returnValues.put("accessToken", accessToken);
        		returnValues.put("refreshToken", refreshToken);
        		return Response.ok().entity(returnValues).build();
        	} else {
        		return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();
        	}
    	} catch (Exception e) {
    		return Response.serverError().entity(e).build();
    	}
    	
    	
    }
    
}
