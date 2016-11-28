package dal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import entities.Notification;
import entities.Subscribe;
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
	static EntityManager em = emf.createEntityManager();

	public User createUser(String username, String pwHash, String email) {
		User u = new User();

		u.setUsername(username);
		u.setPwHash(pwHash);
		u.setEmail(email);
		u.setNotifications(new ArrayList<Notification>());
		u.setSubscribes(new ArrayList<Subscribe>());

		em.getTransaction().begin();
		em.persist(u);
		em.getTransaction().commit();

		return u;
	}

	public void deleteUser(long id) {
		User u = em.find(User.class, id);
		String username = u.getUsername();

		em.getTransaction().begin();
		em.remove(u);
		em.getTransaction().commit();
	}

	public void updateUser(long id, String username, String pwHash, String email, List<Notification> notifications) {
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

	public void refreshUser(User user) {
		em.getTransaction().begin();
		em.refresh(user);
		em.getTransaction().commit();
	}

	public User getUserByUsername(String username) {
		Query q = em.createNamedQuery("User.username", User.class);
		q.setParameter("username", username);
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<User> q2 = cb.createQuery(User.class);
			Root<User> ru = q2.from(User.class);
			ParameterExpression<String> p = cb.parameter(String.class);
			q2.select(ru).where(cb.equal(ru.get("username"), p));
			TypedQuery<User> q3 = em.createQuery(q2);
			q3.setParameter(p, username);
			return q3.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public User getUserById(long id) {
		User u = em.find(User.class, id);
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
				user = createUser(username, pwHash, email);
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
				throw new AppException("Hibás jelszó");
			}
			return user;
		} else {
			throw new AppException("Sikertelen bejelentkezés", "A(z) '" + username + "' felhasználónév nem létezik.");
		}

	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUser() {
		Query q = em.createNamedQuery("User.all", User.class);
		List<User> users;
		try {
			return q.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
