package dao;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Chargement de la configuration et création de la SessionFactory.
 * (hibernate.cfg.xml)
 */
public class HibernateUtil {

  private static final SessionFactory SESSION_FACTORY;

  static {
    try {
      Configuration configuration = new Configuration();
      configuration.configure("hibernate.cfg.xml");
      System.out.println("Hibernate Configuration loaded");

      /*
       * Ajout des classes.
       * Pour metier.Employe le fichier ressource hbm.xml
       * attaché est metier/Employe.hbm.xml.old.
       */
      // configuration.addClass(entities.Employe.class);


      /*
       * Entité.
       */

      List<Class<?>> classList = Arrays.asList(
              entities.Employe.class,
              entities.Demande.class,
              entities.Service.class);
      classList.forEach(configuration::addAnnotatedClass);

      ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
        .applySettings(configuration.getProperties())
        .build();
      System.out.println("Hibernate serviceRegistry created");

      SESSION_FACTORY = configuration.buildSessionFactory(serviceRegistry);
    } catch (HibernateException ex) {
      /*----- Exception -----*/
      System.err.println("Initial SessionFactory creation failed.\n" + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  public static SessionFactory getSessionFactory() {
    return SESSION_FACTORY;
  }
}/*----- Fin de la classe HibernateUtil -----*/
