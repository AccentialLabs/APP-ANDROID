package com.accential.trueone.interfaces;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.OfferPhoto;

@SuppressWarnings("all")
/**
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public interface IOfferPhoto {

	public List<OfferPhoto> listOfferPhotos(Map params);

	public List<OfferPhoto> listPhotoByOffer(int offerId);

}
