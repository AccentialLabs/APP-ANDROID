package com.accential.trueone.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.accential.trueone.bean.CompaniesInvitationsFilter;
import com.accential.trueone.bo.CompaniesInvitationsFilterBO;
import com.accential.trueone.bo.UtilityComponentBO;
import com.accential.trueone.interfaces.ICompaniesInvitationsFilter;
import com.accential.trueone.utils.JSONUtils;
/**
 * 
 * @author Matheus Odilon - accentialbrasil
 *
 */
@SuppressWarnings("all")
public class CompaniesInvitationsFilterDAO implements ICompaniesInvitationsFilter{

	/**
	 * ATENÇÃO: Executar em Thread separada
	 * @param Map
	 * @return List<CompaniesInvitationsFilter>
	 */
	@Override
	public List<CompaniesInvitationsFilter> listCompaniesInvitationsFilter(Map params) {

		List<CompaniesInvitationsFilter> invitations = null;
		UtilityComponentBO bo = null;

		try{

			invitations = new ArrayList<CompaniesInvitationsFilter>();
			bo = new UtilityComponentBO();

			JSONArray array = bo.urlRequestToGetData("companies", "all", params);

			if(array != null){
				List<HashMap> objs = JSONUtils.toList(array);

				Map values = null;
				CompaniesInvitationsFilter invite = null;

				if(!objs.isEmpty()){
					for (HashMap obj : objs) 
					{
						values = JSONUtils.toMap(new JSONObject((Map) obj.get("CompaniesInvitationsFilter")));
						invite = new CompaniesInvitationsFilter();

						invite.setId(Integer.parseInt((String) values.get("id")));
						invite.setAgeGroup(String.valueOf(values.get("age_group")));
						invite.setGender(String.valueOf(values.get("gender")));
						invite.setLocation(String.valueOf(values.get("location")));
						invite.setPolitical(String.valueOf(values.get("political")));
						invite.setRelationshipStatus(String.valueOf(values.get("relationship_status")));
						invite.setReligion(String.valueOf(values.get("religion")));

						Calendar dateRegister = Calendar.getInstance();
						String data = String.valueOf(values.get("date_register")).replace("-", "/");
						if(!data.equals("0000/00/00"))
						{
							Date date = new Date(data);
							dateRegister.setTime(date);
							invite.setDateRegister(dateRegister);
						}
						invitations.add(invite);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return invitations;
	}

	/**
	 * ATENÇÃO: Não é preciso executar em uma Thread separada
	 * @return List<CompaniesInvitationsFilter>
	 * @param Map
	 * @author Matheus Odilon - accentialbrasil
	 */
	@Override
	public List<CompaniesInvitationsFilter> listAllCompaniesInvitationsFilter(Map params) {

		AsyncTask<Map, Void, List<CompaniesInvitationsFilter>> async = new AsyncTask<Map, Void, List<CompaniesInvitationsFilter>>(){
			@Override
			protected List<CompaniesInvitationsFilter> doInBackground(
					Map... params) {
				List<CompaniesInvitationsFilter> invitations =
						CompaniesInvitationsFilterBO.listCompaniesInvitationsFilter(params[0]);


				return invitations;
			}
		};
		try {
			return async.execute(params).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Retorna objeto CompaniesInvitationsFilter resultado da pesquisa
	 * @author Matheus Odilon - accentialbrasil
	 * @return CompaniesInvitationsFilter
	 * @param int 
	 */
	@Override
	public CompaniesInvitationsFilter searchById(int invitationId) {

		CompaniesInvitationsFilter invite = new CompaniesInvitationsFilter();

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String,Map<String, Map<String, String>>>();
		Map<String,Map<String,String>> params = new HashMap<String,Map<String,String>>();
		Map<String,String> conditions = new HashMap<String,String>();

		conditions.put("CompaniesInvitationsFilter.id", String.valueOf(invitationId));
		params.put("conditions", conditions);
		key.put("CompaniesInvitationsFilter", params);

		List<CompaniesInvitationsFilter> invitations = 
				CompaniesInvitationsFilterBO.listAllCompaniesInvitationsFilter(key);

		for (CompaniesInvitationsFilter companiesInvitationsFilter : invitations) {
			if(companiesInvitationsFilter.getId() == invitationId){
				//CASO NECESSITE ADICIONAR BUSCAR POR COMPANY NESTE BLOCO
				invite = companiesInvitationsFilter;
			}
		}
		return invite;
	}

}
