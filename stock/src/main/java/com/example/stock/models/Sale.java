package com.example.stock.models;

import java.util.Date;


public class Sale {

	private Long idProduct;

	private Integer amount;
	
	private Date data = new Date();

	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Sale [idProduct=" + idProduct + ", amount=" + amount + "]";
	}
	
	
}
