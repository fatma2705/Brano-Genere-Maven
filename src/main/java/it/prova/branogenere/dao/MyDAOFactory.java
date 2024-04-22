package it.prova.branogenere.dao;

public class MyDAOFactory {

	private static BranoDAO branoDaoInstance;
	private static GenereDAO genereDaoInstance;

	public static BranoDAO getBranoDaoInstance() {
		if (branoDaoInstance == null) {
			branoDaoInstance = new BranoDAOImpl();
		}
		return branoDaoInstance;

	}

	public static GenereDAO getGenereDaoInstance() {
		if (genereDaoInstance == null) {

			genereDaoInstance = new GenereDAOImpl();
		}
		return genereDaoInstance;
	}

}