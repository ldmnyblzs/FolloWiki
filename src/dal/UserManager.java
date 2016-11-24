package dal;

import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import entities.Notification;
import entities.User;
import logic.Constant;
import logic.IAccountManager;
import logic.AppException;

@Stateless
public class UserManager implements IAccountManager, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7943458182428071266L;

	@PersistenceUnit
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("FolloWikiDB");

	public User createUser(String username, String pwHash, String email, String role) {

		EntityManager em = emf.createEntityManager();
		User u = new User();

		u.setUsername(username);
		u.setPwHash(pwHash);
		u.setEmail(email);
		// u.setRole(role);
		u.setRole(Constant.USER_ROLE);
		u.setNotifications(new ArrayList<Notification>());

		em.getTransaction().begin();
		em.persist(u);
		em.getTransaction().commit();

		System.out.println("User '" + username + "' created.");

		return u;
	}

	public void deleteUser(long id) {

		EntityManager em = emf.createEntityManager();

		User u = em.find(User.class, id);
		String username = u.getUsername();

		em.getTransaction().begin();
		em.remove(u);
		em.getTransaction().commit();

		System.out.println("User '" + username + "' removed.");
	}

	public void updateUser(long id, String username, String pwHash, String email, ArrayList<Notification> notifications){

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		User user = em.find(User.class, id);
		user.setUsername(username);
		user.setPwHash(pwHash);
		user.setEmail(email);
		user.setNotifications(notifications);
		em.merge(user);
		em.persist(user);

		em.getTransaction().commit();

	}
	
	public User getUserByUsername(String username) {

		EntityManager em = emf.createEntityManager();
		Query q = em.createNamedQuery("User.username", User.class);
		q.setParameter("username", username);
		Object u;
		try {

			u = q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		System.out.println("User '" + username + "' found by name.");
		return (User) u;
	}

	public User getUserById(long id) {

		EntityManager em = emf.createEntityManager();
		User u = em.find(User.class, id);
		System.out.println("User '" + u.getUsername() + "' found by id.");
		return u;
	}

	// --- IMPLEMENTALT METODUSOK --- //

	@Override
	public User signUp(String username, String password, String password2, String email) throws AppException {
		User user = getUserByUsername(username);
		if (user == null) {
			if (!password.equals(password2)) {
				throw new AppException("A megadott jelszavak nem egyeznek.");
			}
			String pwHash = password;
			try {
				user = createUser(username, pwHash, email, Constant.USER_ROLE);
				return user;
			} catch (Exception e) {
				throw new AppException("A felhasználó létrehozása sikertelen!",
						"Hiba tötrtént a létrehozás közben. Vedd fel a kapcsolatot az adminisztrátorral: "
								+ e.getMessage());
			}
		} else {
			throw new AppException("A(z) '" + username + "' felhasználónév már foglalt!", "Válassz másik nevet.");

		}
	}

	@Override
	public void delete(String username, String password) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(String username, String password, String password2, String email) {
		// TODO Auto-generated method stub

	}

	@Override
	public User login(String username, String password) throws AppException {

		User user = getUserByUsername(username);
		if (user != null) {
			if (!user.getPwHash().equals(password)) {
				/*
				 * FacesMessage message = new
				 * FacesMessage(FacesMessage.SEVERITY_ERROR,
				 * "Sikertelen bejelentkezés!", "HibĂˇs jelszĂł.");
				 */
				throw new AppException("Hibás jelszó");
			}

			// context.getExternalContext().getSessionMap().put(SESSION_KEY,
			// user);
			// return sm.toList();
			return user;
		} else {
			throw new AppException("Sikertelen bejelentkezés", "A(z) '" + username + "' felhasználónév nem létezik.");
		}

	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub

	}

}
