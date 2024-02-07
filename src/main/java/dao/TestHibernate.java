package dao;

import entities.Demande;
import entities.Employe;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

  private static List<Employe> findAllEmployes() {
    Session session = HibernateUtil.getSessionFactory()
            .getCurrentSession();

    CriteriaQuery<Employe> criteriaQuery = session.getCriteriaBuilder()
            .createQuery(Employe.class);
    criteriaQuery.from(Employe.class);

    List<Employe> employeList = session.createQuery(criteriaQuery)
            .getResultList();

    return employeList;
  }

  private static void afficherDemandesEmploye() {

    Session session = HibernateUtil.getSessionFactory()
            .getCurrentSession();

    Transaction transaction = session.beginTransaction();
    List<Employe> employees = findAllEmployes();


    employees.forEach(employe -> System.out.println(employe.getDemandes()));
    transaction.commit();

  }

  private static void ajouterDemandeEmploye() {
    Employe employee = fetchEmploye();
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();

    Transaction transaction = session.beginTransaction();

    Demande demande = new Demande();
    demande.setDateDemande(new Date());
    demande.setNbJours(5);
    demande.setDateDebut(new Date());
    demande.setEmploye(employee);

    employee.getDemandes()
            .add(demande);

    session.update(employee);
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
    afficherDemandesEmploye();
    ajouterDemandeEmploye();
    fetchEmploye();
  }
}/*----- Fin de la classe TestHibernate -----*/
