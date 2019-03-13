package com.mycompany.simpleservice.svc;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.mycompany.simpleservice.svc.entity.User;
import com.mycompany.simpleservice.svc.facade.UserFacade;

@Singleton
@Startup
public class UserServiceInitializer {

	private static final String DEFAULT_ADMIN_USER_NAME = "admin";
	
	private static final String DEFAULT_ADMIN_USER_PASSWORD = "admin123";
	
	@EJB
	private UserFacade userFacade;
	
	@PostConstruct
	public void init() {
		System.out.println("POSTCONSTRUCT IN USER FACADE");
		System.out.println("Checking for default admin user...");
		//add the admin user if it does not exist
		List<User> users = userFacade.findAll();
		System.out.println(users);
		if (!users.stream().anyMatch(user -> DEFAULT_ADMIN_USER_NAME.equals(user.getUsername()))) {
			System.out.println("No default admin user. Creating it...");
			userFacade.createUser(DEFAULT_ADMIN_USER_NAME, DEFAULT_ADMIN_USER_PASSWORD);
			System.out.println("Default admin user created.");
		} else {
			System.out.println("Default admin user found.");
		}
		System.out.println("Finishing check for default admin user...");
	}
	
}
