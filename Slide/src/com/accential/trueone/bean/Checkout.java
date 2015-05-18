package com.accential.trueone.bean;

import java.io.Serializable;
import java.util.Calendar;
/**
 * 
 * @author Matheus Odilon - accentialbrasil
 *
 */
public class Checkout implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String KEY = "checkouts";

	private int id;
	private User user;
	private Company company;
	private PaymentMethod method;
	private Offer offer;
	private PaymentState paymentState;
	private float unitValue;
	private float totalValue;
	private int amount;
	private double shippingValue;
	private String shippingType;
	//number of days
	private int deliveryTime;
	private String metrics;
	private String address;
	private String city;
	private String zipCode;
	private String state;
	private String district;
	private String number;
	private String complement;
	private Calendar dateTime;
	private String transactionMoipCode;
	private int installment;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public PaymentMethod getMethod() {
		return method;
	}
	public void setMethod(PaymentMethod method) {
		this.method = method;
	}
	public Offer getOffer() {
		return offer;
	}
	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	public PaymentState getPaymentState() {
		return paymentState;
	}
	public void setPaymentState(PaymentState paymentState) {
		this.paymentState = paymentState;
	}
	public float getUnitValue() {
		return unitValue;
	}
	public void setUnitValue(float unitValue) {
		this.unitValue = unitValue;
	}
	public float getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(float totalValue) {
		this.totalValue = totalValue;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getShippingValue() {
		return shippingValue;
	}
	public void setShippingValue(double shippingValue) {
		this.shippingValue = shippingValue;
	}
	public String getShippingType() {
		return shippingType;
	}
	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}
	public int getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(int deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getMetrics() {
		return metrics;
	}
	public void setMetrics(String metrics) {
		this.metrics = metrics;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getComplement() {
		return complement;
	}
	public void setComplement(String complement) {
		this.complement = complement;
	}
	public Calendar getDateTime() {
		return dateTime;
	}
	public void setDateTime(Calendar dateTime) {
		this.dateTime = dateTime;
	}
	public String getTransactionMoipCode() {
		return transactionMoipCode;
	}
	public void setTransactionMoipCode(String transactionMoipCode) {
		this.transactionMoipCode = transactionMoipCode;
	}
	public int getInstallment() {
		return installment;
	}
	public void setInstallment(int installment) {
		this.installment = installment;
	}

}
