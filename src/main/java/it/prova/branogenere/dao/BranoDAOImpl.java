package it.prova.branogenere.dao;

import java.util.List;

import it.prova.branogenere.model.Brano;
import it.prova.branogenere.model.Genere;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class BranoDAOImpl implements BranoDAO {

	EntityManager entityManager;

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	@Override
	public List<Brano> getAll() throws Exception {
		return entityManager.createQuery("SELECT DISTINCT b FROM Brano b LEFT JOIN FETCH b.generi ", Brano.class)
				.getResultList();
	}

	@Override
	public Brano getElement(Long id) throws Exception {
		TypedQuery<Brano> query = entityManager
				.createQuery("SELECT b FROM Brano b LEFT JOIN FETCH b.generi WHERE b.id = :id", Brano.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}

	@Override
	public void update(Brano brano) throws Exception {
		entityManager.merge(brano);
	}

	@Override
	public void insert(Brano brano) throws Exception {
		entityManager.persist(brano);
	}

	@Override
	public void delete(Brano brano) throws Exception {
		entityManager.remove(entityManager.merge(brano));
	}

	@Override
	public boolean exist(Brano brano) throws Exception {
		TypedQuery<Long> query = entityManager.createQuery(
				"SELECT COUNT(*) FROM Brano b WHERE b.titolo = :titoloBrano AND b.autore = :autoreBrano AND b.dataPubblicazione = :dataPubblicazione",
				Long.class);
		query.setParameter("titoloBrano", brano.getTitolo());
		query.setParameter("autoreBrano", brano.getAutore());
		query.setParameter("dataPubblicazione", brano.getDataPubblicazione());
		return query.getSingleResult() > 0;
	}

	@Override
	public Brano getBy(String titolo) throws Exception {
		return entityManager.createQuery("from brano b where b.titolo = '" + titolo + "'", Brano.class)
				.getSingleResult();
	}

	@Override
	public void insertGenere(Brano brano, Genere genere) throws Exception {
		Query query = entityManager
				.createNativeQuery("INSERT INTO brano_genere (id_brano, id_genere) VALUES (:idBrano, :idGenere)");
		query.setParameter("idBrano", brano.getId());
		query.setParameter("idGenere", genere.getId());
		int rowsUpdated = query.executeUpdate();

	}

	@Override
	public void deleteBranoGenereAssociazione(Brano brano) throws Exception {
		Query query = entityManager.createNativeQuery("DELETE FROM  brano_genere WHERE  id_brano =  :idBrano");
		query.setParameter("idBrano", brano.getId());
		query.executeUpdate();
	}

	@Override
	public List<Brano> listaBraniConGeneriConDescrizionePiuDiNCaratteri(int n) throws Exception {
		Query query = entityManager.createNativeQuery(
				"SELECT b.* " + "FROM brano b " + "INNER JOIN brano_genere bg ON b.id = bg.id_brano "
						+ "INNER JOIN genere g ON bg.id_genere = g.id " + "WHERE length(g.descrizione) > :n",
				Brano.class);
		query.setParameter("n", n);
		return query.getResultList();
	}

	@Override
	public List<Genere> estraiListaDescrizioneGenereAssociateAdUnBrano(String titolo) throws Exception {
		Query query = entityManager
				.createNativeQuery(
						"SELECT  g.* " + " FROM genere g " + " INNER JOIN  brano_genere bg ON g.id = bg.id_genere "
								+ " INNER JOIN brano b ON bg.id_brano = b.id " + " WHERE  b.titolo = :titolo ",
						Genere.class);
		query.setParameter("titolo", titolo);
		return query.getResultList();
	}
}
