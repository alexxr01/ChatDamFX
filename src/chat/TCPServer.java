package chat;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TCPServer {
    public static void main(String args[]) throws Exception {
        DataOutputStream outToClient;
        BufferedReader inFromClient;
        Scanner sc = new Scanner(System.in);
        // Message message;
        String message;
        ServerSocket welcomeSocket = new ServerSocket(6100);

        while(true) {
            // El servidor queda a la espera de una conexión, debe ser aceptada o no
            Socket connectionSocket = welcomeSocket.accept();

            // se ha recibido una conexión del lado del cliente
            // se obtiene el flujo de lectura para escuchar al cliente
            inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            // se escucha al cliente
            message = inFromClient.readLine();

            // el cliente ha hablado y la información se convierte a mayúsculas
            // y se responde al cliente
            System.out.println(message);
            // envío de la respuesta al cliente
            message=sc.nextLine();
            outToClient.writeBytes(message);
            connectionSocket.close();
        }
    }
}
