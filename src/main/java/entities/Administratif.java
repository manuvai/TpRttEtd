package entities;

import javax.persistence.*;

@Entity
public class Administratif extends Employe {

    @Column(name = "fonctionE")
    private String fonction;

    @Column(name = "gradeE")
    private String grade;

    public Administratif() {}

    public Administratif(String nom, String prenom, String fonction, String grade) {
        super(nom, prenom);
        setFonction(fonction);
        setGrade(grade);
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
