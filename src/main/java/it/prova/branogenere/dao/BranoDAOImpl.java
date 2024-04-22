package it.prova.branogenere.dao;

import java.util.List;
import java.util.Set;

import it.prova.branogenere.model.Brano;
import it.prova.branogenere.model.Genere;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class BranoDAOImpl implements BranoDAO {

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
					"SELECT COUNT(b) FROM Brano b WHERE b.titolo = :titoloBrano AND b.autore = :autoreBrano AND b.data_pubblicazione = :dataPubblicazione",
					Long.class);
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

	@Override
	public Brano getBy(String titolo) throws Exception {
		try {
			entityManager = EntityManagerUtil.getEntityManager();
			return entityManager.createQuery("from brano b where b.titolo = '" + titolo + "'", Brano.class)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	
	@Override
	public void insertGenere(Brano brano, Genere genere) throws Exception {

		if (brano == null) {
			throw new Exception("Errore valore input");
		}

		if (genere == null) {
			throw new Exception("Errore valore input");
		}

		Query query = entityManager
				.createNativeQuery("INSERT INTO brano_genere (id_brano, id_genere) VALUES (:idBrano, :idGenere)");
		query.setParameter("idBrano", brano.getId());
		query.setParameter("idGenere", genere.getId());
		int rowsUpdated = query.executeUpdate();

	}

	@Override
	public void deleteBranoGenereAssociazione(Brano brano) throws Exception {
		entityManager = EntityManagerUtil.getEntityManager();
		try {
			if (brano == null) {
				throw new Exception("Errore valore input");
			}
			entityManager.getTransaction().begin();
			Query query = entityManager.createNativeQuery(
					"DELETE FROM  brano_genere WHERE  id_genere = :idGenere AND id_brano =  :idBrano");
			for (Genere genere : brano.getGeneri()) {
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

}