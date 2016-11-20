package beans;

import java.time.Duration;

import javax.ejb.Stateful;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

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
	private String url;
    
	private SubscribeManager sm = new SubscribeManager();
		
	
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
	
	public String deleteSubscribe(){
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        long currentSubscribeId = Long.parseLong(request.getParameter("selectedSubscribeId"));
        sm.deleteSubscribe(currentSubscribeId);
		return Constant.CONTROL_KEY;
	}
}
