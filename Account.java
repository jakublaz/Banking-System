import java.util.ArrayList;
import java.util.List;
public class Account {
    private final int ID;
    private double money;
    private final List<Transaction> transactions;

    public  Account(int ID){
        this.ID = ID;
        money=0;
        transactions = new ArrayList<>();
    }

    public void PrintDetails(){
        System.out.println("ID : " + ID);
        System.out.println("Money : " + money);
    }

    public void AddTransaction(Transaction transaction){
        transactions.add(transaction);
    }

    public void  PrintTransactions(){
        //I want to print this as  a window GUI, as a scrollable list
    }

    public int GetID(){
        return this.ID;
    }

    public void SetMoney(double money){
        this.money = money;
    }

    public double GetMoney(){
        return this.money;
    }

    public List<Transaction> GetTransactions() {
        return transactions;
    }
}
