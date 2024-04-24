package it.prova.branogenere.test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import it.prova.branogenere.model.Brano;
import it.prova.branogenere.model.Genere;
import it.prova.branogenere.service.BranoService;
import it.prova.branogenere.service.GenereService;
import it.prova.branogenere.service.MyServiceFactory;

public class TestInterfacciaUtente {

	public static void main(String[] args) throws Exception {

		Scanner scanner = new Scanner(System.in);
		boolean exit = false;

		while (!exit) {
			System.out.println("Benvenuto nella tua interfaccia utente. Scegli un'opzione:");
			System.out.println("1. Aggiungere un brano");
			System.out.println("2. Leggere un brano");
			System.out.println("3. Leggere tutti i brani");
			System.out.println("4. Aggiornare un brano");
			System.out.println("5. Eliminare un brano");
			System.out.println("6. Aggiungere un genere");
			System.out.println("7. Leggere un genere");
			System.out.println("8. Leggere tutti i generi");
			System.out.println("9. Aggiornare un genere");
			System.out.println("10. Eliminare un genere");
			System.out.println("11. Lista I generi dei brani Pubblicati tra ");
			System.out.println("12. Lista i brani con descrizione genere più di");
			System.out.println("13. Estrai lista di descrizioni genere associate ad un brano");
			System.out.println("14. Uscire");

			int choice = scanner.nextInt();
			scanner.nextLine();

			BranoService branoServiceInstance = MyServiceFactory.getBranoServiceInstance();
			GenereService genereServiceInstance = MyServiceFactory.getGenereServiceInstance();

			switch (choice) {
			case 1:
				System.out.println("Hai scelto di aggiungere un brano");
				System.out.println("Inserisci il titolo del brano da aggiungere");
				String titolo = "";
				try {
					titolo = scanner.nextLine();
				} catch (InputMismatchException e) {
					System.out.println("ERRORE : non è  stata inserita una stringa ");
					System.exit(0);
				}
				System.out.println("Inserisci il nome dell'autore del brano");
				String autore = "";
				try {
					autore = scanner.nextLine();
				} catch (InputMismatchException e) {
					System.out.println("ERRORE : non è  stata inserita una stringa ");
					System.exit(0);
				}
				LocalDate InputDataPubblicazione = null;

				System.out.println("Inserisci data pubblicazione del brano (formato \"yyyy-MM-dd\"):");
				String dataPubblicazioneStr = scanner.nextLine();

				try {
					// Effettua il parsing della stringa in un oggetto LocalDate
					InputDataPubblicazione = LocalDate.parse(dataPubblicazioneStr,
							DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				} catch (DateTimeParseException e) {
					System.out.println(
							"ERRORE: Formato data non valido. Assicurati di inserire la data nel formato corretto  \"yyyy-MM-dd\".");
					System.exit(0);
				}

				System.out.print("A Quanti generi vuoi associare questo brano? ");
				int numGeneri = 0;
				boolean inputValido = false;
				while (!inputValido) {
					try {
						numGeneri = Integer.parseInt(scanner.nextLine());
						inputValido = true;
					} catch (NumberFormatException e) {
						System.out.println("Inserisci un numero valido.");
					}
				}

				List<String> generi = new ArrayList<>();

				for (int i = 0; i < numGeneri; i++) {
					System.out.print("Inserisci il genere #" + (i + 1) + ": ");
					String genere = scanner.nextLine();
					generi.add(genere);
				}

				System.out.println("I generi inseriti sono:");
				for (String genere : generi) {
					System.out.println("- " + genere);
				}
				scanner.nextLine();

				Brano brano = new Brano(null, titolo, autore, InputDataPubblicazione);
				TestBranoGenere.inserisciBrano(branoServiceInstance, brano, generi);

				break;

			case 2:
				System.out.println("Hai scelto di leggere un brano");
				System.out.println("Inserisci L'id  del brano da leggere:");
				Long id = null;
				try {
					id = scanner.nextLong();
				} catch (InputMismatchException e) {
					System.out.println("ERRORE : non è  stato inserito un long ");
					System.exit(0);
				}
				scanner.nextLine();
				TestBranoGenere.getBrano(branoServiceInstance, id);

				break;

			case 3:
				System.out.println("Hai scelto di leggere tutti i brani");
				TestBranoGenere.getAllBrani(branoServiceInstance);

				break;
			case 4:
				System.out.println("Hai scelto di aggiornare un brano");
				System.out.println("Inserisci l'id del brano da aggiornare:");
				Long idB = null;
				try {
					idB = scanner.nextLong();
				} catch (InputMismatchException e) {
					System.out.println("ERRORE: Non è stato inserito un long valido");
					System.exit(0);
				}
				scanner.nextLine();

				System.out.println("Inserisci il nuovo titolo del brano da aggiornare:");
				String titoloB = "";
				try {
					titoloB = scanner.nextLine();
				} catch (InputMismatchException e) {
					System.out.println("ERRORE: Non è stata inserita una Stringa ");
					System.exit(0);
				}

				System.out.println("Inserisci il nuovo autore della pizza da aggiornare:");
				String autoreB = "";
				try {
					autoreB = scanner.nextLine();
				} catch (InputMismatchException e) {
					System.out.println("ERRORE: Non è stata inserita una stringa valida");
					System.exit(0);
				}
				LocalDate InputDataPubblicazioneB = null;

				System.out.println("Inserisci la nuova  data pubblicazione del brano (formato \"yyyy-MM-dd\"):");
				String dataPubblicazioneB = scanner.nextLine();

				try {
					InputDataPubblicazioneB = LocalDate.parse(dataPubblicazioneB,
							DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				} catch (DateTimeParseException e) {
					System.out.println(
							"ERRORE: Formato data non valido. Assicurati di inserire la data nel formato corretto  \"yyyy-MM-dd\".");
					System.exit(0);
				}
				System.out.println("Quanti generi vuoi aggiornare?");
				int numGeneriB = 0;
				boolean inpuTValido = false;
				while (!inpuTValido) {
					try {
						numGeneriB = Integer.parseInt(scanner.nextLine());
						inpuTValido = true;
					} catch (NumberFormatException e) {
						System.out.println("Inserisci un numero valido.");
					}
				}

				List<String> generiB = new ArrayList<>();

				for (int i = 0; i < numGeneriB; i++) {
					System.out.print("Inserisci il genere #" + (i + 1) + ": ");
					String genere = scanner.nextLine();
					generiB.add(genere);
				}

				System.out.println("I generi inseriti sono:");
				for (String genere : generiB) {
					System.out.println("- " + genere);
				}
				scanner.nextLine();
				Brano branoB = new Brano(idB, titoloB, autoreB, InputDataPubblicazioneB);
				TestBranoGenere.aggiornaBrano(branoServiceInstance, branoB, generiB);
				break;

			case 5:
				System.out.println("Hai scelto di eliminare un brano");
				System.out.println("Inserisci L'id  del brano da eliminare:");
				Long idb = null;
				try {
					idb = scanner.nextLong();
				} catch (InputMismatchException e) {
					System.out.println("ERRORE : non è  stato inserito un Long ");
					System.exit(0);
				}
				scanner.nextLine();
				Brano branob = new Brano(idb);
				TestBranoGenere.rimuoviBrano(branoServiceInstance, branob);

			case 6:
				System.out.println("Hai scelto di aggiungere un genere");
				System.out.println("Inserisci la descrizione  del genere da aggiungere");
				String descrizione = "";
				try {
					descrizione = scanner.nextLine();
				} catch (InputMismatchException e) {
					System.out.println("ERRORE : non è  stata inserita una stringa ");
					System.exit(0);
				}
				Genere genere = new Genere(null,descrizione);
				TestBranoGenere.inserisciGenere(genereServiceInstance, genere);
				break;

			case 7:
				System.out.println("Hai scelto di leggere un genere");
				System.out.println("Inserisci L'id  del genere da leggere:");
				Long idg = null;
				try {
					idg = scanner.nextLong();
				} catch (InputMismatchException e) {
					System.out.println("ERRORE : non è  stato inserito un long ");
					System.exit(0);
				}
				scanner.nextLine();
				TestBranoGenere.getGenere(genereServiceInstance, idg);
				break;

			case 8:
				System.out.println("Hai scelto di leggere tutti i generi");
				TestBranoGenere.getAllGeneri(genereServiceInstance);
				break;

			case 9:
				System.out.println("Hai scelto di aggiornare un genere");
				System.out.println("Inserisci L'id  del genere da aggiornare:");
				Long idG = null;
				try {
					idG = scanner.nextLong();
				} catch (InputMismatchException e) {
					System.out.println("ERRORE : non è  stato inserito un long ");
					System.exit(0);
				}
				scanner.nextLine();
				System.out.println("Inserisci la nuova  descrizione  del genere ");
				String descrizioneG = "";
				try {
					descrizioneG = scanner.nextLine();
				} catch (InputMismatchException e) {
					System.out.println("ERRORE : non è  stata inserita una stringa ");
					System.exit(0);
				}
				
				Genere genereG = new Genere(idG,descrizioneG);
				TestBranoGenere.aggiornaGenere(genereServiceInstance, genereG);
				break; 
			case 10:
				System.out.println("Hai scelto di eliminare un genere");
				System.out.println("Inserisci L'id del genere da eliminare:");
				Long idGenere = null;
				try {
					idGenere = scanner.nextLong();
				} catch (InputMismatchException e) {
					System.out.println("ERRORE : non è  stato inserito un long ");
					System.exit(0);
				}
				scanner.nextLine();
				Genere deleteGenere = new Genere(idGenere);
				TestBranoGenere.rimuoviGenere(genereServiceInstance, deleteGenere);
			case 11:
				System.out.println(" Hai scelto di listare i generi dei brani pubblicati tra");
				System.out.println("Inserisci il primo anno:");
				int primaData = 0;
				try {
					primaData = scanner.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("ERRORE : non è  stato inserito un'anno valido ");
					System.exit(0);
				}
				System.out.println("Inserisci il secondo anno:");
				int secondaData = 0;
				try {
					secondaData = scanner.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("ERRORE : non è  stato inserito un'anno valido ");
					System.exit(0);
				}
				genereServiceInstance.listaGeneriDiBraniPubblicatiTra(primaData, secondaData);
			case 12:
				System.out.println("Hai scelto di listare tutti i brani con descrizione genere più di");
				System.out.println("Inserisci la lunghezza:");
				int n = 0;
				try {
					n = scanner.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("ERRORE : non è  stato inserito un'anno valido ");
					System.exit(0);
				}
				TestBranoGenere.ListBraniConLunghezzaPiu(branoServiceInstance, n);
			case 13:
				System.out.println("Hai scelto di estrarre una lista di descrizioni genere associate ad un brano");
				System.out.println("Inserisci il titolo del brano:");
				String titoloT = "";
				try {
					titoloT = scanner.nextLine();
				} catch (InputMismatchException e) {
					System.out.println("ERRORE : non è  stata inserita una stringa ");
					System.exit(0);
				}
				TestBranoGenere.estraiListaDescrizioneGenereAssociateAdUnBrano(branoServiceInstance, titoloT);;
				
			case 14:
				exit = true;
				break;

			default:
				System.out.println("Opzione non valida. Riprova.");
			}
		}

		System.out.println("Grazie per aver usato l'interfaccia utente. Arrivederci!");
		scanner.close();
	}

}
