/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.simpleservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author darin
 */
@Path("")
public class SimpleService {
 
    @Path("")
    @GET
    public String getSimple() {
        return "oh hai there!";
    }
    
}
