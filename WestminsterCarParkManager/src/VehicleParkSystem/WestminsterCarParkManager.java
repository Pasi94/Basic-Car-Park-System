package VehicleParkSystem;

import java.sql.*;
import java.util.*;

/**
 * Created by Pasindu on 11/16/2016.
 */

public class WestminsterCarParkManager implements CarParkManager {

    private static Scanner sc = new Scanner(System.in);

    private static final String jdbc_driver = "com.mysql.jdbc.Driver";
    private static final String db_url = "jdbc:mysql://localhost/vehicleparksystem";
    private static final String db_usr = "root";
    private static final String db_psw = "admin123";

    private static int parkingSpace = 20;

    private static List<Vehicle> vehicles = new ArrayList<>();

    private static String[][] tracker = new String[parkingSpace][5];

    private static int entOrder = 0;

    private static void addVehicle() {

        String idPlate, brand;
        int freeSpace = 0;

        for (int i = 0; i < parkingSpace; i++) {
            if (tracker[i][1].equals("e")) {
                freeSpace++;
            }
        }

        if (freeSpace == 0) {
            System.out.println("\n\nCar Park is full !");
            mainMenu();
        } else {

            System.out.println("\nEmpty lot(s): " + freeSpace);
        }

        System.out.print("\nAdd vehicle" + "\n\n\t1 : Motorbike" + "\n\t2 : Car" + "\n\t3 : Van");

        System.out.print("\n\nSelect vehicle type : ");
        char vType = sc.nextLine().charAt(0);

        if (freeSpace == 1 && vType == '3') {
            System.out.println("\nOnly one lot available, cannot add a van !");
            mainMenu();
        }

        switch (vType) {
            case '1':

                idPlate = getUsrInIDPlate();

                brand = getUsrInBrand("motorbike");

                int engCapacity = 0;
                do {
                    try {
                        System.out.print("Enter engine capacity of the bike : ");
                        engCapacity = sc.nextInt();
                    } catch (InputMismatchException e) {

                        System.out.println("\nEnter valid amount of engine capacity !\n");
                        sc.nextLine();
                    }
                    if (engCapacity < 49 || engCapacity > 4000) {

                        System.out.println("\nEnter valid amount of engine capacity !\n");
                    }
                } while (engCapacity < 49 || engCapacity > 4000);

                sc.nextLine();

                Vehicle newBike = new Motorbike(idPlate, brand, engCapacity);

                for (int i = 0; i < parkingSpace; i++) {
                    if (tracker[i][1].equals("e")) {
                        vehicles.add(i, newBike);
                        tracker[i][1] = "o";
                        tracker[i][2] = idPlate;
                        tracker[i][3] = "Motorbike";
                        tracker[i][4] = String.valueOf(entOrder++);

                        break;
                    }
                }

                updateSQL("insert into parkingdetails (idplate, brand, engCapacity, type, entrydate, entrytime) VALUES (\""
                        + newBike.getIdPlate() + "\", \"" + newBike.getBrandOfVehicle() + "\", " + engCapacity +
                        ", \"Motorbike\", \"" + newBike.getEntranceDate() + "\", \"" + newBike.getEntranceTime() + "\")");

                System.out.println("\nVehicle added successfully !" + "\nRemaining free lot(s): " + (--freeSpace));
                break;

            case '2':
                idPlate = getUsrInIDPlate();

                brand = getUsrInBrand("car");

                int doors = 0;
                do {
                    try {
                        System.out.print("Enter number of doors in the car (1-7) : ");
                        doors = sc.nextInt();
                    } catch (InputMismatchException e) {

                        System.out.println("\nEnter valid amount of doors !\n");

                        sc.nextLine();
                    }
                } while (doors <= 1 || doors > 7);

                sc.nextLine();

                String color;
                do {
                    System.out.print("Enter color of the car : ");
                    color = sc.nextLine();

                    if (color.length() <= 20 && color.length() > 0) {
                        break;
                    } else {
                        System.out.print("\nEnter color of the car between 1-20 characters !\n\n");
                    }
                } while (true);

                Vehicle newCar = new Car(idPlate, brand, doors, color);

                for (int i = 0; i < parkingSpace; i++) {
                    if (tracker[i][1].equals("e")) {
                        vehicles.add(i, newCar);
                        tracker[i][1] = "o";
                        tracker[i][2] = idPlate;
                        tracker[i][3] = "Car";
                        tracker[i][4] = String.valueOf(entOrder++);

                        break;
                    }
                }

                updateSQL("insert into parkingdetails (idplate, brand, doors, color, type, entrydate, entrytime) VALUES (\""
                        + newCar.getIdPlate() + "\", \"" + newCar.getBrandOfVehicle() + "\", " + doors + ", \"" + color +
                        "\", \"Car\", \"" + newCar.getEntranceDate() + "\", \"" + newCar.getEntranceTime() + "\")");

                System.out.println("\nVehicle added successfully !" + "\nRemaining free lot(s): " + (--freeSpace));

                break;
            case '3':
                idPlate = getUsrInIDPlate();

                brand = getUsrInBrand("van");

                String cargo;
                do {
                    System.out.print("Enter cargo info of the van : ");
                    cargo = sc.nextLine();

                    if (cargo.length() <= 50 && cargo.length() > 0) {
                        break;
                    } else {

                        System.out.print("\nEnter cargo info of the van between 1-50 characters !\n\n");
                    }
                } while (true);

                Vehicle newVan = new Van(idPlate, brand, cargo);

                for (int i = 0; i < parkingSpace; i++) {
                    if (tracker[i][1].equals("e")) {
                        vehicles.add(i, newVan);
                        tracker[i][1] = "o";
                        tracker[i][2] = idPlate;
                        tracker[i][3] = "Van";
                        tracker[i][4] = String.valueOf(entOrder);

                        for (int j = 0; j < parkingSpace; j++) {
                            if (tracker[j][1].equals("e")) {
                                vehicles.add(j, newVan);
                                tracker[j][1] = "o";
                                tracker[j][2] = idPlate;
                                tracker[j][3] = "Van";
                                tracker[i][4] = String.valueOf(entOrder);

                                break;
                            }
                        }
                        break;
                    }
                }

                updateSQL("insert into parkingdetails (idplate, brand, cargo, type, entrydate, entrytime) VALUES (\""
                        + newVan.getIdPlate() + "\", \"" + newVan.getBrandOfVehicle() + "\", \"" + cargo +
                        "\", \"Van\", \"" + newVan.getEntranceDate() + "\", \"" + newVan.getEntranceTime() + "\")");

                entOrder++;
                --freeSpace;

                System.out.println("\nVehicle added successfully !" + "\nRemaining free lot(s): " + (--freeSpace));
                break;
            default:
                System.out.print("\n### Select a type from the list ! ###\n");

                addVehicle();
        }

        mainMenu();
    }

