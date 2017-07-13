package com.niosocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Date;

public class TCPProtocolImpl implements TCPProtocol {

	private int buffersize;
	public TCPProtocolImpl(int buffersize) {
		this.buffersize = buffersize;
	}

	

	public void handleAccept(SelectionKey key) throws IOException {
		//返回创建此键的通道，接受客户端建立连接的请求，并返回socketchannel对象
		SocketChannel clientchannel = ((ServerSocketChannel)key.channel()).accept();
		//非阻塞式
		clientchannel.configureBlocking(false);
		//注册到selector
		clientchannel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(buffersize));
	}

	public void handleRead(SelectionKey key) throws IOException {
		//获得与客户端通信的信道
		SocketChannel clientChannel = (SocketChannel) key.channel();
		//得到并清空缓冲区
		ByteBuffer buffer = (ByteBuffer) key.attachment();
		buffer.clear();
		//读取信息获得读取的字节数
		long bytesRead = clientChannel.read(buffer);
		if(bytesRead == -1){
			//没有读到内容的情况
			clientChannel.close();
		}else{
			//将缓冲区准备为数据传出状态
			buffer.flip();
			//将字节转化为UTF-16的字符串
			String receivedString  = Charset.forName("UTF-8").newDecoder().decode(buffer).toString();
			//控制台打印出来
			System.out.println("接受来自"+clientChannel.socket().getRemoteSocketAddress()+"的消息"+receivedString);
			//准备发送的 文本
			String sendString = "你好，客户端。@"+new Date().toString()+",已经收到你的消息"+receivedString;
			buffer  = ByteBuffer.wrap(sendString.getBytes("UTF-8"));
			clientChannel.write(buffer);
			//设置为下一次读取 或是写入做准备
			key.interestOps(SelectionKey.OP_READ|SelectionKey.OP_WRITE);
			
		}
		
	} 

	public void handleWrite(SelectionKey key) throws IOException {
		// TODO Auto-generated method stub

	}

}
