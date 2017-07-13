package com.niosocket;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface TCPProtocol {
	
	/*
	 * ����һ��socketchannel�Ĵ���
	 * */
	void handleAccept(SelectionKey key) throws IOException;

	/*
	 * ��һ��socketchannel��ȡ��Ϣ�Ĵ���
	 * */
	void handleRead(SelectionKey key) throws IOException;

	/*
	 * ��һ��socketchannelд����Ϣ�Ĵ���
	 * */
	void handleWrite(SelectionKey key) throws IOException;

}
