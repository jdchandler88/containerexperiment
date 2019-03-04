package com.mycompany.simpleservice.evt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;

@Stateful
public class NotificationStorage implements Serializable {

	
	private List<String> notifications = new ArrayList<>();
	
	public void storeNotification(String note) {
		this.notifications.add(note);
	}
	
	public List<String> getAll() {
		return this.notifications;
	}
	
}
