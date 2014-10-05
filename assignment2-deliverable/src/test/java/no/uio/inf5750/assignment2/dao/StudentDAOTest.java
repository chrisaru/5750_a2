package no.uio.inf5750.assignment2.dao;

import static junit.framework.Assert.*;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import no.uio.inf5750.assignment2.model.Student;
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
public class StudentDAOTest {
	
	@Autowired
	private StudentDAO studentDAO;
	
	
	@Test
	public void testSave() {
		Student c = new Student("test");
		int id = studentDAO.saveStudent(c);
		assertEquals(id, 1);
	}
	
	@Test
	public void testGet() {
		Student c = new Student("test");
		int id = studentDAO.saveStudent(c);
		assertEquals(c, studentDAO.getStudent(id));
	}
	@Test
	public void testGetByName() {
		Student c = new Student("test");
		studentDAO.saveStudent(c);
		assertEquals(studentDAO.getStudentByName(c.getName()).getName(), c.getName());
	}
	@Test
	public void testGetAll() {
		Student c1 = new Student("student1");
		Student c2 = new Student("student2");
		Student c3 = new Student("student3");
		studentDAO.saveStudent(c1);
		studentDAO.saveStudent(c2);
		studentDAO.saveStudent(c3);
		Collection<Student> allLocal = new ArrayList<Student>();
		allLocal.add(c1);
		allLocal.add(c2);
		allLocal.add(c3);
		Collection<Student> allFromDB = studentDAO.getAllStudents();
		assertTrue(allFromDB.containsAll(allLocal));
	}
	@Test
	public void testDel() {
		Student c1 = new Student("student1");
		int id = studentDAO.saveStudent(c1);
		studentDAO.delStudent(c1);
		assertNull(studentDAO.getStudent(id));
	}
}
