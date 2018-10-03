package com.maven.OneToOneBidirectJoinTable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class OneToOneBidirectJoinTable {
	public static void main(String[] args) {
		SessionFactory sessionFactory = new Configuration().configure("hibernateForOracle.cfg.xml")
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		Mobile mobile1 = new Mobile(1, "s7 edge", "Samsung", 52000, null);
		Emp employee1 = new Emp(12, "Sohail", 52000.00, 25, mobile1);
		mobile1.setEmp(employee1);
		Mobile mobile2 = new Mobile(2, "XS_10", "iPhone", 82000, null);
		Emp employee2 = new Emp(13, "aadil", 72000.00, 25, mobile2);
		mobile2.setEmp(employee2);
		Mobile mobile3 = new Mobile(3, "vivo10", "VIVO", 52000, null);
		Emp employee3 = new Emp(14, "dominic", 62000.00, 25, mobile3);
		mobile3.setEmp(employee3);

		session.save(employee1);
		session.persist(employee2);
		session.persist(employee3);

		session.flush();
		transaction.commit();

	}
}

@Entity
@Table(name = "EmpMobileInfo")
class Emp {
	@Id
	private int empId;
	private String empName;
	private double salary;
	private int age;

	@Embedded
	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "Emp_Mobile_Information", joinColumns = @JoinColumn(name = "New_EID", referencedColumnName = "empId"), inverseJoinColumns = @JoinColumn(name = "New_MID", referencedColumnName = "imei"))
	private Mobile mobile;

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Emp(int empId, String empName, double salary, int age, Mobile mobile) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.salary = salary;
		this.age = age;
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return "Emp [empId=" + empId + ", empName=" + empName + ", salary=" + salary + ", age=" + age + ", mobile="
				+ mobile + "]";
	}

	public Emp() {
		super();

	}

}

@Entity
@Table(name = "MobileInfo")
@Embeddable
class Mobile {
	@Id
	private int imei;
	private String mobileModel;
	private String mobileVendor;
	private double mobilePrice;

	@OneToOne(cascade = CascadeType.ALL)
	private Emp emp;

	public Mobile(int imei, String mobileModel, String mobileVendor, double mobilePrice, Emp emp) {
		super();
		this.imei = imei;
		this.mobileModel = mobileModel;
		this.mobileVendor = mobileVendor;
		this.mobilePrice = mobilePrice;
		this.emp = emp;
	}

	public Emp getEmp() {
		return emp;
	}

	public void setEmp(Emp emp) {
		this.emp = emp;
	}

	public int getImei() {
		return imei;
	}

	public void setImei(int imei) {
		this.imei = imei;
	}

	public String getMobileModel() {
		return mobileModel;
	}

	public void setMobileModel(String mobileModel) {
		this.mobileModel = mobileModel;
	}

	public String getMobileVendor() {
		return mobileVendor;
	}

	public void setMobileVendor(String mobileVendor) {
		this.mobileVendor = mobileVendor;
	}

	public double getMobilePrice() {
		return mobilePrice;
	}

	public void setMobilePrice(double mobilePrice) {
		this.mobilePrice = mobilePrice;
	}

	@Override
	public String toString() {
		return "Mobile [imei=" + imei + ", mobileModel=" + mobileModel + ", mobileVendor=" + mobileVendor
				+ ", mobilePrice=" + mobilePrice + ", emp=" + emp + "]";
	}

	public Mobile() {
		super();

	}

}
