package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import no.uio.inf5750.assignment2.dao.DegreeDAO;
import no.uio.inf5750.assignment2.model.Degree;

public class HibernateDegreeDAO implements DegreeDAO{
	static Logger logger = Logger.getLogger(HibernateDegreeDAO.class);
    private SessionFactory sessionFactory;
	@Override
	public int saveDegree(Degree degree) {
		return (Integer) sessionFactory.getCurrentSession().save(degree);
	}

	@Override
	public Degree getDegree(int id) {
		return (Degree) sessionFactory.getCurrentSession().get(Degree.class, id);
	}

	@Override
	public Degree getDegreeByType(String type) {
		return (Degree) sessionFactory.getCurrentSession().get(Degree.class, type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Degree> getAllDegrees() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Courses").list();	
	}

	@Override
	public void delDegree(Degree degree) {	
		Session session = sessionFactory.getCurrentSession();
		session.delete(degree);
	//	session.createQuery("delete from Courses where course_id = " + degree.getId());
		//også slette fra lister som har denne ? 
	}

}
