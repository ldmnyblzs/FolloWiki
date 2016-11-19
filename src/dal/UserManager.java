package dal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import com.sun.xml.rpc.processor.schema.UnimplementedFeatureException;

import entities.User;

public class UserManager {

	@PersistenceUnit
	static	EntityManagerFactory emf = 
		Persistence.createEntityManagerFactory("FolloWikiDB");
	
	public boolean loginUser(){
		throw new UnimplementedFeatureException(null);
	}
	public void createUser(String username, String pwHash, String email){

		EntityManager em = emf.createEntityManager();
		User u = new User();
		
			u.setUsername(username);
			u.setPwHash(pwHash);
			u.setEmail(email);
			
			em.getTransaction().begin();
			em.persist(u);
			em.getTransaction().commit();
			
			System.out.println("User '" + username + "' created.");
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
}
