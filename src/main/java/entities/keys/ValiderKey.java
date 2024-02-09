package entities.keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ValiderKey implements Serializable {
    @Column(name = "CodeDde")
    private int codeDemande;
    @Column(name = "CodeServ")
    private int codeService;

    public ValiderKey() {}

    public ValiderKey(int codeDemande, int codeService) {
        setCodeDemande(codeDemande);
        setCodeService(codeService);
    }

    public int getCodeDemande() {
        return codeDemande;
    }

    public void setCodeDemande(int codeDemande) {
        this.codeDemande = codeDemande;
    }

    public int getCodeService() {
        return codeService;
    }

    public void setCodeService(int codeService) {
        this.codeService = codeService;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValiderKey)) return false;
        ValiderKey that = (ValiderKey) o;
        return getCodeDemande() == that.getCodeDemande()
                && getCodeService() == that.getCodeService();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodeDemande(), getCodeService());
    }
}
