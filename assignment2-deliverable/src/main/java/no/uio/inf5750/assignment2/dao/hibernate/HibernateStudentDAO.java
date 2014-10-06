package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
		int ret =  (Integer) sessionFactory.getCurrentSession().save(student);
		sessionFactory.getCurrentSession().flush();
		return ret;
	}

	@Override
	public Student getStudent(int id) {
		return (Student) sessionFactory.getCurrentSession().get(Student.class, id);
	}

	@Override
	public Student getStudentByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Student where name = :name";
		Query query = session.createQuery(hql);
		query.setString("name", name);
		return (Student) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Student> getAllStudents() {
		Session session = sessionFactory.getCurrentSession();
		return (Collection<Student>) session.createQuery("from Student").list();
	}

	@Override
	public void delStudent(Student student) {
		sessionFactory.getCurrentSession().delete(student);
		sessionFactory.getCurrentSession().flush();
		
	}

}
