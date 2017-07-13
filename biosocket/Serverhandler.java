package com.biosocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Serverhandler implements Runnable {
	private Socket socket;
	public Serverhandler(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			String expression;
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(true){
				if((expression = in.readLine())==null) break;
				System.out.println("服務器接收到消息為==>"+expression);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
