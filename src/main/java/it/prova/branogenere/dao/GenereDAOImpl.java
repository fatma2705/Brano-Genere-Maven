package it.prova.branogenere.dao;

import java.util.List;

import it.prova.branogenere.model.Brano;
import it.prova.branogenere.model.Genere;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class GenereDAOImpl implements GenereDAO {

	EntityManager entityManager;
	

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Genere> getAll() throws Exception {
		try {
			entityManager = EntityManagerUtil.getEntityManager();
			return entityManager.createQuery("from Genere", Genere.class).getResultList();
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	@Override
	public Genere getElement(Long id) throws Exception {
		try {
			entityManager = EntityManagerUtil.getEntityManager();
			return entityManager.find(Genere.class, id);
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	@Override
	public void update(Genere genere) throws Exception {
		try {
			entityManager = EntityManagerUtil.getEntityManager();
			entityManager.getTransaction().begin();
			if (genere == null) {
				throw new Exception("Impossibile eseguire l'update nel DB. Input non valido");
			}
			entityManager.merge(genere);
			entityManager.getTransaction().commit();
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	@Override
	public void insert(Genere genere) throws Exception {
		try {

			entityManager = EntityManagerUtil.getEntityManager();
			entityManager.getTransaction().begin();
			if (genere == null) {
				throw new Exception("Errore valore input");
			}
			entityManager.persist(genere);
			entityManager.getTransaction().commit();
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	@Override
	public void delete(Genere genere) throws Exception {
		try {
			entityManager = EntityManagerUtil.getEntityManager();
			entityManager.getTransaction().begin();
			if (genere == null) {
				throw new Exception("Impossibile eseguire la delete nel DB. Input non valido");
			}
			entityManager.remove(entityManager.merge(genere));
			entityManager.getTransaction().commit();
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	@Override
	public boolean exist(Genere genere) throws Exception {
		try {
			entityManager = EntityManagerUtil.getEntityManager();
			Query query = entityManager
					.createQuery("SELECT COUNT(*) FROM Genere g WHERE g.descrizione = :descrizioneGenere", Long.class);
			query.setParameter("descrizioneGenere", genere.getDescrizione());
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
	public void deleteBranoGenereAssociazione(Genere genere) throws Exception {
		entityManager = EntityManagerUtil.getEntityManager();
		try {
			if (genere == null ) {
				throw new Exception("Errore valore input");
			}
			entityManager.getTransaction().begin();
			Query query = entityManager.createNativeQuery(
					"DELETE FROM  brano_genere WHERE  id_genere = :idGenere AND id_brano =  :idBrano");
			for (Brano brano : genere.getBrani()) {
				query.setParameter("idGenere", genere.getId());
				query.setParameter("id_brano", brano.getId());
				query.executeUpdate();
			}
			entityManager.getTransaction().commit();

		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}

	}

	@Override
	public Genere getBy(String descrizione) throws Exception {
		try {
			entityManager = EntityManagerUtil.getEntityManager();
			return entityManager.createQuery("from Genere g where g.descrizione = '" + descrizione + "'", Genere.class)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}
	
	
	

		
}

