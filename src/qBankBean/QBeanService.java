package qBankBean;

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

public class QBeanService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static DataSource dataSource;
	static Connection conn;
	static Integer maxId = 0;

	public QBeanService(){};
	
	//
	// Connect
	//
	public static void DB_connect() {
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
	// Read
	//
	public static QBean read_qBank_by_ID(String qId) throws IOException,
			ClassNotFoundException, SQLException {

		String[] Q_contentArr = null;
		String na = null;
		Q_contentArr = read_by_ID(qId);
		QBean qBean = new QBean();

		if (Q_contentArr == null) {
			qBean.setQues_body(na);
			qBean.setQues_ans_1(na);
			qBean.setQues_ans_2(na);
			qBean.setQues_ans_3(na);
			qBean.setQues_ans_4(na);
			qBean.setQues_ans_is(na);
			qBean.setQues_category(na);
			return qBean;
		}

		qBean.setQues_id(qId);
		qBean.setQues_body(Q_contentArr[0]);
		qBean.setQues_ans_1(Q_contentArr[1]);
		qBean.setQues_ans_2(Q_contentArr[2]);
		qBean.setQues_ans_3(Q_contentArr[3]);
		qBean.setQues_ans_4(Q_contentArr[4]);
		qBean.setQues_ans_is(Q_contentArr[5]);
		qBean.setQues_category(Q_contentArr[6]);
		String ques_whole = null;
		ques_whole = "<label>題庫ID:" + qId;
		for (int i = 0; i < Q_contentArr.length; i++) {
			if (i == 0)
				ques_whole += ("</label><br><label>" + Q_contentArr[i]);
			else if (i == 1)
				ques_whole += ("(1)" + Q_contentArr[i]);
			else if (i == 2)
				ques_whole += ("(2)" + Q_contentArr[i]);
			else if (i == 3)
				ques_whole += ("(3)" + Q_contentArr[i]);
			else if (i == 4)
				ques_whole += ("(4)" + Q_contentArr[i]);
			else if (i == 5)
				ques_whole += ("</label><br><label style='border-right: groove'> 答案:" + Q_contentArr[i]);
			else if (i == 6)
				ques_whole += ("&nbsp</label>&nbsp" + Q_contentArr[i]);
		}
		
		qBean.setQues_whole(ques_whole);
		// Check
		//System.out.println(ques_whole);
		return qBean;
	}

	private static String[] read_by_ID(String q_id)
			throws ClassNotFoundException, SQLException, IOException {
		PreparedStatement prepStmt = null;
		String[] q = new String[7];

		// Query MySQL
		// create prepare statement
		prepStmt = conn
				.prepareStatement("SELECT ques_body,ques_ans_1,ques_ans_2,ques_ans_3, ques_ans_4, ques_ans_is, ques_category "
						+ "FROM questionbank WHERE ques_id = ? ");
		prepStmt.setString(1, q_id);

		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			q[0] = rs.getString("ques_body");
			q[1] = rs.getString("ques_ans_1");
			q[2] = rs.getString("ques_ans_2");
			q[3] = rs.getString("ques_ans_3");
			q[4] = rs.getString("ques_ans_4");
			q[5] = rs.getString("ques_ans_is");
			q[6] = rs.getString("ques_category");

			prepStmt.clearParameters();
			prepStmt.close();
			return q;
		} else {
			prepStmt.clearParameters();
			prepStmt.close();
			return null;
		}
	}

	//
	// Add row
	//
	public static void addRow(String q_row,String q_ctgry) throws ClassNotFoundException,
			SQLException, IOException {
		PreparedStatement prepStmt = null;
		String[] q = q_row.split("[;]");

		// check
		for (int i = 0; i < q.length; i++) {

			System.out.println("q===" + q[i]);
		}

		// update it to MySQL
		// create prepare statement
		prepStmt = conn.prepareStatement(
				"INSERT INTO questionbank VALUES(?,?,?,?,?,?,?,?,?,?,?,?)",
				Statement.RETURN_GENERATED_KEYS);

		prepStmt.setInt(1, 0);
		prepStmt.setString(2, q[2]); // body
		prepStmt.setString(3, q[3]); // ans1
		prepStmt.setString(4, q[4]); // ans2
		prepStmt.setString(5, q[5]); // ans3
		prepStmt.setString(6, q[6]); // ans4
		prepStmt.setString(7, q[1]); // is answer
		prepStmt.setString(8, q_ctgry); // category
		prepStmt.setString(9, "9");
		prepStmt.setString(10, "9999");
		prepStmt.setString(11, "9");
		Date now = new Date();
		Timestamp ts = new Timestamp(now.getTime());
		prepStmt.setTimestamp(12, ts);

		int iCount = prepStmt.executeUpdate();

		// print
		if (iCount > 0)
			System.out.println("Add a question: OK!");
		else
			System.out.println("Add a question: NG!");

		prepStmt.clearParameters();
		prepStmt.close();
	}

	//
	// Update
	//
	public static String update_qBank_by_ID(String qId, String[] strArr)
			throws IOException, ClassNotFoundException, SQLException {
		String str = update_by_ID(qId, strArr);

		if (!str.isEmpty()) {
			QBean qBean = new QBean();
			qBean.setQues_id(qId);
			qBean.setQues_body(strArr[0]);
			qBean.setQues_ans_1(strArr[1]);
			qBean.setQues_ans_2(strArr[2]);
			qBean.setQues_ans_3(strArr[3]);
			qBean.setQues_ans_4(strArr[4]);
			qBean.setQues_ans_is(strArr[5]);
		}
		return str;
	}

	private static String update_by_ID(String q_id, String[] strArr)
			throws ClassNotFoundException, SQLException, IOException {
		PreparedStatement prepStmt = null;
		String[] q = strArr;

		// Query MySQL
		// create prepare statement
		prepStmt = conn.prepareStatement("UPDATE questionbank SET "
				+ "ques_body=?," + "ques_ans_1=?," + "ques_ans_2=?,"
				+ "ques_ans_3=?, " + "ques_ans_4=?, " + "ques_ans_is=? "
				+ "WHERE ques_id = ? ");

		prepStmt.setString(1, q[0]); // body
		prepStmt.setString(2, q[1]); // ans1
		prepStmt.setString(3, q[2]); // ans2
		prepStmt.setString(4, q[3]); // ans3
		prepStmt.setString(5, q[4]); // ans4
		prepStmt.setString(6, q[5]); // is answer
		prepStmt.setString(7, q_id); // is answer

		int iCount = prepStmt.executeUpdate();
		System.out.println("iCount=" + iCount);
		String ques_whole = null;
		// print
		if (iCount > 0) {
			System.out.println("Update a question: OK!");
			ques_whole = "題庫ID:" + q_id;
			for (int i = 0; i < q.length; i++) {
				if (i == 0)
					ques_whole += ("__" + q[i]);
				else if (i == 1)
					ques_whole += ("(1)" + q[i]);
				else if (i == 2)
					ques_whole += ("(2)" + q[i]);
				else if (i == 3)
					ques_whole += ("(3)" + q[i]);
				else if (i == 4)
					ques_whole += ("(4)" + q[i]);
				else if (i == 5)
					ques_whole += ("__答案:" + q[i]);
			}
		} else {
			System.out.println("Update a question: NG!");
		}

		prepStmt.clearParameters();
		prepStmt.close();

		return ques_whole;
	}

	//
	// Delete
	//
	public static void delete_qBank_by_ID(String qId) throws SQLException, ClassNotFoundException, IOException {
		PreparedStatement prepStmt = null;

		// Query MySQL
		// create prepare statement
		prepStmt = conn
				.prepareStatement("DELETE FROM questionbank WHERE ques_id = ?");

		prepStmt.setString(1, qId); // body

		int iCount = prepStmt.executeUpdate();
		
		System.out.println("iCount=" + iCount);
		// print
		if (iCount > 0) {
			System.out.println("Delete a question: OK!");
		} else {
			System.out.println("Delete a question: NG!");
		}

		prepStmt.clearParameters();
		prepStmt.close();
	}

	//
	// Insert
	//
	public static void insert_qBank_by_ID(String qId, String[] strArr)
			throws SQLException {
		PreparedStatement prepStmt = null;
		String[] q = strArr;

		// Query MySQL
		// create prepare statement

		prepStmt = conn.prepareStatement(
				"INSERT INTO questionbank VALUES(?,?,?,?,?,?,?,?,?,?,?,?)",
				Statement.RETURN_GENERATED_KEYS);

		prepStmt.setInt(1, Integer.parseInt(qId)); // id
		prepStmt.setString(2, q[0]); // body
		prepStmt.setString(3, q[1]); // ans1
		prepStmt.setString(4, q[2]); // ans2
		prepStmt.setString(5, q[3]); // ans3
		prepStmt.setString(6, q[4]); // ans4
		prepStmt.setString(7, q[5]); // is answer
		prepStmt.setString(8, "9");
		prepStmt.setString(9, "9");
		prepStmt.setString(10, "9999");
		prepStmt.setString(11, "9");
		Date now = new Date();
		Timestamp ts = new Timestamp(now.getTime());
		prepStmt.setTimestamp(12, ts);

		int iCount = prepStmt.executeUpdate();
		System.out.println("iCount=" + iCount);
		String ques_whole = null;
		// print
		if (iCount > 0) {
			System.out.println("Insert a question: OK!");
			ques_whole = "題庫ID:" + qId;
			for (int i = 0; i < q.length; i++) {
				if (i == 0)
					ques_whole += ("__" + q[i]);
				else if (i == 1)
					ques_whole += ("(1)" + q[i]);
				else if (i == 2)
					ques_whole += ("(2)" + q[i]);
				else if (i == 3)
					ques_whole += ("(3)" + q[i]);
				else if (i == 4)
					ques_whole += ("(4)" + q[i]);
				else if (i == 5)
					ques_whole += ("__答案:" + q[i]);
			}
		} else {
			System.out.println("Insert a question: NG!");
		}

		if (!ques_whole.isEmpty()) {
			QBean qBCBean = new QBean();
			qBCBean.setQues_id(qId);
			qBCBean.setQues_body(strArr[0]);
			qBCBean.setQues_ans_1(strArr[1]);
			qBCBean.setQues_ans_2(strArr[2]);
			qBCBean.setQues_ans_3(strArr[3]);
			qBCBean.setQues_ans_4(strArr[4]);
			qBCBean.setQues_ans_is(strArr[5]);
			qBCBean.setQues_whole(ques_whole);
		}

		prepStmt.clearParameters();
		prepStmt.close();
	}

	public static void set_qBank_auto_maxID() throws SQLException {

		Integer iMax = 0;

		PreparedStatement prepStmt = null;

		// Query MySQL
		// create prepare statement

		prepStmt = conn
				.prepareStatement("SELECT MAX(ques_id) FROM questionbank");

		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			iMax = rs.getInt(1);
			System.out.println("iMax=" + iMax);
			maxId = iMax;
			QBean qBCBean = new QBean();
			qBCBean.setQues_maxId(iMax);
			qBCBean.setQues_maxId_str(Integer.toString(iMax));
			qBCBean.setQues_id(String.valueOf(iMax + 1));

			String na = null;
			qBCBean.setQues_body(na);
			qBCBean.setQues_ans_1(na);
			qBCBean.setQues_ans_2(na);
			qBCBean.setQues_ans_3(na);
			qBCBean.setQues_ans_4(na);
			qBCBean.setQues_ans_is(na);
			qBCBean.setQues_whole(na);
		}

		prepStmt.clearParameters();
		prepStmt.close();
	}

	public static Integer get_qBank_auto_maxID() {
		return maxId;
	}

	//
	// Close connection
	//
	public static void con_close() throws SQLException {
		conn.close();
	}

}
