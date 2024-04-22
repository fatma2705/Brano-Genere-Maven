package it.prova.branogenere.dao;

import java.util.List;

import it.prova.branogenere.model.Brano;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class BranoDAOImpl implements BranoDAO{
	
	EntityManager entityManager;

	@Override
	public List<Brano> getAll() throws Exception {
		try {
			entityManager = EntityManagerUtil.getEntityManager();
			return entityManager.createQuery("from Brano", Brano.class).getResultList();
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	@Override
	public Brano getElement(Long id) throws Exception {
		try {
			entityManager = EntityManagerUtil.getEntityManager();
			return entityManager.find(Brano.class, id);
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	@Override
	public void update(Brano brano) throws Exception {
		try {
			entityManager = EntityManagerUtil.getEntityManager();
			entityManager.getTransaction().begin();
			if (brano == null) {
				throw new Exception("Impossibile eseguire l'update nel DB. Input non valido");
			}
			entityManager.merge(brano);
			entityManager.getTransaction().commit();
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}
	@Override
	public void insert(Brano brano) throws Exception {
		try {
			entityManager = EntityManagerUtil.getEntityManager();
			entityManager.getTransaction().begin();
			if (brano == null) {
				throw new Exception("Errore valore input");
			}
			entityManager.persist(brano);
			entityManager.getTransaction().commit();
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}
	@Override
	public void delete(Brano brano) throws Exception {
		try {
			entityManager = EntityManagerUtil.getEntityManager();
			entityManager.getTransaction().begin();
			if (brano == null) {
				throw new Exception("Impossibile eseguire la delete nel DB. Input non valido");
			}
			entityManager.remove(entityManager.merge(brano));
			entityManager.getTransaction().commit();
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	@Override
	public boolean exist(Brano brano) throws Exception {
		try {
			entityManager = EntityManagerUtil.getEntityManager();
			Query query = entityManager.createQuery(
					"SELECT COUNT(b) FROM brano b WHERE b.titolo = :titoloBrano AND b.autore = :autoreBrano AND b.data_pubblicazione = :dataPubblicazione", Long.class);
			query.setParameter("titoloBrano", brano.getTitolo());
			query.setParameter("autoreBrano", brano.getAutore());
			query.setParameter("dataPubblicazione", brano.getDataPubblicazione());
			Long result = (Long) query.getSingleResult();
			System.out.println("----------result " + result);
			return result > 0;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
				
		
	}

		

}