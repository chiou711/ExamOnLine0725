package stuRecordBean;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class StuRecBeanService implements Serializable {

	public StuRecBeanService() {

	};
	/**
	 * selectStuRec selectStuRecDb insertStuRec deleteStuRec
	 */
	private static final long serialVersionUID = 1L;
	private static DataSource dataSource;
	static Connection conn;

	private static List<StuRecListBean> stuRecBnList = new ArrayList<StuRecListBean>();

	public List<StuRecListBean> getStuRecBnList() {
		return stuRecBnList;
	}

	public void setStuRecBnList(List<StuRecListBean> stuRecBnList) {
		StuRecBeanService.stuRecBnList = stuRecBnList;
	}

	public List<StuRecListBean> selectStuRecBeanList() throws IOException,
	ClassNotFoundException, SQLException {		

		stuRecBnList.clear();
		PreparedStatement prepStmt = null;
		DbConnect();
		// create prepare statement
		prepStmt = conn.prepareStatement("SELECT stud_id, stud_name,stud_gender,stud_birthday,"
					+ "stud_address,stud_phone_num,stud_examinee_id FROM student");
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			StuRecListBean srListBn = new StuRecListBean();
			srListBn.setStud_id(rs.getString("stud_id"));
			srListBn.setStud_name(rs.getString("stud_name"));
			srListBn.setStud_gender(rs.getString("stud_gender"));
			srListBn.setStud_birthday(rs.getString("stud_birthday"));
			srListBn.setStud_address(rs.getString("stud_address"));
			srListBn.setStud_phone_num(rs.getString("stud_phone_num"));
			srListBn.setStud_examinee_id(rs.getString("stud_examinee_id"));
			stuRecBnList.add(srListBn);
		}
		prepStmt.clearParameters();
		prepStmt.close();
		return stuRecBnList;
	}
	
	//
	// Connect
	//
	public void DbConnect() {
		System.out.println("---------------DbConnect");
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

	// stud_id
	// stud_name
	// stud_gender
	// stud_birthday
	// stud_address
	// stud_phone_num
	// stud_examinee_id
	// stud_apply_time

	//
	// Select
	//
	public StuRecBean selectStuRec(String erId) throws IOException,
			ClassNotFoundException, SQLException {

		StuRecBean srBn = new StuRecBean();
		srBn = selectStuRecDb(erId);

		// Check
		System.out.println(srBn.getStud_id());
		return srBn;
	}

	public StuRecBean selectStuRecDb(String stuId)
			throws ClassNotFoundException, SQLException, IOException {
		PreparedStatement prepStmt = null;
		StuRecBean srBn = new StuRecBean();
		DbConnect();
		// create prepare statement
		prepStmt = conn
				.prepareStatement("SELECT stud_name,stud_gender,stud_birthday,"
						+ "stud_address,stud_phone_num,stud_examinee_id FROM student WHERE stud_id = ? ");

		prepStmt.setString(1, stuId);
		ResultSet rs = prepStmt.executeQuery();
		String na = "";

		if (rs.next()) {
			srBn.setStud_id(stuId);
			srBn.setStud_name(rs.getString("stud_name"));
			srBn.setStud_gender(rs.getString("stud_gender"));
			srBn.setStud_birthday(rs.getString("stud_birthday"));
			srBn.setStud_address(rs.getString("stud_address"));
			srBn.setStud_phone_num(rs.getString("stud_phone_num"));
			srBn.setStud_examinee_id(rs.getString("stud_examinee_id"));
			prepStmt.clearParameters();
			prepStmt.close();
			return srBn;
		} else {
			//
			srBn.setStud_id(stuId);
			srBn.setStud_name(na);
			srBn.setStud_gender(na);
			srBn.setStud_birthday(na);
			srBn.setStud_address(na);
			srBn.setStud_phone_num(na);
			srBn.setStud_examinee_id(na);
			
			prepStmt.clearParameters();
			prepStmt.close();
			DbClose();
			return srBn;
		}
	}

	//
	// Insert
	//
	public void insertStuRec(String stud_id, String stud_name,
			String stud_gender, String stud_birthday, String stud_address,
			String stud_phone_num, String stud_examinee_id) throws SQLException {
		PreparedStatement prepStmt = null;
		DbConnect();
		// create prepare statement
		prepStmt = conn.prepareStatement(
				"INSERT INTO student VALUES(?,?,?,?,?,?,?,?)",
				Statement.RETURN_GENERATED_KEYS);

		prepStmt.setString(1, stud_id);
		prepStmt.setString(2, stud_name);
		prepStmt.setString(3, stud_gender);
		prepStmt.setString(4, stud_birthday);
		prepStmt.setString(5, stud_address);
		prepStmt.setString(6, stud_phone_num);
		prepStmt.setString(7, stud_examinee_id);
		Date now = new Date();
		Timestamp ts = new Timestamp(now.getTime());
		prepStmt.setTimestamp(8, ts);

		int iCount = prepStmt.executeUpdate();
		System.out.println("iCount=" + iCount);
		// print
		if (iCount > 0) {
			StuRecBean srBn = new StuRecBean();
			srBn.setStud_id(stud_id);
			srBn.setStud_name(stud_name);
			srBn.setStud_gender(stud_gender);
			srBn.setStud_birthday(stud_birthday);
			srBn.setStud_address(stud_address);
			srBn.setStud_phone_num(stud_phone_num);
			srBn.setStud_examinee_id(stud_examinee_id);
			System.out.println("Insert a Student Record: OK!");
			System.out.println("stud_id:" + stud_id);
			System.out.println("stud_name" + stud_name);
			System.out.println("stud_gender" + stud_gender);
			System.out.println("stud_birthday:" + stud_birthday);
			System.out.println("stud_address:" + stud_address);
			System.out.println("stud_phone_num:" + stud_phone_num);
			System.out.println("stud_examinee_id:" + stud_examinee_id);
		} else {
			System.out.println("Insert a Student Record: NG!");
		}

		prepStmt.clearParameters();
		prepStmt.close();
		DbClose();
	}

	//
	// Delete
	//
	public void deleteStuRec(String srId) throws SQLException,
			ClassNotFoundException, IOException {
		PreparedStatement prepStmt = null;
		DbConnect();

		// create prepare statement
		prepStmt = conn
				.prepareStatement("DELETE FROM student WHERE stud_id = ?");

		prepStmt.setString(1, srId);

		int iCount = prepStmt.executeUpdate();

		System.out.println("iCount=" + iCount);
		// print
		if (iCount > 0) {
			System.out.println("Delete a Student Record: OK!");
		} else {
			System.out.println("Delete a Student Record: NG!");
		}

		prepStmt.clearParameters();
		prepStmt.close();
		DbClose();
	}
	
	//
	// Check if duplicate
	//
	public boolean checkIfDuplicate(String srId) throws SQLException,
			ClassNotFoundException, IOException {
		PreparedStatement prepStmt = null;
		DbConnect();

		// create prepare statement
		prepStmt = conn
				.prepareStatement("SELECT stud_name FROM student WHERE stud_id = ?");

		prepStmt.setString(1, srId);
		ResultSet rs = prepStmt.executeQuery();
		String s_name = null;
		while (rs.next()) {
			s_name = rs.getString("stud_name");
		}
		
		System.out.println("s_name=" + s_name);


		prepStmt.clearParameters();
		prepStmt.close();
		DbClose();
		
		// print
		if (s_name != null ) {
			System.out.println("Student Record: duplicate!");
			return true;
		} else {
			System.out.println("Student Record: empty!");
			return false;
		}
	}
}
