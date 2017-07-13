package com.niosocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class TCPServer {
	//缓冲区大小
	private static final int BufferSize = 1024;
	//超时时间，单位毫秒
	private static final int TimeOut = 3000;
	//本地监听端口
	private static final int ListenPort = 1978;
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		//创建选择器
		Selector selector = Selector.open();
		//打开监听信道
		ServerSocketChannel listenerChannel = ServerSocketChannel.open();
		//与本地端口绑定
		listenerChannel.socket().bind(new InetSocketAddress(ListenPort));
		//设置为非阻塞模式
		listenerChannel.configureBlocking(false);
		//将选择器绑定到监听信道，只有非阻塞信道才可以注册选择器。并在注册过程中指出
		listenerChannel.register(selector, SelectionKey.OP_ACCEPT);
		//创建一个处理协议的实现类，有它来具体操作
		TCPProtocol protocol = new TCPProtocolImpl(BufferSize);
		
		//反复循环，等待IO
		while(true) {
			//等待某信道就绪（或超时）
			if(selector.select(TimeOut)==0){
				//监听注册通道，当其中有注册的IO。操作可以进行时，该函数返回，并将对应的selectionkey加入selected-key
//				System.out.println("独自等待");
				continue;
			}
			//取得迭代器selectkeys()中包含了每个准备好某一I/O操作的信道selectionkey
			//selected-key Iterator代表了所有通过select()方法监测到可以进行IO操作的channel
			//,这个集合可以通过selectedkeys()拿到
			Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
			while(keyIter.hasNext()){
				SelectionKey key = keyIter.next();
				SelectionKey key1;
				if(keyIter.hasNext()){
					key1 = keyIter.next();
				}
				try {
					if(key.isAcceptable()){
						//有客户端链接请求时
						protocol.handleAccept(key);
					}
					if(key.isReadable()){
						//判断是否有数据发送过来
						//从客户端读取数据
						protocol.handleRead(key);
					}
					if(key.isValid() && key.isWritable()){
						//判断数据有效及科研发送客户端
						//客户端可写时
						protocol.handleWrite(key);
					}
				}catch (Exception e) {
					//出现IO异常，如客户端端来连接时，移除处理过的键
					keyIter.remove();
					continue;
				}
				//移除处理过的键
				keyIter.remove();
			}
		}
	}

}
