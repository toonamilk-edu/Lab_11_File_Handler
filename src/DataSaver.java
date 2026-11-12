import javax.swing.JFileChooser;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class DataSaver {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> records = new ArrayList<>();
        boolean more;

        do {
            String firstName = SafeInput.getNonZeroLenString(in, "Enter first name");
            String lastName = SafeInput.getNonZeroLenString(in, "Enter last name");
            int idNum = SafeInput.getRangedInt(in, "Enter ID number", 0, 999999);
            String email = SafeInput.getNonZeroLenString(in, "Enter email");
            int birthYear = SafeInput.getRangedInt(in, "Enter year of birth", 1900, 2025);

            String line = String.format("%s,%s,%06d,%s,%d", firstName, lastName, idNum, email, birthYear);
            records.add(line);

            more = SafeInput.getYNConfirm(in, "Add another record");
        } while (more);

        JFileChooser chooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        chooser.setCurrentDirectory(workingDirectory);
        chooser.setDialogTitle("Save File");

        if (chooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
            System.out.println("No save location specified. Exiting.");
            System.exit(0);
        }

        Path filePath = chooser.getSelectedFile().toPath();

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (String line : records) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("File saved successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}