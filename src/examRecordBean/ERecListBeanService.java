package examRecordBean;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class ERecListBeanService implements Serializable{

	/**
	 * selectER   selectErDb
	 * insertER   
	 * deleteER   
	 */
	private static final long serialVersionUID = 1L;
	private static DataSource dataSource;
	private static List<ERecListBean> erList = new ArrayList<ERecListBean>();	 

	public List<ERecListBean> getErList() {
		return erList;
	}

	public void setErList(List<ERecListBean> erList) {
		ERecListBeanService.erList = erList;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		ERecListBeanService.conn = conn;
	}

	public long getSerialversionuid() {
		return serialVersionUID;
	}

	static Connection conn;
	public ERecListBeanService(){
	};
	
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

	//	examinee_id
	//	examinee_status
	//	examinee_answers
	//	examinee_score
	//	examinee_exam_paper_id
	
	//
	// Select
	//
	public List<ERecListBean> selectErList() throws IOException,
			ClassNotFoundException, SQLException {
		
		erList.clear();
		PreparedStatement prepStmt = null;
		DbConnect(); 
		// create prepare statement
		prepStmt = conn
				.prepareStatement("SELECT examinee_id,examinee_answers,examinee_score,examinee_exam_paper_id FROM examinee");
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			ERecListBean erLstBn = new ERecListBean();
			erLstBn.setErId(rs.getString("examinee_id"));
			System.out.println("rs.getString('examinee_id')=" + rs.getString("examinee_id")); 
			erLstBn.setErAnsList(rs.getString("examinee_answers"));
			erLstBn.setErScore(rs.getString("examinee_score"));
			erLstBn.setErEpaperId(rs.getString("examinee_exam_paper_id"));
			erList.add(erLstBn);
		}
		prepStmt.clearParameters();
		prepStmt.close();
		return erList;
	}
}
