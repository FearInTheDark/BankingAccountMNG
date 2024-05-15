package org.example.Data;

import jakarta.persistence.EntityManager;
import org.example.Models.Account;
import org.example.Models.Card;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class DB_Manager {
    static Configuration cfg;
    static SessionFactory sessionFactory;
    static Session session;
    static Query<Account> queryAccount;
    static Query<Card> queryCard;
    public static boolean isHibernateInitialized = false;

    public static void initHibernate() {
        cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        cfg.addAnnotatedClass(Account.class);
        cfg.addAnnotatedClass(Card.class);
        cfg.addAnnotatedClass(org.example.Models.Transaction.class);
        sessionFactory = cfg.buildSessionFactory();
        session = sessionFactory.openSession();
        System.out.println("Hibernate initialized in a separate thread");
        isHibernateInitialized = true;
    }

    public static Account queryAccount(String userName) {
        String hql;
        switch (userName.length()) {
            case 10 -> hql = "FROM users_management WHERE phoneNo = :username";
            case 12 -> hql = "FROM users_management WHERE idCard = :username";
            case 14 -> hql = "FROM users_management WHERE id = :username";
            case 16 -> hql = "FROM users_management WHERE cardNumber = :username";
            default -> {
                System.err.println("Invalid username");
                return null;
            }
        }
        queryAccount = session.createQuery(hql, Account.class);
        queryAccount.setParameter("username", userName);

        if (queryAccount.list().isEmpty()) {
            return null;
        }
        return queryAccount.list().get(0) != null ? queryAccount.list().get(0) : null;
    }

    public static Card queryCard(String userInput) {
        String hql;
        switch (userInput.length()) {
            case 10, 12 -> {
                Account account = queryAccount(userInput);
                assert account != null;
                return queryCard(account.getCardNumber());
            }
            case 14 -> hql = "FROM atm_card_details WHERE bankNo = :user";
            case 16 -> hql = "FROM atm_card_details WHERE id = :user";
            default -> {
                System.err.println("Invalid username");
                return null;
            }
        }
        queryCard = session.createQuery(hql, Card.class);
        queryCard.setParameter("user", userInput);

        return queryCard.list().get(0) != null ? queryCard.list().get(0) : null;
    }

    public static boolean saveAccount(Account account) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(account);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.err.println("Error saving account: " + e.getMessage());
            assert transaction != null;
            transaction.rollback();
            return false;
        }
    }

    public static boolean saveCard(Card card) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(card);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.err.println("Error saving card: " + e.getMessage());
            assert transaction != null;
            transaction.rollback();
            return false;
        }
    }

    public static boolean checkExistAccount(String userName) {
        String hql;
        switch (userName.length()) {
            case 10 -> hql = "FROM users_management WHERE phoneNo = :username";
            case 12 -> hql = "FROM users_management WHERE idCard = :username";
            case 14 -> hql = "FROM users_management WHERE id = :username";
            case 16 -> hql = "FROM users_management WHERE cardNumber = :username";
            default -> {
                System.err.println("Invalid username");
                return false;
            }
        }
        queryAccount = session.createQuery(hql, Account.class);
        queryAccount.setParameter("username", userName);


        return !queryAccount.list().isEmpty();
    }

    public static List<org.example.Models.Transaction> queryTransactions(String userInput) {
        String hql;
        Transaction transaction;
        switch (userInput.length()) {
            case 10, 12, 16 -> {
                Account account = queryAccount(userInput);
                assert account != null;
                return queryTransactions(account.getId());
            }
            case 14 -> hql = "FROM atm_transactions WHERE bankNoFrom = :user OR bankNoTo = :user order by date desc";
            default -> {
                System.err.println("Invalid username");
                return null;
            }
        }
        transaction = session.beginTransaction();
        Query<org.example.Models.Transaction> listTransactions = session.createQuery(hql, org.example.Models.Transaction.class);
        listTransactions.setParameter("user", userInput);
        transaction.commit();
        return listTransactions.list();
    }
}
