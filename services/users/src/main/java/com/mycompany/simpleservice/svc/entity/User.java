package com.mycompany.simpleservice.svc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
	
	@Id
	@Column
	@GeneratedValue
	private int id;
	
	@Column
	private String username;
	
	@Column
	private String passwordHash;
	
	@Column
	private String passwordSalt;

}
