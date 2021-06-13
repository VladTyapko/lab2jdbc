package sample.dao;

import sample.Metropolitan;
import sample.Station;

import java.sql.*;

public class ConcreteDAO implements DAO{
    static final String DB_URL = "jdbc:mysql://localhost/metropolitan?serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "Pa$$w0rD17";
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    Connection conn = null;
    Statement stmt = null;
    public ConcreteDAO() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
    }

    @Override
    public Metropolitan getAll(){
        Metropolitan metropolitan = new Metropolitan();
        try{
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT *  FROM line";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int lineId = rs.getInt("line_id");
                String color = rs.getString("color");
                metropolitan.addLine(lineId, color);
            }

            sql = "SELECT *  FROM station";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                int stationId = rs.getInt("station_id");
                int lineId = rs.getInt("line_id");
                String name = rs.getString("name");

                metropolitan.getLine(lineId).addStation(new Station(stationId, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return metropolitan;
    }

    @Override
    public void updateLine(int lineId, int newId, String newColor) {
        try {
            stmt = conn.createStatement();
            String sql;
            sql = " update line set line_id=" + newId + ", color=\"" + newColor + "\" where line_id=" + lineId + ";";
            int status = stmt.executeUpdate(sql);
            if(status < 0){
                System.out.println("Error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateStation(int lineId, int stationId, int newId, String newName){
        try {
            stmt = conn.createStatement();
            String sql;
            sql = " update station set station_id=" + newId + ", new name=\"" + newName +
                    "\" where station_id=" + stationId + " and line_id=" + lineId +";";
            System.out.println(sql);
            int status = stmt.executeUpdate(sql);
            if(status < 0){
                System.out.println("Error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void addLine(int lineId, String color){
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "INSERT INTO line (line_id, color) " + " VALUES (" + lineId + ", \"" + color + "\");";
            int status = stmt.executeUpdate(sql);
            if(status < 0){
                System.out.println("Error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public  void addStation(int lineId, int stationId, String name) {
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "INSERT INTO station (station_id, line_id, name)"+ " VALUES (" + stationId  + ", " + lineId + ",\"" + name + "\");";
            int status = stmt.executeUpdate(sql);
            if(status < 0){
                System.out.println("Error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteLine(int lineId) {
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "delete from line where line_id = " + lineId + ";";
            int status = stmt.executeUpdate(sql);
            if(status < 0){
                System.out.println("Error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteStation(int lineId, int stationId) {
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "delete from station where station_id = " + stationId + " and line_id=" + lineId + ";";
            int status = stmt.executeUpdate(sql);
            if(status < 0){
                System.out.println("Error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
