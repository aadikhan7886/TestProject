package com.maven.ManyToOneBidirectJoinTable;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ManyToOneBidirectJoinTable {
	public static void main(String[] args) {
		SessionFactory sessionFactory = new Configuration().configure("hibernateForOracle.cfg.xml")
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Vehicle v1 = new Vehicle(12, "BMW", null);
		Vehicle v2 = new Vehicle(13, "AUDI", null);
		Vehicle v3 = new Vehicle(14, "MASARATI", null);
		Vehicle v4 = new Vehicle(15, "KOENIGSEGG", null);

		User user1 = new User(1, "DOMINIC", null);
		User user2 = new User(2, "SAM", null);

		Set<Vehicle> vehicle1 = new HashSet<Vehicle>();
		vehicle1.add(v1);
		vehicle1.add(v2);

		Set<Vehicle> vehicle2 = new HashSet<Vehicle>();
		vehicle2.add(v3);
		vehicle2.add(v4);

		user1.setVehicle(vehicle1);
		user2.setVehicle(vehicle2);
		v1.setUser(user1);
		v2.setUser(user1);
		v3.setUser(user2);
		v4.setUser(user2);
		session.save(user1);
		session.save(user2);
		session.flush();
		transaction.commit();

	}
}

@Entity
@Table(name = "UserInfo")
class User {
	@Id
	private int userId;
	private String userName;

	@Embedded
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "User_Vehicle_Info", joinColumns = @JoinColumn(name = "New_UerID", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "New_VID", referencedColumnName = "vehicleId"))
	private Set<Vehicle> vehicle;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Set<Vehicle> getVehicle() {
		return vehicle;
	}

	public void setVehicle(Set<Vehicle> vehicle) {
		this.vehicle = vehicle;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", vehicle=" + vehicle + "]";
	}

	public User(int userId, String userName, Set<Vehicle> vehicle) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.vehicle = vehicle;
	}

	public User() {
		super();

	}

}

@Entity
@Table(name = "VehicleInfo")
@Embeddable
class Vehicle {
	@Id
	private int vehicleId;
	private String vehicleName;

	@ManyToOne(cascade = CascadeType.ALL)
	private User user;

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Vehicle(int vehicleId, String vehicleName, User user) {
		super();
		this.vehicleId = vehicleId;
		this.vehicleName = vehicleName;
		this.user = user;
	}

	@Override
	public String toString() {
		return "Vehicle [vehicleId=" + vehicleId + ", vehicleName=" + vehicleName + ", user=" + user + "]";
	}

	public Vehicle() {
		super();
		// TODO Auto-generated constructor stub
	}

}
