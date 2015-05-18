package com.accential.trueone.bo;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

import android.util.Log;

import com.accential.trueone.base64.Criptography64Utils;
import com.accential.trueone.utils.Base64Util;
import com.accential.trueone.utils.HttpRequestUtil;
import com.accential.trueone.utils.JSONUtils;

/**
 * Classe utilitaria que centraliza todos os m���todos de requisi������o e
 * descriptografia
 * 
 * @author Henrique
 * 
 */
@SuppressWarnings("all")
public class UtilityComponentBO {

	// public static final String API_ENVIRONMENT_URL =
	// "http://secure.trueone.com.br/t1core";
	// public static final String API_MOBILE_URL =
	// "http://secure.trueone.com.br/t1mobilecore";

	public static final String API_ENVIRONMENT_URL = "http://acclabs.accential.com.br/adventa/api";
	public static final String API_MOBILE_URL = "http://acclabs.accential.com.br/adventa/api";

	/**
	 * @param String
	 * @return JSONArray
	 * 
	 */
	public JSONArray decodeData(String base64) {
		try {
			String json = Criptography64Utils.decodeBase64String(base64);

			JSONArray array = JSONUtils.decodeJSON(json);

			if (array == null)
				throw new NullPointerException("Nenhum dado recebido");
			else
				return array;

		} catch (Exception e) {
			e.printStackTrace();
			Log.i("Error : ", e.getMessage());
			return null;
		}
	}

	/**
	 * @param Map
	 * @return byte[]
	 */

	@SuppressWarnings("rawtypes")
	public String encodeData(Map data) {
		try {

			String json = JSONUtils.encodeJSON(data);
			/* String base64 = Criptography64Utils.encodeBase64String(json); */
			return json;

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Error : ", e.getMessage());
			return null;
		}
	}

	public String encodeData2(Map data) {
		try {

			String json = JSONUtils.encodeJSON(data);
			Log.i("IMPRIMINDO JSON BASE64", json);
			String base64 = Criptography64Utils.encodeBase64String(json);
			return base64;

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Error : ", e.getMessage());
			return null;
		}
	}

	/**
	 * Gera TOKEN de seguran���a para comunica������o entre API"s
	 * 
	 * @return byte []
	 */

	public String generateSecureToken() {
		try {

			/**
			 * FAZ A DATA SER RETROATIVA
			 */
			// long time = (Calendar.getInstance().getTimeInMillis() / 1000);

			long time2 = (Calendar.getInstance().getTimeInMillis());

			Map<String, String> list = new HashMap<String, String>();
			Log.i("teste", "TIME IN MILLIS: " + String.valueOf(time2));
			Log.i("teste",
					"TIME NO DIVISION: "
							+ String.valueOf(Calendar.getInstance()
									.getTimeInMillis()));
			Date date = new Date(time2);
			Log.i("teste", "MILLIS TO DATE: " + date.toString());

			list.put("secureNumbers", String.valueOf(time2));
			String json = JSONUtils.encodeJSON(list);
			String base64 = Criptography64Utils.encodeBase64String(json);

			return base64;

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Error on generate Token=>>", e.getMessage());
			return null;
		}
	}

	/**
	 * Respons���vel por solicitar dados de uma determinada API, segundo os
	 * par���metros enviados. Aten������o! Executar este m���todo somente em uma
	 * thread independente!
	 * 
	 * @param String
	 *            api, type
	 * @param Map
	 *            params
	 */
	public JSONArray urlRequestToGetData(String api, String type, Map params) {

		HttpRequestUtil http = null;

		try {

			/* gerando link para requisi������o a API */
			String data = null;
			String encondedParams = null;
			String apiUrl = API_ENVIRONMENT_URL + File.separator;
			apiUrl += api.concat(File.separator.concat("get"));
			apiUrl += File.separator.concat(type);

			if (!params.isEmpty()) {
				encondedParams = encodeData(params);
			} else {
				throw new IllegalArgumentException("invalid argument params");
			}
			apiUrl += File.separator.concat(generateSecureToken());

			// ITEM PARA TESTE, APAGAR APOS USO
			Log.i("TESTE URL", apiUrl);

			http = new HttpRequestUtil();
			http.setWebPageToPointAt(apiUrl);
			http.addPostValue("params", encondedParams);
			data = http.sendPostWithReturnValue();

			return data != null ? decodeData(data) : null;

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Error =>> ", e.getMessage());
			return null;
		}
	}

