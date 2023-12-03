import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class FileOperation {

    private String filename;

    public FileOperation(String filename) {
        this.filename = filename;
    }

    public void writeToFile(String input) {
        try {
            File f = new File(filename);
            FileWriter fw = new FileWriter(f, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(input);

            pw.close();
        } catch (IOException e) {
            System.out.println("Unable to write the data due to " + e.getMessage() + ". Please try again.");
        }
    }

    public ArrayList<String> search(String key) {
        ArrayList<String> lineFound = new ArrayList<>();
        try {
            File f = new File(filename);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);


            String line = br.readLine();
            while (line != null) {
                String[] wordsInLine = line.split(";");
                for (String word : wordsInLine) {
                    if (word.equals(key))
                        lineFound.add(line);
                }
                line = br.readLine();
            }
            br.close();
        } catch(FileNotFoundException ex){
            System.out.println("File not found : " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Unable to read file due to" + ex.getMessage());
        }
        return lineFound;
    }

    public void delete(String IDtoDelete) {
        try {
            File f = new File(filename);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            File fTemp = new File("Temp.txt");
            PrintWriter pw = new PrintWriter(fTemp);

            String line = br.readLine();

            while (line != null) {
                String[] wordsInLine = line.split(";");
                if (!wordsInLine[0].equals(IDtoDelete)) {
                    pw.println(line);
                }
                line = br.readLine();
            }
            pw.close();
            br.close();
        } catch (IOException ex) {
            System.out.println("Unable to read file due to" + ex.getMessage());
        }

        try {
            File originalFile = new File(filename);
            if (originalFile.delete()) {
                File tempFile = new File("Temp.txt");
                if (!tempFile.renameTo(new File(filename))) {
                    System.out.println("Error: Unable to rename the temporary file.");
                }
            } else {
                System.out.println("Error: Unable to delete the original file.");
            }
        } catch (Exception ex) {
            System.out.println("Error due to " + ex.getMessage());
        }
    }

    public void modifyFile(String id, String input) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            StringBuilder contents = new StringBuilder();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(";");
                if (!fields[0].equals(id)) {
                    contents.append(line);
                } else {
                    contents.append(input);
                }
                if (scanner.hasNextLine())
                    contents.append("\n");
            }
            FileWriter fw = new FileWriter(filename, false);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(contents);

            pw.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Unable to read file due to " + ex);
        } catch (IOException e) {
            System.out.println("Unable to read file due to" + e.getMessage());
        }
    }
}
