package com.mycompany.simpleservice.evt;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NotificationStorage {

	
	private List<String> notifications = new ArrayList<>();
	
	public void storeNotification(String note) {
		this.notifications.add(note);
	}
	
	public List<String> getAll() {
		return this.notifications;
	}
	
}
