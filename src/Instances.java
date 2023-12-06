import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Instances {

    public static ArrayList<ArrayList<Integer>> readFromFile(String filename) throws IOException {
        ArrayList<ArrayList<Integer>> points = new ArrayList<>();
        ArrayList<Integer> lineList;
        BufferedReader reader;

        reader = new BufferedReader(new FileReader(filename));
        String line = reader.readLine();
        line = line.strip();
        ArrayList<Integer> pointCount = new ArrayList<>();
        pointCount.add(Integer.parseInt(line));
        points.add(pointCount);
        line = reader.readLine();

        while (line != null) {
            lineList = new ArrayList<>();
            String[] split = line.split(" ", 3);
            for (String i: split) {
                i = i.strip();
                lineList.add(Integer.parseInt(i));
            }
            points.add(lineList);
            line = reader.readLine();
        }
        reader.close();

        return points;
    }

    public static ArrayList<ArrayList<Integer>> randomInstance(int instanceSize, int boardX, int boardY) {
        ArrayList<ArrayList<Integer>> points = new ArrayList<>();
        Set<ArrayList<Integer>> pointSet = new HashSet<>();
        ArrayList<Integer> point;
        Random rand = new Random();
        int i = 1;

        ArrayList<Integer> pointCount = new ArrayList<>();
        pointCount.add(instanceSize);
        points.add(pointCount);

        while (points.size() != instanceSize+1) {
            point = new ArrayList<>();
            point.add(rand.nextInt(boardX-1));
            point.add(rand.nextInt(boardY-1));
            if (!pointSet.contains(point)) {
                pointSet.add(point);
                point.add(0, i);
                points.add(point);
                i+=1;
            }
        }

        return points;
    }

}
