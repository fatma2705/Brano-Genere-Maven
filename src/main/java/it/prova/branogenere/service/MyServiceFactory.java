package it.prova.branogenere.service;

import it.prova.branogenere.dao.MyDAOFactory;

public class MyServiceFactory {

		private static BranoService branoServiceInstance;
		private static GenereService genereServiceInstance;

		public static BranoService getBranoServiceInstance() {
			if (branoServiceInstance == null)
				branoServiceInstance = new BranoServiceImpl();

			try {
				branoServiceInstance.setBranoDAO(MyDAOFactory.getBranoDaoInstance());
				branoServiceInstance.setGenereDAO(MyDAOFactory.getGenereDaoInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}

			return branoServiceInstance;

		}

		public static GenereService getGenereServiceInstance() {
			if (genereServiceInstance == null)
				genereServiceInstance = new GenereServiceImpl();

			try {

				genereServiceInstance.setGenereDAO(MyDAOFactory.getGenereDaoInstance());
			} catch (Exception e) {

				e.printStackTrace();
			}
			return genereServiceInstance;
		}

	}


