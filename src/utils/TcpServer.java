package utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
	
	private Integer port;
	private ServerSocket serverSocket;
	private BufferedReader bufferedReader;
	private DataOutputStream output;
	
	public TcpServer(Integer port) {
		this.port = port;
	}
	
	public void exec(){
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("TCPサーバ接続待ち");
			Socket socket = serverSocket.accept();
			System.out.println("TCPサーバ接続中");
			
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new DataOutputStream(socket.getOutputStream());
			String moji = bufferedReader.readLine();
			
			System.out.println("　入力:" + moji);
			output.writeChars(moji + 2);
			System.out.println("　出力:" + moji + 2);
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				bufferedReader.close();
				output.close();
				serverSocket.close();
				System.out.print("TCPサーバ切断");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
