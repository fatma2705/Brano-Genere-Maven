package it.prova.branogenere.dao;

import java.util.List;

import it.prova.branogenere.model.Brano;
import it.prova.branogenere.model.Genere;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class BranoDAOImpl implements BranoDAO {

	EntityManager entityManager;

	@Override
	public List<Brano> getAll() throws Exception {
		try {
			entityManager = EntityManagerUtil.getEntityManager();
			return entityManager.createQuery("SELECT DISTINCT b FROM Brano b LEFT JOIN FETCH b.generi ", Brano.class).getResultList();
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
					"SELECT COUNT(*) FROM Brano b WHERE b.titolo = :titoloBrano AND b.autore = :autoreBrano AND b.dataPubblicazione = :dataPubblicazione",
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
				query.setParameter("idBrano", brano.getId());
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
	public List<Brano> listaBraniConGeneriConDescrizionePiuDiNCaratteri(int n) throws Exception {
		 try {
		        entityManager = EntityManagerUtil.getEntityManager();
		        

		        Query query = entityManager.createNativeQuery(
		                "SELECT b.* " +
		                "FROM brano b " +
		                "INNER JOIN brano_genere bg ON b.id = bg.id_brano " +
		                "INNER JOIN genere g ON bg.id_genere = g.id " +
		                "WHERE length(g.descrizione) > :n",Brano.class);
		        query.setParameter("n", n);
		        return query.getResultList();

		    } catch (Exception e) {
		        if (entityManager != null && entityManager.getTransaction().isActive()) {
		            entityManager.getTransaction().rollback();
		        }
		        throw e;
		    } finally {
		        if (entityManager != null && entityManager.isOpen()) {
		            entityManager.close();
		        }
		    }
		}

	@Override
	public List<Genere> estraiListaDescrizioneGenereAssociateAdUnBrano(String titolo) throws Exception {
        entityManager = EntityManagerUtil.getEntityManager();
        
        try {
        Query query = entityManager.createNativeQuery(
        		"SELECT  g.* "
    	                + " FROM genere g "
    	                + " INNER JOIN  brano_genere bg ON g.id = bg.id_genere "
    	                + " INNER JOIN brano b ON bg.id_brano = b.id "
    	                + " WHERE  b.titolo = :titolo ",Genere.class);
        query.setParameter("titolo", titolo);
        return query.getResultList();

    } catch (Exception e) {
        if (entityManager != null && entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
        throw e;
    } finally {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
	}

