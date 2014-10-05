package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Entity;

import no.uio.inf5750.assignment2.dao.CourseDAO;
import no.uio.inf5750.assignment2.model.Course;

public class HibernateCourseDAO implements CourseDAO {
    
	static Logger logger = Logger.getLogger(HibernateCourseDAO.class);
    private SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
    
	@Override
	public int saveCourse(Course course) {
		int ret = (Integer) sessionFactory.getCurrentSession().save(course);
		sessionFactory.getCurrentSession().flush();
		return ret;
	}

	@Override
	public Course getCourse(int id) {
		return (Course) sessionFactory.getCurrentSession().get( Course.class, id );
	}

	@Override
	public Course getCourseByCourseCode(String courseCode) {
		return (Course) sessionFactory.getCurrentSession().get( Course.class, courseCode );
	}

	@Override
	public Course getCourseByName(String name) {
		return (Course) sessionFactory.getCurrentSession().get( Course.class, name );
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Course> getAllCourses() {
		Session session = sessionFactory.getCurrentSession();
		return (Collection<Course>) session.createQuery("from Course").list();	
	}

	@Override
	public void delCourse(Course course) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(course);
		session.flush();
	}

}
