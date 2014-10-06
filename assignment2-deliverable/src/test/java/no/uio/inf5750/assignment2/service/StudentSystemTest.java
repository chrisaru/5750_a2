package no.uio.inf5750.assignment2.service;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Degree;
import no.uio.inf5750.assignment2.model.Student;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/META-INF/assignment2/beans.xml"})
@Transactional
public class StudentSystemTest  {

	@Autowired
	private StudentSystem studentSystem;
	
	
	


	@Test
	public void testAddCourse() {
		int id = studentSystem.addCourse("test", "course");
		assertEquals("test", studentSystem.getCourse(id).getCourseCode());
		assertEquals("course", studentSystem.getCourse(id).getName());
	}


	@Test
	public void testUpdateCourse() {
		int id = studentSystem.addCourse("inf1000", "intro");
		studentSystem.updateCourse(id, "inf1060", "cprog");
		Course c = studentSystem.getCourse(id);
		assertEquals(c.getName(), "cprog");	
	}


	@Test
	public void testGetCourse() {
		int id = studentSystem.addCourse("inf1000", "intro");
		Course c = studentSystem.getCourse(id);
		assertEquals(c.getId(), id);
	}


	@Test
	public void testGetCourseByCourseCode() {
		String code = "inf1000";
		studentSystem.addCourse(code, "intro");
		Course c = studentSystem.getCourseByCourseCode(code);
		assertEquals(code, c.getCourseCode());
	}


	@Test
	public void testGetCourseByName() {
		String name = "intro";
		studentSystem.addCourse("inf1000", name);
		Course c = studentSystem.getCourseByName(name);
		assertEquals(name, c.getName());
	}


	@Test
	public void testGetAllCourses() {
		Course c1 = new Course("test1", "course1");
		Course c2 = new Course("test2", "course2");
		Course c3 = new Course("test3", "course3");
		studentSystem.addCourse(c1.getCourseCode(), c1.getName());
		studentSystem.addCourse(c2.getCourseCode(), c2.getName());
		studentSystem.addCourse(c3.getCourseCode(), c3.getName());
		Collection<Course> allLocal = new ArrayList<Course>();
		allLocal.add(c1);
		allLocal.add(c2);
		allLocal.add(c3);
		Collection<Course> allFromDB = studentSystem.getAllCourses();
		assertTrue(allFromDB.containsAll(allLocal));
	}


	@Test
	public void testdelCourse() {
		int id = studentSystem.addCourse("test1", "course1");
		studentSystem.delCourse(id);
		assertNull(studentSystem.getCourse(id));
	}


	@Test
	public void testaddAttendantToCourse() {
		int idC = studentSystem.addCourse("test1", "course1");
		int idS = studentSystem.addStudent("stud");
		studentSystem.addAttendantToCourse(idC, idS);
		Course c = studentSystem.getCourse(idC);
		Student s = studentSystem.getStudent(idS);
		assertNotNull(c.getAttendants());
		assertTrue(c.getAttendants().size() == 1 && c.getAttendants().contains(s));
	}


	@Test
	public void testremoveAttendantFromCourse() {
		int idC = studentSystem.addCourse("test1", "course1");
		int idS1 = studentSystem.addStudent("stud1");
		int idS2 = studentSystem.addStudent("stud2");
		studentSystem.addAttendantToCourse(idC, idS1);
		studentSystem.addAttendantToCourse(idC, idS2);
		studentSystem.removeAttendantFromCourse(idC, idS2);
		Student s1 = studentSystem.getStudent(idS1);
		Student s2 = studentSystem.getStudent(idS2);
		Course c = studentSystem.getCourse(idC);
		assertNotNull(c.getAttendants());
		assertTrue(c.getAttendants().size() == 1 && !c.getAttendants().contains(s2)
				&& c.getAttendants().contains(s1));
	}


	@Test
	public void testaddDegree() {
		int id = studentSystem.addDegree("test");
		assertEquals("test", studentSystem.getDegree(id).getType());
	}


	@Test
	public void testupdateDegree() {
		int id = studentSystem.addDegree("test");
		String type = "master";
		studentSystem.updateDegree(id, type);
		assertEquals(studentSystem.getDegree(id).getType(), type);	
	}


	@Test
	public void testGetDegree() {
		int id = studentSystem.addDegree("test");
		assertEquals(id, studentSystem.getDegree(id).getId());
	}


	@Test
	public void testgetDegreeByType() {
		String type = "master";
		studentSystem.addDegree(type);
		assertEquals(type, studentSystem.getDegreeByType(type).getType());
	}


	@Test
	public void testgetAllDegrees() {
		Degree c1 = new Degree("test1");
		Degree c2 = new Degree("test2");
		Degree c3 = new Degree("test3");
		studentSystem.addDegree(c1.getType());
		studentSystem.addDegree(c2.getType());
		studentSystem.addDegree(c3.getType());
		Collection<Degree> allLocal = new ArrayList<Degree>();
		allLocal.add(c1);
		allLocal.add(c2);
		allLocal.add(c3);
		Collection<Degree> allFromDB = studentSystem.getAllDegrees();
		assertTrue(allFromDB.containsAll(allLocal));
	}


