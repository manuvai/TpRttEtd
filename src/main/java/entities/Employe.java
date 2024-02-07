package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Employe")
public class Employe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codeE;

    @Column(name = "NomE")
    private String nom;

    @Column(name = "prenomE")
    private String prenom;

    public Employe() {
    }

    public Employe(String inNom, String inPrenom) {
        setNom(inNom);
        setPrenom(inPrenom);
    }

    public Integer getCodeE() {
        return codeE;
    }

    public void setCodeE(Integer codeE) {
        this.codeE = codeE;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employe)) return false;
        Employe employe = (Employe) o;
        return getCodeE() == employe.getCodeE()
                && Objects.equals(getNom(), employe.getNom())
                && Objects.equals(getPrenom(), employe.getPrenom());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodeE(), getNom(), getPrenom());
    }

    @Override
    public String toString() {
        return "Employe{" +
                "codeE=" + codeE +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }
}
