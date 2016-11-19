package beans;

import java.util.Date;
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
import entities.User;
import logic.Constant;
import logic.IAccountManager;
import logic.UserException;

@Stateful
@SessionScoped
@ManagedBean(name = "login")
public class UserBean {

	@Resource
	private UserTransaction utx;
	// @ManagedProperty(value="#{screeningManager}")
	// private ScreeningManager sm;

	private IAccountManager am;

	private User user;

	private String username;
	private String password;
	private String passwordv;
	private String fname;
	private String lname;
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

	public String getPasswordv() {
		return passwordv;
	}

	public void setPasswordv(String passwordv) {
		this.passwordv = passwordv;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// --- FONTOSABB METODUSOK --- //

	public String setAccountManager(String loginMethod){

		switch (loginMethod) {
		case "applogin":
			am = new UserManager();
			break;

		case "wikilogin":
			
			break;
			
		case "fblogin":
			
			break;

		default:
			break;
		}
		String x = Constant.SIGN_IN;
		return x;
	}
	
	public String login() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			
			user = am.login(username, password);
			
		} catch (UserException e) {
			context.addMessage(null, e.getErrorMessage());
		}
		context.getExternalContext().getSessionMap().put(Constant.SESSION_KEY, user);
		
		return Constant.CONTROL_PANEL;
	}

	public String signUpUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		return null;
	}

	public String logout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return "login";

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
	 * passwordv = "asd";
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
