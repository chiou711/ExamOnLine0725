package stuApply;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import stuRecordBean.StuRecBeanService;
import stuRecordBean.StuRecListBean;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

//@WebServlet("/stuApply/StuApply.do")
public class StuApply extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public StuApply() {
        super();
    	System.out.println("StuApply constructor");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("StuApply / _doPost");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");	

		StuRecListBean sBn = new StuRecListBean();
		StuRecBeanService sBnSrv = new StuRecBeanService();
		boolean bDup = false;

		// check if duplicate
		try {
			bDup=sBnSrv.checkIfDuplicate(request.getParameter("stud_id"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		sBnSrv.DbConnect();
		sBn.setStud_id(request.getParameter("stud_id"));
		sBn.setStud_name(request.getParameter("stud_name"));
		sBn.setStud_gender(request.getParameter("stud_gender"));
		sBn.setStud_birthday(request.getParameter("varYear")+ request.getParameter("varMonth")+ request.getParameter("varDate"));
		sBn.setStud_address(request.getParameter("stud_address"));
		sBn.setStud_phone_num(request.getParameter("stud_phone_num"));
		sBn.setStud_examinee_id("A00123");
		if(bDup)
			sBn.setStud_apply_again("Yes");
		else
			sBn.setStud_apply_again("No");

		try {
			if(!bDup)
			{
				sBnSrv.insertStuRec(sBn.getStud_id(), 
									sBn.getStud_name(), 
									sBn.getStud_gender(), 
									sBn.getStud_birthday(), 
									sBn.getStud_address(), 
									sBn.getStud_phone_num(), 
									sBn.getStud_examinee_id());
			}
			sBnSrv.DbClose();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		PrintWriter out = response.getWriter(); //CW: set print writer
		Gson gson = new Gson();
		JsonObject jsonObj = new JsonObject();
		
		if (sBn.getStud_examinee_id() == null) {
			jsonObj.addProperty("success", false);
		} else {
			jsonObj.addProperty("success", true);
		}

		JsonElement sBnJsonElement = gson.toJsonTree(sBn);
		jsonObj.add("stuInfo", sBnJsonElement);

		out.println(jsonObj.toString());
		out.close();

	}
}
