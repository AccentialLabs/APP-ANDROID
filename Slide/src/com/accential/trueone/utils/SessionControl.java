package com.accential.trueone.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.util.Log;

import com.accential.trueone.bean.Checkout;
import com.accential.trueone.bean.CompaniesInvitationsUser;
import com.accential.trueone.bean.CompaniesUser;
import com.accential.trueone.bean.Company;
import com.accential.trueone.bean.CompanyPreference;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.OfferPhoto;
import com.accential.trueone.bean.OffersComment;
import com.accential.trueone.bean.User;
import com.accential.trueone.bean.Wishlist;
import com.example.slide.FBActivity;

@SuppressWarnings("all")
/**
 * Classe criada com todos os metodos utils que serão usados durante todo o
 * acesso do usuario ao app
 * 
 * @author Matheus Odilon - accentialbrasil
 * @version 1
 */
public class SessionControl {

	// chaves principais usadas basicamente em todo sistema
	public static final String USER = "user";
	public static final String USER_LOGGED = "loggedUser";
	public static final String OFFER_DETAIL = "offerDetail";

	// chave para salvar e recuperar
	public static final String COMPANIES_USERS = "companiesUser";
	public static final String COMPRAS_USERS = "comprasUser";
	public static final String WISHLIST_USERS = "wishlistUser";
	public static final String OFFER_DETAILS_COMPANY = "offersDetailsCompany";
	public static final String OFFER_DETAILS_COMPANY_PREFERENCES = "offersDetailsCompanyPreferences";
	public static final String FILTERED_OFFERS = "filteredOffers";
	public static final String OFFER_COMMENT = "offersComment";
	public static final String MAP_OFFERS_WISH = "myMap";
	public static final String FIRST_ACCESS = "firstAccessUser";
	public static final String OFFERS_PHOTOS = "offersPhotos";
	public static final String OFFERS = "offersHome";
	public static final String COMPANIES_DETAIL_OFFERS = "compDetailOffers";
	public static final String COMPANIES_DETAIL_COMMENTS = "compDetailComments";
	public static final String COMPANIES_DETAIL_COMPANY = "compDetailCompany";
	public static final String COMPANIES_DETAIL_INVITE = "inviteCompany";

	// orientacao da lista
	public static final String LIST_ORIENTATION = "listOrietation";

	public static final String CONTINUE_SHOPPING = "continueShoppingOffer";
	public static final String OFFER_METRIC_VALUE_FIRST = "firstMetricValue";
	public static final String OFFER_METRIC_VALUE_SECOND = "secondMetricValue";

