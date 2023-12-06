import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Random rand = new Random();
        ArrayList<ArrayList<Integer>> points;

        System.out.println("Do you want to read from file or generate a random instance?");
        System.out.println("\t1. File");
        System.out.println("\t2. Random");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.print("Give a filename >");
                String fileName = scanner.nextLine();
                scanner.close();
                points = Instances.readFromFile("tests/"+fileName+".txt");
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

        int pointCount = points.get(0).get(0);
        points.remove(0);

        final int POPULATION_SIZE = 10000;
        final int s = POPULATION_SIZE/2;
        int generation = 1;
        ArrayList<Individual> population = new ArrayList<>();

        for (int i=0; i < pointCount; i++) {
            Individual gnome = new Individual(Individual.greedyOrder(points, i));
            population.add(gnome);
        }

        for (int i=0; i < POPULATION_SIZE-pointCount; i++) {
            Individual gnome = new Individual(Individual.randomOrder(points));
            population.add(gnome);
        }

//        for (int i=0; i <= POPULATION_SIZE; i++) {
//            Individual gnome = new Individual(Individual.randomOrder(points));
//            population.add(gnome);
//        }

        population.sort(Comparator.comparingDouble(Individual::getDistance));

        System.out.println("Generation " + generation + ", distance " + population.get(0).getDistance());

//        for (int i = 0; i < 1000; i++) {
        while (true) {
            generation++;
            int best = POPULATION_SIZE/10;
            ArrayList<Individual> newPopulation = new ArrayList<>(population.subList(0, best));

            population.subList(0,s).clear();
            int mate = (80*POPULATION_SIZE)/100;
            for (int j = 0; j < mate; j++) {
                Individual parent1 = population.get(rand.nextInt(best));
                Individual parent2 = population.get(rand.nextInt(best));
                ArrayList<ArrayList<Integer>> child = Individual.mate(parent1.getOrder(), parent2.getOrder());
                newPopulation.add(new Individual(child));
            }

            int mut = (10*POPULATION_SIZE)/100;
            for (int j = 0; j < mut; j++) {
                Individual x = new Individual();
                int index = rand.nextInt(best);
                x.copy(population.get(index));
                x.mutate();
                population.remove(index);
                newPopulation.add(x);
            }

            population = newPopulation;
            population.sort(Comparator.comparingDouble(Individual::getDistance));

            System.out.println("Generation " + generation + ", distance " + population.get(0).getDistance());
        }
    }

}