package com.niosocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class TCPClient {

	//�ŵ�ѡ����
	private Selector selector;
	//�������ͨ�ŵ��ŵ�
	SocketChannel socketchannel;
	//Ҫ���ӵķ�����ip��ַ
	private String hostip;
	//Ҫ���ӵ�Զ�̷������ڼ����Ķ˿�
	private int hostlistenningport;
	
	public TCPClient(String hostip,int hostlistenningport) throws IOException {
		this.hostip = hostip;
		this.hostlistenningport = hostlistenningport;
		initialize();
	}
	/*
	 * ��ʼ��
	 * 
	 * */
	private void initialize() throws IOException{
		//�򿪼����ŵ�������Ϊ������ģʽ
		socketchannel = SocketChannel.open(new InetSocketAddress(hostip,hostlistenningport));
		socketchannel.configureBlocking(false);
		
		//�򿪲�ע��ѡ�������ŵ�
		selector = Selector.open();
		socketchannel.register(selector, SelectionKey.OP_READ);
		
		//������ȡ�߳�
		new TCPClientReadThread(selector);
	}
	
	/*
	 * �����ַ�����������
	 * */
	public void sendMsg(String message) throws IOException {
		ByteBuffer writebuffer = ByteBuffer.wrap(message.getBytes("UTF-8"));
		socketchannel.write(writebuffer);
	}
	
	static TCPClient client;
	static boolean mflag = true;
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		client = new TCPClient("192.168.1.113", 1978);
		new Thread() {
			public void run() {
				try{
					client.sendMsg("���!NIO! send to server ");
					while(mflag){
						Scanner scan =  new Scanner(System.in); //������������
						String string = scan.next();
						client.sendMsg(string);
					}
				}catch (Exception e) {
					mflag = false;
					e.printStackTrace();
				}finally{
					mflag = false;
				}
				super.run();
			}
		}.start();
	}

}
