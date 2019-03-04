/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.simpleservice.svc;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mycompany.simpleservice.evt.NotificationStorage;

/**
 *
 * @author darin
 */
@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationsService {
 
	@EJB
	private NotificationStorage publisher;
	
    @Path("")
    @GET
    public List<String> getSimple() {
        return publisher.getAll();
    }
    
}
