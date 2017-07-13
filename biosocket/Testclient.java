package com.biosocket;

public class Testclient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//œyÔ‡·þ„ÕÆ÷
		new Thread(new Runnable() {

			public void run() {
				while(true) {
					//Scanner scan = new Scanner(System.in);
					//String expression = scan.nextLine();
					client.send();
				}
			}
			
		} ).start();

	}

}
