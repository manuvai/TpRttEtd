package dao;

import entities.Demande;
import entities.Employe;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.Date;
import java.util.Objects;

/**
 * Classe de test pour Hibernate.
 */
public class TestHibernate {

  private static void createEmploye() {

    System.out.println(HibernateUtil.getSessionFactory());
    System.out.println("Hibernate !");
    System.out.println(HibernateUtil.getSessionFactory());

    Employe e = new Employe("Dupond", " Attention"); // e est éphémère

    Session session = HibernateUtil.getSessionFactory()
            .getCurrentSession();
    Transaction tc = session.beginTransaction() ;
    session.save(e); // e est persistant

    tc.commit(); // Commit et flush automatique de la session
  }

  private static Employe fetchEmploye() {

    Session session = HibernateUtil.getSessionFactory()
            .getCurrentSession();

    Transaction transaction = Objects.isNull(session.getTransaction()) || !session.getTransaction().isActive()
            ? session.beginTransaction()
            : session.getTransaction();
    Employe employe = session.load(Employe.class, 1);

    System.out.println(employe);
    transaction.commit();

    return employe;
  }

  private static void createDemande() {

    Employe employe = fetchEmploye();
    Session session = HibernateUtil.getSessionFactory()
            .getCurrentSession();

    Transaction transaction = session.beginTransaction();
    Demande demande = new Demande();
    demande.setDateDemande(new Date());
    demande.setDateDebut(new Date());
    demande.setNbJours(3);
    demande.setEmploye(employe);

    session.save(demande);
    transaction.commit();

  }

  /**
   * Programme de test.
   */
  public static void main(String[] args) {
    createEmploye();
    fetchEmploye();
    createDemande();

    Employe emp = fetchEmploye();

    System.out.println(emp);
  }
}/*----- Fin de la classe TestHibernate -----*/
