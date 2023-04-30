public class Account {
    private final int ID;
    private double money;
    private static int number=0;

    public  Account(){
        this.ID = number+1;
        number++;
        money=0;
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
