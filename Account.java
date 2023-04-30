public class Account {
    private final int ID;
    private double money;

    public  Account(int ID){
        this.ID = ID;
        money=0;
    }

    public void PrintDetails(){
        System.out.println("ID : " + ID);
        System.out.println("Money : " + money);
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

}
