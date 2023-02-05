package chat;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class TCPClient {
    // Propiedades por cliente
    static String nickname, ip;
    public static void main(String args[]) throws Exception {
        Usuario usuario = new Usuario(nickname, ip);
        Scanner sc = new Scanner(System.in);
        String message, modifiedSentence;
        DataOutputStream outToServer;
        BufferedReader inFromServer;
        Socket clientSocket = null;
        //se obtiene el flujo de la consola para que el usuario pueda escribr
        //el mensaje al servidor
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Te vas a registrar como nuevo cliente.");
        System.out.println("Introduce nickname:");

        // Recogemos los datos
        nickname = sc.next(); // Recogemos el nickname
        usuario.setNombre(nickname);
        ip = InetAddress.getLocalHost().getHostAddress(); // Recogemos la ip
        usuario.setIp(ip);

        // Comprobaciones varias
        if (usuario.getNombre().length()>=3) {
            System.out.println("Bienvenid@ " + usuario.getNombre() + "! Escribe tu primer mensaje:");
        } else {
            System.out.println(usuario.getNombre() + " es muy corto, prueba con uno de al menos 3+ carácteres.");
            clientSocket.close();
        }

        ArrayList<String> usuarios = new ArrayList<String>();
        usuarios.add(usuario.getNombre());

        Collections.sort(usuarios);

        do {
            //se abre el socket con el servidor
            clientSocket = new Socket("localhost", 6100);
            //se obtiene el flujo de escritura del socket abierto
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            //se obtiene el flujo de lectura del socket abierto
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //se lee el mensaje desde la consola
            message = inFromUser.readLine();
            //se envía al servidor el mensaje
            for(String i : usuarios) {
                outToServer.writeBytes(i);
            }
            outToServer.writeBytes(usuario.getNombre() + " (" + usuario.getIp() + "), dice: " + message + "\n");
            //se espera la respuesta
            modifiedSentence = inFromServer.readLine();
            //se muestra la respuesta recibida
            System.out.println("SERVIDOR DICE: " + modifiedSentence);
            clientSocket.close();
        }while(!message.isEmpty());
        clientSocket.close();
    }
}