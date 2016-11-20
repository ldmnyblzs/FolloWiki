package beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.transaction.RollbackException;
import javax.transaction.UserTransaction;

import dal.UserManager;
import entities.Subscribe;
import entities.User;
import logic.Constant;
import logic.IAccountManager;
import logic.AppException;

@Stateful
@SessionScoped
@ManagedBean(name = "userBean")
public class UserBean {

	@Resource
	private UserTransaction utx;
	// @ManagedProperty(value="#{screeningManager}")
	// private ScreeningManager sm;

	private IAccountManager am;

	private User user;

	private String username;
	private String password;
	private String password2;
	private String email;

	private static boolean start = true;

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

	// --- FONTOSABB METODUSOK --- //

	public String setAppLogin() {
		am = new UserManager();
		return Constant.SIGN_IN_KEY;
	}

	public String login() {
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
		return Constant.SIGN_IN_KEY;

	}
	
	// --- EGYEB METODUSOK --- //

	/*
	 * private User getUser() { try { User user = (User)
	 * em.createNamedQuery("CUser.findByUsername"). setParameter("username",
	 * username).getSingleResult(); return user; } catch (NoResultException nre)
	 * { return null; } }
	 */

	/*
	 * public String firstUsers() throws RollbackException {
	 * 
	 * if(start){
	 * 
	 * User user = new User();
	 * 
	 * 
	 * try { utx.begin(); em.persist(user); utx.commit(); } catch (Exception ex)
	 * { //Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null,
	 * ex); }
	 * 
	 * fname = "asd"; lname = "asd"; username = "asd"; password = "asd";
	 * password2 = "asd";
	 * 
	 * signUpUser();
	 * 
	 * //sm.firstScreenings();
	 * 
	 * start = false; }
	 * 
	 * //return sm.toList(); return null; }
	 */

	// --- GETTER SETTER --- //

	/*
	 * public ScreeningManager getSm() { return sm; }
	 */

	/*
	 * public void setSm(ScreeningManager sm) { this.sm = sm; }
	 */
}
