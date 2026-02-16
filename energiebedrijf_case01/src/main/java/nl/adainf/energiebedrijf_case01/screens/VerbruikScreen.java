package nl.adainf.energiebedrijf_case01.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import nl.adainf.energiebedrijf_case01.dao.VerbruikDao;
import nl.adainf.energiebedrijf_case01.model.Verbruik;

import java.time.LocalDate;

public class VerbruikScreen {

    private final Scene scene;

    public VerbruikScreen(int klantId, Runnable onGoOverview) {

        Label title = new Label("Wekelijks verbruik invoeren");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        DatePicker dpStart = new DatePicker(LocalDate.now().minusDays(7));
        DatePicker dpEind = new DatePicker(LocalDate.now());

        TextField tfStroom = new TextField();
        tfStroom.setPromptText("Stroom (kWh) bijv 30");

        TextField tfGas = new TextField();
        tfGas.setPromptText("Gas (m3) bijv 10");

        Button btnSave = new Button("Opslaan verbruik");
        Button btnOverview = new Button("Naar overzicht");
        Label msg = new Label();

        btnSave.setOnAction(e -> {
            msg.setStyle("-fx-text-fill: red;");
            msg.setText("");

            if (dpStart.getValue() == null || dpEind.getValue() == null
                    || tfStroom.getText().isBlank() || tfGas.getText().isBlank()) {
                msg.setText("Vul alles in.");
                return;
            }

            try {
                double stroom = Double.parseDouble(tfStroom.getText().replace(",", "."));
                double gas = Double.parseDouble(tfGas.getText().replace(",", "."));

                if (stroom < 0 || gas < 0) {
                    msg.setText("Verbruik mag niet negatief zijn.");
                    return;
                }
                if (dpEind.getValue().isBefore(dpStart.getValue())) {
                    msg.setText("Einddatum moet na startdatum zijn.");
                    return;
                }

                Verbruik v = new Verbruik(klantId, stroom, gas, dpStart.getValue(), dpEind.getValue());
                new VerbruikDao().insert(v);

                msg.setStyle("-fx-text-fill: green;");
                msg.setText("Verbruik opgeslagen!");

            } catch (NumberFormatException ex) {
                msg.setText("Stroom/Gas moet een getal zijn.");
            }
        });

        btnOverview.setOnAction(e -> onGoOverview.run());

        VBox root = new VBox(10, title,
                new Label("Periode start:"), dpStart,
                new Label("Periode eind:"), dpEind,
                tfStroom, tfGas,
                btnSave, btnOverview, msg);

        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        scene = new Scene(root, 600, 500);
    }

    public Scene getScene() {
        return scene;
    }
}