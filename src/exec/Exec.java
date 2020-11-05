package exec;

import utils.TcpServer;
import utils.ThreadTest;

public class Exec {
	
	public static void main(String[] args) {
		ThreadTest threadTest = new ThreadTest();
		threadTest.start();
		System.out.println("hogehoge1");
		System.out.println("hogehoge2");
		System.out.println("hogehoge3");
		System.out.println("fuga69");
	}
}
