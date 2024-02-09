package entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Technicien extends Employe {

    @Column(name = "specialiteE")
    private String specialite;

    @Column(name = "niveauE")
    private String niveau;

    public Technicien() {}

    public Technicien(String nom, String prenom, String specialite, String niveau) {
        super(nom, prenom);
        setSpecialite(specialite);
        setNiveau(niveau);
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Technicien)) return false;
        if (!super.equals(o)) return false;
        Technicien that = (Technicien) o;
        return Objects.equals(getSpecialite(), that.getSpecialite())
                && Objects.equals(getNiveau(), that.getNiveau());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSpecialite(), getNiveau());
    }
}
