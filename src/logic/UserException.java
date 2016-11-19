package logic;

import javax.faces.application.FacesMessage;

public class UserException extends Exception {

	private FacesMessage message;
	
	public UserException(String summary, String details) {
		message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, details);
	}
	public UserException(String summary) {
		message = new FacesMessage(summary);
	}
	public FacesMessage getErrorMessage(){
		return message;
	}
}
