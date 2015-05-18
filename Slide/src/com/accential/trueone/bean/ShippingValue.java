package com.accential.trueone.bean;

import java.io.Serializable;

/**
 * Classe usada APENAS para fazer o calculo do frete!!!
 * @version 1
 * @author accentialbrasil
 *
 */
public class ShippingValue implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigo;
	private char entregaDomiciliar;
	private float valorAvisoRecebimento;
	private String msgErro;
	private float valorMaoPropria;
	private float valorDeclarado;
	private int prazoEntrega;
	private float valor;
	private char entregaSabado;
	private int error;

	//GETTERS AND SETTERS
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public char getEntregaDomiciliar() {
		return entregaDomiciliar;
	}
	public void setEntregaDomiciliar(char entregaDomiciliar) {
		this.entregaDomiciliar = entregaDomiciliar;
	}
	public float getValorAvisoRecebimento() {
		return valorAvisoRecebimento;
	}
	public void setValorAvisoRecebimento(float valorAvisoRecebimento) {
		this.valorAvisoRecebimento = valorAvisoRecebimento;
	}
	public String getMsgErro() {
		return msgErro;
	}
	public void setMsgErro(String msgErro) {
		this.msgErro = msgErro;
	}
	public float getValorMaoPropria() {
		return valorMaoPropria;
	}
	public void setValorMaoPropria(float valorMaoPropria) {
		this.valorMaoPropria = valorMaoPropria;
	}
	public float getValorDeclarado() {
		return valorDeclarado;
	}
	public void setValorDeclarado(float valorDeclarado) {
		this.valorDeclarado = valorDeclarado;
	}
	public int getPrazoEntrega() {
		return prazoEntrega;
	}
	public void setPrazoEntrega(int prazoEntrega) {
		this.prazoEntrega = prazoEntrega;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public char getEntregaSabado() {
		return entregaSabado;
	}
	public void setEntregaSabado(char entregaSabado) {
		this.entregaSabado = entregaSabado;
	}
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}




}
