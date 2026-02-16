package nl.adainf.energiebedrijf_case01.dao;

import nl.adainf.energiebedrijf_case01.database.Database;
import nl.adainf.energiebedrijf_case01.model.GasTarief;
import nl.adainf.energiebedrijf_case01.model.StroomTarief;

import java.sql.*;
import java.time.LocalDate;

public class GasTariefDao {

    public void insertStroom(int klantId, double prijs, LocalDate vanaf, LocalDate tot) {
        String sql = "INSERT INTO stroomtarief(klant_id, tarief_kwh, datum_vanaf, datum_tot) VALUES(?,?,?,?)";
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
        String sql = "INSERT INTO gastarief(klant_id, tarief_m3, datum_vanaf, datum_tot) VALUES(?,?,?,?)";
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
        String sql = "SELECT * FROM stroomtarief WHERE klant_id=? ORDER BY id DESC LIMIT 1";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, klantId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new StroomTarief(
                        rs.getInt("id"),
                        rs.getInt("klant_id"),
                        rs.getDouble("tarief_kwh"),
                        rs.getDate("datum_vanaf").toLocalDate(),
                        rs.getDate("datum_tot").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GasTarief getLaatsteGasTarief(int klantId) {
        String sql = "SELECT * FROM gastarief WHERE klant_id=? ORDER BY id DESC LIMIT 1";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, klantId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new GasTarief(
                        rs.getInt("id"),
                        rs.getInt("klant_id"),
                        rs.getDouble("tarief_m3"),
                        rs.getDate("datum_vanaf").toLocalDate(),
                        rs.getDate("datum_tot").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}