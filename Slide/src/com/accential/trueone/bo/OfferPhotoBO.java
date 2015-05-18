package com.accential.trueone.bo;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.OfferPhoto;
import com.accential.trueone.daofactory.DAOFactory;
import com.accential.trueone.interfaces.IOfferPhoto;

public class OfferPhotoBO {

	private static IOfferPhoto dao = DAOFactory.whichFactory(DAOFactory.JSON)
			.JSONDAOOfferPhoto();

	@SuppressWarnings("rawtypes")
	public static List<OfferPhoto> listOfferPhotos(Map params) {
		return dao.listOfferPhotos(params);
	}

	public static List<OfferPhoto> listPhotoByOffer(int offerId) {
		return dao.listPhotoByOffer(offerId);
	}

}
