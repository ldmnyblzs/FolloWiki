package beans; 

import java.io.Serializable;

import javax.ejb.Stateful;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import dal.SubscribeManager;
import dal.UserManager;
import entities.Subscribe;
import logic.Constant;

@Stateful
@SessionScoped
@ManagedBean(name = "subscribeBean")
public class SubscribeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6990763636332699031L;

	@ManagedProperty(value = "#{userBean}")
	private UserBean ub;

	private String url;
	private long id;
	private int frequency;
	private int sensitivity;

	private SubscribeManager sm = new SubscribeManager();
	private UserManager um = new UserManager();

	// --- GETTER SETTER --- //

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	// --- METODUSOK --- //

	public String createSubscribe() {
		sm.createSubscribe(ub.getUser(), url, frequency, sensitivity);
		um.refreshUser(ub.getUser());
		return Constant.CONTROL_KEY;
	}

	public String deleteSubscribe() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		long currentSubscribeId = Long.parseLong(request.getParameter("selectedSubscribeId"));
		sm.deleteSubscribe(currentSubscribeId);
		um.refreshUser(ub.getUser());
		return Constant.CONTROL_KEY;
	}

	public String editSubscribe() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		id = Long.parseLong(request.getParameter("selectedSubscribeId"));

		Subscribe sub = sm.getSubscribeById(id);

		url = sub.getArticle().getUrl();
		frequency = sub.getFrequency();
		sensitivity = sub.getSensitivity();
		um.refreshUser(ub.getUser());

		return Constant.EDIT_KEY;
	}

	public String updateSubscribe() {

		sm.updateSubscribe(id, url, frequency, sensitivity);
		um.refreshUser(ub.getUser());

		return Constant.CONTROL_KEY;
	}
}
