import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class User {
    private String userId;
    private String pin;
    private double balance;
    private List<String> transactionHistory;

    public User(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public boolean checkPin(String pin) {
        return this.pin.equals(pin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited $" + amount);
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactionHistory.add("Withdrawn $" + amount);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void transfer(User receiver, double amount) {
        if (balance >= amount) {
            balance -= amount;
            receiver.deposit(amount);
            transactionHistory.add("Transferred $" + amount + " to " + receiver.getUserId());
        } else {
            System.out.println("Insufficient balance for the transfer.");
        }
    }

    public void showTransactionHistory() {
        System.out.println("Transaction History for " + userId);
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}

public class ATMSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Sample user accounts (You can expand this with more users)
        User user1 = new User("123456", "1234");
        User user2 = new User("789012", "5678");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        System.out.print("Enter User ID: ");
        String userId = scanner.next();
        System.out.print("Enter PIN: ");
        String pin = scanner.next();

        User currentUser = null;

        for (User user : users) {
            if (user.getUserId().equals(userId) && user.checkPin(pin)) {
                currentUser = user;
                break;
            }
        }

        if (currentUser == null) {
            System.out.println("Invalid User ID or PIN.");
            return;
        }

        while (true) {
            System.out.println("Select an option:");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    currentUser.showTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: $");
                    double withdrawAmount = scanner.nextDouble();
                    currentUser.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: $");
                    double depositAmount = scanner.nextDouble();
                    currentUser.deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter recipient's User ID: ");
                    String recipientId = scanner.next();
                    User receiver = null;
                    for (User user : users) {
                        if (user.getUserId().equals(recipientId)) {
                            receiver = user;
                            break;
                        }
                    }
                    if (receiver != null) {
                        System.out.print("Enter amount to transfer: $");
                        double transferAmount = scanner.nextDouble();
                        currentUser.transfer(receiver, transferAmount);
                    } else {
                        System.out.println("Recipient not found.");
                    }
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM.");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please select a valid option.");
            }
        }
    }
}
