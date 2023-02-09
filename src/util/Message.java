package util;

import java.io.Serializable;

import client.UdpChatClient;

//TODO: revisar, optimizar y documentar el código (JavaDoc)
public class Message implements Serializable{
	private static final long serialVersionUID = -1234L;
	private UdpChatClient udpChatClientFrom,udpChatClientTo;
	private String message;
	private int operacion;
	
	public Message(UdpChatClient udpChatClientFrom,
			UdpChatClient udpChatClientTo,
			String message, int operacion) {
		this.udpChatClientFrom=udpChatClientFrom;
		this.udpChatClientTo=udpChatClientTo;
		this.message = message;
		this.operacion = operacion;
	}
	
	public String getMessage() {
		return this.message;
	}
	public UdpChatClient getUdpChatClientFrom() {
		return this.udpChatClientFrom;
	}
	public UdpChatClient getUdpChatClientTo() {
		return this.udpChatClientTo;
	}
	public int getOperacion() {
		return this.operacion;
	}
}
