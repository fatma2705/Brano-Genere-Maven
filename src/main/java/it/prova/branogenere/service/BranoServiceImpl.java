package it.prova.branogenere.service;

import java.util.List;

import it.prova.branogenere.dao.BranoDAO;
import it.prova.branogenere.dao.EntityManagerUtil;
import it.prova.branogenere.dao.GenereDAO;
import it.prova.branogenere.model.Brano;
import it.prova.branogenere.model.Genere;
import jakarta.persistence.EntityManager;

public class BranoServiceImpl implements BranoService {

	private BranoDAO branoDaoInstance;
	private GenereDAO genereDaoInstance;
	EntityManager entityManager;
	
	
	@Override
	public void setBranoDAO(BranoDAO branoDaoInstance) throws Exception {
		this.branoDaoInstance = branoDaoInstance;

	}

	@Override
	public void setGenereDAO(GenereDAO genereDaoInstance) throws Exception {
		this.genereDaoInstance = genereDaoInstance;

	}

	@Override
	public List<Brano> getAll() throws Exception {
		entityManager = EntityManagerUtil.getEntityManager();
		try {
			branoDaoInstance.setEntityManager(entityManager);
			return branoDaoInstance.getAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Brano getElemento(Long id) throws Exception {
		entityManager = EntityManagerUtil.getEntityManager();

		try {
			if (id == null) {
				System.out.println("ID non inserito");
				System.exit(0);
			}
			branoDaoInstance.setEntityManager(entityManager);
			if (branoDaoInstance.getElement(id) == null) {
				System.out.println("Non esiste un brano con questo id");
				System.exit(0);
			}
			return branoDaoInstance.getElement(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void insert(Brano branoInstance, List<String> listaGeneri) throws Exception {
		entityManager = EntityManagerUtil.getEntityManager();

		try {
			if (branoInstance.equals(null)) {
				System.out.println("ERRORE: dati genere non inseriti");
				System.exit(0);
			}
			if (branoInstance.getId() != null) {
				System.out.println("ERRORE: id non  null");
				System.exit(0);
			}
			entityManager.getTransaction().begin();
			branoDaoInstance.setEntityManager(entityManager);
			genereDaoInstance.setEntityManager(entityManager);

			if (branoDaoInstance.exist(branoInstance)) {
				System.out.println("ERRORE: già esiste un brano con gli stessi dati");
				System.exit(0);
			}
			branoDaoInstance.setEntityManager(entityManager);
			branoDaoInstance.insert(branoInstance);
			for (String descrizione : listaGeneri) {
				if (genereDaoInstance.getBy(descrizione) == null) {
					Genere genere = new Genere(null, descrizione);
					genereDaoInstance.insert(genere);
					
					branoDaoInstance.insertGenere(branoInstance, genere);
				} else {
					branoDaoInstance.insertGenere(branoInstance, genereDaoInstance.getBy(descrizione));
					System.out.println("genere inserito con successo");
				}
			}

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
	public void delete(Brano branoInstance) throws Exception {
		entityManager = EntityManagerUtil.getEntityManager();

		try {
			if (branoInstance == null) {
				System.out.println("ERRORE: id brano non inserito");
				System.exit(0);
			}
			entityManager.getTransaction().begin();

			branoDaoInstance.setEntityManager(entityManager);
			Brano existingBrano = branoDaoInstance.getElement(branoInstance.getId());
			if (existingBrano == null) {
				System.out.println("ERRORE: Non esiste un brano con questo id ");
				System.exit(0);
			}
			branoDaoInstance.deleteBranoGenereAssociazione(existingBrano);
			System.out.println("Associazione tra brano e genere rimossa");

			branoDaoInstance.delete(branoInstance);
			System.out.println("Brano rimosso con successo");

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
	public void update(Brano branoInstance, List<String> listaGeneri) throws Exception {
		entityManager = EntityManagerUtil.getEntityManager();

		try {
			if (branoInstance.equals(null)) {
				System.out.println("ERRORE: dati brano non inseriti");
				System.exit(0);
			}
			entityManager.getTransaction().begin();

			branoDaoInstance.setEntityManager(entityManager);
			if (branoDaoInstance.getElement(branoInstance.getId()) == null) {
				System.out.println("ERRORE: Non esiste un brano con questi dati ");
				System.exit(0);
			}
			
			// delete relazione tra brano e genere
			branoDaoInstance.deleteBranoGenereAssociazione(branoInstance);
			System.out.println("relazione tra brano e genere rimossa");
			// update del brano
			branoDaoInstance.setEntityManager(entityManager);
			branoDaoInstance.update(branoInstance);
			System.out.println("brano aggiornta");
			// ciclo che itera dentro la lista descrizioni fornita dall'utente
			for (String descrizione : listaGeneri) {
				// if per controllare se esiste un genere con questa descrizione o no
				if (genereDaoInstance.getBy(descrizione) == null) {
					// se non esiste viene creato
					Genere genere = new Genere(null, descrizione);
					// inserito nella tabella genere
					genereDaoInstance.insert(genere);
					branoDaoInstance.setEntityManager(entityManager);
					// associzione brano a genere
					branoDaoInstance.insertGenere(branoInstance, genere);
					System.out.println("Brano aggiornato con successo");
				} else {
					// se già esiste direttamente associazione brano a genere
					branoDaoInstance.setEntityManager(entityManager);
					branoDaoInstance.insertGenere(branoInstance, genereDaoInstance.getBy(descrizione));
					System.out.println("Brano aggiornato con successo");
				}
			}

			System.out.println("Brano aggiornata con successo");

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
	public void listaBraniConGeneriConDescrizionePiuDiNCaratteri(int n) throws Exception {
		entityManager = EntityManagerUtil.getEntityManager();
		try {
			if (n ==  0 ) {
				System.out.println("ERRORE: lunghezza desiderata non inserita");
				System.exit(0);
			}
			entityManager.getTransaction().begin();

			branoDaoInstance.setEntityManager(entityManager);
			System.out.println("I brani con generi con descrizione più di  " + n + " sono \n");
			System.out.println(branoDaoInstance.listaBraniConGeneriConDescrizionePiuDiNCaratteri(n));
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
	public void estraiListaDescrizioneGenereAssociateAdUnBrano(String titolo) throws Exception {
		entityManager = EntityManagerUtil.getEntityManager();
		try {
			if (titolo ==  "" ) {
				System.out.println("ERRORE: titolo brano non inserito");
				System.exit(0);
			}
			entityManager.getTransaction().begin();

			branoDaoInstance.setEntityManager(entityManager);
			System.out.println("I generi del brano con titolo  " + titolo + " sono \n");
			System.out.println(branoDaoInstance.estraiListaDescrizioneGenereAssociateAdUnBrano(titolo));
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

	


