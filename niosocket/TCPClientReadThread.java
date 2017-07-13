package com.niosocket;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class TCPClientReadThread implements Runnable {

	private Selector selector;
	
	public TCPClientReadThread(Selector selector){
		this.selector = selector;
		new Thread(this).start();
	}
	
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(selector.select() > 0){
				//select方法只能使用一次，用了之后就会自动删除，每个连接到服务器的选择器都是独立的
				//遍历每个有可用IO操作channel对应的selectionkey
				for(SelectionKey sk : selector.selectedKeys()){
					//如果该selectionkeys对应的channel中有可读的数据
					if(sk.isReadable()){
						//使用NIO读取 channel中的数据
						SocketChannel sc =  (SocketChannel) sk.channel();	//获取通道 信息
						ByteBuffer buffer = ByteBuffer.allocate(1024); //分配缓冲区大小
						sc.read(buffer); //读取通道里面的数据放在缓冲区
						buffer.flip(); 	//调用此方法为一系列通道写入或相对获取操作做好准备
						//将字节转化为UTF-16的字符串
						String receivedString = Charset.forName("UTF-8").newDecoder().decode(buffer).toString();
						//控制台打印出来
						System.out.println("接收到来自服务器"+sc.socket().getRemoteSocketAddress()+"的信息"+receivedString);
						//为下次读取做准备
						sk.interestOps(SelectionKey.OP_READ);
					}
					//删除正在处理的selectionkey
					selector.selectedKeys().remove(sk);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
