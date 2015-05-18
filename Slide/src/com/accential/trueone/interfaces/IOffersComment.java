package com.accential.trueone.interfaces;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.OffersComment;

/**
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public interface IOffersComment {

	@SuppressWarnings("rawtypes")
	public List<OffersComment> listOffersComments(Map params);

	@SuppressWarnings("rawtypes")
	public int countOffersComments(Map params);

	@SuppressWarnings("rawtypes")
	public List<OffersComment> listAllOffersComments(Map params);

	public List<OffersComment> searchByOffer(int offerId);

	public int amountOffersCommentByOffer(int offerId);

	public void saveComment(OffersComment comment);

	public List<OffersComment> listByOffer(int offerId);

	public List<OffersComment> listByUserAndOffer(int userId, int offerId);

	public List<OffersComment> listByCompany(int compId);

}
