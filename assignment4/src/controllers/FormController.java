package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import beans.Order;
import beans.User;
import business.MyTimerService;
import business.OrdersBusinessInterface;

@ManagedBean
@ViewScoped

public class FormController {
	
	@Inject
	OrdersBusinessInterface services;
	
	@EJB
	MyTimerService timer;
	
	public String onSubmit() throws SQLException {
		
		// Gets user values from the form
		FacesContext context = FacesContext.getCurrentInstance();
		User user = context.getApplication().evaluateExpressionGet(context, "#{user}", User.class);
		
		// Puts the user object into the Post request
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("user", user);
		
		// Start a time when login is clicked
		//timer.setTimer(10000);
		
		// Prints a message to the console telling which business service is selected
		services.test();
		
		getAllOrders();
		//insertOrder();
		//getAllOrders();
		
		// Takes to the next page listed
		return "TestResponse.xhtml";

		
	}
	
	public String onFlash() {
		FacesContext context = FacesContext.getCurrentInstance();
		User user = context.getApplication().evaluateExpressionGet(context, "#{user}", User.class);
		
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("user", user);
		
		return "TestResponse2.xhtml?faces-redirect=true";
		
	}
	
	public String onBack() {
		FacesContext context = FacesContext.getCurrentInstance();
		User user = context.getApplication().evaluateExpressionGet(context, "#{user}", User.class);
		
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("#{user}", user);
		System.out.println("This is the test message.");
		return "TestForm.xhtml";
	}
	
	public OrdersBusinessInterface getService() {
		return services;
		
	}
	
	public void getAllOrders() throws SQLException {
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String pass = "root";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(url, user, pass);
			System.out.println("Success!!");
			
			stmt = conn.createStatement();
			String sqlstate = "SELECT * FROM testapp.\"Orders\"";
			rs = stmt.executeQuery(sqlstate);
			
			while(rs.next()) {
				System.out.println("Id = " + rs.getInt("ID") + " || Order Number = " + 
						rs.getInt("ORDER_NO") + " || Product Name = " + rs.getString("PRODUCT_NAME") +
						" || Price = " + rs.getFloat("PRICE") + " || Quantity = " + rs.getInt("QUANTITY"));
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Failure!!");
		}
		finally {
			rs.close();
			stmt.close();
			conn.close();
		}
	}
	
	private void insertOrder() throws SQLException {
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String pass = "root";
		
		Connection conn = null;
		Statement stmt = null;
		int rowsAffected = 0;
		
		try {
			conn = DriverManager.getConnection(url, user, pass);
			
			stmt = conn.createStatement();
			String sqlstate = "INSERT INTO testapp.\"Orders\" (\"ORDER_NO\", \"PRODUCT_NAME\", \"PRICE\", \"QUANTITY\") VALUES('001122334455', 'This was inserted new.', 25.00, 100)";
			rowsAffected = stmt.executeUpdate(sqlstate);
			
			System.out.println("Rows affected: " + rowsAffected);
			
		} catch (SQLException e) {
			System.out.println("Error with connection to database.");
		} finally {
			stmt.close();
			conn.close();
			
		}
	}
	
	public String sendOrder() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		User user = context.getApplication().evaluateExpressionGet(context, "#{user}", User.class);
		
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("user", user);
		
		Order order = new Order("10", "Ham Sandwich", (float) 14.50, 2);
		
		services.sendOrder(order);
		
		return("/OrderResponse.xhtml?faces-redirect=true");
	}

}
