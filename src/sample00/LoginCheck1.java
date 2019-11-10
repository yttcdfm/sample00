package sample00;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheck1 extends HttpServlet {
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html); charset=Shift_JIS");
		PrintWriter out = response.getWriter();
		
		String user = request.getParameter("user");
		String pass = request.getParameter("pass");
		
		HttpSession session = request.getSession(true);
		
		boolean check = checkDBUser(user, pass);
		if(check){
			//認証済みにセット
			session.setAttribute("login", "OK");
			
			//本来のアクセス先へ飛ばす
			session.setAttribute("target", "/sample00/Kizi");
			String target = (String)session.getAttribute("target");
			response.sendRedirect(target);
		}else{
			//認証に失敗したら、ログイン画面に戻す
			session.setAttribute("status", "Not Auth");
			response.sendRedirect("/sample00/hello");
		}
	}
	
	//DB接続・切断
	protected boolean accessDB(String name, String password){
		//DBのカラム
		final int SAMPLE00_ID = 1;
		final int SAMPLE00_NAME = 2;
		final int SAMPLE00_PASSWORD = 3;
		
		boolean isCorrectPassword = false;
		
		//jdbc読み込み
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
			openDB();
			isCorrectPassword = checkPassword(name, password);
			closeDB();
			
			return isCorrectPassword;
	}
	
	Connection conn = null;
	Statement stmt = null;
	
	//DBを開く
	protected void openDB(){
		//接続文字列
		String url = "jdbc:postgresql://localhost:5432/sample00";
		String dbUser = "postgres";
		String dbPassword = "yROTZgqpb";
		
		//DB接続
		try {
			DriverManager.setLogWriter(new java.io.PrintWriter(System.out));
			conn = DriverManager.getConnection(url, dbUser, dbPassword);
			
			//自動コミットOFF
			conn.setAutoCommit(false);
			
			//アカウント名を探す
			//入力されたアカウント名を含む行を探す
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	ResultSet rset = null;
	
	//DBを閉じる
	protected void closeDB(){
		try {
			//DB切断
			if(rset != null){
				rset.close();
			}
			
			if(stmt != null){
				stmt.close();
			}
			
			if(conn != null){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected boolean checkPassword(String name, String password){
		boolean isCorrectPassword = false;
		
		String sql = "SELECT password FROM sample00 WHERE sample00.name = '" + name + "'";
		
		String resultPassword = "";

		try {
			rset = stmt.executeQuery(sql);
			
			while(rset.next()){
				resultPassword = rset.getString(1);
				System.out.println(resultPassword);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(Objects.equals(password, resultPassword)){
			//パスワードがマッチしている場合
			isCorrectPassword = true;
		}else{
			//パスワードがマッチしていない場合
			isCorrectPassword = false;
		}
		
		return isCorrectPassword;
	}
	
	//レコード検索機能
	protected int searchRecords(int id){
		//名前による検索
		//TODOレコードに入力された名前を含むものがあるか確認する。
		
		
		//TODOtrueなら名前を含む
		//TODOfalseなら名前を含まない
		
		return 0;
	}
	
	//レコード追加機能
	protected String addRecords(){
		//trueなら、レコードの登録に成功しました。と表示する
		//falseなら、レコードの登録に失敗しました。と表示する
		
		return "";
	}
	
	//レコード削除機能
	protected String removeRecords(){
		//入力された名前を含むレコードがあるかチェック
//		searchRecords();
		//trueなら、レコードの削除に成功しました。と表示する
		//falseなら、レコードの削除に失敗しました。と表示する
		return "";
	}

	//DB上のアカウント情報と一致しているか調べる
	protected boolean checkDBUser(String user, String password){
		if(user == null || user.length() == 0 || password == null || password.length() == 0){
			return false;
		}
		
		return accessDB(user, password);
	}
	
	protected boolean authUser(String user, String pass){
		//ユーザ名とパスワードが入力されていれば認証する。
		if(user == null || user.length() == 0 || pass == null || pass.length() == 0){
			return false;
		}
		
		return true;
	}
}