	@Test
	public void testdelDegree() {
		int id = studentSystem.addDegree("test1");
		studentSystem.delDegree(id);
		assertNull(studentSystem.getDegree(id));
	}


	@Test
	public void testaddRequiredCourseToDegree() {
		int idD = studentSystem.addDegree("degree1");
		int idC = studentSystem.addCourse("course1", "test1");
		studentSystem.addRequiredCourseToDegree(idD, idC);
		Degree d = studentSystem.getDegree(idD);
		Course c = studentSystem.getCourse(idC);
		assertNotNull(d.getRequiredCourses());
		assertTrue(d.getRequiredCourses().size() == 1 && d.getRequiredCourses().contains(c));
		
	}


	@Test
	public void testremoveRequiredCourseFromDegree() {
		int idC1 = studentSystem.addCourse("test1", "course1");
		Course c1 = studentSystem.getCourse(idC1);
		int idC2 = studentSystem.addCourse("test2", "course2");
		Course c2 = studentSystem.getCourse(idC2);
		int idD = studentSystem.addDegree("master");
		studentSystem.addRequiredCourseToDegree(idD, idC1);
		studentSystem.addRequiredCourseToDegree(idD, idC2);
		studentSystem.removeRequiredCourseFromDegree(idD, idC2);
		Degree d = studentSystem.getDegree(idD);
		assertNotNull(d.getRequiredCourses());
		assertTrue(d.getRequiredCourses().size() == 1 && !d.getRequiredCourses().contains(c2)
				&& d.getRequiredCourses().contains(c1));
		
	}


	@Test
	public void addStudent() {
		int id = studentSystem.addStudent("test");
		assertEquals("test", studentSystem.getStudent(id).getName());
	}


	@Test
	public void testupdateStudent() {
		int id = studentSystem.addStudent("test");
		String stud = "stud1";
		studentSystem.updateStudent(id, stud);
		assertEquals(studentSystem.getStudent(id).getName(), stud);	
		
	}


	@Test
	public void testgetStudent() {
		int id = studentSystem.addStudent("test");
		assertEquals(id, studentSystem.getStudent(id).getId());
	}


	@Test
	public void testGetStudentByName() {
		String name = "test";
		studentSystem.addStudent(name);
		assertEquals(name, studentSystem.getStudentByName(name).getName());
	}


	@Test
	public void testgetAllStudents() {
		Student s1 = new Student("test1");
		Student s2 = new Student("test2");
		Student s3 = new Student("test3");
		studentSystem.addStudent(s1.getName());
		studentSystem.addStudent(s2.getName());
		studentSystem.addStudent(s3.getName());
		Collection<Student> allLocal = new ArrayList<Student>();
		allLocal.add(s1);
		allLocal.add(s2);
		allLocal.add(s3);
		Collection<Student> allFromDB = studentSystem.getAllStudents();
		assertTrue(allFromDB.containsAll(allLocal));
	}


	@Test
	public void testdelStudent() {
		int id = studentSystem.addStudent("test1");
		studentSystem.delStudent(id);
		assertNull(studentSystem.getStudent(id));
		
	}


	@Test
	public void tesaddDegreeToStudent() {
		int idD = studentSystem.addDegree("degree1");
		int idS = studentSystem.addStudent("stud1");
		studentSystem.addDegreeToStudent(idS, idD);
		Degree d = studentSystem.getDegree(idD);
		Student s = studentSystem.getStudent(idS);
		assertNotNull(s.getDegrees());
		assertTrue(s.getDegrees().size() == 1 && s.getDegrees().contains(d));
	}


	@Test
	public void testremoveDegreeFromStudent() {
		int idD1 = studentSystem.addDegree("test1");
		Degree d1 = studentSystem.getDegree(idD1);
		int idD2 = studentSystem.addDegree("test2");
		Degree d2 = studentSystem.getDegree(idD2);
		int idS = studentSystem.addStudent("stud");
		studentSystem.addDegreeToStudent(idS, idD1);
		studentSystem.addDegreeToStudent(idS, idD2);
		studentSystem.removeDegreeFromStudent(idS, idD2);
		Student s = studentSystem.getStudent(idS);
		assertNotNull(s.getDegrees());
		assertTrue(s.getDegrees().size() == 1 && !s.getDegrees().contains(d2) && s.getDegrees().contains(d1));
	}


	@Test
	public void teststudentFulfillsDegreeRequirements() {
		int idD = studentSystem.addDegree("master");
		int idS = studentSystem.addStudent("stud");
		int idC1 = studentSystem.addCourse("course1", "name1");
		int idC2 = studentSystem.addCourse("course2", "name2");
		studentSystem.addRequiredCourseToDegree(idD, idC1);
		studentSystem.addRequiredCourseToDegree(idD, idC2);
		studentSystem.addAttendantToCourse(idC1, idS);
		assertFalse(studentSystem.studentFulfillsDegreeRequirements(idS, idD));
		studentSystem.addAttendantToCourse(idC2, idS);
		assertTrue(studentSystem.studentFulfillsDegreeRequirements(idS, idD));
	}
	
}
