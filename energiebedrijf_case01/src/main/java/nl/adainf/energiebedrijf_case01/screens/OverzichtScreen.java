package nl.adainf.energiebedrijf_case01.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import nl.adainf.energiebedrijf_case01.dao.KlantDao;
import nl.adainf.energiebedrijf_case01.dao.GasTariefDao;
import nl.adainf.energiebedrijf_case01.dao.VerbruikDao;
import nl.adainf.energiebedrijf_case01.model.GasTarief;
import nl.adainf.energiebedrijf_case01.model.Klant;
import nl.adainf.energiebedrijf_case01.model.StroomTarief;
import nl.adainf.energiebedrijf_case01.model.Verbruik;

import java.util.ArrayList;

public class OverzichtScreen {

    private final Scene scene;

    public OverzichtScreen(int klantId, Runnable onBack) {

        Label title = new Label("Overzicht verbruik + kosten");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextArea area = new TextArea();
        area.setEditable(false);
        area.setPrefHeight(300);

        Button btnRefresh = new Button("Refresh overzicht");
        Button btnBack = new Button("Terug");

        Label msg = new Label();

        btnRefresh.setOnAction(e -> {
            area.clear();
            msg.setText("");

            // klant + tarieven ophalen
            ArrayList<Klant> klanten = new KlantDao().getAll();
            Klant klant = null;
            for (Klant k : klanten) {
                if (k.getId() == klantId) klant = k;
            }
            if (klant == null) {
                msg.setStyle("-fx-text-fill: red;");
                msg.setText("Klant niet gevonden.");
                return;
            }

            GasTariefDao tariefDao = new GasTariefDao();
            StroomTarief stroomTarief = tariefDao.getLaatsteStroomTarief(klantId);
            GasTarief gasTarief = tariefDao.getLaatsteGasTarief(klantId);

            if (stroomTarief == null || gasTarief == null) {
                msg.setStyle("-fx-text-fill: red;");
                msg.setText("Tarieven niet gevonden.");
                return;
            }

            // verbruik ophalen
            ArrayList<Verbruik> verbruikList = new VerbruikDao().getAllForKlant(klantId);

            double totaalStroom = 0;
            double totaalGas = 0;

            // foreach (les-eis)
            for (Verbruik v : verbruikList) {
                totaalStroom += v.getStroomKwh();
                totaalGas += v.getGasM3();
            }

            double stroomKosten = totaalStroom * stroomTarief.getPrijs();
            double gasKosten = totaalGas * gasTarief.getPrijs();
            double totaalKosten = stroomKosten + gasKosten;

            area.appendText("Klant: " + klant + "\n");
            area.appendText("Jaarlijks voorschot: " + klant.getJaarlijksVoorschot() + "\n");
            area.appendText("Maandelijks voorschot: " + String.format("%.2f", klant.getMaandelijksVoorschot()) + "\n\n");

            area.appendText("Totaal stroom (kWh): " + String.format("%.2f", totaalStroom) + "\n");
            area.appendText("Totaal gas (m3): " + String.format("%.2f", totaalGas) + "\n\n");

            area.appendText("Stroom kosten: " + String.format("%.2f", stroomKosten) + "\n");
            area.appendText("Gas kosten: " + String.format("%.2f", gasKosten) + "\n");
            area.appendText("TOTAAL kosten: " + String.format("%.2f", totaalKosten) + "\n\n");

            // simpele check: “maandelijks inzichtelijk”
            // (super basic: totaal/12 als gemiddelde)
            double gemiddeldPerMaand = totaalKosten / 12.0;
            area.appendText("Gemiddeld per maand: " + String.format("%.2f", gemiddeldPerMaand) + "\n");

            if (gemiddeldPerMaand > klant.getMaandelijksVoorschot()) {
                msg.setStyle("-fx-text-fill: red;");
                msg.setText("WAARSCHUWING: je zit boven je maandelijkse voorschot!");
            } else {
                msg.setStyle("-fx-text-fill: green;");
                msg.setText("OK: je zit onder je voorschot.");
            }
        });

        btnBack.setOnAction(e -> onBack.run());

        VBox root = new VBox(10, title, btnRefresh, area, msg, btnBack);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        scene = new Scene(root, 650, 550);
    }

    public Scene getScene() {
        return scene;
    }
}