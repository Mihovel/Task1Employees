import java.io.*;
import java.util.*;
import java.nio.file.*;

public class Main {

    static ArrayList<Record> listOfRecords = new ArrayList<>();
    static ArrayList<String> listOfSectors = new ArrayList<>();
    static ArrayList<Integer> listOfEmployeesInSector = new ArrayList<>();
    static ArrayList<String> listOfBestExchanges = new ArrayList<>();
    static String nameOfFileBestExchanges = "bestExchanges.txt";

    public static void fillArrayList() {

        listOfRecords.add(new Record("Software engineering", "1", "Goncharov", 600));
        listOfRecords.add(new Record("Software engineering", "2", "Safonov", 600));
        listOfRecords.add(new Record("Software engineering", "3", "Ternovoy", 600));

        listOfRecords.add(new Record("Adjustment", "1", "Yakunin", 999));
        listOfRecords.add(new Record("Adjustment", "2", "Korolev", 999));
        listOfRecords.add(new Record("Adjustment", "3", "BetterAdjuster", 10000));

        listOfRecords.add(new Record("Consulting", "1", "Maksimova", 799));
        listOfRecords.add(new Record("Consulting", "2", "Verin", 899));
        listOfRecords.add(new Record("Consulting", "3", "BetterConsultant", 999));

        for (Record listOfRecord : listOfRecords) {

            if (!listOfSectors.contains(listOfRecord.getEmployeesSector())) {

                listOfSectors.add(listOfRecord.getEmployeesSector());

            }

        }

    }

    public static void fillFile(String fileName) throws IOException {

        Path path = Paths.get(fileName);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {

            for (Record listOfRecord : listOfRecords) {

                writer.write(listOfRecord.toString());

            }

        }

    }

    public static void readFile(String fileName) throws IOException {

        Files.lines(Paths.get(fileName)).forEach(System.out::println);

    }

    public static void averageWages() {

        for (String listOfSector : listOfSectors) {

            int sumWageInSector = 0;
            int employeesInSector = 0;

            for (Record listOfRecord : listOfRecords) {

                if (listOfRecord.getEmployeesSector().equals(listOfSector)) {

                    sumWageInSector += listOfRecord.getEmployeesWage();
                    employeesInSector++;

                }

            }

            listOfEmployeesInSector.add(employeesInSector);

            System.out.println("In sector of " + listOfSector + ": " + sumWageInSector / employeesInSector);

        }

    }

    public static void bestExchanges() throws IOException {

        for (int i = 0; i < listOfSectors.size(); i++) {

            String currentSector = listOfSectors.get(i);
            List<Record> suitableListForCurrentSector = new ArrayList<>();

            for (Record listOfRecord : listOfRecords) {

                if (!listOfRecord.getEmployeesSector().equals(currentSector)) {

                    suitableListForCurrentSector.add(listOfRecord);

                }

            }

            String[] subsetsAsStringsForCurrentSector = new String[(int) Math.pow(2, listOfRecords.size() - listOfEmployeesInSector.get(i)) - 2]; //ВРОДЕ -2??????????????????
            createAllPossibleSubSetsUsingArrayOfStrings(currentSector, subsetsAsStringsForCurrentSector, suitableListForCurrentSector);

        }

    }

    public static String correctString(List<Record> suitableListForCurrentSector, String currentString, String currentSector) {

        String returnAllSurnames = "";

        for (int i = 0; i < currentString.length(); i++) {

            if (Character.toString(currentString.charAt(i)).equals("1")) {

                returnAllSurnames = returnAllSurnames + suitableListForCurrentSector.get(i).getEmployeesSurname() + " , ";

            }

        }

        String result = "";

        for (int i = 0; i < returnAllSurnames.length() - 2; i++) {

            result += Character.toString(returnAllSurnames.charAt(i));

        }


        return result + "to " + currentSector;

    }


