package it.prova.branogenere.test;

import java.util.List;

import it.prova.branogenere.model.Brano;
import it.prova.branogenere.model.Genere;
import it.prova.branogenere.service.BranoService;
import it.prova.branogenere.service.GenereService;


public class TestBranoGenere {

    public static void getAllBrani(BranoService branoInstance) {

        System.out.println(" ----------- Stampa tutti i brani presenti nel database -------------");

        try {
            List<Brano> brani = branoInstance.getAll();
            for (Brano brano : brani) {
                System.out.println(brano.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void inserisciBrano(BranoService branoInstance, Brano brano, List<String> generi) {
        System.out.println(" ----------- Test Inserisci brano -------------");

        try {
            branoInstance.insert(brano, generi);

            System.out.println(" ----------- Test Brano inserito con successo ----");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void aggiornaBrano(BranoService branoInstance, Brano brano, List<String> generi) {
        System.out.println(" -------------- Aggiorna Brano  -----------");

        try {
            branoInstance.update(brano, generi);
            System.out.println(" ----------- Brano aggiornato con successo ----");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void rimuoviBrano(BranoService branoInstance, Brano brano) {
        System.out.println(" -----------------------Rimuovi Brano ----------------");

        try {
            branoInstance.delete(brano);
            System.out.println(" ----------- Brano rimosso con successo ----");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getBrano(BranoService branoInstance, Long id) {
        System.out.println(" -----------------------Stampa Brano con l' id : " + id + " ----------------");

        try {
            System.out.println(branoInstance.getElemento(id));
            System.out.println(" --------------------------------------------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getAllGeneri(GenereService genereInstance) {
        System.out.println(" ----------- Stampa tutti i generi presenti nel database -------------");

        try {
            List<Genere> generi = genereInstance.getAll();
            for (Genere genere : generi) {
                System.out.println(genere.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void inserisciGenere(GenereService genereInstance, Genere genere, List<String> brani) {
        System.out.println(" ----------- Inserisci Genere -------------");

        try {
            genereInstance.insert(genere, brani);
            System.out.println(" ----------- Genere inserito con successo ----");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void aggiornaGenere(GenereService genereInstance, Genere genere) {
        System.out.println(" -------------- Aggiorna Genere  -----------");

        try {
            genereInstance.update(genere);
            System.out.println(" ----------- Genere aggiornato con successo ----");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void rimuoviGenere(GenereService genereInstance, Genere genere) {
        System.out.println(" -----------------------Rimuovi Genere ----------------");

        try {
            genereInstance.delete(genere);
            System.out.println(" ----------- Genere rimosso con successo ----");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getGenere(GenereService genereInstance, Long id) {
        System.out.println(" -----------------------Stampa Genere con l' id : " + id + " ----------------");

        try {
            System.out.println(genereInstance.getElemento(id));
            System.out.println(" --------------------------------------------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}






	
	

	
	