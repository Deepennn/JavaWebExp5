package pojo;

import java.util.Date;

public class Order {
    public Order(int order_id, int product_id, int user_id, Date time) {
		super();
		this.order_id = order_id;
		this.product_id = product_id;
		this.user_id = user_id;
		this.time = time;
	}

/*
	public Order(int order_id, Product product, User user, Date time) {
		super();
		this.order_id = order_id;
		this.product = product;
		this.user = user;
		this.time = time;
	}
*/

	private int order_id;	
	private int product_id;
    private int user_id;
/*
	private Product product;
	private User user;
*/
    private Date time;

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
/*
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
*/
    
    public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

    @Override
    public String toString() {
        return "Order{" +
                "order_id='" + order_id + '\'' +
                "product_id='" + product_id + '\'' +
                "user_id='" + user_id + '\'' +
                "time='" + time + '\'' +
                '}';
    }

/*
    @Override
    public String toString() {
        return "Order{" +
                "order_id='" + order_id + '\'' +
                "product_id='" + product.toString() + '\'' +
                "user_id='" + user.toString() + '\'' +
                "time='" + time + '\'' +
                '}';
    }
*/

}
