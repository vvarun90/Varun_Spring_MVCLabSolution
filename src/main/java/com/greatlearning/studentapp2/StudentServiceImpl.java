package com.greatlearning.studentapp2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Repository
public class StudentServiceImpl implements StudentService {
	private SessionFactory sessionFactory;
	private Session session;

	// 	Using Autowired in constructor-based DI is optional in latest Spring
	@Autowired
	StudentServiceImpl( SessionFactory sessionFactory ) {
	    this.sessionFactory = sessionFactory;
		
		try {
			this.session = this.sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			this.session = this.sessionFactory.openSession();
		}
	}

	@Transactional
	public List<Student> findAll() {
		// Note: If Entity name is specified then use it instead of table name - eg. "from Pustak"
//		Transaction tx = session.beginTransaction();
		List<Student> students = session.createQuery( "from Student", Student.class ).list(); // find all the records from the database table
//		tx.commit();

		return students;
	}

	@Transactional
	public Student findById( int id ) {
		Student student = new Student();

		Transaction tx = session.beginTransaction();
		student = session.get(Student.class, id);
		tx.commit();

		return student;
	}

	@Transactional
	public void save( Student student ) {
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate( student );
		tx.commit();
	}

	@Transactional
	public void deleteById( int id ) {
		Transaction tx = session.beginTransaction();
		Student student = session.get(Student.class, id);
		session.delete( student );
		tx.commit();
	}

	@Transactional
	public void print( List<Student> students ) {
		for( Student student : students ) {
			System.out.println( student );
		}
	}
}
