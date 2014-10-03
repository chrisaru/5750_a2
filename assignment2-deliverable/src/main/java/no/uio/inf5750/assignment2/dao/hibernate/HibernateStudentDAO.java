package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Entity;

import no.uio.inf5750.assignment2.dao.StudentDAO;
import no.uio.inf5750.assignment2.model.Student;

public class HibernateStudentDAO implements StudentDAO {

	static Logger logger = Logger.getLogger(HibernateStudentDAO.class);
    private SessionFactory sessionFactory;
 
    public SessionFactory getSessionFactory() {
 		return sessionFactory;
 	}

 	public void setSessionFactory(SessionFactory sessionFactory) {
 		this.sessionFactory = sessionFactory;
 	}
    
	@Override
	public int saveStudent(Student student) {
		return (Integer) sessionFactory.getCurrentSession().save(student);
	}

	@Override
	public Student getStudent(int id) {
		return (Student) sessionFactory.getCurrentSession().get(Student.class, id);
	}

	@Override
	public Student getStudentByName(String name) {
		return (Student) sessionFactory.getCurrentSession().get(Student.class,name);
	}

	@Override

	public Collection<Student> getAllStudents() {
		Session session = sessionFactory.getCurrentSession();
		return (Collection<Student>) session.createQuery("from Student").list();
	}

	@Override
	public void delStudent(Student student) {
		sessionFactory.getCurrentSession().delete(student);
		
	}

}
