package com.biosocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servernormal {
	public final synchronized static void run() {
		System.out.println("線程開始");
		try {
			ServerSocket server = new ServerSocket(12345);
			System.out.println("服務器已啟動端口號12345");
			while(true){
				Socket socket = server.accept();
				//檔有新的客戶端連進來，就開一個新的線程
				new Thread(new Serverhandler(socket)).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
