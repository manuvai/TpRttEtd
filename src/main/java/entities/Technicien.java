package entities;

import javax.persistence.*;

@Entity
public class Technicien extends Employe {

    @Column(name = "specialiteE")
    private String specialite;

    @Column(name = "niveauE")
    private String niveau;

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
}
