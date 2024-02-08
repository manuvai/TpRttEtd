package dao;

import dto.EmployeRecap;
import entities.Demande;
import entities.Employe;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import org.hibernate.query.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.*;

/**
 * Classe de test pour Hibernate.
 */
public class TestHibernate {

  private static void createEmploye() {

    System.out.println(HibernateUtil.getSessionFactory());
    System.out.println("Hibernate !");
    System.out.println(HibernateUtil.getSessionFactory());

    Employe e = new Employe("Dupond", "Robert"); // e est éphémère

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
    log("\n\n\n******Ex 1*******\n");
    createEmploye();
    log("\n\n\n******Ex 2*******\n");
    fetchEmploye();
    log("\n\n\n******Ex 3*******\n");
    createDemande();

    Employe emp = fetchEmploye();

    System.out.println(emp);

    log("\n\n\n******Ex 4*******\n");
    afficherDemandesEmploye();
    log("\n\n\n******Ex 5*******\n");
    ajouterDemandeEmploye();
    log("\n\n\n******Ex 6*******\n");
    fetchEmploye();

    log("\n\n\n******Ex 11.a*******\n");
    afficherNomEtNombreDemande();
    log("\n\n\n******Ex 11.b*******\n");
    afficherNomEtNombreDemandeClasse();
    log("\n\n\n******Ex 11.c*******\n");
    afficherNomEtNombreDemandeClasse(0);
    log("\n\n\n******Ex 11.d*******\n");
    afficherListeEmployes();
    log("\n\n\n******Ex 11.e*******\n");
    afficherDemandesPlusDe6Jours();
    log("\n\n\n******Ex 11.f*******\n");
    afficherNomEtPrenomHavingNom("on");
  }

  private static void afficherNomEtPrenomHavingNom(String likeParam) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    Transaction transaction = session.beginTransaction();

    Query query = session.createQuery("FROM Employe e WHERE e.nom LIKE :likeParam");
    query.setParameter("likeParam", "%" + likeParam + "%");

    List<Employe> results = (List<Employe>) query.list();

    results.forEach(e -> System.out.println(e.getNom() + " " + e.getPrenom()));

    transaction.commit();
  }

  private static void afficherDemandesPlusDe6Jours() {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    Transaction transaction = session.beginTransaction();

    Query query = session.createQuery("FROM Demande WHERE nbJours > 6");

    List<Demande> results = (List<Demande>) query.list();

    results.forEach(System.out::println);

    transaction.commit();

  }

  private static void log(Object object) {
    System.out.println(object);
  }

  private static void afficherListeEmployes() {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    Transaction transaction = session.beginTransaction();

    Query query = session.createQuery("FROM Employe");

    List results = query.list();

    results.forEach(System.out::println);

    transaction.commit();

  }

  private static void afficherNomEtNombreDemande() {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    Transaction transaction = session.beginTransaction();

    String queryString  = "SELECT e.nomE, COUNT(*) " +
            "FROM Employe e, Demande d " +
            "WHERE d.CodeEmp = e.CodeE " +
            "GROUP BY e.CodeE, e.nomE " +
            "HAVING COUNT(*) > 1";

    Query query = session.createSQLQuery(queryString);

    List results = query.list();

    results.forEach(System.out::println);

    transaction.commit();
  }

  private static void afficherNomEtNombreDemandeClasse() {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    Transaction transaction = session.beginTransaction();

    String queryString  = "SELECT new dto.EmployeRecap(e.nom, COUNT(*)) " +
            "FROM Employe e, Demande d " +
            "WHERE d.employe.codeE = e.codeE " +
            "GROUP BY e.codeE, e.nom " +
            "HAVING COUNT(*) > 1";

    Query query = session.createQuery(queryString);

    List<EmployeRecap> results = (List<EmployeRecap>) query.list();

    results.forEach(System.out::println);

    transaction.commit();
  }

  private static void afficherNomEtNombreDemandeClasse(Integer demandeCount) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    Transaction transaction = session.beginTransaction();

    String queryString  = "SELECT new dto.EmployeRecap(e.nom, COUNT(*)) " +
            "FROM Employe e, Demande d " +
            "WHERE d.employe.codeE = e.codeE " +
            "GROUP BY e.codeE, e.nom " +
            "HAVING COUNT(*) > :demandeCount";

    Query query = session.createQuery(queryString);
    query.setParameter("demandeCount", demandeCount);

    List<EmployeRecap> results = (List<EmployeRecap>) query.list();

    results.forEach(System.out::println);

    transaction.commit();
  }
}

