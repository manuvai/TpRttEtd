package dao;

import dto.EmployeRecap;
import entities.*;
import entities.keys.ValiderKey;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaQuery;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe de test pour Hibernate.
 */
public class TestHibernate {

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
        log("\n\n\n******Ex 12.*******\n");
        ajouterServices();
        log("\n\n\n******Ex 14.*******\n");
        ajouterEmployesDansService();
        log("\n\n\n******Ex 15.*******\n");
        afficherServicesDunEmploye();
        log("\n\n\n******Ex 17.*******\n");
        ajouterUnAvisDunService();
        log("\n\n\n******Ex 18.*******\n");
        afficherAvisValide();
        log("\n\n\n******Ex 19.*******\n");
        afficherAvisToutesDemandes();
        log("\n\n\n******Ex 20.*******\n");
        afficherAvisToutesDemandes();
        log("\n\n\n******Ex 21.*******\n");
        ajouterTechnicien();
        ajouterAdministratif();
    }

    private static void ajouterAdministratif() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Administratif admin = new Administratif("Jean", "Paul", "Cadre", "Supérieur");

        Transaction transaction = session.beginTransaction();

        session.save(admin);

        transaction.commit();
    }

    private static void ajouterTechnicien() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Technicien technicien = new Technicien("Moli", "Marc", "Commerce", "8");

        Transaction transaction = session.beginTransaction();

        session.save(technicien);

        transaction.commit();
    }

    private static void afficherAvisToutesDemandes() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Transaction transaction = session.beginTransaction();

        List<Demande> demandeList = findAllDemande(session);

        demandeList.forEach(demande -> System.out
                .println("La demande " + demande.getCode() +
                        (demande.isValide()
                                ? " est validée"
                                : " est pas validée")));

        transaction.commit();
    }

    private static List<Demande> findAllDemande(Session session) {
        Query query = session.createQuery("FROM Demande");

        return (List<Demande>) query.list();
    }

    private static void afficherAvisValide() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Transaction transaction = session.beginTransaction();

        Demande demande = recupererDemandeById(1, session);

        log(demande.isValide());

        transaction.commit();
    }

    private static void ajouterUnAvisDunService() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Transaction transaction = session.beginTransaction();

        Service service = recupererServiceById(1, session);

        Demande demande = recupererDemandeById(1, session);
        Valider validation = new Valider();
        validation.setAvis("Favorable");
        validation.setDateAvis(new Date());

        lierServiceAvecDemande(service, demande, validation);

        service.getValiders()
                .put(demande, validation);

        session.update(service);

        transaction.commit();
    }

    private static void lierServiceAvecDemande(Service service, Demande demande, Valider validation) {
        ValiderKey validerKey = new ValiderKey();
        validerKey.setCodeService(service.getCode());
        validerKey.setCodeDemande(demande.getCode());
        validation.setKey(validerKey);
    }

    private static void afficherServicesDunEmploye() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Transaction transaction = session.beginTransaction();

        Employe employe = recupererEmployeById(1, session);

        if (employe != null) {

            List<String> nomServiceList = employe.getServices() == null
                    ? new ArrayList<>()
                    : employe.getServices()
                            .stream()
                            .map(Service::getLibelle)
                            .collect(Collectors.toList());

            String response = "L'employé " + employe.getNom() + " " + employe.getPrenom()
                    + " travaille dans ";

            response += nomServiceList.isEmpty()
                    ? "aucun service"
                    : "le(s) service(s) " + String.join(", ", nomServiceList);

            log(response);
        }
        transaction.commit();
    }

    private static void ajouterEmployesDansService() {
        lierEmployeAvecService(1, 1);
    }

    private static void lierEmployeAvecService(int idEmploye, int idService) {
        Session session = HibernateUtil.getSessionFactory()
                .getCurrentSession();

        Transaction transaction = session.beginTransaction();

        Employe employe = recupererEmployeById(idEmploye, session);
        Service service = recupererServiceById(idService, session);

        lierEmployesDansService(employe, service);

        session.update(employe);
        session.update(service);

        transaction.commit();
    }

    private static Employe recupererEmployeById(int id, Session session) {
        Employe employe = null;

        if (session != null) {
            employe = session.get(Employe.class, id);
        }

        return employe;
    }

    private static Service recupererServiceById(int id, Session session) {
        Service service = null;

        if (session != null) {
            service = session.get(Service.class, id);
        }

        return service;
    }
    private static Demande recupererDemandeById(int id, Session session) {
        Demande demande = null;

        if (session != null) {
            demande = session.get(Demande.class, id);
        }

        return demande;
    }

    private static void lierEmployesDansService(Employe employe, Service service) {
        if (Objects.isNull(employe) || Objects.isNull(service)) {
            return;
        }

        Set<Service> servicesLies = employe.getServices() == null
                ? new HashSet<>()
                : employe.getServices();

        Set<Employe> employesLies = service.getEmployes() == null
                ? new HashSet<>()
                : service.getEmployes();

        employesLies.add(employe);
        servicesLies.add(service);

        employe.setServices(servicesLies);
        service.setEmployes(employesLies);
    }

    private static void ajouterServices() {
        List<String> nomServices = Arrays.asList("Comptabilité",
                "RH",
                "Paie",
                "IT");

        List<Service> services = nomServices.stream()
                .map(TestHibernate::constructServiceFromNom)
                .collect(Collectors.toList());

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Transaction transaction = session.beginTransaction();

        services.forEach(session::save);

        transaction.commit();
    }

    private static Service constructServiceFromNom(String nom) {
        Service service = new Service();
        service.setLibelle(nom);
        return service;
    }

    private static void createEmploye() {
        createEmploye("Dupont", "Eric");
        createEmploye("Dupont", "Gargamelle");
        createEmploye("Alisée", "Gargamelle");
    }

    private static void createEmploye(String nom, String prenom) {
        Employe e = new Employe(nom, prenom); // e est éphémère

        Session session = HibernateUtil.getSessionFactory()
                .getCurrentSession();
        Transaction tc = session.beginTransaction() ;
        session.save(e); // e est persistant

        tc.commit(); // Commit et flush automatique de la session
    }

    private static Employe fetchEmploye() {

        return fetchEmploye(1);
    }

    private static Employe fetchEmploye(int inId) {

        Session session = HibernateUtil.getSessionFactory()
                .getCurrentSession();

        Transaction transaction = Objects.isNull(session.getTransaction()) || !session.getTransaction().isActive()
                ? session.beginTransaction()
                : session.getTransaction();
        Employe employe = session.load(Employe.class, inId);

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

        return session.createQuery(criteriaQuery)
                .getResultList();
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

