package nl.adainf.energiebedrijf_case01.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import nl.adainf.energiebedrijf_case01.dao.GasTariefDao;
import nl.adainf.energiebedrijf_case01.dao.KlantDao;
import nl.adainf.energiebedrijf_case01.model.Klant;

import java.time.LocalDate;
import java.util.function.Consumer;

public class StartScreen {

    private final Scene scene;

    public StartScreen(Consumer<Integer> onDone) {

        Label title = new Label("Start - Energiebedrijf Current");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField tfKlantnummer = new TextField();
        tfKlantnummer.setPromptText("Klantnummer, bijvoorbeeld K001");

        TextField tfVoornaam = new TextField();
        tfVoornaam.setPromptText("Voornaam");

        TextField tfAchternaam = new TextField();
        tfAchternaam.setPromptText("Achternaam");

        TextField tfVoorschot = new TextField();
        tfVoorschot.setPromptText("Jaarlijks voorschot, bijvoorbeeld 1800");

        TextField tfStroomTarief = new TextField();
        tfStroomTarief.setPromptText("Stroomtarief per kWh, bijvoorbeeld 0.40");

        TextField tfGasTarief = new TextField();
        tfGasTarief.setPromptText("Gastarief per m3, bijvoorbeeld 1.30");

        DatePicker dpVanaf = new DatePicker(LocalDate.now());
        DatePicker dpTot = new DatePicker(LocalDate.now().plusYears(1));

        Label msg = new Label();

        Button btnStart = new Button("Klant en tarieven opslaan");

        btnStart.setOnAction(e -> {
            msg.setStyle("-fx-text-fill: red;");
            msg.setText("");

            String klantnummer = tfKlantnummer.getText().trim();
            String voornaam = tfVoornaam.getText().trim();
            String achternaam = tfAchternaam.getText().trim();
            String voorschotStr = tfVoorschot.getText().trim();
            String stroomTariefStr = tfStroomTarief.getText().trim();
            String gasTariefStr = tfGasTarief.getText().trim();

            // Controle op lege velden
            if (klantnummer.isEmpty()
                    || voornaam.isEmpty()
                    || achternaam.isEmpty()
                    || voorschotStr.isEmpty()
                    || stroomTariefStr.isEmpty()
                    || gasTariefStr.isEmpty()
                    || dpVanaf.getValue() == null
                    || dpTot.getValue() == null) {
                msg.setText("Vul alle velden in.");
                return;
            }

            try {
                double voorschot = Double.parseDouble(voorschotStr.replace(",", "."));
                double stroomTarief = Double.parseDouble(stroomTariefStr.replace(",", "."));
                double gasTarief = Double.parseDouble(gasTariefStr.replace(",", "."));

                if (voorschot <= 0 || stroomTarief <= 0 || gasTarief <= 0) {
                    msg.setText("Voorschot en tarieven moeten groter zijn dan 0.");
                    return;
                }

                if (dpTot.getValue().isBefore(dpVanaf.getValue())) {
                    msg.setText("Datum tot moet na datum vanaf liggen.");
                    return;
                }

                // Klant object maken
                Klant klant = new Klant(
                        klantnummer,
                        voornaam,
                        achternaam,
                        voorschot
                );

                // Klant opslaan in database
                int klantId = new KlantDao().insert(klant);

                if (klantId == -1) {
                    msg.setText("Klant opslaan mislukt.");
                    return;
                }

                // Tarieven opslaan in database
                GasTariefDao tariefDao = new GasTariefDao();
                tariefDao.insertStroom(klantId, stroomTarief, dpVanaf.getValue(), dpTot.getValue());
                tariefDao.insertGas(klantId, gasTarief, dpVanaf.getValue(), dpTot.getValue());

                msg.setStyle("-fx-text-fill: green;");
                msg.setText("Klant en tarieven opgeslagen!");

                // Door naar scherm voor wekelijks verbruik
                onDone.accept(klantId);

            } catch (NumberFormatException ex) {
                msg.setText("Voorschot, stroomtarief en gastarief moeten getallen zijn.");
            }
        });

        VBox root = new VBox(10,
                title,
                new Label("Klantgegevens"),
                tfKlantnummer,
                tfVoornaam,
                tfAchternaam,
                tfVoorschot,
                new Label("Tarieven"),
                tfStroomTarief,
                tfGasTarief,
                new Label("Tarief geldig vanaf"),
                dpVanaf,
                new Label("Tarief geldig tot"),
                dpTot,
                btnStart,
                msg
        );

        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        scene = new Scene(root, 650, 650);
    }

    public Scene getScene() {
        return scene;
    }
}