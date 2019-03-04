package com.mycompany.simpleservice.evt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

@ApplicationScoped
public class WebsocketSessionManager {

	private List<Session> sessions = new ArrayList<>();;
	
	public void addSession(Session session) {
		this.sessions.add(session);
	}
	
	public void removeSession(Session session) {
		this.sessions.remove(session);
	}
	
	public void publish(String message) {
		this.sessions.stream().forEach(s -> {
			try {
				s.getBasicRemote().sendText(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
}
