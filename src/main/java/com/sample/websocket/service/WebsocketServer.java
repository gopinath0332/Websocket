/**
 * Websocket server will broadcast message to all the connected session.
 */

package com.sample.websocket.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/webecho")
public class WebsocketServer {

	private static final Set<Session> sessionList = Collections.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void onOpen(Session session) {
		System.out.println(session.getId() + " has opened a connection");
		try {
			sessionList.add(session);
			// session.getBasicRemote().sendText("Connection Established with
			// id:::" + session.getId());
			broadcastMessage("Connection Established with id:::" + session.getId());
		} catch (Exception e) {
			System.err.println("Error in opening connection:::");
			e.printStackTrace();
		}
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("Message from " + session.getId() + " :::" + message);
		try {
			// session.getBasicRemote().sendText(message);
			broadcastMessage(session.getId() + ":::::" + message);
		} catch (Exception e) {
			System.err.println("Error in sending message");
			e.printStackTrace();
		}
	}

	@OnClose
	public void onClose(Session session) {
		try {
			System.out.println("Session " + session.getId() + " has ended.");
			sessionList.remove(session);
			broadcastMessage("Session " + session.getId() + " has ended.");
		} catch (Exception e) {
			System.err.println("Error on closing connection:::" + session.getId());
		}

	}

	/**
	 * @param message
	 *            Method to broad case message to all the sessions connected.
	 */
	private void broadcastMessage(String message) {
		try {
			for (Session ses : sessionList) {
				ses.getBasicRemote().sendText(message);
			}
		} catch (Exception e) {
			System.err.println("Error in sending message to all");
			e.printStackTrace();
		}
	}
}
