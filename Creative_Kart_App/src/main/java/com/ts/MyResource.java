package com.ts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.dao.CustomerDAO;
import com.dao.OrderDAO;
import com.dao.ProductDAO;
import com.dto.Customer;
import com.dto.Orders;
import com.dto.Product;

@Path("myresource")
public class MyResource {
	
	@Path("hi")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String hi() throws UnsupportedEncodingException {
		System.out.println("Hi...");
		return "Hi Service!";
	}
	
	@Path("registerCust")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void registerCust(Customer customer) {
		System.out.println("Data Recieved in Customer Register : " + customer);
		CustomerDAO customerDAO = new CustomerDAO();
		customerDAO.register(customer);
	}
	
	@Path("getCustByUserPass/{loginId}/{password}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustByUserPass(@PathParam("loginId") String loginId,@PathParam("password") String password) {
		System.out.println("Recieved path params: "+loginId+" "+password); 
		CustomerDAO customerDAO = new CustomerDAO();
		Customer customer = customerDAO.getCustByUserPass(loginId, password);
		return customer;
	}
	
	@Path("getCustByEmail/{email}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustByEmail(@PathParam("email") String email) {
		System.out.println("Recieved path params: "+email); 
		CustomerDAO customerDAO = new CustomerDAO();
		Customer customer = customerDAO.getCustByEmail(email);
		return customer;
	}
	
	@Path("uploadImage")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void uploadImage(@FormDataParam("productImage") InputStream fileInputStream,@FormDataParam("productImage") FormDataContentDisposition
	formDataContentDisposition, @FormDataParam("productName") String productName, @FormDataParam("price") double price, @FormDataParam("quantity") int quantity, @FormDataParam("category") String category) throws IOException {; 
		int read = 0;
		byte[] bytes = new byte[1024];
		
		String path = this.getClass().getClassLoader().getResource("").getPath();
		
		String pathArr[] = path.split("/WEB-INF/classes/");
		
		FileOutputStream out = new FileOutputStream(new File(pathArr[0]+"/image/", formDataContentDisposition.getFileName()));
				
				
		while((read = fileInputStream.read(bytes)) != -1){	
			
			out.write(bytes,0,read);
		}
		out.flush();
		out.close();
		
		Product product = new Product();
		product.setProductName(productName);
		product.setPrice(price);
		product.setQuantity(quantity);
		product.setCategory(category);
		product.setImageName(formDataContentDisposition.getFileName());
		ProductDAO productDAO = new ProductDAO();
		productDAO.addProduct(product);
	}
	
	@Path("getProducts")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProducts() {

		ProductDAO productDAO = new ProductDAO();
		List <Product> productList = productDAO.getAllProductDetails();

		return productList;
	}
	
	@Path("mail/{emailId}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String mail(@PathParam("emailId") String emailId) throws MessagingException {
		System.out.println("hi sowmya ch");
		String subject="Registration Completed";
		String body="Thank you for choosing us......";
		String email=emailId;
		String host = "smtp.gmail.com";
		String from = "rineesha555@gmail.com";
		String pass = "9246266338";

		Properties props = System.getProperties();

		props.put("mail.smtp.starttls.enable", "true"); // added this line
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		String[] to = {email}; // added this line
		System.out.println(to + email);

		Session session = Session.getDefaultInstance(props, null);
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));

		InternetAddress[] toAddress = new InternetAddress[to.length];

		// To get the array of addresses

		for( int i=0; i < to.length; i++ )
		{
			// changed from a while loop
			toAddress[i] = new InternetAddress(to[i]);
		}

		for( int i=0; i < toAddress.length; i++)
		{
			// changed from a while loop
			message.addRecipient(Message.RecipientType.TO, toAddress[i]);
		}

		message.setSubject(subject);
		message.setText(body);

		Transport transport = session.getTransport("smtp");

		transport.connect(host, from, pass);
		transport.sendMessage(message, message.getAllRecipients());

		transport.close();

        return "Successful";
    }
	
	@Path("confirmOrder")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void confirmOrder(@FormDataParam("deliveryAddress") String deliveryAddress,  @FormDataParam("totalPrice") double totalPrice) throws IOException {; 
		
		System.out.println("myserources..");
		Orders order = new Orders();
		order.setAddress(deliveryAddress);
		order.setAmount(totalPrice);
		order.setDeliveryDate(new Date());
		order.setOrderStatus("ordered");
		OrderDAO orderDAO = new OrderDAO();
		orderDAO.addOrder(order);
	}
	
	@Path("updateCust")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCust(Customer customer) {
		System.out.println("Data Recieved in Customer Update : " + customer);
		CustomerDAO customerDAO = new CustomerDAO();
		customerDAO.update(customer);
	}
	
	
}
