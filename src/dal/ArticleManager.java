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

@Stateless
public class ArticleManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3459609416021775604L;

	@PersistenceUnit
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("FolloWikiDB");
	static EntityManager em = emf.createEntityManager();

	public Article createArticle(String url) {
		Article a = new Article();

		a.setUrl(url);

		em.getTransaction().begin();
		em.persist(a);
		em.getTransaction().commit();

		return a;
	}

	public Article deleteArticle(long id) {
		Article a = em.find(Article.class, id);

		em.getTransaction().begin();
		em.merge(a);
		em.remove(a);
		em.getTransaction().commit();

		return a;
	}

	@SuppressWarnings("unchecked")
	public List<Article> getAllArticles() {
		Query q = em.createNamedQuery("Article.all", Article.class);
		try {
			return q.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Article getArticleByUrl(String url) {
		Query q = em.createNamedQuery("Article.url", Article.class);
		q.setParameter("url", url);
		try {
			return (Article) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
