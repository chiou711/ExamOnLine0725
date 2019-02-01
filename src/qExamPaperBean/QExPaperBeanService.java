package qExamPaperBean;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class QExPaperBeanService implements Serializable{

	/**
	 * selectEP   selectEpDb
	 * insertEP   insertEpDb
	 * deleteEP   deleteEpDb
	 * updateEP   updateEpDb
	 *  
	 */
	private static final long serialVersionUID = 1L;
	private static DataSource dataSource;
	private static String epMaxId = null;
	static Connection conn;
	public QExPaperBeanService(){
	};

	private  static String ePaperKeyword = "";

	public String getEPaperKeyword() {
		return ePaperKeyword;
	}

	public void setEPaperKeyword(String ePaperKeyword) {
		QExPaperBeanService.ePaperKeyword = ePaperKeyword;
	}
	
	//
	// Connect
	//
	public void DbConnect() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/examonline");
			conn = dataSource.getConnection();

		} catch (NamingException ex) {
			throw new RuntimeException(ex);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//
	// Close connection
	//
	public void DbClose() throws SQLException {
		conn.close();
	}

	//
	// Select
	//
	public QExPaperBean selectEP(String epId) throws IOException,
			ClassNotFoundException, SQLException {
		
		String ePaper = null;
		String na = null;
		ePaper = selectEpDb(epId);
		QExPaperBean epBean = new QExPaperBean();

		if (ePaper == null) {
			epBean.setEPaperId(na);
			epBean.setEPaper(na);
			return epBean;
		}

		epBean.setEPaperId(epId);
		epBean.setEPaper(ePaper);
		// Check
		System.out.println(ePaper);

		return epBean;
	}

	public String selectEpDb(String epId)
			throws ClassNotFoundException, SQLException, IOException {
		PreparedStatement prepStmt = null;
		String epStr = null;
		DbConnect(); 
		// Query MySQL
		// create prepare statement
		prepStmt = conn
				.prepareStatement("SELECT exam_paper_ques_id_list "
						+ "FROM exampaper WHERE exam_paper_id = ? ");
		prepStmt.setString(1, epId);

		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			epStr = rs.getString("exam_paper_ques_id_list");
			prepStmt.clearParameters();
			prepStmt.close();
			return epStr;
		} else {
			prepStmt.clearParameters();
			prepStmt.close();
			DbClose();
			return null;
		}
	}

	//
	// Insert
	//
	public void insertEP(String epId, String epStr)
			throws SQLException {
		PreparedStatement prepStmt = null;
		DbConnect();
		// create prepare statement

		prepStmt = conn.prepareStatement(
				"INSERT INTO exampaper VALUES(?,?,?)",
				Statement.RETURN_GENERATED_KEYS);

		prepStmt.setInt(1, Integer.parseInt(epId)); // id
		prepStmt.setString(2, epStr); // body
		Date now = new Date();
		Timestamp ts = new Timestamp(now.getTime());
		prepStmt.setTimestamp(3, ts);

		int iCount = prepStmt.executeUpdate();
		System.out.println("iCount=" + iCount);
		// print
		if (iCount > 0) {
			System.out.println("Insert a Exam Paper: OK!");
			System.out.println("考卷ID:" + epId + "考卷題目:" + epStr);
		} else {
			System.out.println("Insert a Exam Paper: NG!");
		}

		if (!epStr.isEmpty()) {
			QExPaperBean epBean = new QExPaperBean();
			epBean.setEPaperId(epId);
			epBean.setEPaper(epStr);
			DbClose();
		}

		prepStmt.clearParameters();
		prepStmt.close();
	}


	//
	// Update
	//
	public String updateEP(String epId, String epStr)
			throws IOException, ClassNotFoundException, SQLException {
		String str = updateEpDb(epId, epStr);

		if (!str.isEmpty()) {
			QExPaperBean qBean = new QExPaperBean();
			qBean.setEPaperId(epId);
			qBean.setEPaper(epStr);
		}
		return str;
	}

	public String updateEpDb(String epId, String epStr)
			throws ClassNotFoundException, SQLException, IOException {
		PreparedStatement prepStmt = null;
		DbConnect();

		// Query MySQL
		// create prepare statement
		prepStmt = conn.prepareStatement("UPDATE exampaper SET "
				+ "exam_paper_ques_id_list = ?"
				+ "WHERE exam_paper_id = ? ");

		prepStmt.setString(1, epId); // Id
		prepStmt.setString(2, epStr); // Exam Paper


		int iCount = prepStmt.executeUpdate();
		System.out.println("iCount=" + iCount);
		// print
		if (iCount > 0) {
			System.out.println("Update a Exam Paper: OK!");
			System.out.println("考卷ID:" + epId + "考卷題目:" + epStr);
		} else {
			System.out.println("Update a Exam Paper: NG!");
		}

		prepStmt.clearParameters();
		prepStmt.close();
		DbClose();
		return epStr;
	}

	//
	// Delete
	//
	public void deleteEP(String epId) throws SQLException, ClassNotFoundException, IOException {
		PreparedStatement prepStmt = null;
		DbConnect();

		// Query MySQL
		// create prepare statement
		prepStmt = conn
				.prepareStatement("DELETE FROM exampaper WHERE exam_paper_id = ?");

		prepStmt.setString(1, epId); // body

		int iCount = prepStmt.executeUpdate();
		
		System.out.println("iCount=" + iCount);
		// print
		if (iCount > 0) {
			System.out.println("Delete a Exam Paper: OK!");
		} else {
			System.out.println("Delete a Exam Paper: NG!");
		}

		prepStmt.clearParameters();
		prepStmt.close();
		DbClose();
	}

	public  void setEpMaxId() {

	}

	public String getEpMaxId() throws SQLException {
		Integer iMax = 0;
		DbConnect();
		PreparedStatement prepStmt = null;

		// create prepare statement
		prepStmt = conn
				.prepareStatement("SELECT MAX(exam_paper_id) FROM exampaper");

		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			iMax = rs.getInt(1);
			System.out.println("iMax of Exam Paper =" + iMax);
			epMaxId = String.format("%04d",iMax);
		}

		prepStmt.clearParameters();
		prepStmt.close();
		conn.close();
		DbClose();
		return QExPaperBeanService.epMaxId;
	}



}
