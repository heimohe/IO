package com.net;

import java.io.IOException;
import java.util.Random;

public class Test {
	  	//����������  
    public static void main(String[] args) throws InterruptedException {  
        //���з�����  
        new Thread(new Runnable() {  
            public void run() {  
                try {  
                    ServerNormal.start();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }).start();  
        //����ͻ������ڷ���������ǰִ�д���  
        Thread.sleep(100);  
        //���пͻ���   
        char operators[] = {'+','-','*','/'};  
        final Random random = new Random(System.currentTimeMillis());  
        new Thread(new Runnable() {  
            @SuppressWarnings("static-access")  
            public void run() {  
                while(true){  
                    //��������������ʽ  
                    String expression = random.nextInt(10)+""+operators[random.nextInt(4)]+(random.nextInt(10)+1);  
                    Client.send(expression);  
                    try {  
                        Thread.currentThread().sleep(random.nextInt(1000));  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
        }).start();  
    }  
}
