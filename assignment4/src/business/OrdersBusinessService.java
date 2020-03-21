package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import beans.Order;

@Stateless
@Local(OrdersBusinessInterface.class)
@Alternative
public class OrdersBusinessService implements OrdersBusinessInterface {
	
	
	public OrdersBusinessService service;

	List<Order> orders = new ArrayList<Order>();
	
	@Resource(mappedName="java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;

	@Resource(mappedName="java:/jms/queue/Order")
	private Queue queue;

	
	@Override
	public void test() {
		System.out.println("Hello from the OrdersBusinessService");

	}
	
	public OrdersBusinessService() {
		orders.add(new Order("0001", "Product 1", (float)45.00, 2));
		orders.add(new Order("0002", "Product 2", (float)16.55, 5));
		orders.add(new Order("0003", "Product 3", (float)147.12, 1));
		orders.add(new Order("0004", "Product 4", (float)36.99, 6));
		orders.add(new Order("0010", "Ham Sandwich", (float) 14.50, 2));
	}

	@Override
	public List<Order> getOrders() {
		// TODO Auto-generated method stub
		return orders;
	}

	@Override
	public void setOrders(List<Order> orders) {
		this.orders = orders;
		
	}

	@Override
	public void sendOrder(Order order) {
		
		// Send a Message for an Order
		try {
			Connection connection = connectionFactory.createConnection();
			Session  session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer messageProducer = session.createProducer(queue);
			TextMessage message1 = session.createTextMessage();
			TextMessage message2 = session.createTextMessage();
			
			message1.setText("This is test message");
			messageProducer.send(message1);
			
			messageProducer.send(message2);
			
			connection.close();
			System.out.println("This is the test message.");
		
			} 
			catch (JMSException e) 
			{
			e.printStackTrace();
			}
		
	}

}
