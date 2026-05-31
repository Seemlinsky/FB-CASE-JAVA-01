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
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(k.getKlantnummer()));
            ps.setString(2, k.getVoornaam());
            ps.setString(3, k.getAchternaam());
            ps.setDouble(4, k.getJaarlijksVoorschot());

            ps.executeUpdate();

            // In deze database gebruiken we klantnummer als klant-id.
            return Integer.parseInt(k.getKlantnummer());

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Klantnummer moet een getal zijn.");
        }

        return -1;
    }

    public ArrayList<Klant> getAll() {
        ArrayList<Klant> list = new ArrayList<>();

        // De tabel heeft geen kolom id, dus we gebruiken klantnummer.
        String sql = "SELECT * FROM klant ORDER BY klantnummer DESC";

        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                int klantnummer = rs.getInt("klantnummer");

                Klant k = new Klant(
                        klantnummer,
                        String.valueOf(klantnummer),
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