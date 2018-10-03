package com.maven.ManyToManyUniJoinTable;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ManyToManyUniJoinTable {
	public static void main(String[] args) {
		SessionFactory sessionFactory = new Configuration().configure("hibernateForOracle.cfg.xml")
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		Company company1 = new Company(1, "Infosys");
		Company company2 = new Company(2, "IBM");
		Company company3 = new Company(3, "DELL");
		Company company4 = new Company(4, "HP");
		Company company5 = new Company(5, "WIndos");
		Company company6 = new Company(6, "APPLE");

		Owner owner1 = new Owner(1, "DOMINIC", null);
		Owner owner2 = new Owner(2, "SOHAIL", null);
		Owner owner3 = new Owner(3, "AADI", null);

		Set<Company> com1 = new HashSet<Company>();
		com1.add(company1);
		com1.add(company2);
		com1.add(company3);

		Set<Company> com2 = new HashSet<Company>();
		com2.add(company3);
		com2.add(company4);
		com2.add(company5);

		Set<Company> com3 = new HashSet<Company>();
		com3.add(company4);
		com3.add(company5);
		com3.add(company6);

		owner1.setCompany(com1);
		owner2.setCompany(com2);
		owner3.setCompany(com3);

		session.save(owner1);
		session.save(owner2);
		session.save(owner3);
		session.save(company1);
		session.save(company2);
		session.save(company3);
		session.save(company4);
		session.save(company5);
		session.save(company6);
		session.flush();
		transaction.commit();

	}
}

@Entity
@Table(name = "OwnerInfo")
class Owner {
	@Id
	private int ownerId;
	private String ownerName;
	@Embedded
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Owenr_Company_Info", joinColumns = @JoinColumn(name = "New_OwnerD", referencedColumnName = "ownerId"), inverseJoinColumns = @JoinColumn(name = "New_CompID", referencedColumnName = "companyId"))
	private Set<Company> company;

	public Owner(int ownerId, String ownerName, Set<Company> company) {
		super();
		this.ownerId = ownerId;
		this.ownerName = ownerName;
		this.company = company;
	}

	public Set<Company> getCompany() {
		return company;
	}

	public void setCompany(Set<Company> company) {
		this.company = company;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	@Override
	public String toString() {
		return "Owner [ownerId=" + ownerId + ", ownerName=" + ownerName + ", company=" + company + "]";
	}

	public Owner() {
		super();

	}

}

@Entity
@Table(name = "companyInfo")
@Embeddable
class Company {
	@Id
	private int companyId;
	private String companyName;

	public Company(int companyId, String companyName) {
		super();
		this.companyId = companyId;
		this.companyName = companyName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", companyName=" + companyName + "]";
	}

	public Company() {
		super();
		// TODO Auto-generated constructor stub
	}

}