    private static void deleteVehicle() {

        boolean isDeleted = false;
        String type = "";
        int charges = 0;

        System.out.print("\n\nEnter ID plate number to delete vehicle : ");
        String idToDel = sc.nextLine().toUpperCase();

        for (int i = 0; i < parkingSpace; i++) {
            if (tracker[i][2].equals(idToDel)) {
                type = tracker[i][3];
                tracker[i][1] = "e";
                tracker[i][2] = "";
                tracker[i][3] = "";
                tracker[i][4] = "";

                charges = calcCharge(vehicles.get(Integer.valueOf(tracker[i][0])).getEntranceDate(),
                        vehicles.get(Integer.valueOf(tracker[i][0])).getEntranceTime());

                if (type.equals("Van")) {
                    for (int j = 0; j < parkingSpace; j++) {
                        if (tracker[j][2].equals(idToDel)) {
                            tracker[j][1] = "e";
                            tracker[j][2] = "";
                            tracker[j][3] = "";
                            tracker[i][4] = "";
                            break;
                        }
                    }
                }
                isDeleted = true;
                break;
            }
        }
        if (isDeleted) {

            DateTime dt = new DateTime();
            updateSQL("UPDATE parkingdetails SET leavedate = \"" + dt.getDate() +
                    "\", leavetime = \"" + dt.getTime() + "\" WHERE idplate = \"" + idToDel + "\"");

            System.out.print("\nVehicle deleted successfully, it's a " + type + ".\n" +
                    "Parking charges for the " + type + ": " + charges + "\u00a3");

        } else {
            System.out.println("\nThere is no matching vehicle parked.");
        }

        mainMenu();
    }

