package com.dao;

import org.hibernate.SessionFactory;

import com.dto.Orders;
import com.ts.HibernateTemplate;

public class OrderDAO {
	private SessionFactory factory = null;
	
	public int addOrder(Orders order) {
		System.out.println("OrderDAO : " + order); 
		return HibernateTemplate.addObject(order);
	}

}
