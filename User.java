import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private final String name;
    private final String surname;
    private final String login;
    private String password;
    private int age;
    private double money;
    private List<Account> accounts;


    public User(String name, String surname, String login, String password, int age) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.age = age;
        this.surname = surname;
        this.money = 0;
        accounts = new ArrayList<>();
    }

    public User(String name, String surname, String login, String password, int age, double money) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.age = age;
        this.surname = surname;
        this.money = money;
        accounts = new ArrayList<>();
    }

    public void PrintUser() {
        System.out.println("Name: " + this.name);
        System.out.println("Surname: " + this.surname);
        System.out.println("Age: " + this.age);
        System.out.println("Money: " + this.money);
    }

    public void PrintAccount(int ID) {
        for (Account account : accounts) {
            if (account.GetID() == ID) {
                account.PrintDetails();
            }
        }
    }

    public void PrintAccounts() {
        for (Account account : accounts) {
            account.PrintDetails();
        }
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
        accounts.add(account);
        return true;
    }

    private @Nullable Account FindAccount(int ID) {
        for (Account account : accounts) {
            if (account.GetID() == ID) {
                return account;
            }
        }
        return null;
    }

    public boolean TransferMoney_ToAccount(int ID, double money) {
        if (this.money < money) {
            return false;
        }
        if (FindAccount(ID) == null) {
            return false;
        }
        this.money -= money;
        Objects.requireNonNull(FindAccount(ID)).SetMoney(Objects.requireNonNull(FindAccount(ID)).GetMoney() + money);
        return true;
    }

    private boolean TransferMoney_ToUser(int ID, double money) {
        if (FindAccount(ID) == null) {
            return false;
        }
        if (Objects.requireNonNull(FindAccount(ID)).GetMoney() < money) {
            return false;
        }
        this.money += money;
        Objects.requireNonNull(FindAccount(ID)).SetMoney(Objects.requireNonNull(FindAccount(ID)).GetMoney() - money);
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
            TransferMoney_ToUser(ID, Objects.requireNonNull(FindAccount(ID)).GetMoney());
        }
        accounts.remove(FindAccount(ID));
        return true;

    }
}
