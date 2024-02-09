package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "Service")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CodeS")
    private int code;
    @Column(name = "LibelleS")
    private String libelle;

    @ManyToMany(mappedBy = "services")
    private Set<Employe> employes;

    @MapKeyJoinColumn(name = "CodeDde")
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    private Map<Demande,Valider> validers;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(Set<Employe> employes) {
        this.employes = employes;
    }

    public Map<Demande, Valider> getValiders() {
        return validers;
    }

    public void setValiders(Map<Demande, Valider> validers) {
        this.validers = validers;
    }
}
