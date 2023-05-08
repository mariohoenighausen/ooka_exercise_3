package org.ookauebung3.hotelsuchen_component.internal;

import org.ookauebung3.hotelsuchen_component.entities.Hotel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBAccess {
    private final String url = "jdbc:postgresql://dumbo.inf.h-brs.de/demouser";

    private Connection conn;
    public DBAccess(){

    }
    public void openConnection() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Properties props = new Properties();
        // unsecure way of utilizing credentials
        props.setProperty("user", "demouser");
        props.setProperty("password", "demouser");

        try {
            this.conn = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private String createQueryString(int type) {
        String query = "SELECT * FROM buchungsystem.hotel ";

        switch (type) {
            case 1:
                query += "WHERE buchungsystem.hotel.name ilike ?";
                break;
            case 2:
                query += "WHERE buchungsystem.hotel.ort ilike ?";
                break;
            case 3:
                query += "WHERE buchungsystem.hotel.sterne = ?";
                break;
            case 4:
                query += "WHERE buchungsystem.hotel.kontact ilike ?";
                break;
            case 5:
                query += "WHERE buchungsystem.hotel.name ilike ?" +
                        " OR buchungsystem.hotel.ort ilike ?";
                break;
            case 6:
                query += "WHERE buchungsystem.hotel.name ilike ? " +
                        "OR buchungsystem.hotel.ort ilike ? " +
                        "OR buchungsystem.hotel.sterne = ?";
                break;
            case 7:
                query += "WHERE buchungsystem.hotel.name ilike ? " +
                        "OR buchungsystem.hotel.ort ilike ? " +
                        "OR buchungsystem.hotel.sterne = ? " +
                        "OR buchungsystem.hotel.kontact ilike ?";
                break;
            case 8:
                query += "WHERE buchungsystem.hotel.name ilike ?" +
                        " AND buchungsystem.hotel.ort ilike ?";
                break;
            case 9:
                query += "WHERE buchungsystem.hotel.name ilike ? " +
                        "AND buchungsystem.hotel.ort ilike ? " +
                        "AND buchungsystem.hotel.sterne = ?";
                break;
            case 10:
                query += "WHERE buchungsystem.hotel.name ilike ? " +
                        "AND buchungsystem.hotel.ort ilike ? " +
                        "AND buchungsystem.hotel.sterne = ? " +
                        "AND buchungsystem.hotel.kontact ilike ?";
                break;
            default:
                query += "";
        }
        return query;
    }
    private PreparedStatement prepareQueryStatement(Connection conn, int type, String query, String[] values){
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = conn.prepareStatement(query);
            switch (type) {
                case 1:
                case 2:
                case 4:
                    preparedStatement.setString(1, '%' + values[0] + '%');
                    break;
                case 3:
                    preparedStatement.setInt(1, Integer.parseInt(values[0]));
                    break;
                case 5:
                case 8:
                    preparedStatement.setString(1, '%' + values[0] + '%');
                    preparedStatement.setString(2, '%' + values[1] + '%');
                    break;
                case 6:
                case 9:
                    preparedStatement.setString(1, '%' + values[0] + '%');
                    preparedStatement.setString(2, '%' + values[1] + '%');
                    preparedStatement.setInt(3, Integer.parseInt(values[2]));
                    break;
                case 7:
                case 10:
                    preparedStatement.setString(1, '%' + values[0] + '%');
                    preparedStatement.setString(2, '%' + values[1] + '%');
                    preparedStatement.setInt(3, Integer.parseInt(values[2]));
                    preparedStatement.setString(4, '%' + values[3] + '%');
                    break;
                default:
                    break;
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return preparedStatement;
    }
    private void discoverTableData(ResultSetMetaData rsmd) {
        try {
            for (int idx = 1; idx <= rsmd.getColumnCount(); idx++) {
                System.out.println("Column name: " + rsmd.getColumnName(idx));
                System.out.println("Column type: " + rsmd.getColumnTypeName(idx));
                System.out.println("Column types: " + rsmd.getColumnLabel(idx));
                System.out.println("Column type:" + rsmd.getColumnType(idx));
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }

    public Hotel[] getObjects(int type, String value) {
        PreparedStatement stmt;
        ResultSet rs;
        List<Hotel> result = new ArrayList<>();
        String query = createQueryString(type);
        try {
            String[] values = value.contains(",") ? value.split(","): new String[]{value};
            stmt = this.prepareQueryStatement(conn, type, query, values);
            rs = stmt.executeQuery();
            // discoverTableData(rs.getMetaData());
            while (rs.next()) {
                // map the first three column values of a row of the result set
                // to a new Instance of the Hotel entity
                result.add(new Hotel(rs.getInt(1), rs.getString(2),
                        rs.getString(3),rs.getInt(4),rs.getString(5)));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result.toArray(new Hotel[0]);
    }
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
