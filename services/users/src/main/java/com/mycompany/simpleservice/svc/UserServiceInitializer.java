package com.mycompany.simpleservice.svc;

import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.mycompany.simpleservice.svc.entity.Role;
import com.mycompany.simpleservice.svc.entity.User;
import com.mycompany.simpleservice.svc.facade.RoleFacade;
import com.mycompany.simpleservice.svc.facade.UserFacade;

@Singleton
@Startup
public class UserServiceInitializer {

	private static final String DEFAULT_ADMIN_USER_NAME = "admin";
	
	private static final String DEFAULT_ADMIN_USER_PASSWORD = "admin123";
	
	private static final String ADMIN_ROLE_NAME = "Administrators";
	
	private static final String ADMIN_ROLE_DESCRIPTION = "This is a role that grants users elevated permissions.";
	
	@EJB
	private UserFacade userFacade;
	
	@EJB
	private RoleFacade roleFacade;
	
	@PostConstruct
	public void init() {
		System.out.println("POSTCONSTRUCT IN USER FACADE");
		
		//add the admin role if it does not exist
		System.out.println("Checking for admin role...");
		//add the admin role if it does not exist
		List<Role> roles = roleFacade.findAll();
		System.out.println(roles);
		if (!roles.stream().anyMatch(role -> ADMIN_ROLE_NAME.equals(role.getName()))) {
			System.out.println("No admin role. Creating it...");
			roleFacade.createRole(ADMIN_ROLE_NAME, ADMIN_ROLE_DESCRIPTION);
			System.out.println("Admin role created.");
		} else {
			System.out.println("Admin role found.");
		}
		System.out.println("Finishing check for admin role...");
		
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
		
		//add admin user to admin role
		Role adminRole = roleFacade.getByRoleName(ADMIN_ROLE_NAME);
		System.out.println("Checking if default admin user is in admin role");
		Set<User> usersInRole = adminRole.getUsersInRole();
		System.out.println("UsersInRole==" + usersInRole);
		if (!adminRole.getUsersInRole().stream().anyMatch(user -> DEFAULT_ADMIN_USER_NAME.equals(user.getUsername()))) {
			System.out.println("Default admin user not in admin role. Adding...");
			User defaultAdminUser = userFacade.getByUserName(DEFAULT_ADMIN_USER_NAME);
			adminRole.getUsersInRole().add(defaultAdminUser);
			roleFacade.edit(adminRole);
			System.out.println("Default admin user added to admin role.");
		} else {
			System.out.println("Default admin user already added to admin role.");
		}
		System.out.println("Finished check for default admin association to admin role...");
		
		
	}
	
}
