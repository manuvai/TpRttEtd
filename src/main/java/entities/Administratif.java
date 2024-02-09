package entities;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Administratif)) return false;
        if (!super.equals(o)) return false;
        Administratif that = (Administratif) o;
        return Objects.equals(getFonction(), that.getFonction())
                && Objects.equals(getGrade(), that.getGrade());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFonction(), getGrade());
    }
}
