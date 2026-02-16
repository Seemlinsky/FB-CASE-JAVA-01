package nl.adainf.energiebedrijf_case01.model;

import java.time.LocalDate;

public abstract class Tarief {
    protected int id;
    protected int klantId;
    protected double prijs;
    protected LocalDate datumVanaf;
    protected LocalDate datumTot;

    public Tarief(int id, int klantId, double prijs, LocalDate datumVanaf, LocalDate datumTot) {
        this.id = id;
        this.klantId = klantId;
        this.prijs = prijs;
        this.datumVanaf = datumVanaf;
        this.datumTot = datumTot;
    }

    public int getId() { return id; }
    public int getKlantId() { return klantId; }
    public double getPrijs() { return prijs; }
    public LocalDate getDatumVanaf() { return datumVanaf; }
    public LocalDate getDatumTot() { return datumTot; }

    public abstract String getType();
}
