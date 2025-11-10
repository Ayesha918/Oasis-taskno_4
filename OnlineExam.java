
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.HashMap;

public class OnlineExam {
    private String username;
    private String password;
    private boolean loggedIn = false;
    private int timerSeconds = 60; 
    private Timer timer;
    private HashMap<Integer, String> answers = new HashMap<>();

    public void login(Scanner sc) {
        System.out.print("Enter Username: ");
        username = sc.nextLine();
        System.out.print("Enter Password: ");
        password = sc.nextLine();
        loggedIn = true;
        System.out.println("\nLogin successful!");
        startTimer();
    }

    public void updateProfile(Scanner sc) {
        if (loggedIn) {
            System.out.print("Enter new Username: ");
            username = sc.nextLine();
            System.out.print("Enter new Password: ");
            password = sc.nextLine();
            System.out.println("\nProfile updated successfully!");
        } else {
            System.out.println("You must login first!");
        }
    }

    public void selectAnswer(Scanner sc) {
        if (loggedIn) {
            System.out.print("Enter Question Number: ");
            int questionId = sc.nextInt();
            sc.nextLine(); 
            System.out.print("Enter your Answer (A/B/C/D): ");
            String answer = sc.nextLine();
            answers.put(questionId, answer.toUpperCase());
            System.out.println("Answer saved for Question " + questionId);
        } else {
            System.out.println("You must login first!");
        }
    }

    private void startTimer() {
        timer = new Timer();
        System.out.println("\nTimer started for " + timerSeconds + " seconds...");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                autoSubmit();
            }
        }, timerSeconds * 1000);
    }

    private void autoSubmit() {
        System.out.println("\nTime is up! Submitting your answers automatically...");
        submitAnswers();
        logout();
    }

    public void submitAnswers() {
        if (loggedIn) {
            System.out.println("\n----- Submitted Answers -----");
            if (answers.isEmpty()) {
                System.out.println("No answers submitted.");
            } else {
                for (Integer q : answers.keySet()) {
                    System.out.println("Question " + q + " → " + answers.get(q));
                }
            }
            System.out.println("-----------------------------");
        }
    }

    public void logout() {
        loggedIn = false;
        if (timer != null) timer.cancel();
        System.out.println("You have been logged out. Thank you for attending the exam!");
    }

    public static void main(String[] args) {
        OnlineExamination exam = new OnlineExamination();
        Scanner sc = new Scanner(System.in);

        boolean exit = false;

        while (!exit) {
            System.out.println("\n===== Online Examination Menu =====");
            System.out.println("1. Login");
            System.out.println("2. Update Profile");
            System.out.println("3. Select Answer");
            System.out.println("4. Submit & Logout");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1:
                    exam.login(sc);
                    break;
                case 2:
                    exam.updateProfile(sc);
                    break;
                case 3:
                    exam.selectAnswer(sc);
                    break;
                case 4:
                    exam.submitAnswers();
                    exam.logout();
                    break;
                case 5:
                    System.out.println("Exiting the exam system...");
                    exam.logout();
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        sc.close();
        System.out.println("Program ended.");
    }
}
