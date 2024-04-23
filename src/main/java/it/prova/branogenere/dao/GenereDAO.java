package it.prova.branogenere.dao;

import java.util.List;

import it.prova.branogenere.model.Genere;

public interface GenereDAO extends IBaseDAO<Genere> {
	
	public List<Genere> listaGeneriDiBraniPubblicatiTra(int primaData,int secondaData) throws Exception;
	
	
	
	

}
