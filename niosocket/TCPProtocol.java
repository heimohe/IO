package com.niosocket;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface TCPProtocol {
	
	/*
	 * 接受一个socketchannel的处理
	 * */
	void handleAccept(SelectionKey key) throws IOException;

	/*
	 * 从一个socketchannel读取信息的处理
	 * */
	void handleRead(SelectionKey key) throws IOException;

	/*
	 * 向一个socketchannel写入信息的处理
	 * */
	void handleWrite(SelectionKey key) throws IOException;

}
