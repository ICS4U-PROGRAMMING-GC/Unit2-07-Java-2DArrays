
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * 2D arrays program.
 *
 * @author Carel
 * @version 2026-11-08
 */
public final class Arrays2D {

    /** The average mark. */
    private static final double MEAN = 75.0;

    /** The standard deviation of the marks. */
    private static final double STANDARD_DEVIATION = 10.0;

    /**
     * Private constructor.
     */
    private Arrays2D() {
    }

    /**
     * Main method.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        final String[] studentArray = readFile("students.txt");
        final String[] assignmentsArray = readFile("assignments.txt");

        final String[][] results = generateMarks(
            studentArray, assignmentsArray);

        writeCSV(results);
    }

    /**
     * Reads a file and puts its contents into an array.
     *
     * @param fileName the file name
     * @return an array containing the file contents
     */
    private static String[] readFile(final String fileName) {
        final ArrayList<String> tempList = new ArrayList<>();

        try (Scanner fileScanner = new Scanner(new File(fileName))) {

            while (fileScanner.hasNextLine()) {
                final String line = fileScanner.nextLine().trim();

                if (!line.isEmpty()) {
                    tempList.add(line);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }

        final String[] array = new String[tempList.size()];

        for (int i = 0; i < tempList.size(); i++) {
            array[i] = tempList.get(i);
        }

        return array;
    }

    /**
     * Generates random marks for each student.
     *
     * @param studentArray the student names
     * @param assignmentsArray the assignment names
     * @return a 2D array containing the names and marks
     */
    public static String[][] generateMarks(
            final String[] studentArray,
            final String[] assignmentsArray) {

        final String[][] results = new String[
            studentArray.length + 1][assignmentsArray.length + 1];

        final Random random = new Random();

        results[0][0] = "Student";

        for (int i = 0; i < assignmentsArray.length; i++) {
            results[0][i + 1] = assignmentsArray[i];
        }

        for (int i = 0; i < studentArray.length; i++) {
            results[i + 1][0] = studentArray[i];

            for (int j = 0; j < assignmentsArray.length; j++) {
                final int mark = (int) Math.round(
                    random.nextGaussian() * STANDARD_DEVIATION + MEAN);

                results[i + 1][j + 1] = String.valueOf(mark);
            }
        }

        return results;
    }

    /**
     * Saves the marks into a CSV file.
     *
     * @param results the 2D array of results
     */
    private static void writeCSV(final String[][] results) {
        try (PrintWriter writer = new PrintWriter(
                new FileWriter("marks.csv"))) {

            for (int i = 0; i < results.length; i++) {
                for (int j = 0; j < results[i].length; j++) {
                    writer.print(results[i][j]);

                    if (j < results[i].length - 1) {
                        writer.print(",");
                    }
                }

                writer.println();
            }

            System.out.println("Marks saved to marks.csv");

        } catch (IOException e) {
            System.out.println("Error writing CSV file.");
        }
    }
}

