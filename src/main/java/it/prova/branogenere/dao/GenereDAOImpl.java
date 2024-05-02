package it.prova.branogenere.dao;

import java.util.List;

import it.prova.branogenere.model.Genere;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class GenereDAOImpl implements GenereDAO {

	EntityManager entityManager;

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Genere> getAll() throws Exception {
		return entityManager.createQuery("SELECT DISTINCT g FROM Genere g LEFT JOIN FETCH g.brani ", Genere.class)
				.getResultList();
	}

	@Override
	public Genere getElement(Long id) throws Exception {
		TypedQuery<Genere> query = entityManager
				.createQuery("SELECT g FROM Genere g LEFT JOIN FETCH g.brani WHERE g.id = :id", Genere.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}

	@Override
	public void update(Genere genere) throws Exception {
		entityManager.merge(genere);
	}

	@Override
	public void insert(Genere genere) throws Exception {
		entityManager.persist(genere);
	}

	@Override
	public void delete(Genere genere) throws Exception {
		entityManager.remove(entityManager.merge(genere));
	}

	@Override
	public boolean exist(Genere genere) throws Exception {
		TypedQuery<Long> query = entityManager
				.createQuery("SELECT COUNT(*) FROM Genere g WHERE g.descrizione = :descrizioneGenere", Long.class);
		query.setParameter("descrizioneGenere", genere.getDescrizione());
		return query.getSingleResult() > 0;
	}

	@Override
	public void deleteBranoGenereAssociazione(Genere genere) throws Exception {
		Query query = entityManager.createNativeQuery("DELETE FROM  brano_genere WHERE  id_genere = :idGenere ");
		query.setParameter("idGenere", genere.getId());
		query.executeUpdate();
	}

	@Override
	public Genere getBy(String descrizione) throws Exception {
		return entityManager.createQuery("from Genere g where g.descrizione = '" + descrizione + "'", Genere.class)
				.getSingleResult();
	}

	@Override
	public List<Genere> listaGeneriDiBraniPubblicatiTra(int primaData, int secondaData) throws Exception {

		Query query = entityManager.createNativeQuery(
				"SELECT  g.* " + " FROM genere g " + " INNER JOIN  brano_genere bg ON g.id = bg.id_genere "
						+ " INNER JOIN brano b ON bg.id_brano = b.id "
						+ " WHERE YEAR(data_pubblicazione) >:primaData AND YEAR(data_pubblicazione) <:secondaData ",
				Genere.class);
		query.setParameter("primaData", primaData);
		query.setParameter("secondaData", secondaData);
		return query.getResultList();
	}

}
