package it.prova.branogenere.dao;

import java.util.List;

import it.prova.branogenere.model.Brano;
import it.prova.branogenere.model.Genere;

public interface BranoDAO extends IBaseDAO<Brano> {
	
	public void insertGenere(Brano brano,Genere genere) throws Exception;
	
	public List<Brano> ListaBraniConpiuDinCaratteri(int n) throws Exception;
	

}