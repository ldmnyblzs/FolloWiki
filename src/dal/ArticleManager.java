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

	public Article getArticleByUrl(String url) {

		EntityManager em = emf.createEntityManager();
		Query q = em.createNamedQuery("Article.url", Article.class);
		q.setParameter("url", url);
		Article a;
		try {

			a = (Article) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return a;
	}

	public Article createArticle(String url) {

		EntityManager em = emf.createEntityManager();
		Article a = new Article();

		a.setUrl(url);
		// a.setChanges(new ArrayList<Notification>());

		em.getTransaction().begin();
		em.persist(a);
		em.getTransaction().commit();

		return a; 
	}
	
	public Article deleteArticle(long id) {

		EntityManager em = emf.createEntityManager();
		Article a = em.find(Article.class, id);

		em.getTransaction().begin();
		em.merge(a);
		em.remove(a);
		em.getTransaction().commit();

		return a;
	}
}
