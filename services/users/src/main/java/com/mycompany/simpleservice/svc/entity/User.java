package com.mycompany.simpleservice.svc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class User {
	
	@Column
	private String username;
	
	@Column
	private String passwordHash;
	
	@Column
	private String passwordSalt;

}
