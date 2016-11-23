package logic;

import javax.faces.application.FacesMessage;

public class AppException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 757299476555405080L;

	private FacesMessage message;

	public AppException(String summary, String details) {
		message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, details);
	}

	public AppException(String summary) {
		message = new FacesMessage(summary);
	}

	public FacesMessage getErrorMessage() {
		return message;
	}
}
