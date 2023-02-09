package client;

import java.util.Scanner;

public class ThreadChatClient extends Thread {
	private ChatClient chatClient;
	
	public ThreadChatClient(ChatClient chatClient) {
		this.chatClient = chatClient;
		this.setName("Chat de " + chatClient.getUdpChatClientFrom().getNickName());
	}
	
	@Override
	public void run() {
		String message="";
		Scanner sc = new Scanner(System.in);
		while(!message.equals("*")) {
			message = this.chatClient.reciveMessage();
			this.chatClient.clearScreen();
			System.out.println(this.chatClient.getMessageReceived()
					.getUdpChatClientFrom().getNickName() +
					" dice ->" + message);
		}
		sc.close();
	}
}
