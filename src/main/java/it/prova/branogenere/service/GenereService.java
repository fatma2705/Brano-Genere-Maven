package it.prova.branogenere.service;

import java.util.List;

import it.prova.branogenere.dao.BranoDAO;
import it.prova.branogenere.dao.GenereDAO;
import it.prova.branogenere.model.Genere;

public interface GenereService {

	public List<Genere> getAll() throws Exception;

	public Genere getElemento(Long id) throws Exception;

	public void insert(Genere genereInstance) throws Exception;

	public void delete(Genere genereInstance) throws Exception;

	public void update(Genere genereInstance) throws Exception;
	
	public void listaGeneriDiBraniPubblicatiTra(int primaData,int secondaData) throws Exception;

	public void setGenereDAO(GenereDAO genereInstance) throws Exception;

	public void setBranoDAO(BranoDAO branoInstance) throws Exception;

}
