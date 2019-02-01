package stuRecord;

import java.io.*;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import stuRecordBean.StuRecBean;
import stuRecordBean.StuRecBeanService;

@WebServlet("/stuRecord/ReadStuRec.do")
public class ReadStuRec extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=utf-8");

		StuRecBeanService srBnSrv = new StuRecBeanService();
		StuRecBean srBn = new StuRecBean();

		if ((null != req.getParameter("varReadOne")) &&
				(req.getParameter("varReadOne").equals("確定")) ) 
		{
			try {
				srBnSrv.selectStuRec(req.getParameter("stuID"));
				srBn.setReadBy("byId");
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

		}
		else if ((null != req.getParameter("varDeleteOne")) &&
				(req.getParameter("varDeleteOne").equals("確定")) ) 
		{
			try {
				srBnSrv.deleteStuRec(req.getParameter("stuID"));
				srBn.setReadBy("byId");
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

		}
		else if ( (null != req.getParameter("varReadAll")) &&
				(req.getParameter("varReadAll").equals("確定")))
		{
			try {
				srBnSrv.setStuRecBnList(srBnSrv.selectStuRecBeanList());
				srBn.setReadBy("byAll");
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

		}

		// resp.sendRedirect("Read_Q.jsp");
		getServletContext().getRequestDispatcher(
				"/stuRecord/stuRecView.jsp").forward(req, resp);

	}
}
