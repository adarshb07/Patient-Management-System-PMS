import java.sql.*;
import java.util.*;
import java.io.IOException;

public class App {
    
    static String currentUser= null;
    public static void main(String[] args) throws Exception {
        clearScreen();
        mainMenu();
    }

    //connect to database module
    static Statement connect() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/pms";
            String username = "root";
            String password = "1234";
            Connection conn = DriverManager.getConnection(url, username, password); //To Establish connection
            Statement stmt = conn.createStatement();
            return stmt;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    //main Menu module
    static void mainMenu() throws Exception{
        int i = 0;
        while (i == 0) {
            clearScreen();
            welcome();
            System.out.println("Please select an option");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.println("----------------------------------------");
            System.out.print("Option: ");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    clearScreen();
                    login();
                    break;
                case 2:
                    clearScreen();
                    registration();
                    break;
                case 3:
                    clearScreen();
                    closeCmd();
                    System.exit(0);
                    break;

                default:
                    clearScreen();
                    System.out.println("Invalid choice");
                    System.out.println("Please Select a valid option");
                    System.out.println("Press any key to continue");
                    sc.nextLine();
                    sc.nextLine();
                    break;
            }
        }
    }

    //Registration module
    static void registration() throws Exception {
        System.out.println("========================================");
        System.out.println("Registration Form");
        System.out.println("========================================");

        Statement stmt = connect();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name:");
        String name = sc.nextLine();
        // System.out.println("Enter phone number");
        // long number = sc.nextLong();
        // System.out.println("Enter email");
        // String email = sc.next();

        String number = null;
        while (true) {
            System.out.println("Enter phone number (10 digits):");
            number = sc.nextLine();
            if (number.matches("\\d{10}")) {
                break;
            }
            System.out.println("Invalid phone number. Please enter 10 digits.");
        }

        String email = null;
        while (true) {
            System.out.println("Enter email address:");
            email = sc.nextLine();
            if (email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                break;
            }
            System.out.println("Invalid email address. Please enter a valid email address.");
        }

        System.out.println("Enter age:");
        int age = sc.nextInt();

        System.out.println("Enter M/F");
        char ch = sc.next().charAt(0);

        sc.nextLine();
        System.out.println("Enter address:");
        String addr = sc.nextLine();

        System.out.println("Do you have any disability?[True/False]");
        Boolean dis = sc.nextBoolean();
        System.out.println("Enter password");
        String password = sc.next();
        int userid = generateRandomNumber();

        String sql = "INSERT INTO users (userid,name, phonenumber, email, age, gender, address, password, disability)VALUES ("
                + userid + ",'" + name + "'," + number + ",'" + email + "'," + age + ",'" + ch + "','" + addr
                + "','" + password + "'," + dis + ");";
        stmt.executeUpdate(sql);
        System.out.println("Please note down your user id!!!");
        System.out.println(userid);

        clearScreen();
        System.out.println("========================================");
        System.out.println("Registration Successful");
        System.out.println("========================================");

        System.out.println("Press any key to continue");
        sc.nextLine();
        sc.nextLine();
        clearScreen();
    }

    //generate random number module
    public static int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(900000) + 100000;
    }

    //clear screen module
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception ex) {

        }
    }

    //close CMD if running on windows and exit if cmd running with admin privileges
    public static void closeCmd() {
        ProcessBuilder pb = new ProcessBuilder("taskkill", "/IM", "cmd.exe");
        try {
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void welcome() {
        System.out.println("========================================");
        System.out.println("Welcome to the Patient Management System");
        System.out.println("========================================");
    }

    //login Module
    static void login() throws Exception {

        System.out.println("========================================");
        System.out.println("Login Form");
        System.out.println("========================================");

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your email:");
        String email = sc.next();
        System.out.println("Enter your password:");
        String password = sc.next();

        Statement stmt = connect();
        String sql = "SELECT * FROM users WHERE email='" + email + "' AND password='" + password + "';";
        // System.out.println("SQL query: " + sql);
        ResultSet rs = stmt.executeQuery(sql);

        if (rs.next()) {
            clearScreen();
            System.out.println("========================================");
            System.out.println("Login Successful");
            System.out.println("========================================");
            // System.out.println("Welcome " + rs.getString("name"));
            currentUser = rs.getString("name");
            afterLogin();
        } else {
            clearScreen();
            System.out.println("========================================");
            System.out.println("Login Failed: Invalid email or password");
            System.out.println("========================================");
        }

        System.out.println("Press any key to continue");
        sc.nextLine();
        sc.nextLine();
        clearScreen();
    }

    //After login module
    static void afterLogin() throws Exception {
        int i = 0;
        while (i == 0) {
            clearScreen();
            System.out.println("------------------------------------------------------------------------------");
            System.out.println("Welcome " + currentUser+" to the Patient Management System(PMS)");
            System.out.println("------------------------------------------------------------------------------");
            System.out.println("Please select an option");
            System.out.println ("========================================");
            System.out.println("1. Book Appointment");
            System.out.println("2. View Appointment");
            System.out.println("3. Cancel Appointment");
            System.out.println("4. Add Medical History");
            System.out.println("5. Medicine Suggestion");
            System.out.println("6. Current Medication");
            System.out.println("7. Previous Medication");
            System.out.println("8. View Medical History");
            System.out.println("9. Edit Profile");
            System.out.println("10. Logout");
            System.out.println ("========================================");
            System.out.print("Option: ");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    clearScreen();
                    // bookAppointment();
                    break;
                case 2:
                    clearScreen();
                    // viewAppointment();
                    break;
                case 3:
                    clearScreen();
                    // cancelAppointment();
                    break;
                case 4:
                    clearScreen();
                    // addMedicalHistory();
                    break;
                case 5:
                    mds();
                    break;
                case 6:
                    clearScreen();
                    //currentMedication();
                    break;
                case 7:
                    clearScreen();
                    // previousMedication();
                    break;
                case 8:
                    clearScreen();
                    // viewMedicalHistory();
                    break;
                case 9:
                    clearScreen();
                    // editProfile();
                    break;
                case 10:
                    clearScreen();
                    mainMenu();
                    i = 1;
                    break;
                default:
                    clearScreen();
                    System.out.println("Invalid choice");
                    System.out.println("Please Select a valid option");
                    System.out.println("Press any key to continue");
                    sc.nextLine();
                    sc.nextLine();
                    break;
            }
        }
    }


    static void mds()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("USAGE WARNING:");
        System.out.println("Please DO NOT take these suggestions over any prescribed medicines");
        System.out.println("If you have a history of allergic reactions, we advise you to not use this module");
        System.out.println("You are advised not to take any medications WITHOUT CONSULTING a doctor if the symptoms are severe and not improving.");
        System.out.println("Please enter your symptoms (separated by commas): ");
        String symptoms = input.nextLine();
        System.out.println("Enter time in days since the symptoms started");
        int time=input.nextInt();
        String[] symptomList = symptoms.split(",");
        
         if (isSevere(symptomList,time)) 
            {
            System.out.println("Your symptoms are severe. Please see a doctor immediately.");
            } 
        else {
            suggestMedicine(symptomList); 
            }
    }
    public static boolean isSevere(String[] symptoms, int time) {
        // List of 10 most common symptoms
        String[] commonSymptoms = {"fever", "cough", "vomiting", "fatigue", "muscle aches","body ache", "headache", "sore throat", "congestion","runny nose", "nausea", "diarrhea"};
        if(time>=10)
            return true;
        int count = 0;
        for (String symptom : symptoms) {
            if (Arrays.asList(commonSymptoms).contains(symptom.trim().toLowerCase())) {
                count++;
            }
        }
        return count < 3;
    }
    public static void suggestMedicine(String[] symptoms) {
        List<String> matches = new ArrayList<>();
        String[] commonSymptoms = {"fever", "cough", "vomiting", "fatigue", "muscle aches","body ache", "headache", "sore throat", "congestion","runny nose", "nausea", "diarrhea"};
        for (String symptom : symptoms) {
            if (Arrays.asList(commonSymptoms).contains(symptom)) {
                matches.add(symptom);
            }
        }
            if (matches.size() > 0) {
                System.out.println("You have the following symptoms: " + String.join(", ", matches));
                for (String symptom : matches) {
                    switch (symptom) {
                        case "fever":
                            System.out.println("Take Dolo. Dosage: 650 mg for adults and 250 mg for children.");
                            break;
                        case "cough":
                            System.out.println("Take Benadryl.");
                            break;
                        case "vomiting":
                            System.out.println("Take small sips of oral rehydration solution (ORS) to prevent dehydration. Eat easy-to-digest foods like toast, crackers, gelatin or other similar foods to ease an upset stomach.");
                            break;
                        case "fatigue":
                            System.out.println("Maintain a regular sleep schedule, eat a healthy diet, and exercise regularly. Consuming less caffeine during the day and avoiding caffeine at night may also help. Take some ORS and eat lots of fruits.");
                            break;
                        case "muscle aches":
                            System.out.println("Take over-the-counter pain relievers such as aspirin, acetaminophen, ibuprofen or naproxen.");
                            break;
                        case "body ache":
                            System.out.println("Take over-the-counter pain relievers such as aspirin, acetaminophen, ibuprofen or naproxen.");
                            break;
                        case "headache":
                            System.out.println("Remedies that may reduce headache pain include aspirin, paracetamol and ibuprofen. Resting in a darkened room may also help.");
                            break;
                        case "sore throat":
                            System.out.println("Taking pain medication such as ibuprofen and paracetamol may help. Avoid giving aspirin to children because this may cause a rare, serious condition.");
                            break;
                        case "congestion":
                            System.out.println("Inhale karvol plus.");
                            break;
                        case "runny nose":
                            System.out.println("Drink plenty of water and use a humidifier to relieve symptoms. If the runny nose is caused by allergies, taking a non-sedating antihistamine may also help.");
                            break;
                        case "nausea":
                            System.out.println("Take Kaopectate or Pepto-Bismol. Resting, eating bland foods and avoiding strong food odours, perfume, smoke and stuffy rooms may help to reduce nausea.");
                            break;
                        case "diarrhea":
                            System.out.println("Replace lost fluids with an oral rehydration solution (ORS) and take antidiarrhoeal drugs such as loperamide to help prevent dehydration.");
                            break;
                    }
                }
            } else {
                System.out.println("We could not find any matches for your symptoms or your symptoms are not common. Please see a doctor.");
            }
    }
    


}
