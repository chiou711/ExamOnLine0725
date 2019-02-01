package qExamPaper;

import java.io.*;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import qBankBean.QListBean;
import qBankBean.QListBeanService;
import qExamPaperBean.QExPaperBean;
import qExamPaperBean.QExPaperBeanService;


@WebServlet("/qExamPaper/QExPaperManage.do")
public class QExPaperManage extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		QExPaperBean ePaper = new QExPaperBean();

		QExPaperBeanService epBeanSrv = new QExPaperBeanService();

		// get exam paper list
		try {
			ePaper = epBeanSrv.selectEP(req.getParameter("EpId"));
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		// check if list is not empty
		System.out.println("ePaper.getEPaper()=" + ePaper.getEPaper());
        out.print("<style>body {background-color:#d0b0b0;}</style>");
		if (ePaper.getEPaper() != null) {
			out.print("<table style= 'border:5px solid rgb(0,0,120); background-color:#d0b0b0;' border='2' bordercolor='rgb(0,0,30)' cellpadding='2' cellspacing='2'>");
			out.print("<tr><td style='background-color:rgb(200,200,0)'><b>考卷編號: </b>" + ePaper.getEPaperId() +"</td></tr>");
			out.print("<tr><td style='background-color:rgb(200,155,100)'><b>考卷題號列表: </b><br>" + ePaper.getEPaper().replace(';', ' ')+"</td></tr>");
			if (req.getParameter("ManageEp").equals("查詢考卷")) {
				// ID list: split to array
				String[] EPaperArr = ePaper.getEPaper().split("[;]");
				for (int i = 0; i < EPaperArr.length; i++) {
					QListBean qBean4L = new QListBean();
					QListBeanService qBean4ListSrv = new QListBeanService();
					System.out
							.println("EPaperArr[" + i + "] = " + EPaperArr[i]);
					qBean4L.setQues_id(EPaperArr[i]);
					try {
						qBean4L = qBean4ListSrv.read_qBank_by_ID(EPaperArr[i]);
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
					out.print("<tr>");
					out.print("<td>");
					out.print(qBean4L.getQues_whole());
					out.print("</td>");
					out.print("</tr>");
				}

			}
			// Cancel
			else if (req.getParameter("ManageEp").equals("刪除考卷")) {

				try {
					epBeanSrv.deleteEP(ePaper.getEPaperId());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
