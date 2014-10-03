package no.uio.inf5750.assignment2.service.impl;

import java.util.Collection;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

import no.uio.inf5750.assignment2.dao.CourseDAO;
import no.uio.inf5750.assignment2.dao.DegreeDAO;
import no.uio.inf5750.assignment2.dao.StudentDAO;
import no.uio.inf5750.assignment2.dao.hibernate.HibernateCourseDAO;
import no.uio.inf5750.assignment2.dao.hibernate.HibernateDegreeDAO;
import no.uio.inf5750.assignment2.dao.hibernate.HibernateStudentDAO;
import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Degree;
import no.uio.inf5750.assignment2.model.Student;
import no.uio.inf5750.assignment2.service.StudentSystem;

public class DefaultStudentSystem implements StudentSystem  {

	static Logger logger = Logger.getLogger(HibernateStudentDAO.class);
    CourseDAO c;
    DegreeDAO d;
    public CourseDAO getC() {
		return c;
	}

	public void setC(CourseDAO c) {
		this.c = c;
	}

	public DegreeDAO getD() {
		return d;
	}

	public void setD(DegreeDAO d) {
		this.d = d;
	}

	public StudentDAO getS() {
		return s;
	}

	public void setS(StudentDAO s) {
		this.s = s;
	}

	StudentDAO s;
    
    
	@Override
	public int addCourse(String courseCode, String name) {
		Course course = new Course(courseCode, name);
		return c.saveCourse(course);
	}

	@Override
	public void updateCourse(int courseId, String courseCode, String name) {
		Course cs = null;
		if((cs = c.getCourseByName(name)) != null) {
		} else if ((cs = c.getCourseByCourseCode(courseCode)) != null) {
		} else if ((cs = c.getCourse(courseId)) != null) {
		} else {
			logger.error("ERROR,  course did not exist");
			return;
		}
		cs.setId(courseId);
		cs.setName(name);
		cs.setName(name);
		//no need ? check for error ?
		c.saveCourse(cs);
	}
	@Override
	public Course getCourse(int courseId) {
		return c.getCourse(courseId);
	}

	@Override
	public Course getCourseByCourseCode(String courseCode) {
		return c.getCourseByCourseCode(courseCode);
	}

	@Override
	public Course getCourseByName(String name) {
		return c.getCourseByName(name);
	}

	@Override
	public Collection<Course> getAllCourses() {
		return c.getAllCourses();
	}

	@Override
	public void delCourse(int courseId) {
		c.delCourse(getCourse(courseId));
	}

	@Override
	public void addAttendantToCourse(int courseId, int studentId) {
		Course cs = c.getCourse(courseId);
		if(cs == null) {
			System.out.println("ERROR\n");
			return;
		}
		Student st = s.getStudent(studentId);
		if(st == null) {
			System.out.println("ERROR\n");
			return;
		}
		Set<Student> sSet = cs.getAttendants();
		//Set<Course> cSet = st.getCourses();
		sSet.add(st);
		//cSet.add(cs);
		cs.setAttendants(sSet);
		c.saveCourse(cs);
		//st.setCourses(cSet);
	}

	@Override
	public void removeAttendantFromCourse(int courseId, int studentId) {
		Course cs = c.getCourse(courseId);
		if(cs == null) {
			System.out.println("ERROR\n");
			return;
		}
		Student st = s.getStudent(studentId);
		if(st == null) {
			System.out.println("ERROR\n");
			return;
		}
		Set<Student> sSet = cs.getAttendants();
		//Set<Course> cSet = st.getCourses();
		sSet.remove(st);
		//cSet.add(cs);
		cs.setAttendants(sSet);
		c.saveCourse(cs);
		//st.setCourses(cSet);
	}

	@Override
	public int addDegree(String type) {
		Degree de = new Degree(type);
		return d.saveDegree(de);
	}

	@Override
	public void updateDegree(int degreeId, String type) {
		Degree de = d.getDegree(degreeId);
		if(de == null) {
			System.out.println("Error in updatedegree");
		}
	}

	@Override
	public Degree getDegree(int degreeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Degree getDegreeByType(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Degree> getAllDegrees() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delDegree(int degreeId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRequiredCourseToDegree(int degreeId, int courseId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeRequiredCourseFromDegree(int degreeId, int courseId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int addStudent(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateStudent(int studentId, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Student getStudent(int studentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Student getStudentByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Student> getAllStudents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delStudent(int studentId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addDegreeToStudent(int studentId, int degreeId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeDegreeFromStudent(int studentId, int degreeId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean studentFulfillsDegreeRequirements(int studentId, int degreeId) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
