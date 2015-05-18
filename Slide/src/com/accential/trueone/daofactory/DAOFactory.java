package com.accential.trueone.daofactory;

import com.accential.trueone.interfaces.IAditionalAddressesUser;
import com.accential.trueone.interfaces.ICheckout;
import com.accential.trueone.interfaces.ICompaniesInvitationsFilter;
import com.accential.trueone.interfaces.ICompaniesInvitationsUser;
import com.accential.trueone.interfaces.ICompaniesUser;
import com.accential.trueone.interfaces.ICompanyCategory;
import com.accential.trueone.interfaces.ICompanyPreference;
import com.accential.trueone.interfaces.ICompanySubCategory;
import com.accential.trueone.interfaces.IFacebookProfile;
import com.accential.trueone.interfaces.IOCompany;
import com.accential.trueone.interfaces.IOUser;
import com.accential.trueone.interfaces.IOffer;
import com.accential.trueone.interfaces.IOfferFilter;
import com.accential.trueone.interfaces.IOfferPhoto;
import com.accential.trueone.interfaces.IOffersComment;
import com.accential.trueone.interfaces.IOffersUser;
import com.accential.trueone.interfaces.IPaymentMethod;
import com.accential.trueone.interfaces.IUsersWishlistCompany;
import com.accential.trueone.interfaces.IWishlist;

public abstract class DAOFactory {

	public static final byte JSON = 1;
	private static JSONDAOFactory jsonDAO = null;

	public static DAOFactory whichFactory(byte which) {

		try {

			switch (which) {
			case JSON:
				return jsonDAO == null ? new JSONDAOFactory() : jsonDAO;
			default:
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public abstract IOffer JSONDAOOffer();

	public abstract IOUser JSONDAOUser();

	public abstract IOCompany JSONDAOCompany();

	public abstract ICompanyCategory JSONDAOCompanyCategory();

	public abstract ICompanySubCategory JSONDAOCompanySubCategory();

	public abstract IWishlist JSONDAOWishlist();

	public abstract IPaymentMethod JSONDAOPaymentMethod();

	public abstract ICheckout JSONDAOCheckout();

	public abstract IOffersComment JSONDAOOffersComment();

	public abstract ICompaniesUser JSONDAOCompaniesUser();

	public abstract ICompaniesInvitationsFilter JSONDAOCompaniesInvitationsFilter();

	public abstract ICompaniesInvitationsUser JSONDAOCompaniesInvitationsUsers();

	public abstract IFacebookProfile JSONDAOFacebookProfile();

	public abstract IAditionalAddressesUser JSONDAOAditionalAddresses();

	public abstract IUsersWishlistCompany JSONDAOUsersWishlistCompany();

	public abstract ICompanyPreference JSONDAOCompanyPreference();

	public abstract IOfferPhoto JSONDAOOfferPhoto();

	public abstract IOfferFilter JSONDAOfferFilter();

	public abstract IOffersUser JSONDAOOffersUser();

}
