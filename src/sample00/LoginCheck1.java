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
			//�F�؍ς݂ɃZ�b�g
			session.setAttribute("login", "OK");
			
			//�{���̃A�N�Z�X��֔�΂�
			session.setAttribute("target", "/sample00/Kizi");
			String target = (String)session.getAttribute("target");
			response.sendRedirect(target);
		}else{
			//�F�؂Ɏ��s������A���O�C����ʂɖ߂�
			session.setAttribute("status", "Not Auth");
			response.sendRedirect("/sample00/hello");
		}
	}
	
	//DB�ڑ��E�ؒf
	protected boolean accessDB(String name, String password){
		//DB�̃J����
		final int SAMPLE00_ID = 1;
		final int SAMPLE00_NAME = 2;
		final int SAMPLE00_PASSWORD = 3;
		
		boolean isCorrectPassword = false;
		
		//jdbc�ǂݍ���
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
	
	//DB���J��
	protected void openDB(){
		//�ڑ�������
		String url = "jdbc:postgresql://localhost:5432/sample00";
		String dbUser = "postgres";
		String dbPassword = "yROTZgqpb";
		
		//DB�ڑ�
		try {
			DriverManager.setLogWriter(new java.io.PrintWriter(System.out));
			conn = DriverManager.getConnection(url, dbUser, dbPassword);
			
			//�����R�~�b�gOFF
			conn.setAutoCommit(false);
			
			//�A�J�E���g����T��
			//���͂��ꂽ�A�J�E���g�����܂ލs��T��
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	ResultSet rset = null;
	
	//DB�����
	protected void closeDB(){
		try {
			//DB�ؒf
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
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(Objects.equals(password, resultPassword)){
			//�p�X���[�h���}�b�`���Ă���ꍇ
			isCorrectPassword = true;
		}else{
			//�p�X���[�h���}�b�`���Ă��Ȃ��ꍇ
			isCorrectPassword = false;
		}
		
		return isCorrectPassword;
	}
	
	//���R�[�h�����@�\
	protected int searchRecords(int id){
		//���O�ɂ�錟��
		//TODO���R�[�h�ɓ��͂��ꂽ���O���܂ނ��̂����邩�m�F����B
		
		
		//TODOtrue�Ȃ疼�O���܂�
		//TODOfalse�Ȃ疼�O���܂܂Ȃ�
		
		return 0;
	}
	
	//���R�[�h�ǉ��@�\
	protected String addRecords(){
		//true�Ȃ�A���R�[�h�̓o�^�ɐ������܂����B�ƕ\������
		//false�Ȃ�A���R�[�h�̓o�^�Ɏ��s���܂����B�ƕ\������
		
		return "";
	}
	
	//���R�[�h�폜�@�\
	protected String removeRecords(){
		//���͂��ꂽ���O���܂ރ��R�[�h�����邩�`�F�b�N
//		searchRecords();
		//true�Ȃ�A���R�[�h�̍폜�ɐ������܂����B�ƕ\������
		//false�Ȃ�A���R�[�h�̍폜�Ɏ��s���܂����B�ƕ\������
		return "";
	}

	//DB��̃A�J�E���g���ƈ�v���Ă��邩���ׂ�
	protected boolean checkDBUser(String user, String password){
		if(user == null || user.length() == 0 || password == null || password.length() == 0){
			return false;
		}
		
		return accessDB(user, password);
	}
	
	protected boolean authUser(String user, String pass){
		//���[�U���ƃp�X���[�h�����͂���Ă���ΔF�؂���B
		if(user == null || user.length() == 0 || pass == null || pass.length() == 0){
			return false;
		}
		
		return true;
	}
}
