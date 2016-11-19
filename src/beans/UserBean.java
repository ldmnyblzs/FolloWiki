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

@Stateful
@SessionScoped
@ManagedBean(name = "login")
public class UserBean {
    
    public static final String SESSION_KEY = "session";
    public static final String USER_ROLE = "user";
    public static final String ADMIN_ROLE = "admmin";

    @Resource 
    private UserTransaction utx;
    //@ManagedProperty(value="#{screeningManager}")
    //private ScreeningManager sm;

    private String username;
    private String password;
    private String passwordv;
    private String fname;
    private String lname;
    private String email;
    
    private static boolean start = true;
    
    //--- GETTER SETTER --- //
    
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

	public String signInUser() {   
        FacesContext context = FacesContext.getCurrentInstance();
        UserManager um = new UserManager();
        User user = um.getUserByUsername(username);
        if (user != null) {
            if (!user.getPwHash().equals(password)) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                           "Sikertelen bejelentkezés!",
                                           "HibĂˇs jelszĂł.");
                context.addMessage(null, message);
                return null;
            }
            
            context.getExternalContext().getSessionMap().put(SESSION_KEY, user);
            //return sm.toList();
            return null;
        } else {           
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Sikertelen bejelentkezĂ©s",
                    "A(z) '"
                    + username
                    +
                    "' felhasznĂˇlĂłnĂ©v nem lĂ©tezik.");
            context.addMessage(null, message);
            return null;
        }
    }
    

    public String signUpUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserManager um = new UserManager();
        User wuser = um.getUserByUsername(username);
        if (wuser == null) {
            if (!password.equals(passwordv)) {
                FacesMessage message = new FacesMessage("A megadott jelszavak nem egyeznek.");
                context.addMessage(null, message);
                return null;
            }
            wuser = new User();
            /*wuser.setFirstname(fname);
            wuser.setLastname(lname);
            wuser.setPassword(password);
            wuser.setUsername(username);
            wuser.setcRole(USER_ROLE);
            wuser.setSince(new Date());*/
            String pwHash = password;
            try {
                utx.begin();
                um.createUser(username, pwHash, email);
                utx.commit();
                return "login";
            } catch (Exception e) {               
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                        "A felhasznĂˇlĂł lĂ©trehozĂˇsa sikertelen!",
                                                        "Hiba tĂ¶rtĂ©nt a lĂ©trehozĂˇs kĂ¶zben. Vedd fel a kapcsolatot az adminisztrĂˇtorral.");
                context.addMessage(null, message);
                Logger.getAnonymousLogger().log(Level.SEVERE,
                                                "Nem lehet az Ăşj felhasznĂˇlĂłt lĂ©trehozni.",
                                                e);
                return null;
            }
        } else {           
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                    "A(z) '"
                                                      + username 
                                                      + "' felhasznĂˇlĂłnĂ©v mĂˇr foglalt!",
                                                    "KĂ©rlek, vĂˇlassz mĂˇsik nevet.");
            context.addMessage(null, message);
            return null;
        }        
    }
    
    
    public String logout() {
        HttpSession session = (HttpSession)
             FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "login";
        
    }
    
    // --- EGYEB METODUSOK --- //
    
    /*private User getUser() {
        try {
            User user = (User)
            em.createNamedQuery("CUser.findByUsername").
                    setParameter("username", username).getSingleResult();
            return user; 
        } catch (NoResultException nre) {
            return null;
        }
    }*/
    
    /*public String firstUsers() throws RollbackException {

        if(start){
        
        User user = new User();

        
        try {
            utx.begin();
            em.persist(user);
            utx.commit();
        } catch (Exception ex) {
            //Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        fname = "asd";
        lname = "asd";
        username = "asd";
        password = "asd";
        passwordv = "asd";

        signUpUser();
        
        //sm.firstScreenings();
        
        start = false;
        }
        
        //return sm.toList();
        return null;
    }*/
    
    // --- GETTER SETTER --- //
    
    /*public ScreeningManager getSm() {
        return sm;
    }*/

    /*public void setSm(ScreeningManager sm) {
        this.sm = sm;
    } */
}
