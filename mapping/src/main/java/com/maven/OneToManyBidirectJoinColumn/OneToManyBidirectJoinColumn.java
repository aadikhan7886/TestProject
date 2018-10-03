package com.maven.OneToManyBidirectJoinColumn;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class OneToManyBidirectJoinColumn {
	public static void main(String[] args) {
		SessionFactory sessionFactory = new Configuration().configure("hibernateForOracle.cfg.xml")
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		College college1 = new College(111, "MIT", null);
		College college2 = new College(222, "COEP", null);
		College college3 = new College(333, "SINHGAD", null);
		College college4 = new College(444, "VJIT", null);

		University university1 = new University(1, "SBPU", null);
		University university2 = new University(2, "ICE", null);

		Set<College> SBPUcolleges = new HashSet<College>();
		SBPUcolleges.add(college1);
		SBPUcolleges.add(college2);

		Set<College> MUMBAIcolleges = new HashSet<College>();
		MUMBAIcolleges.add(college3);
		MUMBAIcolleges.add(college4);

		college1.setUniversity(university1);
		college2.setUniversity(university1);
		college3.setUniversity(university2);
		college4.setUniversity(university2);
		university1.setCollege(SBPUcolleges);
		university2.setCollege(MUMBAIcolleges);
		
		session.save(university1);
		session.save(university2);

		session.flush();
		transaction.commit();

	}
}

@Entity
@Table(name = "UniversityInfo")
class University {
	@Id
	private int universityId;
	private String universityName;

	@Embedded
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "University_ID")
	private Set<College> college;

	public int getUniversityId() {
		return universityId;
	}

	public void setUniversityId(int universityId) {
		this.universityId = universityId;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	public Set<College> getCollege() {
		return college;
	}

	public void setCollege(Set<College> college) {
		this.college = college;
	}

	@Override
	public String toString() {
		return "University [universityId=" + universityId + ", universityName=" + universityName + ", college="
				+ college + "]";
	}

	public University(int universityId, String universityName, Set<College> college) {
		super();
		this.universityId = universityId;
		this.universityName = universityName;
		this.college = college;
	}

	public University() {
		super();

	}

}

@Entity
@Table(name = "CollegeInfo")
@Embeddable
class College {
	@Id
	private int collegeId;
	private String collegeName;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "University_ID")
	private University university;

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public int getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(int collegeId) {
		this.collegeId = collegeId;
	}

	public University getUniversity() {
		return university;
	}

	public void setUniversity(University university) {
		this.university = university;
	}

	public College(int collegeId, String collegeName, University university) {
		super();
		this.collegeId = collegeId;
		this.collegeName = collegeName;
		this.university = university;
	}

	@Override
	public String toString() {
		return "College [collegeId=" + collegeId + ", collegeName=" + collegeName + ", university=" + university + "]";
	}

	public College() {
		super();
		// TODO Auto-generated constructor stub
	}

}