package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import client.UdpChatClient;
import util.Message;

//TODO: revisar, optimizar y documentar el código (JavaDoc)
public class ThreadChatServer extends Thread {
	private static ArrayList<UdpChatClient> udpClients=new ArrayList<UdpChatClient>();
	private ChatServer chatServer;
	private ObjectInputStream fEntrada;
	private ObjectOutputStream fSalida;
	private Socket socket;
	private boolean stop;

	public ThreadChatServer(ChatServer chatServer, Socket socket) {
		this.chatServer = chatServer;
		this.socket = socket;
		try {
			this.fEntrada = new ObjectInputStream(socket.getInputStream());
			this.fSalida = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static ArrayList<UdpChatClient> getUdpClients() {
		return ThreadChatServer.udpClients;
	}
	@Override
	public void run() {
		Message message = null; 

		//hay que leer los datos de identificación del cliente UDP
		//esperamos los datos del cliente UDP
		long count=0;
		try {
			message = (Message) fEntrada.readObject();
		} catch (ClassNotFoundException | IOException e2) {
			e2.printStackTrace();
		}
		UdpChatClient udpChatClient = message.getUdpChatClientFrom();

		switch (message.getOperacion()) {
		case ChatServer.LISTAR_USUARIOS:
			try {
				//si no existe se añade a la lista de clientes UDP
				if((count = ThreadChatServer.udpClients.stream().filter(u->u.getNickName().
						equals(udpChatClient.getNickName())
						&& u.getHostAddress().equals(udpChatClient.getHostAddress())).count())==0) {
					ThreadChatServer.udpClients.add(udpChatClient);
					this.chatServer.setUdpChatClients(udpClients);
				}
				//se devuelve la lista de clientes UDP al cliente que se ha conectado
				this.fSalida.writeObject(ThreadChatServer.udpClients);
				System.out.println("Cliente: " + udpChatClient.getNickName() + ", " + udpChatClient.getHostAddress() + ":" + udpChatClient.getUdpPort());
			} catch (IOException e1) {
				e1.printStackTrace();
			}finally {
				try {
					fSalida.close();
					fEntrada.close();
					this.socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}				
			break;

		case ChatServer.ELIMINAR_USUARIO:
			//si no existe se añade a la lista de clientes UDP
			if((count = ThreadChatServer.udpClients.stream().filter(u->u.getNickName().
					equals(udpChatClient.getNickName())
					&& u.getHostAddress().equals(udpChatClient.getHostAddress())).count())==1) {
				ThreadChatServer.udpClients.remove(udpChatClient);
				this.chatServer.setUdpChatClients(udpClients);
			}	
			break;
			
		default:
			break;
		}
	}
}