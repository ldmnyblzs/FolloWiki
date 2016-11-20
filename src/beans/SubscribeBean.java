package beans;

import java.time.Duration;

import javax.ejb.Stateful;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import dal.SubscribeManager;
import logic.Constant;

@Stateful
@RequestScoped
@ManagedBean(name = "subscribeBean")
public class SubscribeBean {

    @ManagedProperty(value="#{userBean}")
    private UserBean ub;
    private int frequency;
    private int sensitivity;
    
	private SubscribeManager sm = new SubscribeManager();
	private String url;
	
	
	
	public UserBean getUb() {
		return ub;
	}



	public void setUb(UserBean ub) {
		this.ub = ub;
	}



	public int getFrequency() {
		return frequency;
	}



	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}



	public int getSensitivity() {
		return sensitivity;
	}



	public void setSensitivity(int sensitivity) {
		this.sensitivity = sensitivity;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public String createSubscribe(){
		sm.createSubscribe(ub.getUser(), url, frequency, sensitivity);
		return Constant.CONTROL_KEY;
	}
	
}
