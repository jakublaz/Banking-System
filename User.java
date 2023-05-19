import org.jetbrains.annotations.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class User {
    private final String name;
    private final String surname;
    private final String login;
    private final String password;
    private int age;
    private double money;
    private final List<Account> accountsPolish;
    private final Map<String,Account> accountsOther;


    public User(String name, String surname, String login, String password, int age) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.age = age;
        this.surname = surname;
        this.money = 0;
        accountsPolish = new ArrayList<>();
        accountsOther = new HashMap<>();
    }

    public User(String name, String surname, String login, String password, int age, double money) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.age = age;
        this.surname = surname;
        this.money = money;
        accountsPolish = new ArrayList<>();
        accountsOther = new HashMap<>();
    }

    public void SetMoney(double money) {
        this.money = money;
    }

    public double GetMoney() {
        return this.money;
    }

    public void SetAge(int age) {
        this.age = age;
    }

    public int GetAge() {
        return this.age;
    }

    public String GetName() {
        return this.name;
    }

    public String GetSurname() {
        return this.surname;
    }

    public String GetLogin() {
        return this.login;
    }

    public boolean AddAccount(int ID) {
        if(FindAccount(ID) != null){
            return false;
        }
        Account account = new Account(ID);
        accountsPolish.add(account);
        return true;
    }

    public @Nullable Account FindAccount(int ID) {
        for (Account account : accountsPolish) {
            if (account.GetID() == ID) {
                return account;
            }
        }
        return null;
    }

    public boolean TransferMoney_ToAccount(int ID, double money) {
        Objects.requireNonNull(FindAccount(ID)).SetMoney(Objects.requireNonNull(FindAccount(ID)).GetMoney() + money);
        return true;
    }

    //The first one ID means from where we want to transfer money and the second one means where we want to transfer money
    public boolean TransferMoney_BetweenAccounts(int ID, int ID2, double money) {
        if (FindAccount(ID) == null) {
            return false;
        }
        if (FindAccount(ID2) == null) {
            return false;
        }
        if (Objects.requireNonNull(FindAccount(ID)).GetMoney() < money) {
            return false;
        }
        Objects.requireNonNull(FindAccount(ID)).SetMoney(Objects.requireNonNull(FindAccount(ID)).GetMoney() - money);
        Objects.requireNonNull(FindAccount(ID2)).SetMoney(Objects.requireNonNull(FindAccount(ID2)).GetMoney() + money);
        return true;
    }

    public boolean CloseAccount(int ID){
        if(FindAccount(ID) == null){
            return false;
        }
        if(Objects.requireNonNull(FindAccount(ID)).GetMoney() != 0){
            JOptionPane.showMessageDialog(null, "You can't close account with money on it", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        accountsPolish.remove(FindAccount(ID));
        return true;

    }

    public String GetPassword() {
        return password;
    }

    public List<Account> GetAccountsPolish() {
        return accountsPolish;
    }

    public Map<String,Account> GetAccountsOther(){
        return accountsOther;
    }

    public void ShowUserData(JPanel panel){
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JLabel retrievedLabel) {

                if (retrievedLabel.getName().equals("labelmoney")) {
                    // Modify the text of the retrieved label
                    retrievedLabel.setText("Money: " + money);
                    continue;
                }

                if (retrievedLabel.getName().equals("labelname")) {
                    // Modify the text of the retrieved label
                    retrievedLabel.setText("Name: " + name);
                    continue;
                }

                if (retrievedLabel.getName().equals("labelsurname")) {
                    // Modify the text of the retrieved label
                    retrievedLabel.setText("Surname: " + surname);
                    continue;
                }

                if (retrievedLabel.getName().equals("labelage")) {
                    // Modify the text of the retrieved label
                    retrievedLabel.setText("Age: " + age);
                }

            }
        }

        panel.revalidate();
        panel.repaint();
    }

    public double AddMoney(double money){
        this.money += money;
        return this.money;
    }

    public boolean WithdrawMoney(int ID, double money) {
        Objects.requireNonNull(FindAccount(ID)).SetMoney(Objects.requireNonNull(FindAccount(ID)).GetMoney()-money);
        this.money -= money;
        Transaction transaction = new Transaction(ID, 0,money,"Withdraw");
        Objects.requireNonNull(FindAccount(ID)).AddTransaction(transaction);
        return true;
    }
}
