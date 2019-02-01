package examDo;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import qBankBean.QBean;
import qBankBean.QListBean;
import qBankBean.QListBeanService;
import qBankBean.QBeanService;
import qExamPaperBean.QExPaperBean;
import qExamPaperBean.QExPaperBeanService;
import examRecordBean.ERecBean;
import examRecordBean.ERecBeanService;

@WebServlet("/examDo/ExamScore.do")
public class ExamScore extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");

		ERecBean erBn = new ERecBean();
		ERecBeanService erBnSrv = new ERecBeanService();
		HttpSession session = req.getSession();
		erBn.setErId((String)session.getAttribute("stuExID"));
		erBn.setErEpaperId((String)session.getAttribute("epId"));

		
		PrintWriter out = response.getWriter();
		String[] ansArr = req.getParameterValues("examAns");

		String[] erArr = new String[3];
		
		String ansStr = "";
		String na ="";
		
		for(int i=0;i<ansArr.length;i++ )
		{
			if(na.equals(ansArr[i]))
				ansArr[i] = "0";
			System.out.println("ansArr[i]" +ansArr[i]);
			ansStr += ansArr[i];
		}
		
		erArr[0]= ansStr;
		erArr[2]= (String) session.getAttribute("epId");
				
		if (session.getAttribute("rightAnsList") != null) {
			
			List<String> rightAnsList = (List<String>) session.getAttribute("rightAnsList");
			int score = 100;
			// calculate score
			for (int i = 0; i < rightAnsList.size(); i++) {
				if (!ansArr[i].equals(rightAnsList.get(i)))  // 
					score -= (100 / rightAnsList.size());
			}

			// get the score
			erArr[1] = String.valueOf(score);
			
			// print exam result
			out.print("<title>考試結果:</title>");
			out.print("<style>body {background-color:#70c0c0;}</style>");
			out.print("<h1>考試結果:</h1>");
			out.print("<table style= 'text-align: left;' border='1' cellpadding='2' cellspacing='2'>");
       		out.print("<tr><th>題號</th><th>答題</th><th>解答</th></tr>");
       		
			for (int i = 0; i < rightAnsList.size(); i++) {
	       		out.print("<tr><td>" + String.valueOf(i+1)+ "</td>");
	       		out.print("<td>" + ansArr[i]+ "</td>");
	       		out.print("<td>" + rightAnsList.get(i) + "</td></tr>");
			}
       		out.print("</table>");
			out.print("<h1>分數:" + score + "  離開測驗:<input type='button' value='離開' onclick='window.close()'>"+"</h1>");
			
			//Mail the result
			out.print("<hr size=1 color=blue width=100%>");
			out.print("<h1>郵寄考試結果:</h1>");
			out.print("<form action='mail.do' method='post'>");
			out.print("<input type='submit' value='傳送'/><br>");
			out.print("寄件人：<input type='text' name='from' size='50'/><br>");
			out.print("收件人：<input type='text' name='to' size='50'/><br>");
			out.print("主　旨：<input type='text' name='subject' size='50' value='線上測驗考試結果'/><br><br>");
			out.print("<textarea name='text' rows='3' cols='50'>");
			out.print("准考證號碼:"+ (String)session.getAttribute("stuExID")+"&#10");
			out.print("考試分數:"+ score);
			out.print("</textarea>");
			out.print("</form>");

			// insert Exam Record 
       		try {
				erBnSrv.insertER(erBn.getErId(), erArr);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
