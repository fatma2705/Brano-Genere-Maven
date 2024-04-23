package it.prova.branogenere.test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import it.prova.branogenere.model.Brano;
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
            System.out.println("3. Aggiornare un brano");
            System.out.println("4. Eliminare un brano");
            System.out.println("5. Aggiungere un genere");
            System.out.println("6. Leggere un genere");
            System.out.println("7. Aggiornare un genere");
            System.out.println("8. Eliminare un genere");
            System.out.println("9. Uscire");

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
                       InputDataPubblicazione = LocalDate.parse(dataPubblicazioneStr, DateTimeFormatter.ofPattern( "yyyy-MM-dd"));
                   } catch (DateTimeParseException e) {
                       System.out.println("ERRORE: Formato data non valido. Assicurati di inserire la data nel formato corretto  \"yyyy-MM-dd\".");
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
                   
                   Brano brano = new Brano(null,titolo,autore,InputDataPubblicazione);
                   TestBranoGenere.inserisciBrano(branoServiceInstance, brano,generi);
                   
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


                case 3:
                    System.out.println("Hai scelto di aggiornare un brano");
                    // Codice per aggiornare un brano
                    break;

                case 4:
                    System.out.println("Hai scelto di eliminare un brano");
                    // Codice per eliminare un brano
                    break;

                case 5:
                    System.out.println("Hai scelto di aggiungere un genere");
                    // Codice per aggiungere un genere
                    break;

                case 6:
                    System.out.println("Hai scelto di leggere un genere");
                    // Codice per leggere un genere
                    break;

                case 7:
                    System.out.println("Hai scelto di aggiornare un genere");
                    // Codice per aggiornare un genere
                    break;

                case 8:
                    System.out.println("Hai scelto di eliminare un genere");
                    // Codice per eliminare un genere
                    break;

                case 9:
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
