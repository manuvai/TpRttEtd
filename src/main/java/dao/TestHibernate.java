package dao;

import entities.Employe;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

/**
 * Classe de test pour Hibernate.
 */
public class TestHibernate {

  /**
   * Programme de test.
   */
  public static void main(String[] args) {
    System.out.println(HibernateUtil.getSessionFactory());
    System.out.println("Hibernate !");
    System.out.println(HibernateUtil.getSessionFactory());

    Employe e = new Employe("Dupond", " Attention"); // e est éphémère

    Session ses = HibernateUtil.getSessionFactory().getCurrentSession();
    Transaction tc = ses.beginTransaction() ;
    ses.save(e); // e est persistant

    Employe employe = ses.get(Employe.class, 1);

    System.out.println(employe);

    tc.commit(); // Commit et flush automatique de la session
  }
}/*----- Fin de la classe TestHibernate -----*/
