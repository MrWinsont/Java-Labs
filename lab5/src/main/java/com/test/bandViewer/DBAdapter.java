package com.test.bandViewer;

import java.sql.*;
import java.util.ArrayList;

public class DBAdapter {
    static Connection con;

    static void CreateOrConnection(){
        if (con == null) {
            try {
                con = DriverManager.getConnection("jdbc:sqlite:musBands.db");
                Statement stmt = con.createStatement();
                System.out.print("ok");
                String sql="""
                            CREATE TABLE IF NOT EXISTS band_info(
                            band_id INTEGER,
                            country TEXT NOT NULL,
                            birth_year INTEGER NOT NULL,
                            death_year INTEGER,
                            info TEXT,
                            CONSTRAINT band_info_band_FK FOREIGN KEY (band_id) REFERENCES band(band_id) ON DELETE CASCADE ON UPDATE CASCADE);

                        """;
                stmt.execute(sql);
                System.out.println("Table created");
            } catch (Exception e){
                System.out.println(e);
            }
        }
    }

    public ArrayList<Band> SelectData() throws SQLException{
        ArrayList<Band> bandInfo = new ArrayList<Band>();

        String sql = "SELECT b.band_name, bi.country, bi.birth_year, bi.info\r\n" + //
                     "FROM band_info bi \r\n" + //
                     "INNER JOIN band b on bi.band_id = b.band_id";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            String name = rs.getString("band_name");
            String country = rs.getString("country");
            int year = rs.getInt("birth_year");
            String info = rs.getString("info");
            bandInfo.add(new Band(name, country, year, info));
        }
        return bandInfo;
    }

    void InsertMusician(String name, String instrument, Band band) throws SQLException{
        String sql = "INSERT INTO musician(musician_name, instrument) VALUES('"+name+"', '"+instrument+"')";
        Statement stmt = con.createStatement();
        stmt.execute(sql);
        stmt.close();
        sql = " INSERT INTO band_group (musician_id, band_id)\r\n" + //
            "    VALUES ((SELECT musician_id FROM musician WHERE musician_name = '"+name+"'), (SELECT band_id FROM band WHERE band_name = '"+band.getName()+"'));";
        stmt = con.createStatement();
        stmt.execute(sql);
        stmt.close();
        System.out.println("Inserted data");
    }

    void InsertBandInfo(String bandName, String country, Integer year, String info) throws SQLException {
        String sql = "INSERT INTO band(band_name) VALUES('"+bandName+"')";
        Statement stmt = con.createStatement();
        stmt.execute(sql);
        stmt.close();
        sql = " INSERT INTO band_info (band_id, country, birth_year, info)\r\n" + //
              "    VALUES ((SELECT band_id FROM band WHERE band_name = '"+bandName+"'), '"+country+"', '"+year+"', '"+info+"');";
        stmt = con.createStatement();
        stmt.execute(sql);
        stmt.close();
        System.out.println("Inserted data");
    }

    void DeleteBandData(String bandName) throws SQLException{
        String sql = "DELETE FROM band_info WHERE band_id = (SELECT band_id FROM band WHERE band_name = '"+bandName+"')";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        sql = "DELETE FROM band WHERE band_name = '"+bandName+"'";
        stmt = con.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        System.out.println("Deleted data");
    }

    void DeleteMusicianData(String musicianName) throws SQLException{
        String sql = "DELETE FROM musician WHERE musician_name = '"+musicianName+"'";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        System.out.println("Deleted data");
    }

    void  UpdateBandData(Band band, String oldName) throws SQLException {
        String sql = "UPDATE band_info SET country='"+band.getCountry()+"', birth_year = '"+band.getYear()+"', info = '"+band.getInfo()+"' WHERE band_id= (SELECT band_id FROM band WHERE band_name = '"+oldName+"')";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        sql = "UPDATE band SET band_name ='"+band.getName()+"' WHERE band_name='"+oldName+"'";
        stmt = con.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        System.out.println("Updated data");
    }
    
    public ArrayList<Musician> SelectMusicianData(String bandName) throws SQLException{
        ArrayList<Musician> musInfo = new ArrayList<Musician>();

        String sql = "SELECT m.musician_name, m.instrument\r\n" + //
                     "FROM musician m\r\n" + //
                     "INNER JOIN band_group bg on bg.musician_id = m.musician_id\r\n" + //
                     "INNER JOIN band b on b.band_id = bg.band_id\r\n" + //
                     "WHERE b.band_name = '"+bandName+"'";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            String name = rs.getString("musician_name");
            String instrument = rs.getString("instrument");
            musInfo.add(new Musician(name, instrument));
        }
        return musInfo;
    }


}
