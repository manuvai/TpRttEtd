package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private Integer code;
    @Column(name = "LibelleS")
    private String libelle;

    @ManyToMany(mappedBy = "services")
    private Set<Employe> employes;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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
}