	/**
	 * Respons���vel por solicitar dados para troca de senha na API, segundo os
	 * parametros enviados.
	 * 
	 * @param String
	 *            api, type
	 * @param Map
	 *            params
	 */

	public JSONArray requestPasswordRecovery(String api, String type, Map params) {

		try {
			String data = null;
			String encondedParams = null;
			String apiUrl = API_ENVIRONMENT_URL;
			apiUrl += File.separator + api + "/passwordRecovery";
			apiUrl += "/first";

			if (!params.isEmpty()) {
				encondedParams = encodeData(params);
				apiUrl += File.separator.concat(generateSecureToken());

				Log.i("IMPRIME URL", apiUrl);

				// efetuando requisi������o a API
				HttpRequestUtil http = new HttpRequestUtil();
				http.setWebPageToPointAt(apiUrl);
				http.addPostValue("params", encondedParams);
				data = http.sendPostWithReturnValue();

				return data != null ? decodeData(data) : null;
			} else {
				throw new IllegalArgumentException("invalid argument params");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Erro=>>", e.getMessage());
			return null;
		}
	}

	/**
	 * Respons���vel por solicitar dados de uma determinada API, segundo os
	 * par���metros enviados.
	 * 
	 * @param String
	 *            api, type
	 * @param Map
	 *            params
	 */

	public JSONArray urlRequestWishlist(String api, String type, Map params) {

		try {
			String data = null;
			String encondedParams = null;
			String apiUrl = API_ENVIRONMENT_URL;
			apiUrl += "/" + api + "/saveWishlist";
			apiUrl += "/first";

			if (!params.isEmpty()) {
				encondedParams = encodeData(params);
				apiUrl += File.separator.concat(generateSecureToken());

				/* efetuando requisi������o a API */
				HttpRequestUtil http = new HttpRequestUtil();
				http.setWebPageToPointAt(apiUrl);
				http.addPostValue("params", encondedParams);
				data = http.sendPostWithReturnValue();

				return data != null ? decodeData(data) : null;
			} else {
				throw new IllegalArgumentException("invalid argument params");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Erro=>>", e.getMessage());
			return null;
		}
	}

	/**
	 * Respons���vel por solicitar dados de uma determinada API, segundo os
	 * par���metros enviados.
	 * 
	 * @param string
	 *            $api, $type
	 * @param array
	 *            $params
	 */

	public JSONArray urlRequestToLoginData(String api, String type, Map params) {

		try {
			String data = null;
			String encondedParams = null;
			String apiUrl = API_ENVIRONMENT_URL;
			apiUrl += api + "/companyLogin";
			apiUrl += File.separator.concat(type);

			if (!params.isEmpty()) {
				encondedParams = encodeData(params);
				apiUrl += File.separator.concat(generateSecureToken());

				/* efetuando requisi������o a API */
				HttpRequestUtil http = new HttpRequestUtil();
				http.setWebPageToPointAt(apiUrl);
				http.addPostValue("params", encondedParams);
				data = http.sendPostWithReturnValue();

				return data != null ? decodeData(data) : null;
			} else {
				throw new IllegalArgumentException("invalid argument params");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Erro=>>", e.getMessage());
			return null;
		}
	}

	/**
	 * Respons���vel por cadastrar/editar dados de uma determinada API, segundo
	 * os par���metros enviados.
	 * 
	 * @param string
	 *            $api
	 * @param array
	 *            $params
	 */
	public JSONArray urlRequestToSaveData(String api, String type, Map params) {

		try {
			String data = null;
			String encondedParams = null;
			String apiUrl = API_ENVIRONMENT_URL;
			apiUrl += "/" + api + "/save";
			apiUrl += File.separator.concat("all");

			if (!params.isEmpty()) {
				encondedParams = encodeData(params);
				apiUrl += File.separator.concat(generateSecureToken());

				// PARA TESTE, APAGAR AP��S USO
				Log.i("CONTEUDO DO APIURL", apiUrl + "params=" + encondedParams);
				Log.i("CONTEUDO DO ENCONDEDPARAMS", encondedParams);

				/* efetuando requisicao a API */
				HttpRequestUtil http = new HttpRequestUtil();
				http.setWebPageToPointAt(apiUrl);
				http.addPostValue("params", encondedParams);
				data = http.sendPostWithReturnValue();

				// PARA TESTE, APAGAR AP��S USAR
				Log.i("CONTEUDO DO 'DATA", data);

				return data != null ? decodeData(data) : null;
			} else {
				throw new IllegalArgumentException("invalid argument params");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Erro no urlRequestToSaveData=>>", e.getMessage());
			return null;
		}
	}

	/**
	 * ESSE METODO RARAMENTE SER�� USANDO - POIS EM PRODU����O APENAS MUDAMOS OS
	 * STATUS DOS DETERMINADOS OBJETOS Respons���vel por excluir dados de uma
	 * determinada API, segundo os par���metros enviados.
	 * 
	 * @param string
	 *            api
	 */

	public JSONArray urlRequestToDeleteData(String api, String type, Map params) {

		try {
			String data = null;
			String encondedParams = null;
			String apiUrl = API_ENVIRONMENT_URL;
			apiUrl += "/" + api + "/delete";
			apiUrl += File.separator.concat("all");

			if (!params.isEmpty()) {
				encondedParams = encodeData(params);
				apiUrl += File.separator.concat(generateSecureToken());

				/* efetuando requisi������o a API */
				HttpRequestUtil http = new HttpRequestUtil();
				http.setWebPageToPointAt(apiUrl);
				http.addPostValue("params", encondedParams);
				data = http.sendPostWithReturnValue();

				return data != null ? decodeData(data) : null;
			} else {
				throw new IllegalArgumentException("invalid argument params");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Erro=>>", e.getMessage());
			return null;
		}
	}

	/**
	 * Respons���vel por excluir dados de uma determinada API, segundo os
	 * par���metros enviados.
	 * 
	 * @param string
	 *            api
	 */

	public JSONArray urlRequestToCheckout(String api, String type, Map params) {

		try {
			String data = null;
			String encondedParams = null;
			String apiUrl = API_ENVIRONMENT_URL;
			apiUrl += "/" + api + "/checkouts";
			apiUrl += File.separator.concat("all");

			if (!params.isEmpty()) {
				encondedParams = encodeData(params);
				apiUrl += File.separator.concat(generateSecureToken());

				/* efetuando requisi������o a API */
				HttpRequestUtil http = new HttpRequestUtil();
				http.setWebPageToPointAt(apiUrl);
				http.addPostValue("params", encondedParams);
				data = http.sendPostWithReturnValue();

				return data != null ? decodeData(data) : null;
			} else {
				throw new IllegalArgumentException("invalid argument params");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Erro=>>", e.getMessage());
			return null;
		}
	}

	public Object calcFrete(String servico, String CEPorigem,
			String CEPdestino, float peso, float altura, float largura,
			float comprimento, double valor) {
		/*
		 * Implementar m���todo de calculo de frete
		 */
		return null;
	}

	// METODOS TESTE

	public JSONArray urlToCreateUser(String api, String type, Map params) {

		try {
			String data = null;
			String encondedParams = null;
			String apiUrl = API_ENVIRONMENT_URL;
			apiUrl += "/" + api + "/createUser";
			apiUrl += "/first";

			if (!params.isEmpty()) {
				encondedParams = encodeData(params);
				apiUrl += File.separator.concat(generateSecureToken());

				/* efetuando requisi������o a API */
				HttpRequestUtil http = new HttpRequestUtil();
				http.setWebPageToPointAt(apiUrl);
				http.addPostValue("params", encondedParams);
				data = http.sendPostWithReturnValue();

				return data != null ? decodeData(data) : null;
			} else {
				throw new IllegalArgumentException("invalid argument params");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Erro=>>", e.getMessage());
			return null;
		}
	}

	// TESTE CALCULA IMPOSTO DE FRETE
	public Map urlToCalcuateShippingValue(Map params) {

		try {
			String data = null;
			String encondedParams = null;
			String apiUrl = API_ENVIRONMENT_URL;
			apiUrl += "/" + "payments" + "/calculateShippingValue";
			apiUrl += File.separator.concat("all");

			if (!params.isEmpty()) {
				encondedParams = encodeData(params);
				apiUrl += File.separator.concat(generateSecureToken());

				/* efetuando requisi������o a API */
				HttpRequestUtil http = new HttpRequestUtil();
				http.setWebPageToPointAt(apiUrl);
				http.addPostValue("params", encondedParams);
				data = http.sendPostWithReturnValue();

				String json = Criptography64Utils.decodeBase64String(data);

				Log.e("JSON CORREIOS", "JSON CORREIOS: " + json);

				String[] value = json.split("\",");
				String valor = value[1].replace("\"Valor\":\"", "");
				String prazo = value[2].replace("\"PrazoEntrega\":\"", "");

				Map<String, String> shippingValue = new HashMap<String, String>();
				shippingValue.put("Valor", valor);
				shippingValue.put("PrazoEntrega", prazo);

				Log.i("VALOR DE ENTREGA", valor);

				return shippingValue;
			} else {
				throw new IllegalArgumentException("invalid argument params");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Erro=>>", e.getMessage());
			return null;
		}
	}

	public JSONArray urlRequestToSaveDataCheck(String api, String type,
			Map params) {

		try {
			String data = null;
			String encondedParams = null;
			String apiUrl = API_ENVIRONMENT_URL;
			apiUrl += "/" + api + "/save";
			apiUrl += File.separator.concat("all");
			Base64Util util = new Base64Util();

			if (!params.isEmpty()) {
				encondedParams = encodeData(params);
				apiUrl += File.separator.concat(generateSecureToken());

				/* efetuando requisicao a API */
				HttpRequestUtil http = new HttpRequestUtil();
				http.setWebPageToPointAt(apiUrl);
				http.addPostValue("params", encondedParams);
				data = http.sendPostWithReturnValue();

				// PARA TESTE, APAGAR AP��S USAR
				Log.i("CONTEUDO DO DATA DE CHECKOUT ~>", data);
				// TESTE: COLOCAR O '+' PARA TESTAR DECODE DE STRING
				Log.i("CONTEUDO DO DATA DECODE",
						String.valueOf(util.decode(data + "+")));

				return data != null ? decodeData(data) : null;
			} else {
				throw new IllegalArgumentException("invalid argument params");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Erro no urlRequestToSaveData=>>", e.getMessage());
			return null;
		}
	}

	public String urlRequestToSaveDataCheck2(String api, String type, Map params) {

		try {
			String data = null;
			String encondedParams = null;
			String apiUrl = API_ENVIRONMENT_URL;
			apiUrl += "/" + api + "/save";
			apiUrl += File.separator.concat("all");
			Base64Util util = new Base64Util();

			if (!params.isEmpty()) {
				encondedParams = encodeData(params);
				apiUrl += File.separator.concat(generateSecureToken());

				/* efetuando requisicao a API */
				HttpRequestUtil http = new HttpRequestUtil();
				http.setWebPageToPointAt(apiUrl);
				http.addPostValue("params", encondedParams);
				data = http.sendPostWithReturnValue();

				// PARA TESTE, APAGAR AP��S USAR
				Log.i("CONTEUDO DO 'DATA", data);

				return data;
			} else {
				throw new IllegalArgumentException("invalid argument params");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Erro no urlRequestToSaveData=>>", e.getMessage());
			return null;
		}
	}

	public void urlRequestAddres(String cep) {

	}

	// public JSONArray urlToCalcuateShippingValue(Map params) {
	//
	// try {
	// String data = null;
	// String encondedParams = null;
	// String apiUrl = API_ENVIRONMENT_URL;
	// apiUrl += "/" + "payments" + "/calculateShippingValue";
	// apiUrl += File.separator.concat("all");
	//
	// if (!params.isEmpty()) {
	// encondedParams = encodeData(params);
	// apiUrl += File.separator.concat(generateSecureToken());
	//
	// /* efetuando requisi������o a API */
	// HttpRequestUtil http = new HttpRequestUtil();
	// http.setWebPageToPointAt(apiUrl);
	// http.addPostValue("params", encondedParams);
	// data = http.sendPostWithReturnValue();
	//
	// String json = Criptography64Utils.decodeBase64String(data);
	//
	// String[] value = json.split("\",");
	// String valor = value[1].replace("\"Valor\":\"", "");
	// String prazo = value[2].replace("\"PrazoEntrega\":\"", "");
	//
	// Map<String, String> shippingValue = new HashMap<String, String>();
	// shippingValue.put("Valor", valor);
	// shippingValue.put("PrazoEntrega", prazo);
	//
	// Log.i("VALOR DE ENTREGA", valor);
	//
	// return data != null ? decodeData(data) : null;
	// } else {
	// throw new IllegalArgumentException("invalid argument params");
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// Log.e("Erro=>>", e.getMessage());
	// return null;
	// }
	// }

	// public JSONArray urlRequestMyPayment(Map params) {
	//
	// HttpRequestUtil http = null;
	//
	// try{
	//
	// /*gerando link para requisi������o a API*/
	// String data = null;
	// String encondedParams = null;
	// String apiUrl = "https://secure.trueone.com.br/t1mobilecore" +
	// File.separator;
	// apiUrl += "payments/checkout";
	//
	// if(!params.isEmpty()) {
	// encondedParams = encodeData(params);
	// } else {
	// throw new IllegalArgumentException("invalid argument params");
	// }
	// apiUrl += File.separator + encondedParams +
	// File.separator.concat(generateSecureToken());
	//
	// //ITEM PARA TESTE, APAGAR APOS USO
	// Log.i("TESTE URL", apiUrl);
	//
	// http = new HttpRequestUtil();
	// http.setWebPageToPointAt(apiUrl);
	// //http.addPostValue("params", encondedParams);
	// data = http.sendPostWithReturnValue();
	//
	// return data != null ? decodeData(data) : null;
	//
	// } catch(Exception e){
	// e.printStackTrace();
	// Log.e("Error =>> ", e.getMessage());
	// return null;
	// }
	// }

	public JSONArray urlRequestToGetPayments(Map params) {

		HttpRequestUtil http = null;

		try {

			/* gerando link para requisi������o a API */
			String data = null;
			String token = generateSecureToken();
			String encondedParams = encodeData2(params);
			Base64Util base = new Base64Util();
			String json = JSONUtils.encodeJSON(params);
			String based = base.encode(json.getBytes());

			String apiUrl = "http://secure.trueone.com.br/t1mobilecore/iphone/payments/checkout/"
					+ based + "/" + token;
			// ITEM PARA TESTE, APAGAR APOS USO
			// Log.i("TOKEN", token);
			Log.i("TESTE URL", apiUrl);
			Log.i("BASE64", based);

			http = new HttpRequestUtil();
			http.setWebPageToPointAt(apiUrl);
			// http.addPostValue("params", encondedParams);
			data = http.sendPostWithReturnValue();

			// return data != null ? decodeData(data) : null;
			return null;

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Error =>> ", e.getMessage());
			return null;
		}
	}

	// SALVA CHECKOUT - RETORNA ID
	public int urlRequestToSaveDataCheckRetornaIntId(String api, String type,
			Map params) {

		try {
			int id = 0;
			String data = null;
			String encondedParams = null;
			String apiUrl = API_ENVIRONMENT_URL;
			apiUrl += "/" + api + "/save";
			apiUrl += File.separator.concat("all");
			Base64Util util = new Base64Util();

			if (!params.isEmpty()) {
				encondedParams = encodeData(params);
				apiUrl += File.separator.concat(generateSecureToken());

				/* efetuando requisicao a API */
				HttpRequestUtil http = new HttpRequestUtil();
				http.setWebPageToPointAt(apiUrl);
				http.addPostValue("params", encondedParams);
				data = http.sendPostWithReturnValue();

				String stringBase = data + "+";
				Base64Util bas64 = new Base64Util();
				byte[] chars = bas64.decode(stringBase);
				String stringDecoded;
				try {
					stringDecoded = new String(chars, "UTF-8");
					Log.i("TESTE DE DECODE", stringDecoded);

					String sub = stringDecoded.substring(46, 50);
					id = Integer.parseInt(sub);
					Log.i("TESTE DE ENCODE - SUNSTRING", String.valueOf(id));
				} catch (Exception e) {
					Log.e("ERROR", "exception");
					e.printStackTrace();
				}

				return id;
			} else {
				throw new IllegalArgumentException("invalid argument params");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Erro no urlRequestToSaveData=>>", e.getMessage());
			return 0;
		}
	}

	/**
	 * Faz o calculo do frete diretamente e retorna map com prazo e valor do
	 * frete a api dos correios nos retorna um XML, onde fazemos um split e
	 * capturamos apenas os valores que são usados por nós nos calculos.
	 * 
	 * O retorno dos dois valores do Map é String, fazer converção para uso de
	 * ambos
	 * 
	 * @return
	 */
	public Map calculaAndFrete(String cepOrigem, String cepDestino, String peso) {
		String data = null;
		String url = "http://ws.correios.com.br/calculador/CalcPrecoPrazo.aspx?"
				+ "nCdEmpresa=&sDsSenha=&sCepOrigem="
				+ cepOrigem
				+ "&sCepDestino="
				+ cepDestino
				+ "&nVlPeso="
				+ peso
				+ "&nCdFormato=1&nVlComprimento=16&nVlAltura=4&nVlLargura=14&"
				+ "sCdMaoPropria=n&nVlValorDeclarado=1.0&sCdAvisoRecebimento=n&nCdServico=41106&"
				+ "nVlDiametro=0&StrRetorno=xml";

		Log.e("", url);
		HttpRequestUtil http = new HttpRequestUtil();
		http.setWebPageToPointAt(url);
		data = http.sendPostWithReturnValue();

		// pog
		String[] value = data.split("/");
		// recura do array de strings
		String valor = value[1].replace("Codigo><Valor>", "");
		String prazo = value[2].replace("Valor><PrazoEntrega>", "");
		// retira os caracteres que nao iremos usar
		valor = valor.replace("<", "");
		prazo = prazo.replace("<", "");
		Log.e("VALOR: ", "VALOOOOOOR: " + valor);
		Log.e("VALOR: ", "PRAZOOOOOO: " + prazo);

		// criando map de retorno que irá conter os valores
		Map calculado = new HashMap();
		calculado.put("Valor", valor);
		calculado.put("PrazoEntrega", prazo);

		return calculado;
	}

	public void sendEmailNewUser(Map params) {
		String url = "http://acclabs.accential.com.br/adventa/ses/users/newUser.php";
		String encondedParams = encodeData(params);
		// fazendo requisição
		HttpRequestUtil http = new HttpRequestUtil();
		http.setWebPageToPointAt(url);
		try {
			http.addPostValue("params", encondedParams);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String data = http.sendPostWithReturnValue();
	}

}
