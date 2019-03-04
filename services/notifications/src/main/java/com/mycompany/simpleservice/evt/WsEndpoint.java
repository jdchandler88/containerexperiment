package com.mycompany.simpleservice.evt;

import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/notifications")
public class WsEndpoint {

	@Inject
	private WebsocketSessionManager sessionManager;
	
	@OnOpen
	public void onOpen(Session session) {
		sessionManager.addSession(session);
	}
	
	@OnClose
	public void onClose(Session session) {
		sessionManager.removeSession(session);
	}
	
}
