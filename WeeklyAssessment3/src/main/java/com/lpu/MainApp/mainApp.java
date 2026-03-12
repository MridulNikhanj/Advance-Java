package com.lpu.MainApp;

import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.lpu.Entity.*;

public class mainApp {

    public static void main(String[] args) {

        SessionFactory factory =
                new Configuration().configure().buildSessionFactory();

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== UNIVERSITY MENU =====");
            System.out.println("1. Add Department with Students");
            System.out.println("2. Add Courses");
            System.out.println("3. Enroll Student in Course");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();

            try {

                switch (choice) {

                // ===========================
                // 1️⃣ CREATE DEPARTMENT + STUDENTS + IDCARD
                // ===========================
                case 1:

                    System.out.print("Enter Department Name: ");
                    String deptName = sc.nextLine();

                    Department dept = new Department();
                    dept.setName(deptName);

                    System.out.print("How many students? ");
                    int n = sc.nextInt();
                    sc.nextLine();

                    for (int i = 0; i < n; i++) {

                        System.out.print("Enter Student Name: ");
                        String sname = sc.nextLine();

                        Student s = new Student();
                        s.setName(sname);
                        s.setDepartment(dept);

                        dept.getStudents().add(s);

                        System.out.print("Enter ID Card Number: ");
                        String card = sc.nextLine();

                        IDCard id = new IDCard();
                        id.setCardNumber(card);

                        s.setIdCard(id);
                        id.setStudent(s);

                        session.persist(s);
                    }

                    session.persist(dept);
                    System.out.println("Department + Students Saved!");
                    break;

                // ===========================
                // 2️⃣ ADD COURSES
                // ===========================
                case 2:

                    System.out.print("How many courses to add? ");
                    int ccount = sc.nextInt();
                    sc.nextLine();

                    for (int i = 0; i < ccount; i++) {

                        System.out.print("Enter Course Name: ");
                        String cname = sc.nextLine();

                        Course c = new Course();
                        c.setCourseName(cname);

                        session.persist(c);
                    }

                    System.out.println("Courses Added!");
                    break;

                // ===========================
                // 3️⃣ ENROLL STUDENT IN COURSE
                // ===========================
                case 3:

                    System.out.print("Enter Student ID: ");
                    int sid = sc.nextInt();

                    System.out.print("Enter Course ID: ");
                    int cid = sc.nextInt();

                    Student student = session.get(Student.class, sid);
                    Course course = session.get(Course.class, cid);

                    if (student == null || course == null) {
                        System.out.println("Invalid Student or Course ID!");
                    } else {

                        // owning side = Student
                        student.getCourses().add(course);

                        // inverse side update (good practice)
                        course.getStudents().add(student);

                        session.persist(student);
                        System.out.println("Student enrolled in course!");
                    }

                    break;

                // ===========================
                // 4️⃣ EXIT PROGRAM
                // ===========================
                case 4:
                    tx.commit();
                    session.close();
                    factory.close();
                    System.out.println("Program Closed.");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice!");

                }

                tx.commit();

            } catch (Exception e) {

                tx.rollback();
                e.printStackTrace();
            }

            session.close();
        }
    }
}