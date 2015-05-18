package com.accential.trueone.bo;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.OffersComment;
import com.accential.trueone.daofactory.DAOFactory;
import com.accential.trueone.interfaces.IOffersComment;

/**
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class OffersCommentBO {

	private static IOffersComment dao = DAOFactory
			.whichFactory(DAOFactory.JSON).JSONDAOOffersComment();

	/**
	 * ATEN����O: EXECUTAR ESSE M��TODO SEMPRE EM UMA THREAD SEPARADA
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param Map
	 * @return List<OffersComment>
	 */
	@SuppressWarnings("rawtypes")
	public static List<OffersComment> listOffersComments(Map params) {
		return dao.listOffersComments(params);
	}

	/**
	 * ATEN����O: EXECUTAR ESSE M��TODO SEMPRE EM UMA THREAD SEPARADA Retorna a
	 * quantidade (em n��meros) total da lista de comentarios de acordo com os
	 * paramentros enviados
	 * 
	 * @param Map
	 * @return int
	 * @author Matheus Odilon - accentialbrasil
	 */
	@SuppressWarnings("rawtypes")
	public static int countOffersComments(Map params) {
		return dao.countOffersComments(params);
	}

	/**
	 * Retorna quantidade de coment��rios de acordo com o id da Oferta passado
	 * 
	 * @param offerId
	 * @return
	 */
	public static int amountOffersCommentByOffer(int offerId) {
		return dao.amountOffersCommentByOffer(offerId);
	}

	/**
	 * ATEN����O: Esse m��todo n��o necessita ser executado em uma thread
	 * separada, pois j�� �� executando de forma ass��ncrona
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param Map
	 * @return List<OffersComment>
	 */
	@SuppressWarnings("rawtypes")
	public static List<OffersComment> listAllOffersComments(Map params) {
		return dao.listAllOffersComments(params);
	}

	public static List<OffersComment> searchByOffer(int offerId) {
		return dao.searchByOffer(offerId);
	}

	/**
	 * Recebe objeto comentario, faz a verificação caso já exista: Editamos o
	 * comentario. Caso não: criamos um novo registro.
	 * 
	 * @param comment
	 */
	public static void saveComment(OffersComment comment) {
		dao.saveComment(comment);
	}

	public static List<OffersComment> listByOffer(int offerId) {
		return dao.listByOffer(offerId);
	}

	public static List<OffersComment> listByUserAndOffer(int userId, int offerId) {
		return dao.listByUserAndOffer(userId, offerId);
	}

	/**
	 * Lista os comentarios para o detalhe da empresa.
	 * 
	 * @param compId
	 * @return List<OffersComment>
	 */
	public static List<OffersComment> listByCompany(int compId) {
		return dao.listByCompany(compId);
	}
}
