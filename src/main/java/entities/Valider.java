package entities;

import entities.keys.ValiderKey;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Valider")
public class Valider {
    @EmbeddedId
    private ValiderKey key;

    private String avis;

    private Date dateAvis;
    @ManyToOne
    @JoinColumn(name = "CodeServ", insertable = false, updatable = false)
    private Service serviceValidateur;

    public Valider() {}

    public Valider(ValiderKey key, String avis, Date dateAvis) {
        setKey(key);
        setAvis(avis);
        setDateAvis(dateAvis);
    }

    public ValiderKey getKey() {
        return key;
    }

    public void setKey(ValiderKey key) {
        this.key = key;
    }

    public String getAvis() {
        return avis;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }

    public Date getDateAvis() {
        return dateAvis;
    }

    public void setDateAvis(Date dateAvis) {
        this.dateAvis = dateAvis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Valider)) return false;
        Valider valider = (Valider) o;
        return Objects.equals(getKey(), valider.getKey())
                && Objects.equals(getAvis(), valider.getAvis())
                && Objects.equals(getDateAvis(), valider.getDateAvis());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getAvis(), getDateAvis());
    }
}
