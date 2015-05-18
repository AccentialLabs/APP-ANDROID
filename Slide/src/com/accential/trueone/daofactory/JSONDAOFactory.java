package com.accential.trueone.daofactory;

import com.accential.trueone.dao.*;
import com.accential.trueone.interfaces.*;

public class JSONDAOFactory extends DAOFactory {

	@Override
	public IOffer JSONDAOOffer() {
		return new OfferDAO();
	}

	@Override
	public IOUser JSONDAOUser() {
		return new UserDAO();
	}

	@Override
	public IOCompany JSONDAOCompany() {
		return new CompanyDAO();
	}

	@Override
	public ICompanyCategory JSONDAOCompanyCategory() {
		return new CompanyCategoryDAO();
	}

	@Override
	public ICompanySubCategory JSONDAOCompanySubCategory() {
		return new CompanySubCategoryDAO();
	}

	@Override
	public IWishlist JSONDAOWishlist() {
		return new WishlistDAO();
	}

	@Override
	public ICheckout JSONDAOCheckout() {
		return new CheckoutDAO();
	}

	@Override
	public IPaymentMethod JSONDAOPaymentMethod() {
		return new PaymentMethodDAO();
	}

	@Override
	public IOffersComment JSONDAOOffersComment() {
		return new OffersCommentDAO();
	}

	@Override
	public ICompaniesUser JSONDAOCompaniesUser() {
		return new CompaniesUserDAO();
	}

	@Override
	public ICompaniesInvitationsFilter JSONDAOCompaniesInvitationsFilter() {
		return new CompaniesInvitationsFilterDAO();
	}

	@Override
	public ICompaniesInvitationsUser JSONDAOCompaniesInvitationsUsers() {
		return new CompaniesInvitationsUserDAO();
	}

	@Override
	public IFacebookProfile JSONDAOFacebookProfile() {
		return new FacebookProfileDAO();
	}

	@Override
	public IAditionalAddressesUser JSONDAOAditionalAddresses() {
		return new AditionalAddressesUserDAO();
	}

	@Override
	public IUsersWishlistCompany JSONDAOUsersWishlistCompany() {
		return new UsersWishlistCompanyDAO();
	}

	@Override
	public ICompanyPreference JSONDAOCompanyPreference() {
		return new CompanyPreferenceDAO();
	}

	@Override
	public IOfferPhoto JSONDAOOfferPhoto() {
		return new OfferPhotoDAO();
	}

	@Override
	public IOfferFilter JSONDAOfferFilter() {
		return new OfferFilterDAO();
	}

	@Override
	public IOffersUser JSONDAOOffersUser() {
		return new OffersUserDAO();
	}

}
