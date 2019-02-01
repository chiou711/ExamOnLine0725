package examRecordBean;

public class ERecBean {
	// for being Java bean
	
//	examinee_id
//	examinee_status
//
//	examinee_answers
//	examinee_score
//
//	examinee_exam_paper_id
	public ERecBean() {
	}
	
	private  static String erId = null;
	private  static String erAnsList = null;
	private  static String erScore = null;
	private  static String erEpaperId = null;
	
	public String getErId() {
		return erId;
	}
	public void setErId(String erId) {
		ERecBean.erId = erId;
	}
	public String getErAnsList() {
		return erAnsList;
	}
	public void setErAnsList(String erAnsList) {
		ERecBean.erAnsList = erAnsList;
	}
	public String getErScore() {
		return erScore;
	}
	public void setErScore(String erScore) {
		ERecBean.erScore = erScore;
	}
	public String getErEpaperId() {
		return erEpaperId;
	}
	public void setErEpaperId(String erEpaperId) {
		ERecBean.erEpaperId = erEpaperId;
	}
}
