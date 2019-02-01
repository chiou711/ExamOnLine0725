package examRecord;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examRecordBean.ERecBean;
import examRecordBean.ERecBeanService;
import examRecordBean.ERecListBean;
import examRecordBean.ERecListBeanService;

import qBankBean.QBean;
import qBankBean.QListBean;
import qBankBean.QListBeanService;
import qBankBean.QBeanService;

@WebServlet("/examRecord/ReadEr.do")
public class ReadEr extends HttpServlet {
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
            	ERecBeanService erBeanSrv =new ERecBeanService();
           		ERecBean erBean =new ERecBean();	
            	erBeanSrv.DbConnect();
                String stuExId = req.getParameter("stuExID"); 
           		erBean = erBeanSrv.selectER(stuExId);
           		erBeanSrv.DbClose();
           		erBeanSrv.setReadBy("byId");
            	req.setAttribute("eRecordBean",erBean);
        		erBeanSrv.DbClose();
            }
            else if(req.getParameter("radioBy").equals("ReadAll"))
            {
            	ERecBeanService erBeanSrv =new ERecBeanService();
            	ERecListBeanService erLstBeanSrv =new ERecListBeanService();
           		ERecListBean erBean =new ERecListBean();	
           		erLstBeanSrv.DbConnect();
           		erBeanSrv.setReadBy("byAll");

           		List<ERecListBean> erList = erLstBeanSrv.selectErList();
               	req.setAttribute("erListBeanService", erList); 
        		erLstBeanSrv.DbClose();
            }
       	} catch (ClassNotFoundException | SQLException e) 
       	{
				e.printStackTrace();
		}

//		resp.sendRedirect("Read_Q.jsp");
        getServletContext().getRequestDispatcher("/examRecord/examRecView.jsp").forward(req, resp);

    }
}
