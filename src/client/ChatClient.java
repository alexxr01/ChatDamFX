package client;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;
import util.Chateable;
import util.Message;
// Importaciones necesarias para javafx
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

//TODO: revisar, optimizar y documentar el código (JavaDoc)
public class ChatClient implements Chateable {
	
	private final int SERVER_PORT = 9999;
	private String SERVER_ADDRESS = null; //"192.168.10.194";
	private UdpChatClient udpChatClientTo;
	private UdpChatClient udpChatClientFrom;
	private ArrayList<UdpChatClient> udpChatClients;
	private Socket socket;
	private ObjectOutputStream fSalida;
	private ObjectInputStream fEntrada;
	private String nickName;
	private DatagramSocket socketUDP;
	private Message messageSent;
	private Message messageReceived;
	
	// Métodos y propiedades generadas por y para la interfaz JavaFX
	@FXML
    private Button botonConectar;
    @FXML
    private TextField introducirIp;

    @FXML
    void conectarServidor(ActionEvent event) {
    	SERVER_ADDRESS = introducirIp.getText();
    	if (this.socket == null) {
    		mensajeError();
			return;
		} else {
			try {
			    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vistas/listausuarios.fxml"));
			    Parent root1 = (Parent) fxmlLoader.load();
			    Stage stage = new Stage();
			    stage.setTitle("Lista de usuarios");
			    stage.setScene(new Scene(root1));  
			    stage.show();
			} catch (Exception e) {
				System.out.println("Error al cargar la ventana de usuarios");
				e.printStackTrace();
			}
		}
    }
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		ChatClient chatClient = new ChatClient();

		System.out.print("Introduzca su nombre:");
		chatClient.nickName = sc.next();

