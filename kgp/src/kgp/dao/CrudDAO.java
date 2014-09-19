
package kgp.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CrudDAO<T> {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("KGP");
	
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
	public void salvar(T t){
		EntityManager em = getEntityManager();
		
		em.getTransaction().begin();
		em.persist(t);
		em.getTransaction().commit();
		em.close();
	}
	
	public void excluir(T t) {
		EntityManager em = getEntityManager();
		
		em.getTransaction().begin();
		em.remove(t);
		em.getTransaction().commit();
		em.close();
	}	
	
	public void atualizar(T t) {
		EntityManager em = getEntityManager();
		
		em.getTransaction().begin();
		em.merge(t);
		em.getTransaction().commit();
		em.close();
	}	
	
	public void limpar() {
		EntityManager em = getEntityManager();
		
		em.getTransaction().begin();
		em.flush();
		em.getTransaction().commit();
		em.close();
	}
	
	public void transacao(Transacao t) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		t.executar(em);
		em.getTransaction().commit();
	}
}