    private static void printCurrentVehicleList() {

        int[][] sortedArray = sortCurrentlyParked();
        if (sortedArray.length == 0) {
            System.out.println("\nThere is no vehicles currently parked.");
        } else {

            System.out.printf("%n%-12s %-22s %-11s", "ID Plate", "Entry date time", "Type");
            System.out.println("\n=====================================================");

            for (int i = sortedArray.length - 1; i >= 0; i--) {
                System.out.printf("%n%-12s %-22s %-11s",
                        tracker[sortedArray[i][0]][2],
                        vehicles.get(sortedArray[i][0]).getEntranceTime() + " " +
                                vehicles.get(sortedArray[i][0]).getEntranceDate(),
                        tracker[sortedArray[i][0]][3]);
            }
        }
        mainMenu();
    }

    private static void printStatistics() {

        double bike = 0, car = 0, van = 0;

        for (int i = 0; i < parkingSpace; i++) {
            if (!tracker[i][1].equals("e")) {
                if (tracker[i][3].equalsIgnoreCase("motorbike")) {
                    bike++;
                } else if (tracker[i][3].equalsIgnoreCase("car")) {
                    car++;
                } else {
                    van++;
                }
            }
        }
        van = van / 2;

        double total = bike + car + van;

        if (total != 0) {
            bike = (bike / total) * 100;
            car = (car / total) * 100;
            van = (van / total) * 100;
            total = (total / parkingSpace) * 100;
        }

        System.out.println("\nStatistics\n");
        System.out.printf("Motorbike percentage : %3.0f%%%n" +
                        "Car percentage : %3.0f%%%n" +
                        "Van percentage : %3.0f%%%n" +
                        "Parking space occupancy : %3.0f%%",
                bike, car, van, total);

        int[][] sortArray = sortCurrentlyParked();

        if (sortArray.length == 0) {
            System.out.printf("%n%nLast parked vehicle : N/A%n" +
                    "Longest parked vehicle : N/A%n");
        } else {

            System.out.printf("%n%nLast parked vehicle : %s  %s  %s%n" +
                            "Longest parked vehicle : %s  %s  %s%n",
                    tracker[sortArray[sortArray.length - 1][0]][2],
                    tracker[sortArray[sortArray.length - 1][0]][3],
                    vehicles.get(sortArray[sortArray.length - 1][0]).getEntranceTime() + " " +
                            vehicles.get(sortArray[sortArray.length - 1][0]).getEntranceDate(),

                    tracker[sortArray[0][0]][2],
                    tracker[sortArray[0][0]][3],
                    vehicles.get(sortArray[0][0]).getEntranceTime() + " " +
                            vehicles.get(sortArray[0][0]).getEntranceDate());
        }

        mainMenu();
    }

    private static void printTheListOfVehicle() {

        System.out.print("\nEnter a date to print the list (YYYY/MM/DD) : ");
        String date = sc.nextLine();

        if (date.matches("([0-9]{4})/([0-9]{2})/([0-9]{2})")) {

            if (Integer.valueOf(date.substring(0, 4)) > 1900
                    && Integer.valueOf(date.substring(0, 4)) < 2500
                    && Integer.valueOf(date.substring(5, 7)) > 0
                    && Integer.valueOf(date.substring(5, 7)) < 13
                    && Integer.valueOf(date.substring(8, 10)) > 0
                    && Integer.valueOf(date.substring(8, 10)) < 32) {

                Connection conn = null;
                Statement stmt = null;
                ResultSet rs;

                try {
                    Class.forName(jdbc_driver);
                    conn = DriverManager.getConnection(db_url, db_usr, db_psw);
                    stmt = conn.createStatement();

                    rs = stmt.executeQuery("SELECT * FROM parkingdetails WHERE DATE(entrydate) = \""
                            + date + "\" ORDER BY entrytime DESC");

                    if (rs.isBeforeFirst()) {

                        System.out.printf("%n%-12s %-22s %-22s %-11s", "ID Plate", "Entry date time", "Leave date time", "Type");
                        System.out.println("\n======================================================================");

                        while (rs.next()) {

                            String idPlate = rs.getString("idplate");
                            String type = rs.getString("type");
                            String entryDate = rs.getString("entrydate");
                            String entryTime = rs.getString("entrytime");
                            String leaveDate = rs.getString("leavedate");
                            String leaveTime = rs.getString("leavetime");

                            if (leaveDate == null) {
                                leaveDate = "PARKED";
                                leaveTime = "STILL";
                            }

                            System.out.printf("%n%-12s %-22s %-22s %-11s", idPlate,
                                    entryTime + " " + entryDate, leaveTime + " " + leaveDate, type);
                        }
                    } else {

                        System.out.println("There is no vehicles parked at the given date");
                    }

                } catch (SQLException | ClassNotFoundException e) {

                } finally {

                    try {
                        if (stmt != null) {
                            stmt.close();
                        }
                    } catch (SQLException e) {

                    }
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException e) {

                    }
                }
            } else {

                System.out.println("\nInvalid date, try entering valid date !\n");
                printTheListOfVehicle();
            }
        } else {

            System.out.println("\nEnter the date with given date format !\n");
            printTheListOfVehicle();
        }

