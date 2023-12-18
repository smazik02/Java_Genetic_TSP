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
        ArrayList<Integer> startPoint = this.order.getFirst();
        for (ArrayList<Integer> endPoint : this.order.subList(1, this.order.size())) {
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
        ArrayList<Integer> startPoint = this.order.get(geneA);
        for (ArrayList<Integer> endPoint : this.order.subList(geneA + 1, geneB + 1)) {
            dist = Math.sqrt(Math.pow(endPoint.get(1) - startPoint.get(1), 2) +
                    Math.pow(endPoint.get(2) - startPoint.get(2), 2));
            totalDistance += dist;
            startPoint = endPoint;
        }
        return totalDistance;
    }

    public static ArrayList<ArrayList<Integer>> mate(ArrayList<ArrayList<Integer>> parent1, ArrayList<ArrayList<Integer>> parent2) {
        ArrayList<ArrayList<Integer>> remaining;
        ArrayList<ArrayList<Integer>> res;
        Random rand = new Random();
        int geneA = rand.nextInt(1, parent1.size() - 2);
        int geneB = rand.nextInt(1, parent2.size() - 2);
        int start = Math.min(geneA, geneB);
        int end = Math.max(start + 1, Math.max(geneA, geneB));

        Set<ArrayList<Integer>> tmp = new LinkedHashSet<>(parent1.subList(start, end));
        remaining = new ArrayList<>();
        for (ArrayList<Integer> x : parent2) {
            boolean contains = tmp.contains(x);
            if (!contains) {
                remaining.add(x);
            }
        }
        res = new ArrayList<>();
        res.addAll(remaining.subList(0, start));
        res.addAll(tmp);
        res.addAll(remaining.subList(start, remaining.size()));

        if (res.size() != parent1.size()) {
            res.add(res.getFirst());
        }
        return res;
    }

    public void mutate(int mutatesize) {
        Random rand = new Random();
        for (int i = 0; i < mutatesize; i++) {
            int geneA = rand.nextInt(1, this.order.size() - 2);
            int geneB = rand.nextInt(1, this.order.size() - 2);
            while (geneA == geneB) {
                geneB = rand.nextInt(1, this.order.size() - 2);
            }
            if (geneA > geneB) {
                int tmp = geneA;
                geneA = geneB;
                geneB = tmp;
            }
            ArrayList<Integer> tmp = this.order.get(geneA);
            this.order.set(geneA, this.order.get(geneB));
            this.order.set(geneB, tmp);
        }
        this.distance = this.calDistance();
    }

    public static ArrayList<ArrayList<Integer>> greedyOrder(ArrayList<ArrayList<Integer>> points, int start) {
        return Greedy.greedyPathStart(points, start);
    }

    public static ArrayList<ArrayList<Integer>> randomOrder(ArrayList<ArrayList<Integer>> points) {
        ArrayList<ArrayList<Integer>> ord = new ArrayList<>(points);
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
        return this.order;
    }

    public double getDistance() {
        return this.distance;
    }

    public void copy(Individual x) {
        this.setOrder(x.getOrder());
        this.setDistance(x.getDistance());
    }

}
