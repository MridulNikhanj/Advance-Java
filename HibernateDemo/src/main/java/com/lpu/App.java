package com.lpu;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.lpu.Entity.Student;

public class App {

	public static void main(String[] args) {

		// HIBERNATE CONFIGURATION
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

		Scanner sc = new Scanner(System.in);

		int choice;

		do {

			System.out.println("\n====== STUDENT MANAGEMENT MENU ======");
			System.out.println("1. Create Student");
			System.out.println("2. Get Student By ID");
			System.out.println("3. Get All Students");
			System.out.println("4. Update Student");
			System.out.println("5. Delete Student");
			System.out.println("6. Exit");
			System.out.print("Enter choice: ");

			choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {

			case 1:
				System.out.print("Enter Name: ");
				String name = sc.nextLine();

				System.out.print("Enter Age: ");
				int age = sc.nextInt();
				sc.nextLine();

				createStudent(factory, name, age);
				break;

			case 2:
				System.out.print("Enter Student ID: ");
				int id = sc.nextInt();
				sc.nextLine();

				getStudentById(factory, id);
				break;

			case 3:
				getAllStudents(factory);
				break;

			case 4:
				System.out.print("Enter ID to Update: ");
				int updateId = sc.nextInt();
				sc.nextLine();

				System.out.println("What do you want to update?");
				System.out.println("1. Name");
				System.out.println("2. Age");
				System.out.println("3. Both");
				System.out.print("Enter choice: ");

				int updateChoice = sc.nextInt();
				sc.nextLine();

				String newName = null;
				Integer newAge = null;

				if (updateChoice == 1 || updateChoice == 3) {
					System.out.print("Enter New Name: ");
					newName = sc.nextLine();
				}

				if (updateChoice == 2 || updateChoice == 3) {
					System.out.print("Enter New Age: ");
					newAge = sc.nextInt();
					sc.nextLine();
				}

				updateStudent(factory, updateId, newName, newAge);
				break;

			case 5:
				System.out.print("Enter ID to Delete: ");
				int deleteId = sc.nextInt();
				sc.nextLine();

				deleteStudent(factory, deleteId);
				break;

			case 6:
				System.out.println("Exiting Program");
				break;

			default:
				System.out.println("Invalid Choice! Try Again.");
			}

		} while (choice != 6);

		factory.close();
		sc.close();
	}

	// CRUD OPERATIONS (METHODS)

	// CREATE
	public static void createStudent(SessionFactory factory, String name, int age) {

		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();

		try {
			Student student = new Student(name, age);
			session.persist(student);
			tx.commit();

			System.out.println("Student Created: " + student);

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// READ BY ID
	public static void getStudentById(SessionFactory factory, int id) {

		Session session = factory.openSession();

		try {
			Student student = session.get(Student.class, id);

			if (student != null) {
				System.out.println("Student Found: " + student);
			} else {
				System.out.println("No Student Found With ID: " + id);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// READ ALL
	public static void getAllStudents(SessionFactory factory) {

		Session session = factory.openSession();

		try {
			List<Student> students = session.createQuery("from Student", Student.class).getResultList();

			System.out.println("Student List:");

			for (Student s : students) {
				System.out.println(s);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// UPDATE
	public static void updateStudent(SessionFactory factory, int id, String name, Integer age) {

		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();

		try {
			Student student = session.get(Student.class, id);

			if (student != null) {

				if (name != null) {
					student.setName(name);
				}

				if (age != null) {
					student.setAge(age);
				}

				tx.commit();
				System.out.println("Student Updated Successfully: " + student);

			} else {
				System.out.println("Student Not Found");
			}

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// DELETE
	public static void deleteStudent(SessionFactory factory, int id) {

		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();

		try {
			Student student = session.get(Student.class, id);

			if (student != null) {
				session.remove(student);
				tx.commit();
				System.out.println("Student Deleted Successfully");
			} else {
				System.out.println("Student Not Found");
			}

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}
