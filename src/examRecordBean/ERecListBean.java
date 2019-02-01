package examRecordBean;

public class ERecListBean {
	// for being Java bean
	
//	examinee_id
//	examinee_status
//
//	examinee_answers
//	examinee_score
//
//	examinee_exam_paper_id
	public ERecListBean() {
	}
	
	private  String erId = null;
	private  String erAnsList = null;
	private  String erScore = null;
	private  String erEpaperId = null;
	
	public String getErId() {
		return erId;
	}
	public void setErId(String erId) {
		this.erId = erId;
	}
	public String getErAnsList() {
		return erAnsList;
	}
	public void setErAnsList(String erAnsList) {
		this.erAnsList = erAnsList;
	}
	public String getErScore() {
		return erScore;
	}
	public void setErScore(String erScore) {
		this.erScore = erScore;
	}
	public String getErEpaperId() {
		return erEpaperId;
	}
	public void setErEpaperId(String erEpaperId) {
		this.erEpaperId = erEpaperId;
	}
}
