package nl.adainf.energiebedrijf_case01.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import nl.adainf.energiebedrijf_case01.dao.KlantDao;
import nl.adainf.energiebedrijf_case01.model.Klant;

import java.util.function.Consumer;

public class StartScreen {

    private final Scene scene;

    public StartScreen(Consumer<Integer> onDone) {

        Label title = new Label("Start - Nieuwe klant");

        TextField tfKlantnummer = new TextField();
        tfKlantnummer.setPromptText("Klantnummer (bv K001)");

        TextField tfVoornaam = new TextField();
        tfVoornaam.setPromptText("Voornaam");

        TextField tfAchternaam = new TextField();
        tfAchternaam.setPromptText("Achternaam");

        TextField tfVoorschot = new TextField();
        tfVoorschot.setPromptText("Jaarlijks voorschot (bv 180)");

        Label msg = new Label();
        Button btnStart = new Button("Start");

        btnStart.setOnAction(e -> {
            msg.setText("");

            String klantnummer = tfKlantnummer.getText().trim();
            String voornaam = tfVoornaam.getText().trim();
            String achternaam = tfAchternaam.getText().trim();
            String voorschotStr = tfVoorschot.getText().trim();

            // simpele checks (lesstijl)
            if (klantnummer.isEmpty() || voornaam.isEmpty() || achternaam.isEmpty() || voorschotStr.isEmpty()) {
                msg.setText("Vul alles in.");
                return;
            }

            double voorschot;
            try {
                voorschot = Double.parseDouble(voorschotStr);
            } catch (NumberFormatException ex) {
                msg.setText("Voorschot moet een getal zijn.");
                return;
            }

            if (voorschot <= 0) {
                msg.setText("Voorschot moet groter dan 0 zijn.");
                return;
            }

            // Klant object maken (id=0 want database maakt 'm aan)
            Klant k = new Klant(
                    0,
                    klantnummer,
                    voornaam,
                    achternaam,
                    voorschot
            );

            int klantId = new KlantDao().insert(k);

            if (klantId == -1) {
                msg.setText("Opslaan mislukt (DB).");
                return;
            }

            msg.setText("Klant opgeslagen! id=" + klantId);

            // doorgaan naar volgende screen
            onDone.accept(klantId);
        });

        VBox root = new VBox(10,
                title,
                tfKlantnummer,
                tfVoornaam,
                tfAchternaam,
                tfVoorschot,
                btnStart,
                msg
        );
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        scene = new Scene(root, 600, 400);
    }

    public Scene getScene() {
        return scene;
    }
}
