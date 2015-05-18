package com.accential.trueone.bean;

/**
 * Enum simples, representa os Estados da compra de um determinado cliente/usuario
 * @author Matheus Odilon - accentialbrasil
 *
 */
public enum PaymentState {

	AUTORIZADO, 
	INICIADO,
	BOLETO_IMPRESSO,
	CONCLUIDO, 
	CANCELADO,
	EM_ANALISE,
	ESTORNADO,
	EM_REVISAO,
	REEMBOLSADO,
	INICIO;

	/**
	 * Retorna Enum de acordo com o id de PaymentState,
	 * representa 'payments_state' 
	 * @param int paymentStateId
	 * @return PaymentState
	 */
	public static PaymentState getPaymentState(int paymentStateId){
		PaymentState state = null;

		switch (paymentStateId) {
		case 1:
			state = AUTORIZADO;
			break;
		case 2:
			state = INICIADO;
			break;
		case 3:
			state = BOLETO_IMPRESSO;
			break;
		case 4:
			state = CONCLUIDO;
			break;
		case 5:
			state = CANCELADO;
			break;
		case 6:
			state = EM_ANALISE;
			break;
		case 7:
			state = ESTORNADO;
			break;
		case 8:
			state = EM_REVISAO;
			break;
		case 9:
			state = REEMBOLSADO;
			break;
		case 14:
			state = INICIO;
			break;
		default:
			break;
		}

		return state;
	}

	/**
	 * Retorna Id do do Enum (Referente ao ID do BD),
	 * @param PaymentState state
	 * @return int
	 * @author Matheus Odilon - accentialbrasil
	 */
	public static int getPaymentStateId(PaymentState state){
		int id = 0;

		switch (state) {
		case AUTORIZADO:
			id = 1;
			break;
		case INICIADO:
			id = 2;
			break;
		case BOLETO_IMPRESSO:
			id = 3;
			break;
		case CONCLUIDO:
			id = 4;
			break;
		case CANCELADO:
			id = 5;
			break;
		case EM_ANALISE:
			id = 6;
			break;
		case ESTORNADO:
			id = 7;
			break;
		case EM_REVISAO:
			id = 8;
			break;
		case REEMBOLSADO:
			id = 9;
			break;
		case INICIO:
			id = 14;
			break;
		default:
			break;
		}
		return id;
	}

}
