package com.niosocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class TCPClient {

	//信道选择器
	private Selector selector;
	//与服务器通信的信道
	SocketChannel socketchannel;
	//要连接的服务器ip地址
	private String hostip;
	//要连接的远程服务器在监听的端口
	private int hostlistenningport;
	
	public TCPClient(String hostip,int hostlistenningport) throws IOException {
		this.hostip = hostip;
		this.hostlistenningport = hostlistenningport;
		initialize();
	}
	/*
	 * 初始化
	 * 
	 * */
	private void initialize() throws IOException{
		//打开监听信道并设置为非阻塞模式
		socketchannel = SocketChannel.open(new InetSocketAddress(hostip,hostlistenningport));
		socketchannel.configureBlocking(false);
		
		//打开并注册选择器到信道
		selector = Selector.open();
		socketchannel.register(selector, SelectionKey.OP_READ);
		
		//启动读取线程
		new TCPClientReadThread(selector);
	}
	
	/*
	 * 发送字符串到服务器
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
					client.sendMsg("你好!NIO! send to server ");
					while(mflag){
						Scanner scan =  new Scanner(System.in); //键盘输入数据
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
