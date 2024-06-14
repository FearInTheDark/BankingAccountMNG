package Data;

import Models.ModelAccount;
import Models.ModelCard;
import Models.ModelTransaction;
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
    static Query<ModelAccount> queryAccount;
    static Query<ModelCard> queryCard;
    public static boolean isHibernateInitialized = false;

    public static void initHibernate() {
        cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        cfg.addAnnotatedClass(ModelAccount.class);
        cfg.addAnnotatedClass(ModelCard.class);
        cfg.addAnnotatedClass(ModelTransaction.class);
        sessionFactory = cfg.buildSessionFactory();
        session = sessionFactory.openSession();
        System.out.println("Hibernate initialized in a separate thread");
        isHibernateInitialized = true;
    }

    public static boolean checkExistAccount(String userName) {
        String hql;
        switch (userName.length()) {
            case 10 -> hql = "FROM users_management WHERE phoneNo = :username";
            case 12 -> hql = "FROM users_management WHERE idCard = :username";
            case 14 -> hql = "FROM users_management WHERE id = :username";
            case 16 -> hql = "FROM users_management WHERE cardNumber = :username";
            default -> {
                return false;
            }
        }
        queryAccount = session.createQuery(hql, ModelAccount.class);
        queryAccount.setParameter("username", userName);


        return !queryAccount.list().isEmpty();
    }

    public static ModelAccount queryAccount(String userName) {
        String hql;
        switch (userName.length()) {
            case 10 -> hql = "FROM users_management WHERE phoneNo = :username";
            case 12 -> hql = "FROM users_management WHERE idCard = :username";
            case 14 -> hql = "FROM users_management WHERE id = :username";
            case 16 -> hql = "FROM users_management WHERE cardNumber = :username";
            default -> {
                return null;
            }
        }
        queryAccount = session.createQuery(hql, ModelAccount.class);
        queryAccount.setParameter("username", userName);

        if (queryAccount.list().isEmpty()) {
            return null;
        }
        return queryAccount.list().get(0) != null ? queryAccount.list().get(0) : null;
    }

    public static List<ModelAccount> queryAccounts() {
        Transaction transaction = null;
        try {
            String hql = "From users_management";
            transaction = session.beginTransaction();
            Query<ModelAccount> queryAccounts = session.createQuery(hql, ModelAccount.class);
            transaction.commit();
            return queryAccounts.list();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static List<ModelAccount> searchAccounts(String search) {
        Transaction transaction = null;
        try {
            String hql = "From users_management where id like :search or phoneNo like :search or fullName like :search or idCard like :search";
            transaction = session.beginTransaction();
            Query<ModelAccount> queryAccounts = session.createQuery(hql, ModelAccount.class);
            queryAccounts.setParameter("search", "%" + search + "%");
            transaction.commit();
            return queryAccounts.list();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static boolean saveAccount(ModelAccount modelAccount) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(modelAccount);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.err.println("Error saving account: " + e.getMessage());
            assert transaction != null;
            transaction.rollback();
            return false;
        }
    }

    public static void updateAccount(ModelAccount modelAccount) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(modelAccount);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void deleteAccount(ModelAccount modelAccount) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(modelAccount);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static ModelCard queryCard(String userInput) {
        String hql;
        switch (userInput.length()) {
            case 10, 12 -> {
                ModelAccount modelAccount = queryAccount(userInput);
                assert modelAccount != null;
                return queryCard(modelAccount.getCardNumber());
            }
            case 14 -> hql = "FROM atm_card_details WHERE bankNo = :user";
            case 16 -> hql = "FROM atm_card_details WHERE id = :user";
            default -> {
                return null;
            }
        }
        queryCard = session.createQuery(hql, ModelCard.class);
        queryCard.setParameter("user", userInput);

        return queryCard.list().get(0) != null ? queryCard.list().get(0) : null;
    }

    public static boolean saveCard(ModelCard modelCard) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(modelCard);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.err.println("Error saving card: " + e.getMessage());
            assert transaction != null;
            transaction.rollback();
            return false;
        }
    }

    public static void updateCard(ModelCard modelCard) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(modelCard);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static List<ModelTransaction> queryTransactions(String userInput) {
        String hql;
        Transaction transaction;
        switch (userInput.length()) {
            case 10, 12, 16 -> {
                ModelAccount modelAccount = queryAccount(userInput);
                assert modelAccount != null;
                return queryTransactions(modelAccount.getId());
            }
            case 14 -> hql = "FROM atm_transactions WHERE bankNoFrom = :user OR bankNoTo = :user order by date desc";
            default -> {
                return null;
            }
        }
        transaction = session.beginTransaction();
        Query<ModelTransaction> listTransactions = session.createQuery(hql, ModelTransaction.class);
        listTransactions.setParameter("user", userInput);
        transaction.commit();
        return listTransactions.list();
    }

    public static void saveTransaction(ModelTransaction modelTransaction) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(modelTransaction);
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Error saving transaction: " + e.getMessage());
            assert transaction != null;
            transaction.rollback();
        }
    }
}
