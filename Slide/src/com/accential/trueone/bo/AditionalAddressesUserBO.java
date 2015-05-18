package com.accential.trueone.bo;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.AditionalAddressesUser;
import com.accential.trueone.daofactory.DAOFactory;
import com.accential.trueone.interfaces.IAditionalAddressesUser;

public class AditionalAddressesUserBO {

	private static IAditionalAddressesUser dao = DAOFactory.whichFactory(
			DAOFactory.JSON).JSONDAOAditionalAddresses();

	/**
	 * LISTA TODOS ENDERE��OS COMPLEMENTARES - EXECUTAR EM THREAD SEPARADA
	 * 
	 * @param params
	 * @return List<AditionalAddressesUser>
	 */
	public static List<AditionalAddressesUser> listAddresses(Map params) {
		return dao.listAddresses(params);
	}

	/**
	 * LISTA TODOS OS ENDERECOS COMPLEMENTARES - N��O NECESSITA EXECUTAR EM
	 * THREAD SEPARADA
	 * 
	 * @param params
	 * @return List<AditionalAddressesUser>
	 */
	public static List<AditionalAddressesUser> listaAllAddresses(Map params) {
		return dao.listaAllAddresses(params);
	}

	/**
	 * RETORNA ENDERECO COMPLEMENTAR POR ID DO USUARIO
	 * 
	 * @param id
	 * @return AditionalAddressesUser
	 */
	public static AditionalAddressesUser searchAddressByUserId(int id) {
		return dao.searchAddressByUserId(id);
	}

	/**
	 * SALVA ENDERECO COMPLEMENTAR NA BASE DE DADOS
	 * 
	 * @param address
	 */
	public static void saveAddress(AditionalAddressesUser address) {
		dao.saveAddress(address);
	}

	/**
	 * Lista TODOS os endereços adicionais do usuario
	 * 
	 * @param userId
	 * @return
	 */
	public static List<AditionalAddressesUser> listAllByUser(int userId) {
		return dao.listAllByUser(userId);
	}

}
