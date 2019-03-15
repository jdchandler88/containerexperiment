package com.mycompany.simpleservice.svc.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@NamedQuery(
	    name = "Role.findByName",
	    query = "SELECT r FROM Role r where r.name = :rolename")
public class Role {

	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String name;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "roles_to_users",
		joinColumns = @JoinColumn(name = "role_id"),
		inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	@JsonManagedReference
	private Set<User> usersInRole = new HashSet<>();
	
	@Column
	private String description;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<User> getUsersInRole() {
		return usersInRole;
	}
	public void setUsersInRole(Set<User> usersInRole) {
		this.usersInRole = usersInRole;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}

