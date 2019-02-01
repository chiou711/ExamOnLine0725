package qBank;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import qBankBean.QBean;
import qBankBean.QListBean;
import qBankBean.QListBeanService;
import qBankBean.QBeanService;



@WebServlet("/qBank/ReadQ.do")
public class ReadQ extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException 
    {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

       	try 
       	{
            if(req.getParameter("radioBy").equals("ReadById"))
            {
                QBeanService.DB_connect();
                String qId = req.getParameter("q_id"); 
           		QBean qBean =new QBean();	
           		qBean.setQues_id(qId);
           		qBean = QBeanService.read_qBank_by_ID(qId);
            	
           		qBean.setReadBy("byId");
            	req.setAttribute("qBankConfBean",qBean);
            }
            else if(req.getParameter("radioBy").equals("ReadAll"))
            {
           		QBean qBCBean =new QBean();	
            	qBCBean.setReadBy("byAll");

            	QListBeanService.DB_connect();
            	QListBeanService qBServBean =new QListBeanService();	
            	List<QListBean> qBBeanList = qBServBean.getqContentList();
               	req.setAttribute("qBeanListService", qBBeanList); 
            }
       	} catch (ClassNotFoundException | SQLException e) 
       	{
				e.printStackTrace();
		}


//		resp.sendRedirect("Read_Q.jsp");
        getServletContext().getRequestDispatcher("/qBank/ReadQView.jsp").forward(req, resp);
        try {
	        	if(req.getParameter("radioBy").equals("ReadById"))
	        		QBeanService.con_close();
	        	else if(req.getParameter("radioBy").equals("ReadAll"))
	        		QListBeanService.con_close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
