package nl.adainf.energiebedrijf_case01.dao;

import nl.adainf.energiebedrijf_case01.database.Database;
import nl.adainf.energiebedrijf_case01.model.Verbruik;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VerbruikDao {

    public void insert(Verbruik v) {
        String sql = "INSERT INTO verbruik(klantnummer, stroom_kwh, gas_m3, datum_start, datum_eind) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, v.getKlantId());
            ps.setDouble(2, v.getStroomKwh());
            ps.setDouble(3, v.getGasM3());
            ps.setDate(4, Date.valueOf(v.getDatumStart()));
            ps.setDate(5, Date.valueOf(v.getDatumEind()));

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Verbruik> getAllForKlant(int klantId) {
        ArrayList<Verbruik> list = new ArrayList<>();

        // In onze database heet de koppeling klantnummer, niet klant_id.
        String sql = "SELECT * FROM verbruik WHERE klantnummer = ? ORDER BY datum_start ASC";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, klantId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Verbruik verbruik = new Verbruik(
                            0,
                            rs.getInt("klantnummer"),
                            rs.getDouble("stroom_kwh"),
                            rs.getDouble("gas_m3"),
                            rs.getDate("datum_start").toLocalDate(),
                            rs.getDate("datum_eind").toLocalDate()
                    );

                    list.add(verbruik);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}