import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        TreeSet<String> animalBST = new TreeSet<>();
        ArrayList<String> animalAL = new ArrayList<>();
        //read from file
        try{
            Scanner read = new Scanner(new File("animals.txt"));
            while(read.hasNextLine()){
                animalAL.add(read.nextLine());
            }
            read.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File not found.");
        }
        System.out.println("Testing BST with random data");
        testBST(animalAL, animalBST);
        System.out.println("Height of the BST: " + animalBST.height());
        System.out.println("BST is balanced: " + animalBST.isBalanced());


        System.out.println("\nTesting BST with sorted data");
        animalBST.clear();
        animalAL.sort(null);
        testBST(animalAL, animalBST);
        System.out.println("Height of the BST: " + animalBST.height());
        System.out.println("BST is balanced: " + animalBST.isBalanced());

    }

    public static void testBST(ArrayList<String> al, TreeSet<String> bst) {
        for(String animal: al){
            bst.add(animal);
        }
        int totalContains = 0;
        int totalAdd = 0;
        int totalRemove = 0;
        for(int i = 0; i < 20; i++){
            int randomIndex = (int)(Math.random() * al.size());
            String randomAnimal = al.get(randomIndex);
            bst.contains(randomAnimal);
            bst.add(randomAnimal);
            bst.remove(randomAnimal);
            totalContains += TreeSet.cIterations;
            totalAdd += TreeSet.aIterations;
            totalRemove += TreeSet.rIterations;
            System.out.printf("%-50s\t%-10d\t%-10d\t%-10d\n", randomAnimal, 
            TreeSet.cIterations, TreeSet.aIterations, TreeSet.rIterations);
        }
        System.out.printf("%-50s\t%-10d\t%-10d\t%-10d\n", "Average", 
            totalContains/20, totalAdd/20, totalRemove/20);
    }
}
