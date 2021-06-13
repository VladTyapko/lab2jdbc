package sample.dao;

import sample.Metropolitan;

public interface DAO {
    Metropolitan getAll();
    void updateLine(int lineId, int newId, String newColor);
    void updateStation(int lineId, int stationId, int newId, String newName);
    void addLine(int lineId, String color);
    void addStation(int lineId, int stationId, String name);
    void deleteLine(int lineId);
    void deleteStation(int lineId, int stationId);
}

