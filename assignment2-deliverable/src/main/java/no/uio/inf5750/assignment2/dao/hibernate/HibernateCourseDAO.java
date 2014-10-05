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
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Course where courseCode = :courseCode";
		Query query = session.createQuery(hql);
		query.setString("courseCode", courseCode);
		return (Course) query.uniqueResult();
	}

	@Override
	public Course getCourseByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Course where name = :name";
		Query query = session.createQuery(hql);
		query.setString("name", name);
		return (Course) query.uniqueResult();
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
