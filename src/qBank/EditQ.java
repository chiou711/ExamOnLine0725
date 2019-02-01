package qBank;



import java.io.*;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import qBankBean.QBean;
import qBankBean.QBeanService;

@WebServlet("/qBank/EditQ.do")
public class EditQ extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException 
    {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        
        String qId = req.getParameter("q_id");
        QBean qBean =new QBean();
        QBeanService.DB_connect();

        if(req.getParameter("QEdit").equals("列出內容"))
        {
        	qBean.setQues_id(qId);
			try {
				qBean = QBeanService.read_qBank_by_ID(qId);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        else if(req.getParameter("QEdit").equals("修改"))
        {
        	qBean.setQues_id(qId);
        	String[] strArr = new String[6];
        	
        	strArr[0]= req.getParameter("varQ");
        	strArr[1]= req.getParameter("varA1");
        	strArr[2]= req.getParameter("varA2");
        	strArr[3]= req.getParameter("varA3");
        	strArr[4]= req.getParameter("varA4");
        	strArr[5]= req.getParameter("varCA");

			try {
				String strEdit = QBeanService.update_qBank_by_ID(qId,strArr);
				qBean.setQues_whole(strEdit);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
        }
        else if(req.getParameter("QEdit").equals("新增"))
        {
        	qBean.setQues_id(qId);
        	String[] strArr = new String[6];
        	
        	strArr[0]= req.getParameter("varQ");
        	strArr[1]= req.getParameter("varA1");
        	strArr[2]= req.getParameter("varA2");
        	strArr[3]= req.getParameter("varA3");
        	strArr[4]= req.getParameter("varA4");
        	strArr[5]= req.getParameter("varCA");
        	
        	try {
				QBeanService.insert_qBank_by_ID(qId,strArr);
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        else if(req.getParameter("QEdit").equals("刪除"))
        {
        	qBean.setQues_id(qId);
        	try {
				QBeanService.delete_qBank_by_ID(qId);
				QBeanService.read_qBank_by_ID(qId);
			} catch (SQLException | ReflectiveOperationException e) {
				e.printStackTrace();
			}
        }
        else if(req.getParameter("QEdit").equals("建立"))
        {
        	try {
				QBeanService.set_qBank_auto_maxID();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        
      getServletContext().getRequestDispatcher("/qBank/EditQView.jsp").forward(req, resp);

      	try {
			QBeanService.con_close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

    }
}

