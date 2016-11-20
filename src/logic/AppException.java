package logic;

import javax.faces.application.FacesMessage;

public class AppException extends Exception {

	private FacesMessage message;
	
	public AppException(String summary, String details) {
		message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, details);
	}
	public AppException(String summary) {
		message = new FacesMessage(summary);
	}
	public FacesMessage getErrorMessage(){
		return message;
	}
}
