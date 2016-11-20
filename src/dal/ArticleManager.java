package dal;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import entities.Article;
import entities.Notification;
import entities.User;
import logic.Constant;

public class ArticleManager {

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
		System.out.println("Article '" + a.getUrl() + "' found by name.");
		return a;
	}

	public Article createArticle(String url) {

		EntityManager em = emf.createEntityManager();
		Article a = new Article();

		a.setUrl(url);
		a.setDifferences(new ArrayList<String>());

		em.getTransaction().begin();
		em.persist(a);
		em.getTransaction().commit();

		System.out.println("Article '" + url + "' created.");

		return a;
	}
}