    public static void createAllPossibleSubSetsUsingArrayOfStrings(String currentSector, String[] subsetsAsStringsForCurrentSector, List<Record> suitableListForCurrentSector) {

        for (int i = 1; i < Math.pow(2, suitableListForCurrentSector.size()) - 1; i++) {

            subsetsAsStringsForCurrentSector[i - 1] = Integer.toBinaryString(i);

        }

        for (int i = 0; i < subsetsAsStringsForCurrentSector.length - 2; i++) { //ТОЧНО -2??????????????????????????????????????????????????????????

            if (subsetsAsStringsForCurrentSector[i].length() < suitableListForCurrentSector.size()) {

                String addString = "";

                for (int j = 0; j < suitableListForCurrentSector.size() - subsetsAsStringsForCurrentSector[i].length(); j++) {

                    addString += "0";

                }

                subsetsAsStringsForCurrentSector[i] = addString + subsetsAsStringsForCurrentSector[i];

            }

        }

        for (String currentString : subsetsAsStringsForCurrentSector) {

            List<Integer> listOfChosen = new ArrayList<>(); //позиции единицы в строке
            int exchanging = 0; //число рабочих, которых перебрасваем из одного отдела в другой
            /* FIXED */
            int countSumOfExchangingEmployees = 0;

            for (int j = 0; j < currentString.length(); j++) {

                if (Character.toString(currentString.charAt(j)).equals("1")) {

                    listOfChosen.add(j);
                    exchanging++;
                    /* FIXED */
                    countSumOfExchangingEmployees = countSumOfExchangingEmployees + suitableListForCurrentSector.get(j).getEmployeesWage();

                }

            }

            String sectorOfFirstEmployeeForChecking = suitableListForCurrentSector.get(listOfChosen.get(0)).getEmployeesSector();
            boolean isTheSameSector = listOfChosen.stream().allMatch(x -> suitableListForCurrentSector.get(x).getEmployeesSector().equals(sectorOfFirstEmployeeForChecking));

            if (isTheSameSector) {

                List<Record> checkingEmployeesFromOneSector = new ArrayList<>();

                for (Integer integer : listOfChosen) {

                    checkingEmployeesFromOneSector.add(suitableListForCurrentSector.get(integer));

                }

                int countEmployeesInCurrentSector = 0;
                int WageOfAllEmployeesInCurrentSector = 0;
                int countEmployeesInExchangeSector = 0;
                int WageOfAllEmployeesInExchangeSector = 0;

                for (Record listOfRecord : listOfRecords) {

                    if (listOfRecord.getEmployeesSector().equals(currentSector)) {

                        countEmployeesInCurrentSector++;
                        WageOfAllEmployeesInCurrentSector += listOfRecord.getEmployeesWage();

                    }

                    if (listOfRecord.getEmployeesSector().equals(sectorOfFirstEmployeeForChecking)) {

                        countEmployeesInExchangeSector++;
                        WageOfAllEmployeesInExchangeSector += listOfRecord.getEmployeesWage();

                    }

                }

                int averageWageInCurrentSector = WageOfAllEmployeesInCurrentSector / countEmployeesInCurrentSector;
                int averageWageInExchangeSector = WageOfAllEmployeesInExchangeSector / countEmployeesInExchangeSector;

                if (countEmployeesInExchangeSector - exchanging != 0) {

                    /* FIXED */
                    boolean ch = (averageWageInCurrentSector < ((averageWageInCurrentSector * countEmployeesInCurrentSector + countSumOfExchangingEmployees) / (countEmployeesInCurrentSector + exchanging)) & (averageWageInExchangeSector < (averageWageInExchangeSector * countEmployeesInExchangeSector - countSumOfExchangingEmployees) / (countEmployeesInExchangeSector - exchanging)));
                    //TODO Исправить эту цифру, добавить её как переменную
                    if (ch) {

                        listOfBestExchanges.add(correctString(suitableListForCurrentSector, currentString, currentSector));

                    }

                }

            }

        }

    }

    public static void writeFileOfBestExchanges(List<String> listOfBestExchanges) throws IOException {

        for (String listOfBestExchange : listOfBestExchanges) {
            System.out.println(listOfBestExchange);
        }

        File fv = new File(nameOfFileBestExchanges);

        if (!fv.exists()) {

            fv.createNewFile();
            FileWriter writer = new FileWriter(nameOfFileBestExchanges);
            for (String listOfBestExchange : listOfBestExchanges) {

                writer.write(listOfBestExchange + System.lineSeparator());

            }

        } else {

            PrintWriter pw = new PrintWriter(fv);
            pw.print("");
            pw.close();

            FileWriter writer = new FileWriter(nameOfFileBestExchanges);
            for (String listOfBestExchange : listOfBestExchanges) {

                writer.write(listOfBestExchange + System.lineSeparator());

            }

        }

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
            pw.close();

        }

        readFile(fileName);

        System.out.println("........................");

        averageWages();

        System.out.println("........................\n");

        System.out.println("Best exchanges: \n");

        bestExchanges();

        writeFileOfBestExchanges(listOfBestExchanges);

    }

}
