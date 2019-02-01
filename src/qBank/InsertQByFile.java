package qBank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import qBankBean.QBeanService;

@MultipartConfig(location = "h:/temp_h")
@WebServlet("/qBank/InsertQbyFile.do")
public class InsertQByFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=utf-8");
		String strQCat = null;

		try {
			List<FileItem> items = new ServletFileUpload(
					new DiskFileItemFactory()).parseRequest(req);
			for (FileItem item : items) {
				if (item.isFormField()) {
					// Process regular form field					
					String fieldname = item.getFieldName();
					//System.out.println("fieldname=" + fieldname);
					String fieldvalue = item.getString();
					//System.out.println("fieldvalue=" + fieldvalue);

					//construct to utf-8
					fieldvalue = new String(fieldvalue.getBytes("ISO8859_1"), "utf-8");
					
					if (fieldname.equals("qCategory")) {
						System.out.println("qCategory=" + fieldvalue);
						strQCat = fieldvalue;
					}
				} else {
					// Process form file field (input type="file").
					String fieldname = item.getFieldName();
					//System.out.println("fieldname=" + fieldname);
					String filename = FilenameUtils.getName(item.getName());
					//System.out.println("filename=" + filename);
					InputStream filecontent = item.getInputStream();

					BufferedReader reader = new BufferedReader(
							new InputStreamReader(filecontent));
					String input = null;
					String requestBody = "";

					boolean bStart = false;
					QBeanService.DB_connect();

					while ((input = reader.readLine()) != null) {
						if (input.contains("<Start>")) {
							input = reader.readLine(); // read one more line
							bStart = true;
						} else if (input.contains("<End>")) {
							break;
						}

						if (bStart == true) {

							try {
								QBeanService.addRow(input,strQCat);
							} catch (ClassNotFoundException | SQLException e) {
								e.printStackTrace();
							}
							requestBody = requestBody + input + "<br>";
						}
					}
					QBeanService.con_close();
					PrintWriter out = resp.getWriter();
					out.print("<title>Servlet BodyView</title>");
					out.print("<style>body {background-color:#70c0c0;}</style>");
					out.print(requestBody);
				}
			}
		} catch (FileUploadException e) {
			throw new ServletException("Cannot parse multipart request.", e);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
