package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeRecap {
    private String nom;
    private long nbDemandes;

    public EmployeRecap() {

    }
    public EmployeRecap(String inNom, long inNbDemandes) {
        nom = inNom;
        nbDemandes = inNbDemandes;
    }

    @Override
    public String toString() {
        return "EmployeRecap{" +
                "nom='" + nom + '\'' +
                ", nbDemandes=" + nbDemandes +
                '}';
    }
}
