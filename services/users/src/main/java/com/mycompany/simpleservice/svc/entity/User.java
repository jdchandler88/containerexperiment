package com.mycompany.simpleservice.svc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(
    name = "User.findByName",
    query = "SELECT u FROM User u where u.username = :username")
public class User {
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String username;
	
	@Column
	private String passwordHash;
	
	@Column
	private String passwordSalt;

	public String getUsername() {
		return username;
	}
	
	public String getPasswordHash() {
		return passwordHash;
	}
	
	public String getPasswordSalt() {
		return passwordSalt;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}
	
}
