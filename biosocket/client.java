package com.biosocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class client {

	public static void send() {
		try {
			Socket socket = new Socket("127.0.0.1", 12345);
			System.out.println("�͑��ˆ��ӳɹ�");
			// 2����ȡ���������������˷�����Ϣ
			// �򱾻���52000�˿ڷ����ͻ�����
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			// ��ϵͳ��׼�����豸����BufferedReader����
			PrintWriter write = new PrintWriter(socket.getOutputStream());
			// ��Socket����õ��������������PrintWriter����
			//3����ȡ������������ȡ�������˵���Ӧ��Ϣ 
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// ��Socket����õ�����������������Ӧ��BufferedReader����
			String readline;
			readline = br.readLine(); // ��ϵͳ��׼�������һ�ַ���
			while (!readline.equals("end")) {
				// ���ӱ�׼���������ַ���Ϊ "end"��ֹͣѭ��
				write.println(readline);
				// ����ϵͳ��׼���������ַ��������Server
				write.flush();
				// ˢ���������ʹServer�����յ����ַ���
				System.out.println("Client:" + readline);
				// ��ϵͳ��׼����ϴ�ӡ������ַ���
				System.out.println("Server:" + in.readLine());
				// ��Server����һ�ַ���������ӡ����׼�����
				readline = br.readLine(); // ��ϵͳ��׼�������һ�ַ���
			 } // ����ѭ��
			//4���ر���Դ 
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
