package sample00;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Kizi extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws IOException, ServletException{
		
		response.setContentType("text/html; charset=Shift_JIS");
	    PrintWriter out = response.getWriter();
	    
		out.println("<html>");
		out.println("<head>");
		out.println("<title>�悤�����I</title>");
		out.println("</head>");
		
		out.println("<body>");
		out.println("<H1>�悤�����I</H1>");
		out.println("<p>");
		out.println("<input type=\"button\" value=\"�����@�\\" onclick=\"history.back()\">");
		out.println("</p>");
		out.println("</body>");
		out.println("</html>");
	}
}
