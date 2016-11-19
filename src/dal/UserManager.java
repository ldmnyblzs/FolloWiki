package dal;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import com.sun.xml.rpc.processor.schema.UnimplementedFeatureException;

import entities.User;
import logic.IAccountManager;
import logic.UserException;

public class UserManager implements IAccountManager{

	@PersistenceUnit
	static	EntityManagerFactory emf = 
		Persistence.createEntityManagerFactory("FolloWikiDB");
	
	public boolean loginUser(){
		throw new UnimplementedFeatureException(null);
	}
	public User createUser(String username, String pwHash, String email){

		EntityManager em = emf.createEntityManager();
		User u = new User();
		
			u.setUsername(username);
			u.setPwHash(pwHash);
			u.setEmail(email);
			
			em.getTransaction().begin();
			em.persist(u);
			em.getTransaction().commit();
			
			System.out.println("User '" + username + "' created.");
			
			return u;
	}
	public void deleteUser(long id){

		EntityManager em = emf.createEntityManager();
		
		User u = em.find(User.class, id);
		String username = u.getUsername();
		
		em.getTransaction().begin();
		em.remove(u);
		em.getTransaction().commit();
			
		System.out.println("User '" + username + "' removed.");
	}
	public User getUserByUsername(String username){
		
		EntityManager em = emf.createEntityManager();
		Query q = em.createNamedQuery("User.username", User.class);
		q.setParameter("username", username);
		User u = (User) q.getSingleResult();
		System.out.println("User '" + u.getUsername() + "' found by name.");
		return u;
	}
	public User getUserById(long id){
		
		EntityManager em = emf.createEntityManager();
		User u = em.find(User.class, id);
		System.out.println("User '" + u.getUsername() + "' found by id.");
		return u;
	}
	
	@Override
	public User signUp(String username, String password, String password2, String email) throws UserException{
		User user = getUserByUsername(username);
		if (user == null) {
			if (!password.equals(password2)) {
				throw new UserException("A megadott jelszavak nem egyeznek.");
			}
			String pwHash = password;
			try {
				user = createUser(username, pwHash, email);
				return user;
			} catch (Exception e) {
				throw new UserException("A felhasználó létrehozása sikertelen!",
						"Hiba tötrtént a létrehozás közben. Vedd fel a kapcsolatot az adminisztrátorral.");
			}
		} else {
			throw new UserException("A(z) '" + username + "' felhasználónév már foglalt!", "Válassz másik nevet.");
			
		}
	}
	@Override
	public void deleteAcc(String username, String password) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(String username, String password, String password2, String email) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public User login(String username, String password) throws UserException {

		UserManager um = new UserManager();
        User user = um.getUserByUsername(username);
        if (user != null) {
            if (!user.getPwHash().equals(password)) {
                /*FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                           "Sikertelen bejelentkezés!",
                                           "HibĂˇs jelszĂł.");*/
                throw new UserException("Hibás jelszó");
            }
            
            //context.getExternalContext().getSessionMap().put(SESSION_KEY, user);
            //return sm.toList();
            return user;
        } else {           
        	throw new UserException(
                    "Sikertelen bejelentkezés",
                    "A(z) '"
                    + username
                    +
                    "' felhasználónév már létezik.");
        }
		
	}
	@Override
	public void logout() {
		// TODO Auto-generated method stub
		
	}
	
	
}
