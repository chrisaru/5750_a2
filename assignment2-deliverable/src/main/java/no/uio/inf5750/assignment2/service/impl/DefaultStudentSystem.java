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
		if(c.getAllCourses().contains(course)) {
			return 0;
		}
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
		cs.setCourseCode(courseCode);
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
		Course cs = c.getCourse(courseId);
		Set<Student> studs = cs.getAttendants();
		for(Student st : studs) {
			st.getCourses().remove(cs);
			s.saveStudent(st);
		}
		Collection<Degree> degrees = d.getAllDegrees();
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
			System.out.println("ERROR\n");
			return;
		}
		Student st = s.getStudent(studentId);
		if(st == null) {
			System.out.println("ERROR\n");
			return;
		}
		Set<Student> sSet = cs.getAttendants();
		Set<Course> cSet = st.getCourses();
		sSet.add(st);
		cSet.add(cs);
		cs.setAttendants(sSet);
		c.saveCourse(cs);
		st.setCourses(cSet);
		s.saveStudent(st);
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
		Set<Course> cSet = st.getCourses();
		sSet.remove(st);
		cSet.add(cs);
		cs.setAttendants(sSet);
		c.saveCourse(cs);
		st.setCourses(cSet);
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
			System.out.println("Error in updatedegree");
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
			System.out.println("ERROR in deldegree");
			return;
		}
		d.delDegree(de);
	}

	@Override
	public void addRequiredCourseToDegree(int degreeId, int courseId) {
		Degree de = getDegree(degreeId);
		if(de == null) {
			System.out.println("WRONG DEGREEID IN Add_req_to_course");
		}
		Course cs = getCourse(courseId);
		if(cs == null) {
			System.out.println("WRONG courseid IN Add_req_to_course");
		}
		Set<Course> courses = de.getRequiredCourses();
		courses.add(cs);
		de.setRequiredCourses(courses);
		d.saveDegree(de);
	}

	@Override
	public void removeRequiredCourseFromDegree(int degreeId, int courseId) {
		Degree de = getDegree(degreeId);
		if(de == null) {
			System.out.println("WRONG DEGREEID IN rem_req_to_course");
		}
		Course cs = getCourse(courseId);
		if(cs == null) {
			System.out.println("WRONG courseid IN rem_req_to_course");
		}
		Set<Course> courses = de.getRequiredCourses();
		courses.remove(cs);
		de.setRequiredCourses(courses);
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
			System.out.println("Error in updatestud");
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
			System.out.println("ERROR in deldegree");
			return;
		}
		Collection<Course> courses = c.getAllCourses();
		Iterator<Course> it = courses.iterator();
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
			System.out.println("WRONG studid IN Add_degree_to_stud");
		}
		Degree de = d.getDegree(degreeId);
		if(de == null) {
			System.out.println("WRONG degreeid IN Add_degree_to_student");
		}
		
		Set<Degree> degrees = st.getDegrees();
		degrees.add(de);
		st.setDegrees(degrees);
		s.saveStudent(st);
	}

	@Override
	public void removeDegreeFromStudent(int studentId, int degreeId) {
		Student st = getStudent(studentId);
		if(st == null) {
			System.out.println("WRONG studid IN Add_degree_to_stud");
		}
		Degree de = getDegree(degreeId);
		if(de == null) {
			System.out.println("WRONG degreeid IN Add_degree_to_student");
		}
		
		Set<Degree> degrees = st.getDegrees();
		degrees.add(de);
		st.setDegrees(degrees);
		s.saveStudent(st);
	}

	@Override
	public boolean studentFulfillsDegreeRequirements(int studentId, int degreeId) {
		Student st = s.getStudent(studentId);
		if(st == null) {
			System.out.println("ERROR with studentid in fulfill");
		}
		Degree de = d.getDegree(degreeId);
		if(de == null) {
			System.out.println("ERROR with degree id in fulfill");
		}
		return st.getCourses().containsAll(de.getRequiredCourses());
	}
	
}
