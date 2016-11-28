package beans; 

import java.io.Serializable;
import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

import dal.UserManager;
import entities.User;
import logic.Constant;
import logic.IAccountManager;
import logic.AppException;

@Stateful
@SessionScoped
@ManagedBean(name = "userBean")
public class UserBean implements Serializable {

	private static final long serialVersionUID = -600377825227986561L;

	@Resource
	private UserTransaction utx;

	private IAccountManager am;

	private User user;

	private String username;
	private String password;
	private String password2;
	private String email;

	// --- GETTER SETTER --- //

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// --- METODUSOK --- //

	public String setAppLogin() {
		am = new UserManager();
		return Constant.SIGN_IN_KEY;
	}

	public String login() {

		user = null;

		if (am == null)
			am = new UserManager();

		FacesContext context = FacesContext.getCurrentInstance();
		try {

			user = am.login(username, password);

			context.getExternalContext().getSessionMap().put(Constant.SESSION_KEY, user);

			return Constant.CONTROL_KEY;

		} catch (AppException e) {
			context.addMessage(null, e.getErrorMessage());
		}

		return Constant.SIGN_IN_KEY;
	}

	public String signUpUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (am == null)
			am = new UserManager();

		try {
			user = am.signUp(username, password, password2, email);
			return Constant.SIGN_IN_KEY;

		} catch (AppException e) {
			context.addMessage(null, e.getErrorMessage());
			return Constant.SIGN_UP_KEY;
		}
	}

	public String logout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (session != null) {
			session.invalidate();
		}
		this.user = null;
		return Constant.INDEX_SIDE_PATH;

	}
}