		if(chatClient.getUdpClients(chatClient.nickName)) {	
			try {
				chatClient.socketUDP = new DatagramSocket(chatClient.udpChatClientFrom.getUdpPort());
			} catch (SocketException e) {
				e.printStackTrace();
			}

			new ThreadChatClient(chatClient).start();
			chatClient.menu();
			System.out.println("Gracias por usar el servicio!");
		}

	}
	public void menu() {
		Scanner sc = new Scanner(System.in);
		int option=0;		
		//hay que actualizar la lista de clientes del chat
		this.getUdpClients(this.nickName);
		while(option!=4) {
			this.clearScreen();
			System.out.println("Elija  una opción:");
			option = sc.nextInt();

			//TODO: definir opciones de menú para: 1. listar usuarios con los que chatear
			//2. seleccionar usuario con el chatear, 3. chatear con usuario seleccionado 
			//y 4. salir (finaliza el programa)
			switch(option) {
			case 1:
				//hay que actualizar la lista de clientes del chat
				this.getUdpClients(this.nickName);

				System.out.println("> Usuarios en el chat");
				//se muestra la lista de clientes de chat con los que conversar
				
				//se ha modificado el código anterior para mostrar todos los clientes
				//de chat con distinto nombre, esto permite probar el código
				//con la misma IP y mismo equipo
				if(this.udpChatClients.size()==1) {
					System.out.println("NO hay usuarios en el chat");
				}else {
					this.udpChatClients.stream().filter(e->!e.getNickName()
							.equals(this.nickName)).forEach(e->System.out.println(e.getNickName()
									+ " " + (this.udpChatClients.indexOf(e) + 1)));
				}
				break;
			case 2:
				if(this.udpChatClients.size()>1) { 
					System.out.print("Introduzca el nombre del usuario con el conversar:");
					String nickname = sc.next();
					this.udpChatClientTo = this.udpChatClients.stream()
							.filter(e->e.getNickName().equals(nickname)).findFirst().get();

				}else {
					System.out.println("No hay usuarios en el chat");
				}
				break;
			case 3:
				if(this.udpChatClientTo!=null) {
					//ya tenemos el cliente UDP con el que hablar
					//comienza la conversación
					this.sendMessage(this.udpChatClientTo, LISTAR_USUARIOS);
				}else {
					System.out.println("El usuario seleccionado no existe, inténtelo de nuevo!");
				}
				break;
			case 4:
				this.exit();
				break;
			default:
				System.out.println("Opción incorrecta...");
				break;
			}
		}
	}

	public void exit() {
		String hostAddress;
		Message message = null;
		try {
			//hostAddress = InetAddress.getLocalHost().getHostAddress();
			hostAddress = this.getMyLocalIp();

			this.udpChatClientFrom=new UdpChatClient(nickName,
					hostAddress);

			//preguntamos al servidor, conexión TCP, por la lista de clientes para el chat
			this.socket = new Socket(SERVER_ADDRESS,SERVER_PORT);
			if(this.socket==null) {
				return;
			}
			//se obtiene la lista de clientes UDP para chatear
			try {
				this.fSalida = new ObjectOutputStream(this.socket.getOutputStream());
				// Hay que enviar un objeto del tipo Message con el Udpclientfrom
				// del usuario que envia la petición, recogiendo tambien el tipo de operación.
				message = new Message(udpChatClientFrom, null, "", ELIMINAR_USUARIO);
				this.fSalida.writeObject(message);
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					this.fSalida.close();
					this.socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean getUdpClients(String nickName) {
		String hostAddress;
		Message message = null;
		try {
			//hostAddress = InetAddress.getLocalHost().getHostAddress();
			hostAddress = this.getMyLocalIp();

			this.udpChatClientFrom=new UdpChatClient(nickName,
					hostAddress);

			//preguntamos al servidor, conexión TCP, por la lista de clientes para el chat
			this.socket = new Socket(SERVER_ADDRESS,SERVER_PORT);
			//después de la conexión al servidor obtengo el puerto TCP en el client
			this.udpChatClientFrom.setUdpPort(this.socket.getLocalPort());
			if(this.socket==null)return false;
			//se obtiene la lista de clientes UDP para chatear
			try {
				this.fSalida = new ObjectOutputStream(this.socket.getOutputStream());
				// Hay que enviar un objeto del tipo Message con el Udpclientfrom
				// del usuario que envia la petición, recogiendo tambien el tipo de operación.
				message = new Message(udpChatClientFrom, null, "", LISTAR_USUARIOS);
				this.fSalida.writeObject(message);

				this.fEntrada = new ObjectInputStream(this.socket.getInputStream());
				this.udpChatClients = (ArrayList<UdpChatClient>) this.fEntrada.readObject();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}finally {
				try {
					this.fEntrada.close();
					this.fSalida.close();
					this.socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public String getMyLocalIp() {
		Enumeration<NetworkInterface> networkInterfaceEnumeration = null;
		String ip = "";
		try {
			networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
			while( networkInterfaceEnumeration.hasMoreElements()){
				for ( InterfaceAddress interfaceAddress : networkInterfaceEnumeration.nextElement().getInterfaceAddresses())
					if ( interfaceAddress.getAddress().isSiteLocalAddress())
						ip = interfaceAddress.getAddress().getHostAddress();
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return ip;
	}
	public void sendMessage(UdpChatClient udpChatClientTo, int operacion) {
		try {
			Scanner sc = new Scanner(System.in);
			String mensajePeticion="";

			InetAddress hostServidor = 
					InetAddress.getByName(udpChatClientTo.getHostAddress());
			int puertoServidor = udpChatClientTo.getUdpPort();

			// Construimos el DatagramPacket que contendrá la 
			//respuesta
			do {
				System.out.println("Mensaje (* para salir) -> ");
				mensajePeticion = sc.nextLine();
				if(mensajePeticion.equals("*")) break;
				//Construimos un datagrama para enviar el mensaje al 
				//servidor
				this.messageSent = new Message(this.udpChatClientFrom,
						this.udpChatClientTo, mensajePeticion, operacion);
				//se indicia al hilo del chat quien es el destinatario
				//del mensaje

				ByteArrayOutputStream bytes = new ByteArrayOutputStream(1000);
				ObjectOutputStream os = new ObjectOutputStream (bytes);
				os.writeObject(this.messageSent);
				os.flush();

				DatagramPacket peticion =
						new DatagramPacket(bytes.toByteArray(), 
								bytes.size(), 
								hostServidor,puertoServidor);

				// Enviamos el datagrama
				this.socketUDP.send(peticion);
				mensajePeticion = "";
				os.close();
			}while(!mensajePeticion.equals("*"));
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
	}
	public String reciveMessage() {
		byte[] mensajeRespuesta = new byte[1000];
		String responseMessage;
		try {			
			DatagramPacket respuesta =
					new DatagramPacket(mensajeRespuesta, 
							mensajeRespuesta.length);

			this.socketUDP.receive(respuesta);

			ByteArrayInputStream byteStream = new
					ByteArrayInputStream(mensajeRespuesta);
			ObjectInputStream is = new
					ObjectInputStream(new BufferedInputStream(byteStream));
			Object o = is.readObject();
			is.close();

			if(!(o instanceof Message)) return "";
			this.messageReceived = (Message) o;

			// devolvemos la respuesta del cliente a la salida estandar
			responseMessage = new String(this.messageReceived.getMessage());
		}catch(IOException | ClassNotFoundException e) {
			System.out.println(e);
			responseMessage = "";
		}
		return responseMessage;
	}
	public void clearScreen() {
		try {
			Runtime.getRuntime().exec("clear");
		} catch (IOException e) {
			e.printStackTrace();
		}   
	}
	
	@FXML
	private void mensajeError() {
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setHeaderText(null);
	    alert.setTitle("Error");
	    alert.setContentText("La dirección IP [" + SERVER_ADDRESS + "] no conecta con ningún servidor.");
	    alert.showAndWait();
	}
	
	public UdpChatClient getUdpChatClientTo() {
		return this.udpChatClientTo;
	}
	public UdpChatClient getUdpChatClientFrom() {
		return this.udpChatClientFrom;
	}
	public Message getMessageSent() {
		return this.messageSent;
	}
	public Message getMessageReceived() {
		return this.messageReceived;
	}
}
