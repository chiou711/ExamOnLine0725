package qBankBean;

public class QListBean {
	// for being Java bean
	private  String readBy = "";
	private  String ques_id = null;
	private  String ques_body = null;
	private  String ques_ans_1 = null;
	private  String ques_ans_2 = null;
	private  String ques_ans_3 = null;
	private  String ques_ans_4 = null;
	private  String ques_ans_is = null;
	private  String ques_category = null;
	private  String ques_whole = null;
	private  Integer ques_maxId = 0;
	private  String ques_maxId_str = null;	
	
	public QListBean(){};
	
	public String getQues_category() {
		return ques_category;
	}

	public void setQues_category(String ques_category) {
		this.ques_category = ques_category;
	}	
	
	public String getQues_whole() {
		return ques_whole;
	}

	public void setQues_whole(String ques_whole) {
		this.ques_whole = ques_whole;
	}

	public String getQues_maxId_str() {
		return ques_maxId_str;
	}

	public void setQues_maxId_str(String ques_maxId_str) {
		this.ques_maxId_str = ques_maxId_str;
	}

	public String getQues_id() {
		return ques_id;
	}

	public void setQues_id(String ques_id) {
		this.ques_id = ques_id;
	}

	public String getQues_body() {
		return ques_body;
	}

	public void setQues_body(String ques_body) {
		this.ques_body = ques_body;
	}

	public String getQues_ans_1() {
		return ques_ans_1;
	}

	public void setQues_ans_1(String ques_ans_1) {
		this.ques_ans_1 = ques_ans_1;
	}

	public String getQues_ans_2() {
		return ques_ans_2;
	}

	public void setQues_ans_2(String ques_ans_2) {
		this.ques_ans_2 = ques_ans_2;
	}

	public String getQues_ans_3() {
		return ques_ans_3;
	}

	public void setQues_ans_3(String ques_ans_3) {
		this.ques_ans_3 = ques_ans_3;
	}

	public String getQues_ans_4() {
		return ques_ans_4;
	}

	public void setQues_ans_4(String ques_ans_4) {
		this.ques_ans_4 = ques_ans_4;
	}

	public String getQues_ans_is() {
		return ques_ans_is;
	}

	public void setQues_ans_is(String ques_ans_is) {
		this.ques_ans_is = ques_ans_is;
	}

	public String getReadBy() {
		return readBy;
	}

	public void setReadBy(String readBy) {
		this.readBy = readBy;
	}

	public Integer getQues_maxId() {
		
		return ques_maxId;
	}

	public void setQues_maxId(Integer iMax) {
		this.ques_maxId= iMax;
	}

}
