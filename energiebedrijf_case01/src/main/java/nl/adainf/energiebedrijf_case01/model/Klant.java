package nl.adainf.energiebedrijf_case01.model;

public class Klant {
    private int id;
    private String klantnummer;
    private String voornaam;
    private String achternaam;
    private double jaarlijksVoorschot;

    public Klant(int id, String klantnummer, String voornaam, String achternaam, double jaarlijksVoorschot) {
        this.id = id;
        this.klantnummer = klantnummer;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.jaarlijksVoorschot = jaarlijksVoorschot;
    }

    public Klant(String klantnummer, String voornaam, String achternaam, double jaarlijksVoorschot) {
        this(0, klantnummer, voornaam, achternaam, jaarlijksVoorschot);
    }

    public int getId() { return id; }
    public String getKlantnummer() { return klantnummer; }
    public String getVoornaam() { return voornaam; }
    public String getAchternaam() { return achternaam; }
    public double getJaarlijksVoorschot() { return jaarlijksVoorschot; }

    public void setId(int id) { this.id = id; }

    public double getMaandelijksVoorschot() {
        return jaarlijksVoorschot / 12.0;
    }

    @Override
    public String toString() {
        return klantnummer + " - " + voornaam + " " + achternaam;
    }
}