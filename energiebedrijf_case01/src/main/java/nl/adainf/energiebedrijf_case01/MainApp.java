package nl.adainf.energiebedrijf_case01;

import javafx.application.Application;
import javafx.stage.Stage;
import nl.adainf.energiebedrijf_case01.screens.OverzichtScreen;
import nl.adainf.energiebedrijf_case01.screens.StartScreen;
import nl.adainf.energiebedrijf_case01.screens.VerbruikScreen;

public class MainApp extends Application {

    private Stage stage;
    private int klantId;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("Energiebedrijf Case 01");
        showStart();
        stage.show();
    }

    private void showStart() {
        StartScreen start = new StartScreen(klantId -> {
            this.klantId = klantId;
            showVerbruik();
        });

        stage.setScene(start.getScene());
        stage.show();
    }

    private void showVerbruik() {
        VerbruikScreen v = new VerbruikScreen(klantId, this::showOverzicht);
        stage.setScene(v.getScene());
    }

    private void showOverzicht() {
        OverzichtScreen o = new OverzichtScreen(klantId, this::showVerbruik);
        stage.setScene(o.getScene());
    }
}
