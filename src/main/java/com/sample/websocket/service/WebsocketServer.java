package com.sample.websocket.service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/webecho")
public class WebsocketServer {
	int i = 0;

	@OnOpen
	public void onOpen(Session session) {
		System.out.println(session.getId() + " has opened a connection");
		try {
			session.getBasicRemote().sendText("Connection Established:::");
		} catch (Exception e) {
			System.err.println("Error in opening connection:::");
			e.printStackTrace();
		}
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("Message from " + session.getId() + " :::" + message);
		try {
			session.getBasicRemote().sendText(message);

			while (i <= 10) {
				Thread.sleep(2000);
				System.out.println("Message ::" + i);
				session.getBasicRemote().sendText("Message " + i);
				i++;
			}
		} catch (Exception e) {
			System.err.println("Error in sending message");
			e.printStackTrace();
		}
	}

	@OnClose
	public void onClose(Session session) {
		System.out.println("Session " + session.getId() + " has ended.");
	}
}
