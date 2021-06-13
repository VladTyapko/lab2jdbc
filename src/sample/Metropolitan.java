package sample;

import java.util.ArrayList;

public class Metropolitan {
    private ArrayList<Line> lines = new ArrayList<>();

    public void addLine(int id, String color){
        lines.add(new Line(id, color));
    }

    public void addLine(Line line) {
        lines.add(line);
    }

    public Line getLine(int id) {
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).line_id == id) {
                return lines.get(i);
            }
        }
        return null;
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public int countLines() {
        return lines.size();
    }

    public void deleteLine(int id) throws Exception {
        Line lineToDelete = getLine(id);
        if (lineToDelete == null) {
            throw new Exception("Line doesnt exist");
        }
        lines.remove(lineToDelete);
    }

    public void addStation(int code, String from, String to, int aircompanyCode) throws Exception {
        Line line = getLine(code);
        if (line == null) {
            throw new Exception("Line doesnt exist");
        }
        ArrayList<Station> stations = line.getStations();
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).station_id == code) {
                throw new Exception("This flight has already exist");
            }
        }
        Station station = new Station(code, from);
        line.addStation(station);



    }

}
