package com.example.websocketdemo.service;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Service
public class ChatHandler extends TextWebSocketHandler{

	private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<WebSocketSession>();
	 ArrayList<String> msj = new ArrayList<String>();
	
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception{
		super.afterConnectionEstablished(session);
        sessions.add(session);

        for (String mensaje : msj) {
            session.sendMessage(new TextMessage(mensaje));
        }
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
		sessions.remove(session);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
		String payload = message.getPayload();
		
		if (payload.contains("websocket")) {
            payload += "El protocolo de WebSockets es un protocolo de la capa 7 del modelo OSI, que funciona a través del protocolo TCP. Los WebSocket permiten comunicación a dos vías, desde el cliente hacia el servidor y desde el servidor hacia el cliente sobre una misma conexión.Funciona en los puertos 80 y 443";
		}
		msj.add(payload);
		
		for (WebSocketSession webSocketSession : sessions) {
			webSocketSession.sendMessage(message);

		}
	}
}
