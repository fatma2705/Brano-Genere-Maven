package it.prova.branogenere.dao;

import java.util.List;

import it.prova.branogenere.model.Brano;
import it.prova.branogenere.model.Genere;
import jakarta.persistence.EntityManager;

public interface IBaseDAO<T> {

		public List<T> getAll() throws Exception;

		public T getElement(Long id) throws Exception;

		public void update(T o) throws Exception;

		public void insert(T o) throws Exception;

		public void delete(T o) throws Exception;

		public boolean exist(T o) throws Exception;
		
		public T getBy(String stringa)throws Exception;
		
		public void deleteBranoGenereAssociazione(T o) throws Exception;

		// questo mi serve per l'injection
		public void setEntityManager(EntityManager entityManager);

	}