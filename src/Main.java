import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.nio.file.*;

class Record implements Serializable {

    static final long serialVersionUID = 1L;
    String employeesSector;
    String employeesNumber;
    String employeesSurname;
    int employeesWage;

    public Record(String employeesSector, String employeesNumber, String employeesSurname, int employeesWage) {

        this.employeesSector = employeesSector;
        this.employeesNumber = employeesNumber;
        this.employeesSurname = employeesSurname;
        this.employeesWage = employeesWage;

    }

    @Override
    public String toString() {

        return employeesSector + "\n" +
                employeesNumber + "\n" +
                employeesSurname + "\n" +
                employeesWage;

    }

}

public class Main {

    static ArrayList<Record> listOfRecords = new ArrayList<>();
    static ArrayList<String> listOfEmployeesSectors = new ArrayList<>();

    public static void fillArrayList() {

        listOfRecords.add(new Record("Software engineering", "1", "Goncharov", 998));
        listOfRecords.add(new Record("Software engineering", "2", "Safonov", 998));
        listOfRecords.add(new Record("Software engineering", "3", "Ternovoy", 999));

        listOfRecords.add(new Record("Adjustment", "1", "Yakunin", 999));
        listOfRecords.add(new Record("Adjustment", "2", "Korolev", 999));
        listOfRecords.add(new Record("Adjustment", "3", "BetterAdjuster", 1000));

        listOfRecords.add(new Record("Consulting", "1", "Maksimova", 799));
        listOfRecords.add(new Record("Consulting", "2", "Verin", 899));
        listOfRecords.add(new Record("Consulting", "3", "BetterConsultant", 999));

    }

    public static void fillFile(String fileName) throws IOException {

        Path path = Paths.get("‪J:\\JavaProjects\\10Project\\newFile.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {

            for (Record listOfRecord : listOfRecords) {

                writer.write(listOfRecord.toString());

            }

        }

    }

    public static void readFile(String fileName) throws IOException {

        Scanner sc = new Scanner(new File(fileName));

        while (sc.hasNext()) {

            System.out.println(sc.nextLine());
        }

        sc.close();

    }

    public static void averageWages(String fileName) throws IOException {


    }

    public static void main(String[] args) throws IOException {

        String fileName = "newFile.txt";
        File fv = new File(fileName);
        fillArrayList();

        if (!fv.exists()) {

            fv.createNewFile();
            fillFile(fileName);

        } else {

            PrintWriter pw = new PrintWriter(fileName);
            pw.print("");
            fillFile(fileName);

        }

        readFile(fileName);

        averageWages(fileName);

    }
}
