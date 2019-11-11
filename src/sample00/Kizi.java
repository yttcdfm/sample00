package sample00;

import java.io.*;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Kizi extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws IOException, ServletException{
		
		response.setContentType("text/html; charset=Shift_JIS");
	    PrintWriter out = response.getWriter();
	    
	    HttpSession session = request.getSession(false);
	    
		out.println("<html>");
		out.println("<head>");
		out.println("<title>ようこそ！</title>");
		out.println("</head>");
		
		if(session == null){
			out.println("<p>初回訪問です</p>");
			session = request.getSession(true);
			
			long createTime = session.getCreationTime();
			Date createDate = new Date(createTime);
			
			out.println("<p>");
			out.println("セッション開始時間：" + createDate + "<br>");
			out.println("</p>");
			
			session.setAttribute("visited", "1");
		}else{
			long createTime = session.getCreationTime();
			Date createDate = new Date(createTime);
			
			long accessTime = session.getLastAccessedTime();
			Date accessDate = new Date(accessTime);
			
			out.println("<p>");
			out.println("セッション開始時間：" + createDate + "<br>");
			out.println("最終アクセス時間：" + accessDate);
			
			String visitedStr = (String)session.getAttribute("visited");
			int visited = Integer.parseInt(visitedStr);
			visited++;
			
			out.println("<p>訪問回数は");
			out.println(visited);
			out.println("回目です</p>");
			
			session.setAttribute("visited", Integer.toString(visited));
		}
		
		out.println("<a href=\" /sample00/Kizi\">再表示</a>");
		
		out.println("<body>");
		out.println("<p>");
		out.println("<input type=\"button\" value=\"検索機能\" onclick=\"history.back()\">");
		out.println("</p>");
		out.println("</body>");
		out.println("</html>");
	}
}
