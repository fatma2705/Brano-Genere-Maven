package it.prova.branogenere.service;

import java.util.List;

import it.prova.branogenere.dao.BranoDAO;
import it.prova.branogenere.dao.GenereDAO;
import it.prova.branogenere.model.Brano;

public interface BranoService {

	public List<Brano> getAll() throws Exception;

	public Brano getElemento(Long id) throws Exception;

	public void insert(Brano branoInstance, List<String> listaGeneri) throws Exception;

	public void delete(Brano branoInstance) throws Exception;

	public void update(Brano branoInstance, List<String> listaGeneri) throws Exception;

	public void listaBraniConGeneriConDescrizionePiuDiNCaratteri(int n) throws Exception;

	public void estraiListaDescrizioneGenereAssociateAdUnBrano(String titolo) throws Exception;

	public void setBranoDAO(BranoDAO branoDaoInstance) throws Exception;

	public void setGenereDAO(GenereDAO genereDaoInstance) throws Exception;

}
