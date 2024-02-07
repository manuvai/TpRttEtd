package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Demande")
public class Demande implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codeD")
    private long code;

    @Column(name = "dateD")
    private Date dateDemande;

    private float nbJours;

    private Date dateDebut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CodeEmp", nullable = false)
    private Employe employe;

    public Demande() {

    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public Date getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    public float getNbJours() {
        return nbJours;
    }

    public void setNbJours(float nbJours) {
        this.nbJours = nbJours;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Demande)) return false;
        Demande demande = (Demande) o;
        return getCode() == demande.getCode()
                && Float.compare(demande.getNbJours(), getNbJours()) == 0
                && Objects.equals(getDateDemande(), demande.getDateDemande())
                && Objects.equals(getDateDebut(), demande.getDateDebut());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getDateDemande(), getNbJours(), getDateDebut());
    }

    @Override
    public String toString() {
        return "Demande{" +
                "code=" + code +
                ", dateDemande=" + dateDemande +
                ", nbJours=" + nbJours +
                ", dateDebut=" + dateDebut +
                '}';
    }
}
