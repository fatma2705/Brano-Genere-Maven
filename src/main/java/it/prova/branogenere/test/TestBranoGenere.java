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

            System.out.println(" ----------- Fine Test Brano  ----");

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
        System.out.println(" -----------------------Test Stampa Brano con l' id : " + id + " ----------------");

        try {
            System.out.println(branoInstance.getElemento(id));
            System.out.println(" ----------------------------Fine Test----------------------------------------");

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

    public static void inserisciGenere(GenereService genereInstance, Genere genere) {
        System.out.println(" ----------- Inserisci Genere -------------");

        try {
            genereInstance.insert(genere);
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
        System.out.println(" -----------------------Test Stampa Genere con l' id : " + id + " ----------------");

        try {
            System.out.println(genereInstance.getElemento(id));
            System.out.println(" ------------------------------Fine Test Stampa Genere--------------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void ListGeneriBraniTra(GenereService genereInstance,int primaData,int secondaData) {
    	 System.out.println(" -----------------------Test Lista generi dei brani tra ----------------");

         try {
            genereInstance.listaGeneriDiBraniPubblicatiTra(primaData, secondaData);
             System.out.println(" ------------------------------Fine Test Lista generi dei brani tra--------------------------------------");

         } catch (Exception e) {
             e.printStackTrace();
         }
     }
    
    public static void ListBraniConLunghezzaPiu(BranoService branoInstance,int n) {
   	 System.out.println(" -----------------------Test Brani con lunghezza più ----------------");

        try {
        	branoInstance.ListaBraniConpiuDinCaratteri(n);
        	System.out.println(" ------------------------------Fine Test  Brani con lunghezza più--------------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}






	
	

	
	