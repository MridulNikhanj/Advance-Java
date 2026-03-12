package com.lpu;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.lpu.Entity.MenuItem;

public class App {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

		Scanner sc = new Scanner(System.in);
		int choice;

		do {
			System.out.println("RESTAURANT MENU SYSTEM");
			System.out.println("1. Add");
			System.out.println("2. View");
			System.out.println("3. Update");
			System.out.println("4. Delete");
			System.out.println("5. Exit");
			System.out.print("Enter choice: ");

			choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {

			case 1:
				System.out.print("Enter Name: ");
				String name = sc.nextLine();

				System.out.print("Enter Price: ");
				double price = sc.nextDouble();
				sc.nextLine();

				System.out.print("Enter Category: ");
				String category = sc.nextLine();

				System.out.print("Available (true/false): ");
				boolean available = sc.nextBoolean();
				sc.nextLine();

				addMenuItem(factory, name, price, category, available);
				break;

			case 2:
				viewAllItems(factory);
				break;

			case 3:
				System.out.print("Enter ID to update price: ");
				int id = sc.nextInt();

				System.out.print("Enter New Price: ");
				double newPrice = sc.nextDouble();
				sc.nextLine();

				updatePrice(factory, id, newPrice);
				break;

			case 4:
				System.out.print("Enter ID to delete: ");
				int deleteId = sc.nextInt();
				sc.nextLine();

				deleteItem(factory, deleteId);
				break;

			case 5:
				System.out.println("Exiting...");
				break;

			default:
				System.out.println("Invalid choice!");
			}

		} while (choice != 5);

		factory.close();
		sc.close();
	}

	// ADD
	public static void addMenuItem(SessionFactory factory, String name, double price, String category,
			boolean available) {

		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();

		try {
			MenuItem item = new MenuItem(name, price, category, available);
			session.persist(item);
			tx.commit();
			System.out.println("Item Added Successfully");

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// VIEW ALL
	public static void viewAllItems(SessionFactory factory) {

		Session session = factory.openSession();

		try {
			List<MenuItem> items = session.createQuery("from MenuItem", MenuItem.class).getResultList();

			System.out.println("\nMenu Items:");
			for (MenuItem m : items) {
				System.out.println(m);
			}

		} finally {
			session.close();
		}
	}
	

	// UPDATE 
	public static void updatePrice(SessionFactory factory, int id, double price) {

		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();

		try {
			MenuItem item = session.get(MenuItem.class, id);

			if (item != null) {
				item.setPrice(price);
				tx.commit();
				System.out.println("Price Updated: " + item);
			} else {
				System.out.println("Item Not Found");
			}

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// DELETE
	public static void deleteItem(SessionFactory factory, int id) {

		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();

		try {
			MenuItem item = session.get(MenuItem.class, id);

			if (item != null) {
				session.remove(item);
				tx.commit();
				System.out.println("Item Deleted");
			} else {
				System.out.println("Item Not Found");
			}

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
}
