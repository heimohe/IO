package com.biosocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servernormal {
	public final synchronized static void run() {
		System.out.println("�����_ʼ");
		try {
			ServerSocket server = new ServerSocket(12345);
			System.out.println("�������ц��Ӷ˿�̖12345");
			while(true){
				Socket socket = server.accept();
				//�n���µĿ͑����B�M�����_һ���µľ���
				new Thread(new Serverhandler(socket)).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
