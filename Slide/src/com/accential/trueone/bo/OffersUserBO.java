package com.accential.trueone.bo;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.OffersUser;
import com.accential.trueone.daofactory.DAOFactory;
import com.accential.trueone.interfaces.IOffersUser;

@SuppressWarnings("all")
public class OffersUserBO {

	private static IOffersUser dao = DAOFactory.whichFactory(DAOFactory.JSON)
			.JSONDAOOffersUser();

	/**
	 * Deve ser executada em thread separada da principal
	 * 
	 * @param params
	 * @return
	 */
	public static List<OffersUser> listOffersUser(Map params) {
		return dao.listOffersUser(params);
	}

	/**
	 * Esse metodo pode ser executado diretamente na Activity, recomendamos que
	 * o execute em um service para que nao haja atrasado no processamento
	 * completo da aplicacao
	 * 
	 * @return
	 */
	public static List<OffersUser> listAllOffersUsersByUser(int userId) {
		return dao.listAllOffersUsersByUser(userId);
	}

}
