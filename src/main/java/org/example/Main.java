package org.example;

import org.example.Models.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        SessionFactory factory = configuration.buildSessionFactory();

        Session session = factory.openSession();

        String hql = "From atm_transactions";
        Query query = session.createQuery(hql);

        List<Transaction> transactions = query.list();
        if (transactions.isEmpty()) System.out.println("There is no transaction");
        else for (Transaction transaction : transactions) {
            System.out.println("Letter: " + transaction.getLetter());
        }
        session.close();

    }
}