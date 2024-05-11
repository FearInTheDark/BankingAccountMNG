package org.example.Data;

import jakarta.persistence.EntityManager;
import org.example.Models.Account;
import org.example.Models.Card;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class DB_Manager {
    Configuration cfg;
    SessionFactory sessionFactory;
    static Session session;
    static Query<Account> queryAccount;
    static Query<Card> queryCard;
    EntityManager entityManager;
    public DB_Manager() {
        cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        sessionFactory = cfg.buildSessionFactory();
        session = sessionFactory.openSession();
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

    public void close() {
        session.close();
        sessionFactory.close();
    }
}
