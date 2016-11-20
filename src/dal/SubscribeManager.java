package dal;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedProperty;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import com.sun.xml.rpc.processor.generator.GeneratorUtil.SubclassComparator;
import com.sun.xml.rpc.processor.schema.UnimplementedFeatureException;

import beans.UserBean;
import entities.Article;
import entities.Subscribe;
import entities.User;

public class SubscribeManager {

	@PersistenceUnit
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("FolloWikiDB");

	public Subscribe createSubscribe(User user, String url, int frequency, int sensitivity){
		
		EntityManager em = emf.createEntityManager();
		ArticleManager am = new ArticleManager();
		Article article = am.getArticleByUrl(url);
		if(article == null) article = am.createArticle(url);
		
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
	public List<Subscribe> getAllSubscribeByArtikel(){
		throw new UnimplementedFeatureException(null);
	}
	public List<Subscribe> getAllSubscribeByUserId(long id){
		EntityManager em = emf.createEntityManager();
		Query q = em.createNamedQuery("Subscribe.userId", Subscribe.class);
		q.setParameter("userid", id);
		List<Subscribe> subs;
		try {

			subs =  q.getResultList();
		} catch (NoResultException e) {
			return null;			
		}
		return subs;
	}
	
	public User getUserByUsername(String username){
		
		EntityManager em = emf.createEntityManager();
		Query q = em.createNamedQuery("User.username", User.class);
		q.setParameter("username", username);
		User u;
		try {

			u = (User) q.getSingleResult();
		} catch (NoResultException e) {
			return null;			
		}
		System.out.println("User '" + u.getUsername() + "' found by name.");
		return u;
	}
	public void deleteSubscribe(long id) {

		EntityManager em = emf.createEntityManager();		
		em.getTransaction().begin();
		Subscribe sub = em.find(Subscribe.class, id);
		em.merge(sub);
		em.remove(sub);
		em.getTransaction().commit();
		
	}
	
	public Subscribe getSubscribeById(long id){

		EntityManager em = emf.createEntityManager();
		Subscribe sub;
		em.getTransaction().begin();
		sub = em.find(Subscribe.class, id);
		em.getTransaction().commit();
		
		return sub;
	}
}
