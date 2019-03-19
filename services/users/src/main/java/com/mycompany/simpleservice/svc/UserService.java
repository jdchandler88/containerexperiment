/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.simpleservice.svc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mycompany.simpleservice.evt.Publisher;
import com.mycompany.simpleservice.svc.dto.UserRequestDto;
import com.mycompany.simpleservice.svc.entity.Role;
import com.mycompany.simpleservice.svc.entity.User;
import com.mycompany.simpleservice.svc.facade.RoleFacade;
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
	private RoleFacade roleFacade;
	
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
        		List<String> roles = authenticatedUser.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList());
        		String[] rolesArray = new String[roles.size()];
        		roles.toArray(rolesArray);
        		String accessToken = TokenUtil.issueToken(user.getUsername(), rolesArray, 30);
        		String refreshToken = TokenUtil.issueToken(user.getUsername(), rolesArray, 60*30);
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
    
    @Path("/roles")
    @GET
    public Response getRoles() {
    	return Response.ok().entity(roleFacade.findAll()).build();
    }
    
    @Path("/{username}/roles")
    @GET
    public Response getUserRoles(@PathParam("username") String username) {
    	try {
    		Set<Role> userRoles = userFacade.getByUserName(username).getRoles();
    		return Response.ok().entity(userRoles).build();
    	} catch (Exception e) {
    		return Response.serverError().build();
    	}
    }
    
    
}
