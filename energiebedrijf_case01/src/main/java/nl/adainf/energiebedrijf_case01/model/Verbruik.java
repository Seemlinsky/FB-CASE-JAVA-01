package nl.adainf.energiebedrijf_case01.model;

import java.time.LocalDate;

public class Verbruik {
    private int id;
    private int klantId;
    private double stroomKwh;
    private double gasM3;
    private LocalDate datumStart;
    private LocalDate datumEind;

    public Verbruik(int id, int klantId, double stroomKwh, double gasM3, LocalDate datumStart, LocalDate datumEind) {
        this.id = id;
        this.klantId = klantId;
        this.stroomKwh = stroomKwh;
        this.gasM3 = gasM3;
        this.datumStart = datumStart;
        this.datumEind = datumEind;
    }

    public Verbruik(int klantId, double stroomKwh, double gasM3, LocalDate datumStart, LocalDate datumEind) {
        this(0, klantId, stroomKwh, gasM3, datumStart, datumEind);
    }

    public int getId() { return id; }
    public int getKlantId() { return klantId; }
    public double getStroomKwh() { return stroomKwh; }
    public double getGasM3() { return gasM3; }
    public LocalDate getDatumStart() { return datumStart; }
    public LocalDate getDatumEind() { return datumEind; }

    public void setId(int id) { this.id = id; }
}