package com.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.dto.Product;
import com.ts.HibernateTemplate;

public class ProductDAO {
	private SessionFactory factory = null;
	
	public List<Product> getAllProductDetails() {
		List<Product> productDetails = (List)HibernateTemplate.getObjectListByQuery("From Product");
		return productDetails;	
	}
	
	
	public int addProduct(Product product) {
		System.out.println(product); 
		return HibernateTemplate.addObject(product);
	}

}
