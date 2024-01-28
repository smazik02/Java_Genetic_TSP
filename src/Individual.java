import java.util.*;

public class Individual {

    private ArrayList<ArrayList<Integer>> order;
    private double distance;

    Individual() {
        this.order = new ArrayList<>();
        this.distance = 0.0;
    }

    Individual(ArrayList<ArrayList<Integer>> order) {
        this.order = order;
        this.distance = this.calDistance();
    }

    private double calDistance() {
        double totalDistance = 0;
        double dist;
        var startPoint = order.getFirst();
        for (var endPoint : order.subList(1, order.size())) {
            dist = Math.sqrt(Math.pow(endPoint.get(1) - startPoint.get(1), 2) +
                    Math.pow(endPoint.get(2) - startPoint.get(2), 2));
            totalDistance += dist;
            startPoint = endPoint;
        }
        return totalDistance;
    }

    public double distanceBetweenGenes(int geneA, int geneB) {
        double totalDistance = 0;
        double dist;
        var startPoint = order.get(geneA);
        for (var endPoint : order.subList(geneA + 1, geneB + 1)) {
            dist = Math.sqrt(Math.pow(endPoint.get(1) - startPoint.get(1), 2) +
                    Math.pow(endPoint.get(2) - startPoint.get(2), 2));
            totalDistance += dist;
            startPoint = endPoint;
        }
        return totalDistance;
    }

    public static ArrayList<ArrayList<Integer>> mate(ArrayList<ArrayList<Integer>> parent1, ArrayList<ArrayList<Integer>> parent2) {
        Random rand = new Random();
        int geneA = rand.nextInt(1, parent1.size() - 2);
        int geneB = rand.nextInt(1, parent2.size() - 2);
        int start = Math.min(geneA, geneB);
        int end = Math.max(start + 1, Math.max(geneA, geneB));

        var tmp = new LinkedHashSet<>(parent1.subList(start, end));
        var remaining = new ArrayList<ArrayList<Integer>>();
        if (tmp.getFirst() == parent2.getFirst())
            start++;
        tmp.remove(parent2.getFirst());
        for (var x : parent2) {
            if (!tmp.contains(x)) {
                remaining.add(x);
            }
        }
        var res = new ArrayList<ArrayList<Integer>>();
        res.addAll(remaining.subList(0, start));
        res.addAll(tmp);
        res.addAll(remaining.subList(start, remaining.size()));

        return res;
    }

    public void mutate() {
        Random rand = new Random();
        int geneA = rand.nextInt(1, order.size() - 1);
        int geneB = rand.nextInt(1, order.size() - 1);
        while (geneA == geneB) {
            geneB = rand.nextInt(1, order.size() - 1);
        }
        if (geneA > geneB) {
            int tmp = geneA;
            geneA = geneB;
            geneB = tmp;
        }
        Collections.swap(order, geneA, geneB);
        distance = calDistance();
    }

    public static ArrayList<ArrayList<Integer>> greedyOrder(ArrayList<ArrayList<Integer>> points, int start) {
        return Greedy.greedyPathStart(points, start);
    }

    public static ArrayList<ArrayList<Integer>> randomOrder(ArrayList<ArrayList<Integer>> points) {
        var ord = new ArrayList<>(points);
        Collections.shuffle(ord);
        ord.add(ord.getFirst());
        return ord;
    }

    public void setOrder(ArrayList<ArrayList<Integer>> order) {
        this.order = order;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public ArrayList<ArrayList<Integer>> getOrder() {
        return order;
    }

    public double getDistance() {
        return distance;
    }

    public void copy(Individual x) {
        this.setOrder(x.getOrder());
        this.setDistance(x.getDistance());
    }

}
