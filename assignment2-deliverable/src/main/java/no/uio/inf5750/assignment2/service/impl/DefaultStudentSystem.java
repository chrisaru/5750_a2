package no.uio.inf5750.assignment2.service.impl;

import java.util.Collection;
import java.util.Iterator;
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
    StudentDAO s;
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
		cs.setName(name);
		cs.setCourseCode(courseCode);
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
		Course cs = c.getCourse(courseId);
		Set<Student> studs = cs.getAttendants();
		//need to remove the course from all students that has it
		for(Student st : studs) {
			st.getCourses().remove(cs);
			s.saveStudent(st);
		}
		Collection<Degree> degrees = d.getAllDegrees();
		//need to remove the course from degrees that require it
		for(Degree de : degrees) {
			if(de.getRequiredCourses().contains(cs)) {
				de.getRequiredCourses().remove(cs);
				d.saveDegree(de);
			}
		}
		c.delCourse(c.getCourse(courseId));
	}

	@Override
	public void addAttendantToCourse(int courseId, int studentId) {
		Course cs = c.getCourse(courseId);
		if(cs == null) {
			logger.error("Invalid courseId");
			return;
		}
		Student st = s.getStudent(studentId);
		if(st == null) {
			logger.error("Invalid studentID");
			return;
		}
		Set<Student> sSet = cs.getAttendants();
		Set<Course> cSet = st.getCourses();
		sSet.add(st);
		cSet.add(cs);
		c.saveCourse(cs);
		s.saveStudent(st);
	}

	@Override
	public void removeAttendantFromCourse(int courseId, int studentId) {
		Course cs = c.getCourse(courseId);
		if(cs == null) {
			logger.error("invalid courseid");
			return;
		}
		Student st = s.getStudent(studentId);
		if(st == null) {
			logger.error("invalid studentid");
			return;
		}
		Set<Student> sSet = cs.getAttendants();
		Set<Course> cSet = st.getCourses();
		sSet.remove(st);
		//also has to remove course from student
		cSet.remove(cs);
		c.saveCourse(cs);
		s.saveStudent(st);
	}

	@Override
	public int addDegree(String type) {
		Degree de = new Degree(type);
		return d.saveDegree(de);
	}

	@Override
	public void updateDegree(int degreeId, String type) {
		Degree de = getDegree(degreeId);
		if(de == null) {
			logger.error("invalid degreeid");
		}
		de.setType(type);
		d.saveDegree(de);
	}

	@Override
	public Degree getDegree(int degreeId) {
		return d.getDegree(degreeId);
	}

	@Override
	public Degree getDegreeByType(String type) {
		return d.getDegreeByType(type);
	}

	@Override
	public Collection<Degree> getAllDegrees() {
		return d.getAllDegrees();
	}

	@Override
	public void delDegree(int degreeId) {
		Degree de = d.getDegree(degreeId);
		if(de == null) {
			logger.error("Invalid degreeid");
			return;
		}
		d.delDegree(de);
	}

	@Override
	public void addRequiredCourseToDegree(int degreeId, int courseId) {
		Degree de = getDegree(degreeId);
		if(de == null) {
			logger.error("invalid degreeid");
		}
		Course cs = getCourse(courseId);
		if(cs == null) {
			logger.error("invalid courseid");
		}
		Set<Course> courses = de.getRequiredCourses();
		courses.add(cs);
		d.saveDegree(de);
	}

	@Override
	public void removeRequiredCourseFromDegree(int degreeId, int courseId) {
		Degree de = getDegree(degreeId);
		if(de == null) {
			logger.error("invalid degreeid");
		}
		Course cs = getCourse(courseId);
		if(cs == null) {
			logger.error("invalid courseid");
		}
		Set<Course> courses = de.getRequiredCourses();
		courses.remove(cs);
		d.saveDegree(de);
	}

	@Override
	public int addStudent(String name) {
		Student stud = new Student(name);
		return s.saveStudent(stud);
	}

	@Override
	public void updateStudent(int studentId, String name) {
		Student st = s.getStudent(studentId);
		if(st == null) {
			logger.error("invalid studentid");
		}
		st.setName(name);
		s.saveStudent(st);
	}

	@Override
	public Student getStudent(int studentId) {
		return s.getStudent(studentId);
	}

	@Override
	public Student getStudentByName(String name) {
		return s.getStudentByName(name);
	}

	@Override
	public Collection<Student> getAllStudents() {
		return s.getAllStudents();
	}

	@Override
	public void delStudent(int studentId) {
		Student st = s.getStudent(studentId);
		if(st == null) {
			logger.error("invalid studentid");
			return;
		}
		Collection<Course> courses = c.getAllCourses();
		Iterator<Course> it = courses.iterator();
		//need to remove the student from all the courses it's enrolled in
		while(it.hasNext()) {
			Course cs = it.next();
			if(cs.getAttendants().contains(st)) {
				cs.getAttendants().remove(st);
				c.saveCourse(cs);
			}
		}
		s.delStudent(st);
	}

	@Override
	public void addDegreeToStudent(int studentId, int degreeId) {
		Student st = s.getStudent(studentId);
		if(st == null) {
			logger.error("invalid studentid");
		}
		Degree de = d.getDegree(degreeId);
		if(de == null) {
			logger.error("invalid degreeid");
		}
		
		Set<Degree> degrees = st.getDegrees();
		degrees.add(de);
		s.saveStudent(st);
	}

	@Override
	public void removeDegreeFromStudent(int studentId, int degreeId) {
		Student st = getStudent(studentId);
		if(st == null) {
			logger.error("invalid studentid");
		}
		Degree de = getDegree(degreeId);
		if(de == null) {
			logger.error("invalid degreeid");
		}
		
		Set<Degree> degrees = st.getDegrees();
		degrees.remove(de);
		s.saveStudent(st);
	}

	@Override
	public boolean studentFulfillsDegreeRequirements(int studentId, int degreeId) {
		Student st = s.getStudent(studentId);
		if(st == null) {
			logger.error("invalid studentid");
		}
		Degree de = d.getDegree(degreeId);
		if(de == null) {
			logger.error("invalid degreeid");
		}
		return st.getCourses().containsAll(de.getRequiredCourses());
	}
	
}
