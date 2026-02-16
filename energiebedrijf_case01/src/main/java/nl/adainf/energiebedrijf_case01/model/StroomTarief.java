package nl.adainf.energiebedrijf_case01.model;

import java.time.LocalDate;

public class StroomTarief extends Tarief {
    public StroomTarief(int id, int klantId, double prijs, LocalDate vanaf, LocalDate tot) {
        super(id, klantId, prijs, vanaf, tot);
    }
    @Override public String getType() { return "Stroom"; }
}