	/**
	 * Transforma texto do paramentro em objeto User
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param string
	 * @return User
	 */
	public static User decodeSessionUser(String string) {

		try {
			byte[] decodedBytes = Base64.decodeBase64(string.getBytes());

			ByteArrayInputStream byteIn = new ByteArrayInputStream(decodedBytes);
			ObjectInputStream in = new ObjectInputStream(byteIn);
			@SuppressWarnings("unchecked")
			Map<String, User> data2 = (Map<String, User>) in.readObject();

			User user = data2.get("loggedUser");
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Transforma objeto User em string para poder ser enviado como parametros
	 * no SharedPreferences
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param user
	 * @return
	 */
	public static String encodeSessionUser(User user) {

		// criamos o map que será decodificado
		Map<String, User> preferencesMap = new HashMap<String, User>();
		preferencesMap.put("loggedUser", user);

		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(preferencesMap);

			byte[] encoded = Base64.encodeBase64(byteOut.toByteArray());

			String userByte = new String(encoded);
			return userByte;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Transforma objeto Offer em string para poder ser enviado como parametros
	 * no SharedPreferences
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param user
	 * @return
	 */
	public static String encodeSessionOffer(Offer offer) {

		// criamos o map que será decodificado
		Map<String, Offer> preferencesMap = new HashMap<String, Offer>();
		preferencesMap.put("offerDetail", offer);

		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(preferencesMap);

			byte[] encoded = Base64.encodeBase64(byteOut.toByteArray());

			String userByte = new String(encoded);
			return userByte;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Transforma texto do paramentro em objeto Offer
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param string
	 * @return User
	 */
	public static Offer decodeSessionOffer(String string) {

		try {
			byte[] decodedBytes = Base64.decodeBase64(string.getBytes());

			ByteArrayInputStream byteIn = new ByteArrayInputStream(decodedBytes);
			ObjectInputStream in = new ObjectInputStream(byteIn);
			@SuppressWarnings("unchecked")
			Map<String, Offer> data2 = (Map<String, Offer>) in.readObject();

			Offer offer = data2.get("offerDetail");
			return offer;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Transforma Lista de assinaturas em string para poder ser enviado/salvo no
	 * SharedPreferences
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param signatures
	 * @return String
	 */
	public static String encodeSessionCompaniesUser(
			List<CompaniesUser> signatures) {

		// criamos o map que será decodificado
		Map<String, List<CompaniesUser>> preferencesMap = new HashMap<String, List<CompaniesUser>>();
		preferencesMap.put("signatures", signatures);

		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(preferencesMap);

			byte[] encoded = Base64.encodeBase64(byteOut.toByteArray());

			String userByte = new String(encoded);
			return userByte;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Transforma texto do paramentro em Lista de Assinaturas
	 * 
	 * @param string
	 * @return
	 */
	public static List<CompaniesUser> decodeSessionCompaniesUser(String string) {

		try {
			byte[] decodedBytes = Base64.decodeBase64(string.getBytes());

			ByteArrayInputStream byteIn = new ByteArrayInputStream(decodedBytes);
			ObjectInputStream in = new ObjectInputStream(byteIn);
			@SuppressWarnings("unchecked")
			Map<String, List<CompaniesUser>> data2 = (Map<String, List<CompaniesUser>>) in
					.readObject();

			List<CompaniesUser> signatures = data2.get("signatures");
			return signatures;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Transforma Lista de compras em string para poder ser enviado/salvo no
	 * SharedPreferences
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param compras
	 * @return String
	 */
	public static String encodeSessionComprasUser(List<Checkout> compras) {

		// criamos o map que será decodificado
		Map<String, List<Checkout>> preferencesMap = new HashMap<String, List<Checkout>>();
		preferencesMap.put("compras", compras);

		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(preferencesMap);

			byte[] encoded = Base64.encodeBase64(byteOut.toByteArray());

			String userByte = new String(encoded);
			return userByte;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Transforma texto do paramentro em Lista de Compras
	 * 
	 * @param string
	 * @return
	 */
	public static List<Checkout> decodeSessionComprasUser(String string) {

		try {
			byte[] decodedBytes = Base64.decodeBase64(string.getBytes());

			ByteArrayInputStream byteIn = new ByteArrayInputStream(decodedBytes);
			ObjectInputStream in = new ObjectInputStream(byteIn);
			@SuppressWarnings("unchecked")
			Map<String, List<Checkout>> data2 = (Map<String, List<Checkout>>) in
					.readObject();

			List<Checkout> compras = data2.get("compras");
			return compras;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Transforma Lista de desejos em string para poder ser enviado/salvo no
	 * SharedPreferences
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param compras
	 * @return String
	 */
	public static String encodeSessionWishlistUser(List<Wishlist> wishlists) {

		// criamos o map que será decodificado
		Map<String, List<Wishlist>> preferencesMap = new HashMap<String, List<Wishlist>>();
		preferencesMap.put("wishlists", wishlists);

		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(preferencesMap);

			byte[] encoded = Base64.encodeBase64(byteOut.toByteArray());

			String userByte = new String(encoded);
			return userByte;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Transforma texto do paramentro em Lista de Desejos
	 * 
	 * @param string
	 * @return
	 */
	public static List<Wishlist> decodeSessionWishlistUser(String string) {

		try {
			byte[] decodedBytes = Base64.decodeBase64(string.getBytes());

			ByteArrayInputStream byteIn = new ByteArrayInputStream(decodedBytes);
			ObjectInputStream in = new ObjectInputStream(byteIn);
			@SuppressWarnings("unchecked")
			Map<String, List<Wishlist>> data2 = (Map<String, List<Wishlist>>) in
					.readObject();

			List<Wishlist> wishlist = data2.get("wishlists");
			return wishlist;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Verifica conexão de internet - precisamos fazer essa verificação, para
	 * evitar FATAL ERROR quando as thread não puderem ser executadas
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkInternetConnection(Context context) {
		boolean conectado;
		ConnectivityManager conectivtyManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conectivtyManager.getActiveNetworkInfo() != null
				&& conectivtyManager.getActiveNetworkInfo().isAvailable()
				&& conectivtyManager.getActiveNetworkInfo().isConnected()) {
			conectado = true;
		} else {
			conectado = false;
		}
		return conectado;
	}

	/**
	 * Transforma objeto Company em string para poder ser enviado como
	 * parametros no SharedPreferences
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param company
	 * @return
	 */
	public static String encodeSessionCompany(Company company) {

		// criamos o map que será decodificado
		Map<String, Company> preferencesMap = new HashMap<String, Company>();
		preferencesMap.put("company", company);

		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(preferencesMap);

			byte[] encoded = Base64.encodeBase64(byteOut.toByteArray());

			String userByte = new String(encoded);
			return userByte;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Transforma texto do paramentro em objeto Company
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param string
	 * @return company
	 */
	public static Company decodeSessionCompany(String string) {

		try {
			byte[] decodedBytes = Base64.decodeBase64(string.getBytes());

			ByteArrayInputStream byteIn = new ByteArrayInputStream(decodedBytes);
			ObjectInputStream in = new ObjectInputStream(byteIn);
			@SuppressWarnings("unchecked")
			Map<String, Company> data2 = (Map<String, Company>) in.readObject();

			Company company = data2.get("company");
			return company;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Transforma objeto CompanyPreference em string para poder ser enviado como
	 * parametros no SharedPreferences
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param preference
	 * @return
	 */
	public static String encodeSessionCompanyPreference(
			CompanyPreference preference) {

		// criamos o map que será decodificado
		Map<String, CompanyPreference> preferencesMap = new HashMap<String, CompanyPreference>();
		preferencesMap.put("preference", preference);

		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(preferencesMap);

			byte[] encoded = Base64.encodeBase64(byteOut.toByteArray());

			String userByte = new String(encoded);
			return userByte;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Transforma texto do paramentro em objeto CompanyPreference
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param string
	 * @return preference
	 */
	public static CompanyPreference decodeSessionCompanyPreference(String string) {

		try {
			byte[] decodedBytes = Base64.decodeBase64(string.getBytes());

			ByteArrayInputStream byteIn = new ByteArrayInputStream(decodedBytes);
			ObjectInputStream in = new ObjectInputStream(byteIn);
			@SuppressWarnings("unchecked")
			Map<String, CompanyPreference> data2 = (Map<String, CompanyPreference>) in
					.readObject();

			CompanyPreference preference = data2.get("preference");
			return preference;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Transforma Lista de OFERTAS em string para poder ser enviado/salvo no
	 * SharedPreferences
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param compras
	 * @return String
	 */
	public static String encodeSessionOffers(List<Offer> offers) {

		// criamos o map que será decodificado
		Map<String, List<Offer>> preferencesMap = new HashMap<String, List<Offer>>();
		preferencesMap.put("offers", offers);

		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(preferencesMap);

			byte[] encoded = Base64.encodeBase64(byteOut.toByteArray());

			String userByte = new String(encoded);
			return userByte;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Transforma texto do paramentro em Lista de OFERTAS
	 * 
	 * @param string
	 * @return
	 */
	public static List<Offer> decodeSessionOffers(String string) {

		try {
			byte[] decodedBytes = Base64.decodeBase64(string.getBytes());

			ByteArrayInputStream byteIn = new ByteArrayInputStream(decodedBytes);
			ObjectInputStream in = new ObjectInputStream(byteIn);
			@SuppressWarnings("unchecked")
			Map<String, List<Offer>> data2 = (Map<String, List<Offer>>) in
					.readObject();

			List<Offer> offers = data2.get("offers");
			return offers;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Transforma Lista de OFERTAS em string para poder ser enviado/salvo no
	 * SharedPreferences
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param compras
	 * @return String
	 */
	public static String encodeSessionOffersComment(List<OffersComment> comments) {

		// criamos o map que será decodificado
		Map<String, List<OffersComment>> preferencesMap = new HashMap<String, List<OffersComment>>();
		preferencesMap.put("comments", comments);

		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(preferencesMap);

			byte[] encoded = Base64.encodeBase64(byteOut.toByteArray());

			String userByte = new String(encoded);
			return userByte;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Transforma Map em string para poder ser enviado/salvo no
	 * SharedPreferences
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param Map
	 * @return String
	 */
	public static String encodeSessionMap(Map map) {

		// criamos o map que será decodificado
		Map<String, Map> preferencesMap = new HashMap<String, Map>();
		preferencesMap.put("myMap", map);

		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(preferencesMap);

			byte[] encoded = Base64.encodeBase64(byteOut.toByteArray());

			String userByte = new String(encoded);
			return userByte;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Transforma texto do paramentro em Map
	 * 
	 * @param string
	 * @return
	 */
	public static Map decodeSessionMap(String string) {

		try {
			byte[] decodedBytes = Base64.decodeBase64(string.getBytes());

			ByteArrayInputStream byteIn = new ByteArrayInputStream(decodedBytes);
			ObjectInputStream in = new ObjectInputStream(byteIn);
			@SuppressWarnings("unchecked")
			Map<String, Map> data2 = (Map<String, Map>) in.readObject();

			Map map = data2.get("myMap");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Transforma texto do paramentro em Lista de OFERTAS
	 * 
	 * @param string
	 * @return
	 */
	public static List<OffersComment> decodeSessionOffersComments(String string) {

		try {
			byte[] decodedBytes = Base64.decodeBase64(string.getBytes());

			ByteArrayInputStream byteIn = new ByteArrayInputStream(decodedBytes);
			ObjectInputStream in = new ObjectInputStream(byteIn);
			@SuppressWarnings("unchecked")
			Map<String, List<OffersComment>> data2 = (Map<String, List<OffersComment>>) in
					.readObject();

			List<OffersComment> comments = data2.get("comments");
			return comments;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void shareApp(Context context) {
		String shareBody = "Você ainda não conhece o Adventa? Não perca mais tempo e tenha o maximo de facilidade na hora de suas compras. Baixe agora!";
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Adventa");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
		context.startActivity(Intent.createChooser(sharingIntent,
				"Compartilhe por..."));
	}

	/**
	 * Usuario Sair - apaga todos os dados do SharedPreference. "Zera o app"
	 * 
	 * @param context
	 * @param preference
	 */
	public static void logoof(Context context, SharedPreferences preference) {

		SharedPreferences.Editor editor = preference.edit();
		editor.clear();
		editor.commit();

		Intent intent = new Intent(context, FBActivity.class);
		context.startActivity(intent);
	}

	/**
	 * 
	 * Cria agendamento para exibiçao da notificação padrao do app onde
	 * convidamos o usuario a voltar ao aplicativo para ver as novidades do app
	 * - Redirecionamos para vitrine
	 * 
	 */
	public static void sendNotificationNews(Context context) {

	}

	/**
	 * Verifica se esse é primeiro acesso do usuario, Caso TRUE = sim, é o
	 * primeiro acesso ------- Caso FALSE - nao, usuario ja acessou outras vezes
	 * 
	 * @param preference
	 * @param context
	 * @return
	 */
	public static boolean verifyFirstAccess(SharedPreferences preference) {

		String firstAccess = preference.getString(FIRST_ACCESS, "true");

		if (firstAccess.equals("true")) {
			Log.e("Verify first access", "Yes, first access!!!");
			return true;

		} else {
			Log.e("Verify first access", "No, is not the first access!!!");
			return false;
		}
	}

	/**
	 * Transforma Lista de FOTOS DAS OFERTAS em string para poder ser
	 * enviado/salvo no SharedPreferences
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param compras
	 * @return String
	 */
	public static String encodeSessionOffersPhotos(List<OfferPhoto> photos) {

		// criamos o map que será decodificado
		Map<String, List<OfferPhoto>> preferencesMap = new HashMap<String, List<OfferPhoto>>();
		preferencesMap.put("offersOffers", photos);

		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(preferencesMap);

			byte[] encoded = Base64.encodeBase64(byteOut.toByteArray());

			String userByte = new String(encoded);
			return userByte;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Transforma texto do paramentro em Lista de FOTOS DAS OFERTAS
	 * 
	 * @param string
	 * @return
	 */
	public static List<OfferPhoto> decodeSessionOffersPhoto(String string) {

		try {
			byte[] decodedBytes = Base64.decodeBase64(string.getBytes());

			ByteArrayInputStream byteIn = new ByteArrayInputStream(decodedBytes);
			ObjectInputStream in = new ObjectInputStream(byteIn);
			@SuppressWarnings("unchecked")
			Map<String, List<OfferPhoto>> data2 = (Map<String, List<OfferPhoto>>) in
					.readObject();

			List<OfferPhoto> photos = data2.get("offersOffers");
			return photos;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String encodeSessionInvitation(CompaniesInvitationsUser invite) {

		// criamos o map que será decodificado
		Map<String, CompaniesInvitationsUser> preferencesMap = new HashMap<String, CompaniesInvitationsUser>();
		preferencesMap.put("invite", invite);

		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(preferencesMap);

			byte[] encoded = Base64.encodeBase64(byteOut.toByteArray());

			String userByte = new String(encoded);
			return userByte;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static CompaniesInvitationsUser decodeSessionInvitatios(String string) {

		try {
			byte[] decodedBytes = Base64.decodeBase64(string.getBytes());

			ByteArrayInputStream byteIn = new ByteArrayInputStream(decodedBytes);
			ObjectInputStream in = new ObjectInputStream(byteIn);
			@SuppressWarnings("unchecked")
			Map<String, CompaniesInvitationsUser> data2 = (Map<String, CompaniesInvitationsUser>) in
					.readObject();

			CompaniesInvitationsUser CompaniesInvitationsUser = data2
					.get("invite");
			return CompaniesInvitationsUser;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
