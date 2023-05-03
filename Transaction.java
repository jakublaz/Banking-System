public class Transaction {
    final int ID;
    final int ID_A1;
    final int ID_A2;
    final double money;
    final String type;

    public Transaction(int id, int idA1, int idA2, double money, String type){
        ID = id;
        ID_A1 = idA1;
        ID_A2 = idA2;
        this.money = money;
        this.type = type;
    }
}
