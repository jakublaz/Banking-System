public class Transaction {
    int ID_main = 1;
    final int ID;
    final int ID_A1;    //account we get money from
    final int ID_A2;    //account we give money to
    final double money;
    final String type;

    public Transaction(int idA1, int idA2, double money, String type){
        ID = ID_main++;
        ID_A1 = idA1;
        ID_A2 = idA2;
        this.money = money;
        this.type = type;
    }

    public void PrintDetails(){
        System.out.println("ID : " + ID);
        System.out.println("ID_A1 : " + ID_A1);
        System.out.println("ID_A2 : " + ID_A2);
        System.out.println("Money : " + money);
        System.out.println("Type : " + type);
    }

    public int GetID(){
        return this.ID;
    }

    public int GetID_A1(){
        return this.ID_A1;
    }

    public int GetID_A2(){
        return this.ID_A2;
    }

    public double GetMoney(){
        return this.money;
    }

    public String GetType(){
        return this.type;
    }
}
