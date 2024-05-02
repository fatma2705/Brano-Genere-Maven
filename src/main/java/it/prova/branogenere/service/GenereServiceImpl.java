package it.prova.branogenere.service;

import java.util.List;

import it.prova.branogenere.dao.BranoDAO;
import it.prova.branogenere.dao.EntityManagerUtil;
import it.prova.branogenere.dao.GenereDAO;
import it.prova.branogenere.model.Genere;
import jakarta.persistence.EntityManager;

public class GenereServiceImpl implements GenereService {

	private GenereDAO genereDaoInstance;
	private BranoDAO branoDaoInstance;
	EntityManager entityManager;

	@Override
	public void setGenereDAO(GenereDAO genereInstance) throws Exception {
		this.genereDaoInstance = genereInstance;

	}

	@Override
	public void setBranoDAO(BranoDAO branoInstance) throws Exception {
		this.branoDaoInstance = branoInstance;

	}

	@Override
	public List<Genere> getAll() throws Exception {
		entityManager = EntityManagerUtil.getEntityManager();
		try {
			genereDaoInstance.setEntityManager(entityManager);
			List<Genere> generi = genereDaoInstance.getAll();
			if (generi.isEmpty()) {
				System.out.println("Database vuoto nulla da stampare");
				System.exit(0);
			}
			return generi;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Genere getElemento(Long id) throws Exception {
		entityManager = EntityManagerUtil.getEntityManager();
		try {
			if (id == null) {
				System.out.println("ID non inserito");
				System.exit(0);
			}
			genereDaoInstance.setEntityManager(entityManager);
			return genereDaoInstance.getElement(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void insert(Genere genereInstance) throws Exception {
		entityManager = EntityManagerUtil.getEntityManager();
		try {
			if (genereInstance.equals(null)) {
				System.out.println("ERRORE: dati genere non inseriti");
				System.exit(0);
			}
			if (genereInstance.getId() != null) {
				System.out.println("ERRORE: id non  nullo");
				System.exit(0);
			}
			entityManager.getTransaction().begin();
			genereDaoInstance.setEntityManager(entityManager);
			if (genereDaoInstance.exist(genereInstance)) {
				System.out.println("ERRORE: già esiste questo tipo di genere");
				System.exit(0);
			}
			genereDaoInstance.insert(genereInstance);
			System.out.println("Genere inserito con successo");
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}

	}

	@Override
	public void delete(Genere genereInstance) throws Exception {
		entityManager = EntityManagerUtil.getEntityManager();
		try {
			// controllo se sono stati inseriti i dati nella variabile genereInstance
			if (genereInstance == null) {
				System.out.println("ERRORE: id genere non inserito");
				System.exit(0);
			}
			entityManager.getTransaction().begin();

			genereDaoInstance.setEntityManager(entityManager);
			// controllo se esiste un genere con questo id
			Genere existingGenere = genereDaoInstance.getElement(genereInstance.getId());
			if (existingGenere == null) {
				System.out.println("ERRORE: Non esiste un genere con questo id ");
				System.exit(0);
			}
			// delete della associazione tra genere e brano
			genereDaoInstance.deleteBranoGenereAssociazione(existingGenere);
			System.out.println("Associazione tra Genere e brano rimossa");
			// infine delete del genere
			genereDaoInstance.delete(genereInstance);
			System.out.println("Genere rimosso dal DB");
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void update(Genere genereInstance) throws Exception {
		entityManager = EntityManagerUtil.getEntityManager();
		try {
			if (genereInstance.equals(null)) {
				System.out.println("ERRORE: dati genere non inseriti");
				System.exit(0);
			}
			entityManager.getTransaction().begin();

			genereDaoInstance.setEntityManager(entityManager);
			if (genereDaoInstance.getElement(genereInstance.getId()) == null) {
				System.out.println("ERRORE: Non esiste un genere con questi dati ");
				System.exit(0);
			}
			genereDaoInstance.update(genereInstance);
			entityManager.getTransaction().commit();
			System.out.println(" genere aggiornato con succcesso");
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}

	}

	@Override
	public void listaGeneriDiBraniPubblicatiTra(int primaData, int secondaData) throws Exception {
		entityManager = EntityManagerUtil.getEntityManager();
		try {
			if (primaData == 0 || secondaData == 0) {
				System.out.println("ERRORE: date non inserite");
				System.exit(0);
			}
			entityManager.getTransaction().begin();
			genereDaoInstance.setEntityManager(entityManager);
			System.out.println(
					"I generi dei brani pubblicati tra l'anno " + primaData + " e l'anno " + secondaData + " sono \n");
			System.out.println(genereDaoInstance.listaGeneriDiBraniPubblicatiTra(primaData, secondaData));
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}

	}
}
