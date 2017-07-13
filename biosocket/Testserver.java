package com.biosocket;

import java.util.Scanner;

import com.net.Client;

public class Testserver {

	/**
	 * @author wyl
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		//œyÔ‡·þ„ÕÆ÷
		new Thread(new Runnable() {

			public void run() {
				Servernormal.run();
			}
			
		} ).start();
		
		
	}

}
