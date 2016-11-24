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

import entities.Article;
import entities.Notification;
import entities.Subscribe;
import entities.User;

@Stateless
public class SubscribeManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4296443767603977769L;

	@PersistenceUnit
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("FolloWikiDB");

	public Subscribe createSubscribe(User user, String url, int frequency, int sensitivity) {

		EntityManager em = emf.createEntityManager();
		ArticleManager am = new ArticleManager();
		Article article = am.getArticleByUrl(url);
		if (article == null)
			article = am.createArticle(url);

		Subscribe sub = new Subscribe();
		sub.setFrequency(frequency);
		sub.setSensitivity(sensitivity);
		sub.setUser(user);
		sub.setArticle(article);

		em.getTransaction().begin();
		em.persist(sub);
		em.getTransaction().commit();

		System.out.println("Subscribe '" + url + "' created.");

		return sub;
	}
	
	public List<User> getAllSubscribedUserByArticle(String url) {

		EntityManager em = emf.createEntityManager();
		Query q = em.createNamedQuery("User.articleUrl", User.class);
		q.setParameter("url", url);
		List<User> users;
		try {
			@SuppressWarnings("unchecked")
			List<User> resultList = (List<User>) q.getResultList();
			users = resultList;
		} catch (NoResultException e) {
			return null;
		}
		return users;
		
	}
	
	public List<String> getAllArticleUrl() {

		EntityManager em = emf.createEntityManager();
		Query q = em.createNamedQuery("ArticleUrl.toSubscribe", Article.class);
		List<String> articles;
		try {
			@SuppressWarnings("unchecked")
			List<String> resultList = (List<String>) q.getResultList();
			articles = resultList;
		} catch (NoResultException e) {
			return null;
		}
		return articles;
		
	}
	
	public List<Subscribe> getAllSubscribeByArtikelUrl(String url
			) {
		throw new UnsupportedOperationException();
	}

	public List<Subscribe> getAllSubscribeByUserId(long id) {
		EntityManager em = emf.createEntityManager();
		Query q = em.createNamedQuery("Subscribe.userId", Subscribe.class);
		q.setParameter("userid", id);
		List<Subscribe> subs;
		try {

			@SuppressWarnings("unchecked")
			List<Subscribe> resultList = (List<Subscribe>) q.getResultList();
			subs = resultList;
		} catch (NoResultException e) {
			return null;
		}
		return subs;
	}

	public void deleteSubscribe(long id) {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Subscribe sub = em.find(Subscribe.class, id);
		em.merge(sub);
		em.remove(sub);
		em.getTransaction().commit();

	}

	public Subscribe getSubscribeById(long id) {

		EntityManager em = emf.createEntityManager();
		Subscribe sub;
		em.getTransaction().begin();
		sub = em.find(Subscribe.class, id);
		em.getTransaction().commit();

		return sub;
	}

	public void updateSubscribe(long id, String url, int frequency, int sensitivity) {

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		Subscribe sub = em.find(Subscribe.class, id);
		sub.setFrequency(frequency);
		sub.setSensitivity(sensitivity);
		em.merge(sub);
		em.persist(sub);

		em.getTransaction().commit();

	}
	
	public void saveNotification(Notification notification) {
		
		UserManager um = new UserManager();
		
		ArrayList<User> users = (ArrayList<User>) getAllSubscribedUserByArticle(notification.getUrl());
		for (User user : users) {
			ArrayList<Notification> notifications = user.getNotifications();
			notifications.add(notification);
			user.setNotifications(notifications);
			um.updateUser(user.getId(), user.getUsername(), user.getPwHash(), user.getEmail(), notifications);
		}
		
	}

	public Subscribe getSubscribeByUserIdAndUrl(long userId, String url) {
		EntityManager em = emf.createEntityManager();
		Query q = em.createNamedQuery("Subscribe.userIdAndUrl", Subscribe.class);
		q.setParameter("userid", userId);
		q.setParameter("url", url);
		Subscribe sub;
		try {

			Subscribe resultList = (Subscribe) q.getSingleResult();
			sub = resultList;
		} catch (NoResultException e) {
			return null;
		}
		return sub;
	}
}
