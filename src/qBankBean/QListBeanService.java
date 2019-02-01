package qBankBean;

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

public class QListBeanService implements Serializable{
	private static final long serialVersionUID = 1L;
	private static DataSource dataSource;
	static Connection conn;
	static Integer maxId = 0;

	private static List<QListBean> qContentList = new ArrayList<QListBean>();

	public QListBeanService(){};
	
	public List<QListBean> getqContentList() throws SQLException, ClassNotFoundException, IOException 
	{
		int i = 0;
		set_qBank_auto_maxID();
		qContentList.clear();
		do {
			QListBean qBankDbBn = new QListBean();
			qBankDbBn = read_qBank_by_ID(String.format("%06d",i + 1)); // CW: start from 1

			if (qBankDbBn.getQues_whole() != null) {
				//System.out.println("c2 " + qBankDbBn.getQues_whole());
				
				qContentList.add(qBankDbBn);
				i++;
			}
			else
			{
				i++;
				continue;
			}
		} while (i < get_qBank_auto_maxID());
//		System.out.println("c3 " + qContentList.get(0).getQues_body());
//		System.out.println("c3 " + qContentList.get(1).getQues_body());
//		System.out.println("c3 " + qContentList.get(2).getQues_body());
//		System.out.println("c3 " + qContentList.get(3).getQues_body());
//		System.out.println("c3 " + qContentList.get(4).getQues_body());
//		System.out.println("c3 " + qContentList.get(5).getQues_body());
//		System.out.println("c3 " + qContentList.get(6).getQues_body());
		return qContentList;
	}

	public void setqContentList(List<QListBean> qContentList) {
		QListBeanService.qContentList = qContentList;
	}

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
	public QListBean read_qBank_by_ID(String qId) throws IOException,
			ClassNotFoundException, SQLException {

		String[] Q_contentArr = null;
		String na = null;
		Q_contentArr = read_by_ID(qId);
		QListBean qBankDbBean = new QListBean();

		if (Q_contentArr == null) {
			// QBankDbBean qBankDbBean = new QBankDbBean();
			qBankDbBean.setQues_body(na);
			qBankDbBean.setQues_ans_1(na);
			qBankDbBean.setQues_ans_2(na);
			qBankDbBean.setQues_ans_3(na);
			qBankDbBean.setQues_ans_4(na);
			qBankDbBean.setQues_ans_is(na);
			qBankDbBean.setQues_category(na);
			return qBankDbBean;
		}

		// QBankDbBean qBankDbBean = new QBankDbBean();
		qBankDbBean.setQues_id(qId);
		qBankDbBean.setQues_body(Q_contentArr[0]);
		qBankDbBean.setQues_ans_1(Q_contentArr[1]);
		qBankDbBean.setQues_ans_2(Q_contentArr[2]);
		qBankDbBean.setQues_ans_3(Q_contentArr[3]);
		qBankDbBean.setQues_ans_4(Q_contentArr[4]);
		qBankDbBean.setQues_ans_is(Q_contentArr[5]);
		qBankDbBean.setQues_category(Q_contentArr[6]);
		String ques_whole = null;
		ques_whole = "題庫ID:" + qId;
		for (int i = 0; i < Q_contentArr.length; i++) {
			if (i == 0)
				ques_whole += ("___" + Q_contentArr[i]);
			else if (i == 1)
				ques_whole += ("(1)" + Q_contentArr[i]);
			else if (i == 2)
				ques_whole += ("(2)" + Q_contentArr[i]);
			else if (i == 3)
				ques_whole += ("(3)" + Q_contentArr[i]);
			else if (i == 4)
				ques_whole += ("(4)" + Q_contentArr[i]);
			else if (i == 5)
				ques_whole += ("___ 答案:" + Q_contentArr[i]);
			else if (i == 6)
				ques_whole += ("分類:" + Q_contentArr[i]);
		}
		qBankDbBean.setQues_whole(ques_whole);
		// Check
		//System.out.println(ques_whole);
		return qBankDbBean;
	}

	private String[] read_by_ID(String q_id)
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

	public void set_qBank_auto_maxID() throws SQLException {

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
			QListBean qBankDbBean = new QListBean();
			qBankDbBean.setQues_maxId(iMax);
			qBankDbBean.setQues_maxId_str(Integer.toString(iMax));
			qBankDbBean.setQues_id(String.valueOf(iMax + 1));

			String na = null;
			qBankDbBean.setQues_body(na);
			qBankDbBean.setQues_ans_1(na);
			qBankDbBean.setQues_ans_2(na);
			qBankDbBean.setQues_ans_3(na);
			qBankDbBean.setQues_ans_4(na);
			qBankDbBean.setQues_ans_is(na);
			qBankDbBean.setQues_whole(na);
		}

		prepStmt.clearParameters();
		prepStmt.close();
	}

	public Integer get_qBank_auto_maxID() {
		return maxId;
	}

	//
	// Close connection
	//
	public static void con_close() throws SQLException {
		conn.close();
	}

}
