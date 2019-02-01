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
import qExamPaperBean.QExPaperBeanService;

@WebServlet("/qExamPaper/QExPaperMake.do")
public class QExPaperMake extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response)
            throws ServletException, IOException 
    {
        req.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter(); 

        String[] EPaperArr =req.getParameterValues("EPaperArr");
    	QExPaperBeanService epBeanSrv = new QExPaperBeanService();
		
    	try {
			System.out.println(epBeanSrv.getEpMaxId());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
        out.print("<style>body {background-color:#d0b0b0;}</style>");

        // List
        if((null != req.getParameter("EditE")) && (req.getParameter("EditE").equals("新增考卷")) && (EPaperArr != null))
        {
        	// get list
	   		String epStr = "";
	   		out.print("<table style= 'text-align: left;' border='1' cellpadding='2' cellspacing='2'>");
	        for(int i=0;i<EPaperArr.length;i++)
	        {
	       		QListBean qBean4L =new QListBean();	
	       		QListBeanService qBean4ListSrv = new QListBeanService();
	       		qBean4L.setQues_id(EPaperArr[i]);
	       		try {
	       			qBean4L = qBean4ListSrv.read_qBank_by_ID(EPaperArr[i]);
					epStr += EPaperArr[i].concat(";");
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
		        //print
	       		out.print("<tr>");
	       		out.print("<td>");
	       		out.print( qBean4L.getQues_whole());
	       		out.print("</td>");
	       		out.print("</tr>");
	        }
	        try {
	        	epBeanSrv.insertEP(req.getParameter("EpId"), epStr);
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
        }
        //Clear
        else if( (null != req.getParameter("EditE")) && (req.getParameter("EditE").equals("清空考卷稿")))
        {
            System.out.println("清空考卷稿");
        }
        
        if( (null != req.getParameter("EditSearch") )  && (req.getParameter("EditSearch").equals("列出") || req.getParameter("highLightSearch").equals("亮顯")) )
         {
        	epBeanSrv.setEPaperKeyword(req.getParameter("EpKeyword"));
        	getServletContext().getRequestDispatcher("/qExamPaper/qExPaperSearch.jsp").forward(req, response);
        }
        

        
    }
}

