import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private final String name;
    private final String surname;
    private int age;
    private double money;
    private List<Account> accounts;


    public User(String name, String surname, int age) {
        this.name = name;
        this.age = age;
        this.surname = surname;
        this.money = 0;
        accounts = new ArrayList<>();
    }

    public User(String name, String surname, int age, double money) {
        this.name = name;
        this.age = age;
        this.surname = surname;
        this.money = money;
        accounts = new ArrayList<>();
    }

    public void Print() {
        System.out.println("Name: " + this.name);
        System.out.println("Surname: " + this.surname);
        System.out.println("Age: " + this.age);
        System.out.println("Money: " + this.money);
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

    public int AddAccount(){
        Account account = new Account();
        accounts.add(account);
        return account.GetID();
    }

    private @Nullable Account FindAccount(int ID) {
        for (Account account : accounts) {
            if (account.GetID() == ID) {
                return account;
            }
        }
        return null;
    }

    public boolean TransferMoney(int ID, double money) {
        if (this.money < money) {
            return false;
        }
        if(FindAccount(ID) == null){
            return false;
        }
        this.money -= money;
        Objects.requireNonNull(FindAccount(ID)).SetMoney(Objects.requireNonNull(FindAccount(ID)).GetMoney()+money);
        return true;
    }

}
