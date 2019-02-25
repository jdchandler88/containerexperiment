/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.simpleservice.svc;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.mycompany.simpleservice.evt.Publisher;

/**
 *
 * @author darin
 */
@Path("")
public class SimpleService {
 
	@EJB
	private Publisher publisher;
	
    @Path("")
    @GET
    public String getSimple() {
        return "oh hai there!";
    }
    
    @Path("")
    @POST
    public void publishMessage(String message) {
    	publisher.publish(message);
    }
    
}
