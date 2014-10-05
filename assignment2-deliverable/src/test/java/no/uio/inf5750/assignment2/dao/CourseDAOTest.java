package no.uio.inf5750.assignment2.dao;
import static junit.framework.Assert.*;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Student;

import org.hibernate.mapping.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/META-INF/assignment2/beans.xml"})
@Transactional
public class CourseDAOTest {
	
	@Autowired
	private CourseDAO courseDAO;
	
	
	@Test
	public void testSave() {
		Course c = new Course("INF1000", "intro");
		int id = courseDAO.saveCourse(c);
		assertEquals(id, 1);
	}
	
	@Test
	public void testGet() {
		Course c = new Course("get", "test");
		int id = courseDAO.saveCourse(c);
		assertEquals(c, courseDAO.getCourse(id));
	}
	@Test
	public void testGetByCode() {
		Course c = new Course("code", "test");
		courseDAO.saveCourse(c);
		assertEquals(courseDAO.getCourseByCourseCode(c.getCourseCode()).getCourseCode(), c.getCourseCode());
	}
	@Test
	public void testGetByName() {
		Course c = new Course("test", "name");
		courseDAO.saveCourse(c);
		assertEquals(courseDAO.getCourseByName(c.getName()).getName(), c.getName());
	}
	@Test
	public void testGetAll() {
		Course c1 = new Course("test1", "course1");
		Course c2 = new Course("test2", "course2");
		Course c3 = new Course("test3", "course3");
		courseDAO.saveCourse(c1);
		courseDAO.saveCourse(c2);
		courseDAO.saveCourse(c3);
		Collection<Course> allLocal = new ArrayList<Course>();
		allLocal.add(c1);
		allLocal.add(c2);
		allLocal.add(c3);
		Collection<Course> allFromDB = courseDAO.getAllCourses();
		assertTrue(allFromDB.containsAll(allLocal));
	}
	@Test
	public void testDel() {
		Course c1 = new Course("test1", "course1");
		int id = courseDAO.saveCourse(c1);
		courseDAO.delCourse(c1);
		assertNull(courseDAO.getCourse(id));
	}
}
