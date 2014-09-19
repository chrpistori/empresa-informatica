package kgp.dao;

import javax.persistence.EntityManager;

public interface Transacao {
	public void executar(EntityManager em);
}
