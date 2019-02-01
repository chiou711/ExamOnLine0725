package examRecordBean;

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


public class ERecBeanService implements Serializable{

	/**
	 * selectER   selectErDb
	 * insertER   
	 * deleteER   
	 */
	private static final long serialVersionUID = 1L;
	private static DataSource dataSource;
	private static  String readBy = null;

	static Connection conn;
	public ERecBeanService(){
		 
	};
	
	//
	// Connect
	//
	public void DbConnect() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env"); //refer https://stackoverflow.com/questions/11631839/what-is-javacomp-env
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

	//	examinee_id
	//	examinee_status
	//	examinee_answers
	//	examinee_score
	//	examinee_exam_paper_id
	
	//
	// Select
	//
	public ERecBean selectER(String erId) throws IOException,
			ClassNotFoundException, SQLException {
		
		String na = null;
		ERecBean erBn = new ERecBean();
		erBn = selectErDb(erId);

		if (erBn.getErAnsList() == null) {
			erBn.setErId(na);
			erBn.setErAnsList(na);
			erBn.setErScore(na);
			erBn.setErEpaperId(na);
			return erBn;
		}

		// Check
		System.out.println(erBn.getErId());
		return erBn;
	}

	public ERecBean selectErDb(String erId)
			throws ClassNotFoundException, SQLException, IOException {
		PreparedStatement prepStmt = null;
		ERecBean erBn = new ERecBean();
		DbConnect(); 
		// Query MySQL
		// create prepare statement
		prepStmt = conn
				.prepareStatement("SELECT examinee_answers,examinee_score,examinee_exam_paper_id FROM examinee WHERE examinee_id = ? ");
		prepStmt.setString(1, erId);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			erBn.setErId(erId);
			erBn.setErAnsList(rs.getString("examinee_answers"));
			erBn.setErScore(rs.getString("examinee_score"));
			erBn.setErEpaperId(rs.getString("examinee_exam_paper_id"));
			prepStmt.clearParameters();
			prepStmt.close();
			return erBn;
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
	public void insertER(String erId, String[] erArr)
			throws SQLException {
		PreparedStatement prepStmt = null;
		DbConnect();
		// create prepare statement

		prepStmt = conn.prepareStatement(
				"INSERT INTO examinee VALUES(?,?,?,?,?,?)",
				Statement.RETURN_GENERATED_KEYS);
		
		prepStmt.setString(1, erId); // examinee_id
		prepStmt.setString(2, "9"); // examinee_status 0: not attended 1: pass 2: candidate 3: fail
		prepStmt.setString(3, erArr[0]); // examinee_answers
		prepStmt.setString(4, erArr[1]); // examinee_score
		prepStmt.setString(5, erArr[2]); // examinee_exam_paper_id
		Date now = new Date();
		Timestamp ts = new Timestamp(now.getTime());
		prepStmt.setTimestamp(6, ts);

		int iCount = prepStmt.executeUpdate();
		System.out.println("iCount=" + iCount);
		// print
		if (iCount > 0) {
			ERecBean erBean = new ERecBean();
			erBean.setErId(erId);
			erBean.setErAnsList(erArr[0]);
			erBean.setErScore(erArr[1]);
			erBean.setErEpaperId(erArr[2]);
			System.out.println("Insert a Examinee Record: OK!");
			System.out.println("examinee_id:" + erId);
			System.out.println("examinee_answers:" + erArr[0]);
			System.out.println("examinee_score:" +erArr[1]);
			System.out.println("examinee_exam_paper_id:" + erArr[2]);
		} else {
			System.out.println("Insert a Examinee Record: NG!");
		}

		prepStmt.clearParameters();
		prepStmt.close();
		DbClose();
	}



	
	//
	// Delete
	//
	public void deleteER(String erId) throws SQLException, ClassNotFoundException, IOException {
		PreparedStatement prepStmt = null;
		DbConnect();

		// Query MySQL
		// create prepare statement
		prepStmt = conn
				.prepareStatement("DELETE FROM examinee WHERE examinee_id = ?");

		prepStmt.setString(1, erId); 

		int iCount = prepStmt.executeUpdate();
		
		System.out.println("iCount=" + iCount);
		// print
		if (iCount > 0) {
			System.out.println("Delete a Examinee Record: OK!");
		} else {
			System.out.println("Delete a Examinee Record: NG!");
		}

		prepStmt.clearParameters();
		prepStmt.close();
		DbClose();
	}

	public String getReadBy() {
		return readBy;
	}

	public void setReadBy(String readBy) {
		ERecBeanService.readBy = readBy;
	}


}
