package com.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class OrderDetails {
	
	@Id
	@GeneratedValue
	private int orderDetailsId;
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "orderId")
	private Orders orders;
	
	@ManyToOne
	@JoinColumn(name = "proId")
	private Product product;
	
	public int getOrderDetailsId() {
		return orderDetailsId;
	}
	public void setOrderDetailsId(int orderDetailsId) {
		this.orderDetailsId = orderDetailsId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}	

}
