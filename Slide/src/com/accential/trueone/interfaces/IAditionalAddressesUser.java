package com.accential.trueone.interfaces;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.AditionalAddressesUser;

/**
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public interface IAditionalAddressesUser {

	List<AditionalAddressesUser> listAddresses(Map params);

	List<AditionalAddressesUser> listaAllAddresses(Map params);

	AditionalAddressesUser searchAddressByUserId(int id);

	void saveAddress(AditionalAddressesUser address);

	List<AditionalAddressesUser> listAllByUser(int userId);

}
