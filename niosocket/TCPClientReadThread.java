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
				//select����ֻ��ʹ��һ�Σ�����֮��ͻ��Զ�ɾ����ÿ�����ӵ���������ѡ�������Ƕ�����
				//����ÿ���п���IO����channel��Ӧ��selectionkey
				for(SelectionKey sk : selector.selectedKeys()){
					//�����selectionkeys��Ӧ��channel���пɶ�������
					if(sk.isReadable()){
						//ʹ��NIO��ȡ channel�е�����
						SocketChannel sc =  (SocketChannel) sk.channel();	//��ȡͨ�� ��Ϣ
						ByteBuffer buffer = ByteBuffer.allocate(1024); //���仺������С
						sc.read(buffer); //��ȡͨ����������ݷ��ڻ�����
						buffer.flip(); 	//���ô˷���Ϊһϵ��ͨ��д�����Ի�ȡ��������׼��
						//���ֽ�ת��ΪUTF-16���ַ���
						String receivedString = Charset.forName("UTF-8").newDecoder().decode(buffer).toString();
						//����̨��ӡ����
						System.out.println("���յ����Է�����"+sc.socket().getRemoteSocketAddress()+"����Ϣ"+receivedString);
						//Ϊ�´ζ�ȡ��׼��
						sk.interestOps(SelectionKey.OP_READ);
					}
					//ɾ�����ڴ����selectionkey
					selector.selectedKeys().remove(sk);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
