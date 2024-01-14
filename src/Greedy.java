import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Greedy {

    static ArrayList<ArrayList<Integer>> points;
    static Set<ArrayList<Integer>> visited;
    static int pointCount;
    static ArrayList<Integer> startPoint;
    static double totalDistance;
    static ArrayList<ArrayList<Integer>> path;

    public static ArrayList<ArrayList<Integer>> greedyPathStart(ArrayList<ArrayList<Integer>> pointList, int startIndex) {
        points = pointList;
        startPoint = points.get(startIndex);
        visited = new HashSet<>();
        visited.add(startPoint);
        pointCount = points.size();
        path = new ArrayList<>();

        greedyPathGen(startPoint);
        return path;
    }

    public static void greedyPathGen(ArrayList<Integer> curPoint) {
        var minPoint = curPoint;
        path.add(curPoint);
        if (visited.size() == pointCount) {
            path.add(startPoint);
            return;
        }
        double minDistance = Double.POSITIVE_INFINITY;
        for (var point : points) {
            if (point == curPoint)
                continue;
            if (visited.contains(point))
                continue;
            double distance = Math.sqrt(Math.pow(point.get(1) - curPoint.get(1), 2) +
                    Math.pow(point.get(2) - curPoint.get(2), 2));
            if (distance < minDistance) {
                minDistance = distance;
                minPoint = point;
            }
        }
        boolean contains = visited.contains(minPoint);
        if (!contains)
            visited.add(minPoint);
        greedyPathGen(minPoint);
    }

    public static void fromPointToPoint(ArrayList<Integer> curPoint) {
        var minPoint = curPoint;
        System.out.println(curPoint);
        if (visited.size() == pointCount) {
            double dist = Math.sqrt(Math.pow(startPoint.get(1) - curPoint.get(1), 2) +
                    Math.pow(startPoint.get(2) - curPoint.get(2), 2));
            totalDistance += dist;
            System.out.println("Distance to " + startPoint.get(0) + ": " + Math.round(dist));
            System.out.println(startPoint);
            return;
        }
        double minDistance = Double.POSITIVE_INFINITY;
        for (var point : points) {
            if (point == curPoint)
                continue;
            if (visited.contains(point))
                continue;
            double distance = Math.sqrt(Math.pow(point.get(1) - curPoint.get(1), 2) +
                    Math.pow(point.get(2) - curPoint.get(2), 2));
            if (distance < minDistance) {
                minDistance = distance;
                minPoint = point;
            }
        }
        boolean contains = visited.contains(minPoint);
        if (!contains)
            visited.add(minPoint);
        totalDistance += minDistance;
        System.out.println("Distance to " + minPoint.getFirst() + ": " + Math.round(minDistance));
        fromPointToPoint(minPoint);
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Do you want to read from file or generate a random instance?");
        System.out.println("\t1. File");
        System.out.println("\t2. Random");
        System.out.print(">");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.print("Give a filename >");
                String fileName = scanner.nextLine();
                scanner.close();
                points = Instances.readFromFile("tests/" + fileName + ".txt");
                break;
            case 2:
                System.out.print("Give an instance size >");
                int instanceSize = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Give board's max X coordinates >");
                int boardX = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Give board's max Y coordinates >");
                int boardY = scanner.nextInt();
                scanner.nextLine();
                scanner.close();
                points = Instances.randomInstance(instanceSize, boardX, boardY);
                break;
            default:
                scanner.close();
                System.out.println("Wrong number, choose between 1 and 2");
                return;
        }

        pointCount = points.getFirst().getFirst();
        points.removeFirst();
        startPoint = points.getFirst();
        visited = new HashSet<>();
        visited.add(startPoint);
        totalDistance = 0;

        fromPointToPoint(points.getFirst());
        System.out.println("Total distance: " + Math.round(totalDistance));
    }

}
