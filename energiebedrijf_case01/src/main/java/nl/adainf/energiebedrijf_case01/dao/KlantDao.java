package nl.adainf.energiebedrijf_case01.dao;

import nl.adainf.energiebedrijf_case01.database.Database;
import nl.adainf.energiebedrijf_case01.model.Klant;

import java.sql.*;
import java.util.ArrayList;

public class KlantDao {

    public int insert(Klant k) {
        String sql = "INSERT INTO klant(klantnummer, voornaam, achternaam, jaarlijks_voorschot) " +
                "VALUES(?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, k.getKlantnummer());
            ps.setString(2, k.getVoornaam());
            ps.setString(3, k.getAchternaam());
            ps.setDouble(4, k.getJaarlijksVoorschot());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ArrayList<Klant> getAll() {
        ArrayList<Klant> list = new ArrayList<>();
        String sql = "SELECT * FROM klant ORDER BY id DESC";

        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Klant k = new Klant(
                        rs.getInt("id"),
                        rs.getString("klantnummer"),
                        rs.getString("voornaam"),
                        rs.getString("achternaam"),
                        rs.getDouble("jaarlijks_voorschot")
                );
                list.add(k);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}