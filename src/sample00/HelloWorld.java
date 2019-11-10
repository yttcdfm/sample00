package sample00;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HelloWorld extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException{

    response.setContentType("text/html; charset=Shift_JIS");
    PrintWriter out = response.getWriter();

    out.println("<html>");
    out.println("<head>");
    out.println("<title>���O�C���y�[�W</title>");
    out.println("</head>");
    out.println("<body>");

    out.println("<h1>���O�C�����</h1>");

    HttpSession session = request.getSession(true);

    /* �F�؎��s����Ăяo���ꂽ�̂��ǂ��� */
    Object status = session.getAttribute("status");

    if (status != null){
      out.println("<p>�F�؂Ɏ��s���܂���</p>");
      out.println("<p>�ēx���[�U�[���ƃp�X���[�h����͂��ĉ�����</p>");

      session.setAttribute("status", null);
    }

    out.println("<form method=\"POST\" action=\"/sample00/LoginCheck\" name=\"LoginCheck\">");
    out.println("<table>");
    out.println("<tr>");
    out.println("<td>���[�U�[��</td>");
    out.println("<td><input type=\"text\" name=\"user\" size=\"32\"></td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td>�p�X���[�h</td>");
    out.println("<td><input type=\"password\" name=\"pass\" size=\"32\"></td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td><input type=\"submit\" value=\"login\"></td>");
    out.println("<td><input type=\"reset\" value=\"reset\"></td>");
    out.println("</tr>");
    out.println("</table>");
    out.println("</form>");

    out.println("</body>");
    out.println("</html>");
  }
}