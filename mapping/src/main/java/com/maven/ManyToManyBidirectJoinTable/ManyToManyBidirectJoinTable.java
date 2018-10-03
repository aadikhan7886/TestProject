package com.maven.ManyToManyBidirectJoinTable;


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

public class ManyToManyBidirectJoinTable {
	public static void main(String[] args) {
		SessionFactory sessionFactory = new Configuration().configure("hibernateForOracle.cfg.xml")
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		Company company1 = new Company(101, "Infosys",null);
		Company company2 = new Company(201, "IBM",null);
		Company company3 = new Company(301, "DELL",null);
		Company company4 = new Company(401, "HP",null);
		Company company5 = new Company(501, "WIndos",null);
		Company company6 = new Company(601, "APPLE",null);

		Owner owner1 = new Owner(1, "DOMINIC", null);
		Owner owner2 = new Owner(2, "SOHAIL", null);
		Owner owner3 = new Owner(3, "AADI", null);
		Owner owner4 = new Owner(4, "SAM", null);

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
		
		Set<Owner> own1 = new HashSet<Owner>();
		own1.add(owner1);
		own1.add(owner2);
	
		Set<Owner> own2 = new HashSet<Owner>();
		own2.add(owner3);
		own2.add(owner4);
		
		owner1.setCompany(com1);
		owner2.setCompany(com2);
		owner3.setCompany(com3);
		owner4.setCompany(com2);
		
		company1.setOwner(own1);
		company2.setOwner(own2);
		company3.setOwner(own1);
		company4.setOwner(own2);
		company5.setOwner(own1);
		company6.setOwner(own2);
		
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
	@JoinTable(name = "Owner_Company_Info", joinColumns = @JoinColumn(name = "New_OwnerD", referencedColumnName = "ownerId"), inverseJoinColumns = @JoinColumn(name = "New_CompID", referencedColumnName = "companyId"))
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
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Owner> owner;

	public Company(int companyId, String companyName, Set<Owner> owner) {
		super();
		this.companyId = companyId;
		this.companyName = companyName;
		this.owner = owner;
	}

	public Set<Owner> getOwner() {
		return owner;
	}

	public void setOwner(Set<Owner> owner) {
		this.owner = owner;
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
		return "Company [companyId=" + companyId + ", companyName=" + companyName + ", owner=" + owner + "]";
	}

	public Company() {
		super();
		// TODO Auto-generated constructor stub
	}

}
