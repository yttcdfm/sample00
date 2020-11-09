package utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.stream.Stream;

public class TcpServer {
	
	private ServerSocket serverSocket;
	private Integer port;
	private BufferedReader bufferedReader;
	private DataOutputStream output;
	private Boolean isEnd;
	
	public TcpServer(Integer port) {
		this.port = port;
	}
	
	private String getMacAddress(Inet4Address inetAddress) {
		String macAddress = "";
		
		try {
			NetworkInterface networkInterface = NetworkInterface.getByInetAddress(inetAddress);
			byte[] str = networkInterface.getHardwareAddress();
			
			if(str.length == 6) {
				for(int i = 0; i < str.length; i++) {
					macAddress += String.format("%02x", str[i]);
					macAddress += i <= 4 ? ":" : "";
				}
			}else {
				macAddress = "xx:xx:xx:xx:xx:xx";
			}
			
		} catch (SocketException e) {
			e.printStackTrace();
		}

		return macAddress;
	}
	
	public void exec(){		
		try {
			System.out.println("マシン情報");
			Inet4Address inetAddress = (Inet4Address) Inet4Address.getLocalHost();
			System.out.println("  * ホスト名\t" + inetAddress.getCanonicalHostName());
			System.out.println("  * IPアドレス\t" + inetAddress.getHostAddress());
			String macAddress = getMacAddress(inetAddress);
			System.out.println("  * MACアドレス\t" + macAddress);
			
			serverSocket = new ServerSocket(port);
			System.out.println("TCPサーバ接続待ち");
			Socket socket = serverSocket.accept();
			System.out.println("TCPサーバ接続中");
			
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new DataOutputStream(socket.getOutputStream());

			execProccess();
			
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
	
	public void execProccess() throws IOException {
		String moji = bufferedReader.readLine();	//byte取り出したいときはDataInputStream
		System.out.println("　入力:" + moji);
		output.writeChars(moji + 2);
		System.out.println("　出力:" + moji + 2);
		setIsEnd(true);
	}
	
	public void setIsEnd(Boolean isEnd) {
		this.isEnd = isEnd;
	}
	
	public Boolean getIsEnd() {
		return this.isEnd;
	}
	
	public static void main(String[] args) {
		TcpServer tcpServer = new TcpServer(10001);
		tcpServer.exec();
	}
}
