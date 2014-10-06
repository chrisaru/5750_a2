package no.uio.inf5750.assignment2.dao;

import static junit.framework.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import no.uio.inf5750.assignment2.model.Degree;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/META-INF/assignment2/beans.xml"})
@Transactional
public class DegreeDAOTest {
	
	@Autowired
	private DegreeDAO degreeDAO;
	
	
	@Test
	public void testSave() {
		Degree c = new Degree("test");
		int id = degreeDAO.saveDegree(c);
		assertEquals(id, 1);
	}
	
	@Test
	public void testGet() {
		Degree c = new Degree("test");
		int id = degreeDAO.saveDegree(c);
		assertEquals(c, degreeDAO.getDegree(id));
	}
	@Test
	public void testGetByType() {
		Degree c = new Degree("test");
		degreeDAO.saveDegree(c);
		assertEquals(degreeDAO.getDegreeByType(c.getType()).getType(), c.getType());
	}
	@Test
	public void testGetAll() {
		Degree c1 = new Degree("degree1");
		Degree c2 = new Degree("degree2");
		Degree c3 = new Degree("degree3");
		degreeDAO.saveDegree(c1);
		degreeDAO.saveDegree(c2);
		degreeDAO.saveDegree(c3);
		Collection<Degree> allLocal = new ArrayList<Degree>();
		allLocal.add(c1);
		allLocal.add(c2);
		allLocal.add(c3);
		Collection<Degree> allFromDB = degreeDAO.getAllDegrees();
		assertTrue(allFromDB.containsAll(allLocal));
	}
	@Test
	public void testDel() {
		Degree c1 = new Degree("degree1");
		int id = degreeDAO.saveDegree(c1);
		degreeDAO.delDegree(c1);
		assertNull(degreeDAO.getDegree(id));
	}
}

