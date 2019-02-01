package qBankBean;

public class QBean {
	// for being Java bean
	private  static String readBy = "";
	private  static String ques_id = null;
	private  static String ques_body = null;
	private  static String ques_ans_1 = null;
	private  static String ques_ans_2 = null;
	private  static String ques_ans_3 = null;
	private  static String ques_ans_4 = null;
	private  static String ques_ans_is = null;
	private  static String ques_category = null;
	private  static String ques_whole = null;
	private  static Integer ques_maxId = 0;
	private  static String ques_maxId_str = null;	
	
	public QBean(){};

	public String getQues_category() {
		return ques_category;
	}

	public void setQues_category(String ques_category) {
		QBean.ques_category = ques_category;
	}	
	
	public String getQues_whole() {
		return ques_whole;
	}

	public void setQues_whole(String ques_whole) {
		QBean.ques_whole = ques_whole;
	}

	public String getQues_maxId_str() {
		return ques_maxId_str;
	}

	public void setQues_maxId_str(String ques_maxId_str) {
		QBean.ques_maxId_str = ques_maxId_str;
	}

	public String getQues_id() {
		return ques_id;
	}

	public void setQues_id(String ques_id) {
		QBean.ques_id = ques_id;
	}

	public String getQues_body() {
		return ques_body;
	}

	public void setQues_body(String ques_body) {
		QBean.ques_body = ques_body;
	}

	public String getQues_ans_1() {
		return ques_ans_1;
	}

	public void setQues_ans_1(String ques_ans_1) {
		QBean.ques_ans_1 = ques_ans_1;
	}

	public String getQues_ans_2() {
		return ques_ans_2;
	}

	public void setQues_ans_2(String ques_ans_2) {
		QBean.ques_ans_2 = ques_ans_2;
	}

	public String getQues_ans_3() {
		return ques_ans_3;
	}

	public void setQues_ans_3(String ques_ans_3) {
		QBean.ques_ans_3 = ques_ans_3;
	}

	public String getQues_ans_4() {
		return ques_ans_4;
	}

	public void setQues_ans_4(String ques_ans_4) {
		QBean.ques_ans_4 = ques_ans_4;
	}

	public String getQues_ans_is() {
		return ques_ans_is;
	}

	public void setQues_ans_is(String ques_ans_is) {
		QBean.ques_ans_is = ques_ans_is;
	}

	public String getReadBy() {
		return readBy;
	}

	public void setReadBy(String readBy) {
		QBean.readBy = readBy;
	}

	public Integer getQues_maxId() {
		
		return ques_maxId;
	}

	public void setQues_maxId(Integer iMax) {
		QBean.ques_maxId= iMax;
	}

}
