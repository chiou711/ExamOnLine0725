package stuRecordBean;

public class StuRecBean {

	// for being Java bean
	public StuRecBean() {
	}
	private  static String readBy = "";
	private  static String stud_id = null;
	private  static String stud_name = null;
	private  static String stud_gender = null;
	private  static String stud_birthday = null;
	private  static String stud_address = null;
	private  static String stud_phone_num = null;
	private  static String stud_examinee_id = null;
	private  static String stud_apply_time = null;

	public String getReadBy() {
		return readBy;
	}

	public void setReadBy(String readBy) {
		StuRecBean.readBy = readBy;
	}	
	
	public String getStud_id() {
		return stud_id;
	}
	public void setStud_id(String stud_id) {
		StuRecBean.stud_id = stud_id;
	}
	public String getStud_name() {
		return stud_name;
	}
	public void setStud_name(String stud_name) {
		StuRecBean.stud_name = stud_name;
	}
	public String getStud_gender() {
		return stud_gender;
	}
	public void setStud_gender(String stud_gender) {
		StuRecBean.stud_gender = stud_gender;
	}
	public String getStud_birthday() {
		return stud_birthday;
	}
	public void setStud_birthday(String stud_birthday) {
		StuRecBean.stud_birthday = stud_birthday;
	}
	public String getStud_address() {
		return stud_address;
	}
	public void setStud_address(String stud_address) {
		StuRecBean.stud_address = stud_address;
	}
	public String getStud_phone_num() {
		return stud_phone_num;
	}
	public void setStud_phone_num(String stud_phone_num) {
		StuRecBean.stud_phone_num = stud_phone_num;
	}
	public String getStud_examinee_id() {
		return stud_examinee_id;
	}
	public void setStud_examinee_id(String stud_examinee_id) {
		StuRecBean.stud_examinee_id = stud_examinee_id;
	}
	public String getStud_apply_time() {
		return stud_apply_time;
	}
	public void setStud_apply_time(String stud_apply_time) {
		StuRecBean.stud_apply_time = stud_apply_time;
	}
}
