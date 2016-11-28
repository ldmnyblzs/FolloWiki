package dal;

import java.io.Serializable;
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

	private static final long serialVersionUID = -4296443767603977769L;

	@PersistenceUnit
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("FolloWikiDB");
	static EntityManager em = emf.createEntityManager();

	public Subscribe createSubscribe(User user, String url, int frequency, int sensitivity) {
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

		return sub;
	}

	public void deleteSubscribe(long id) {
		em.getTransaction().begin();
		Subscribe sub = em.find(Subscribe.class, id);

		boolean lastSub = false;
		Article article = sub.getArticle();
		List<User> users = getAllSubscribedUserByArticle(article);
		if (users.size() == 1 && users.get(0).getId() == sub.getUser().getId()) {
			lastSub = true;
		}

		em.merge(sub);
		em.remove(sub);

		em.getTransaction().commit();

		if (lastSub) {
			ArticleManager am = new ArticleManager();
			am.deleteArticle(article.getId());
		}
	}

	public void updateSubscribe(long id, String url, int frequency, int sensitivity) {
		em.getTransaction().begin();

		Subscribe sub = em.find(Subscribe.class, id);
		sub.setFrequency(frequency);
		sub.setSensitivity(sensitivity);
		em.merge(sub);
		em.persist(sub);

		em.getTransaction().commit();
	}

	public Subscribe getSubscribeById(long id) {
		Subscribe sub;
		em.getTransaction().begin();
		sub = em.find(Subscribe.class, id);
		em.getTransaction().commit();
		return sub;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllSubscribedUserByArticle(Article article) {
		Query q = em.createNamedQuery("User.article", User.class);
		q.setParameter("article", article);
		try {
			return q.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Subscribe getSubscribeByUserAndArticle(User user, Article article) {
		Query q = em.createNamedQuery("Subscribe.userAndArticle", Subscribe.class);
		q.setParameter("user", user);
		q.setParameter("article", article);
		try {
			return (Subscribe) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void saveNotification(Notification notification) {

		UserManager um = new UserManager();

		List<User> users = getAllSubscribedUserByArticle(notification.getArticle());
		for (User user : users) {
			List<Notification> notifications = user.getNotifications();
			notifications.add(notification);
			user.setNotifications(notifications);
			um.updateUser(user.getId(), user.getUsername(), user.getPwHash(), user.getEmail(), notifications);
		}
	}
}
