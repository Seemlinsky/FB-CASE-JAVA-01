package nl.adainf.energiebedrijf_case01.dao;

import nl.adainf.energiebedrijf_case01.database.Database;
import nl.adainf.energiebedrijf_case01.model.GasTarief;
import nl.adainf.energiebedrijf_case01.model.StroomTarief;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class GasTariefDao {

    public void insertStroom(int klantId, double prijs, LocalDate vanaf, LocalDate tot) {
        String sql = "INSERT INTO stroomtarief(klantnummer, tarief_kwh, datum_vanaf, datum_tot) VALUES(?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, klantId);
            ps.setDouble(2, prijs);
            ps.setDate(3, Date.valueOf(vanaf));
            ps.setDate(4, Date.valueOf(tot));

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertGas(int klantId, double prijs, LocalDate vanaf, LocalDate tot) {
        String sql = "INSERT INTO gastarief(klantnummer, tarief_m3, datum_vanaf, datum_tot) VALUES(?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, klantId);
            ps.setDouble(2, prijs);
            ps.setDate(3, Date.valueOf(vanaf));
            ps.setDate(4, Date.valueOf(tot));

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public StroomTarief getLaatsteStroomTarief(int klantId) {
        String sql = "SELECT * FROM stroomtarief WHERE klantnummer = ? ORDER BY datum_vanaf DESC LIMIT 1";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, klantId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new StroomTarief(
                            0,
                            rs.getInt("klantnummer"),
                            rs.getDouble("tarief_kwh"),
                            rs.getDate("datum_vanaf").toLocalDate(),
                            rs.getDate("datum_tot").toLocalDate()
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public GasTarief getLaatsteGasTarief(int klantId) {
        String sql = "SELECT * FROM gastarief WHERE klantnummer = ? ORDER BY datum_vanaf DESC LIMIT 1";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, klantId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new GasTarief(
                            0,
                            rs.getInt("klantnummer"),
                            rs.getDouble("tarief_m3"),
                            rs.getDate("datum_vanaf").toLocalDate(),
                            rs.getDate("datum_tot").toLocalDate()
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}