package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "Demande")
public class Demande implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codeD")
    private int code;

    @Column(name = "dateD")
    private Date dateDemande;

    private float nbJours;

    private Date dateDebut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CodeEmp")
    private Employe employe;

    @MapKeyJoinColumn(name = "CodeServ")
    @OneToMany(mappedBy = "demande", cascade = CascadeType.ALL)
    private Map<Service,Valider> validers;

    public Demande() {

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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

    public boolean isValide() {
        Set<Service> services = getEmploye() == null || employe.getServices() == null
                ? new HashSet<>()
                : employe.getServices();

        List<Valider> validations = services.stream()
                .map(service -> service.getValiders().get(this))
                .collect(Collectors.toList());

        return validations.stream()
                .allMatch(valider -> valider != null && valider.isValide());
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
