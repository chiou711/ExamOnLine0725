package qExamPaperBean;

public class QExPaperBean {
	// for being Java bean
	private  static String ePaperId = null;
	private  static String ePaper = null;
	private  static String ePaperMaxId = null;


	public String getEPaperId() {
		return ePaperId;
	}

	public void setEPaperId(String ePaperId) {
		QExPaperBean.ePaperId = ePaperId;
	}
	
	public String getEPaper() {
		return ePaper;
	}
	
	public void setEPaper(String ePaper) {
		QExPaperBean.ePaper = ePaper;
	}
	
	public String getEPaperMaxId() {
		return ePaperMaxId;
	}
	
	public void setEPaperMaxId(String ePaperMaxId) {
		QExPaperBean.ePaperMaxId = ePaperMaxId;
	}
	

}