        mainMenu();
    }

    private static void printParkingCharges() {

        int[][] sortedArray = sortCurrentlyParked();

        if (sortedArray.length == 0) {

            System.out.println("\nThere is no vehicles currently parked.");
        } else {

            System.out.printf("%n%-12s %-22s %-11s %-10s", "ID Plate", "Entry date time", "Type", "Charges");
            System.out.println("\n============================================================");

            for (int i = sortedArray.length - 1; i >= 0; i--) {
                System.out.printf("%n%-12s %-22s %-11s %-10s",
                        tracker[sortedArray[i][0]][2],
                        vehicles.get(sortedArray[i][0]).getEntranceTime() + " " +
                                vehicles.get(sortedArray[i][0]).getEntranceDate(),
                        tracker[sortedArray[i][0]][3],
                        calcCharge(vehicles.get(sortedArray[i][0]).getEntranceDate(),
                                vehicles.get(sortedArray[i][0]).getEntranceTime()) + "\u00a3");
            }
        }

        mainMenu();
    }

    private static void mainMenu() {

        System.out.print("\n\nMenu\n\n 1 : Add a new vehicle" + "\n 2 : Delete a vehicle"
                + "\n 3 : Print the list of currently parked vehicles" + "\n 4 : Print statistics of parking usage"
                + "\n 5 : Print the list of vehicles parked in a specific date"
                + "\n 6 : Print parking charges for each customer"
                + "\n 0 : Exit" + "\n\nSelect a action : ");

        char usrInput = sc.nextLine().charAt(0);

        switch (usrInput) {
            case '1':
                addVehicle();
                break;
            case '2':
                deleteVehicle();
                break;
            case '3':
                printCurrentVehicleList();
                break;
            case '4':
                printStatistics();
                break;
            case '5':
                printTheListOfVehicle();
                break;
            case '6':
                printParkingCharges();
                break;
            case '0':

                System.out.println("\nGOOD BYE !");
                System.exit(0);

            default:

                System.out.println("\nInvalid selection !");
                System.out.print("\nSelect a action : ");
                mainMenu();
        }
    }

    private static int calcCharge(String date, String time) {

        DateTime currentDT = new DateTime();
        String curr_D = currentDT.getDate();
        String curr_T = currentDT.getTime();

        int curr_yy = Integer.valueOf(curr_D.substring(0, 4));
        int curr_MM = Integer.valueOf(curr_D.substring(5, 7));
        int curr_dd = Integer.valueOf(curr_D.substring(8, 10));
        int curr_hh = Integer.valueOf(curr_T.substring(0, 2));
        int curr_mm = Integer.valueOf(curr_T.substring(3, 5));

        int entr_yy = Integer.valueOf(date.substring(0, 4));
        int entr_MM = Integer.valueOf(date.substring(5, 7));
        int entr_dd = Integer.valueOf(date.substring(8, 10));
        int entr_hh = Integer.valueOf(time.substring(0, 2));
        int entr_mm = Integer.valueOf(time.substring(3, 5));

        int diff_dd = numberOfDays(curr_yy, curr_MM, curr_dd)
                - numberOfDays(entr_yy, entr_MM, entr_dd);

        int diff_mm = ((curr_hh * 60) + curr_mm) - ((entr_hh * 60) + entr_mm);

        int tot_diff = (diff_dd * 24 * 60) + diff_mm;
        int charge = 0;

        while ((tot_diff / 60) >= 24) {
            charge += 30;
            tot_diff -= 1440;
        }

        while ((tot_diff / 60) > 3) {
            charge++;
            tot_diff -= 60;
        }

        while ((tot_diff / 60) > 0) {
            charge += 3;
            tot_diff -= 60;
        }

        if (tot_diff > 0 && tot_diff < 60 && charge >= 9) {

            charge++;
        } else if (tot_diff >= 0) {

            charge += 3;
        }

        return charge;
    }

    private static int numberOfDays(int year, int month, int day) {

        if (month <= 2) {
            year--;
            month += 13;
        } else {
            month++;
        }
        return (((1461 * year) / 4) + ((153 * month) / 5) + day);
    }

    private static String getUsrInIDPlate() {

        System.out.print("\nEnter ID-Plate number : ");
        String idPlate = sc.nextLine().toUpperCase();

        if (idPlate.length() <= 0 || idPlate.length() > 10) {
            System.out.print("\nEnter valid ID plate, between 1 to 10 characters !\n");
            getUsrInIDPlate();
        }
        return idPlate;
    }

    private static String getUsrInBrand(String type) {

        System.out.print("Enter brand of the " + type + " : ");
        String brand = sc.nextLine();

        if (brand.length() <= 0 || brand.length() > 50) {
            System.out.print("\nEnter brand name between 1 to 50 characters !\n");
            getUsrInBrand(type);
        }
        return brand;
    }

    private static void updateSQL(String sql) {

        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(jdbc_driver);

            conn = DriverManager.getConnection(db_url, db_usr, db_psw);

            stmt = conn.createStatement();

            stmt.executeUpdate(sql);

        } catch (SQLException | ClassNotFoundException e) {

        } finally {

            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {

            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {

            }
        }
    }

    private static int[][] sortCurrentlyParked() {

        List<String> tempVan = new ArrayList<>();

        int count = 0;

        for (int i = 0; i < parkingSpace; i++) {

            if (!tracker[i][1].equals("e")) {
                if (tracker[i][3].equalsIgnoreCase("Van") && !tempVan.contains(tracker[i][2])) {

                    tempVan.add(tracker[i][2]);
                    count++;

                } else if (!tracker[i][3].equalsIgnoreCase("Van")) {
                    count++;
                }
            }
        }
        tempVan.clear();

        int[][] sortArray = new int[count][2];
        count = 0;

        for (int i = 0; i < parkingSpace; i++) {

            if (!tracker[i][1].equals("e")) {
                if (tracker[i][3].equalsIgnoreCase("Van") && !tempVan.contains(tracker[i][2])) {
                    tempVan.add(tracker[i][2]);
                    sortArray[count][0] = i;
                    sortArray[count][1] = Integer.parseInt(tracker[i][4]);
                    count++;
                } else if (!tracker[i][3].equalsIgnoreCase("Van")) {
                    sortArray[count][0] = i;
                    sortArray[count][1] = Integer.parseInt(tracker[i][4]);
                    count++;
                }
            }
        }

        int temp1, temp2;

        for (int j = 1; j < sortArray.length; j++) {

            for (int i = 1; i < sortArray.length; i++) {
                if (sortArray[i - 1][1] > sortArray[i][1]) {
                    temp1 = sortArray[i][0];
                    temp2 = sortArray[i][1];
                    sortArray[i][0] = sortArray[i - 1][0];
                    sortArray[i][1] = sortArray[i - 1][1];
                    sortArray[i - 1][0] = temp1;
                    sortArray[i - 1][1] = temp2;
                }
            }
        }

        return sortArray;
    }

    public static void main(String[] args) {

        for (int i = 0; i < parkingSpace; i++) {
            tracker[i][0] = Integer.toString(i);
            tracker[i][1] = "e";
            tracker[i][2] = " ";
        }

        System.out.println("Welcome to Westminster Car Park System");

        mainMenu();
    }
}