import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Random rand = new Random();
        ArrayList<ArrayList<Integer>> points;
        ArrayList<Double> bestDistances;

        System.out.println("Do you want to read from file or generate a random instance?");
        System.out.println("1. File");
        System.out.println("2. Random");
        System.out.print(">");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.print("\nGive a filename >");
                String fileName = scanner.nextLine();
                points = Instances.readFromFile("tests/" + fileName + ".txt");
                break;
            case 2:
                System.out.print("\nGive an instance size >");
                int instanceSize = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Give board's max X coordinates >");
                int boardX = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Give board's max Y coordinates >");
                int boardY = scanner.nextInt();
                scanner.nextLine();
                points = Instances.randomInstance(instanceSize, boardX, boardY);
                break;
            default:
                scanner.close();
                System.out.println("\nWrong number, choose between 1 and 2");
                return;
        }

        int pointCount = points.getFirst().getFirst();
        points.removeFirst();

        System.out.print("\nInput population size >");
        final int POPULATION_SIZE = scanner.nextInt();
        final int s = POPULATION_SIZE / 2;
        scanner.nextLine();
        int generation = 1;
        ArrayList<Individual> population = new ArrayList<>();

        System.out.println("\nDo you want to input greedy algorithm data into the population, or do you want all instances to be random?");
        System.out.println("1. Greedy");
        System.out.println("2. Random");
        System.out.print(">");
        choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                for (int i = 0; i < pointCount; i++) {
                    Individual gnome = new Individual(Individual.greedyOrder(points, i));
                    population.add(gnome);
                }
                for (int i = 0; i < POPULATION_SIZE - pointCount; i++) {
                    Individual gnome = new Individual(Individual.randomOrder(points));
                    population.add(gnome);
                }
                break;
            case 2:
                for (int i=0; i <= POPULATION_SIZE; i++) {
                    Individual gnome = new Individual(Individual.randomOrder(points));
                    population.add(gnome);
                }
                break;
            default:
                scanner.close();
                System.out.println("Wrong number, choose between 1 and 2");
                return;
        }
        scanner.close();

        population.sort(Comparator.comparingDouble(Individual::getDistance));

        bestDistances = new ArrayList<>(10);
        bestDistances.add(population.getFirst().getDistance());
        int mutateSize = 1;

        System.out.println("\nGeneration " + generation + ", distance " + population.getFirst().getDistance());

        for (int i = 0; i < 100; i++) {
//        while (true) {
            generation++;
            int best = POPULATION_SIZE / 10;
            ArrayList<Individual> newPopulation = new ArrayList<>(population.subList(0, best));

            population.subList(s, population.size()).clear();

            int mate = (80 * POPULATION_SIZE) / 100;
            for (int j = 0; j < mate; j++) {
                Individual parent1 = population.get(rand.nextInt(best));
                Individual parent2 = population.get(rand.nextInt(best));
                while (parent1 == parent2) {
                    parent2 = population.get(rand.nextInt(best));
                }
                ArrayList<ArrayList<Integer>> child = Individual.mate(parent1.getOrder(), parent2.getOrder());
                newPopulation.add(new Individual(child));
            }

            int mut = (10 * POPULATION_SIZE) / 100;
            for (int j = 0; j < mut; j++) {
                Individual x = new Individual();
                int index = rand.nextInt(best);
                x.copy(population.get(index));
                x.mutate(mutateSize);
                population.remove(index);
                best--;
                newPopulation.add(x);
            }

            population = newPopulation;
            population.sort(Comparator.comparingDouble(Individual::getDistance));

            bestDistances.add(population.getFirst().getDistance());
            Collections.sort(bestDistances);
            if (bestDistances.size() > 10) {
                bestDistances.subList(0, 10).clear();
                boolean equal = new HashSet<>(bestDistances).size() <= 1;
                if (equal && mutateSize <= 5) {
                    mutateSize++;
                }
            }

            System.out.println("Generation " + generation + ", distance " + population.getFirst().getDistance());
        }

        ArrayList<Integer> finalSolution = new ArrayList<>();
        for (ArrayList<Integer> point: population.getFirst().getOrder())
            finalSolution.add(point.getFirst());
        System.out.println("\nFinal solution: " + finalSolution);

    }

}