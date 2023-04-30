public class Main {
    public static void main(String[] args) {
        //sout == System.out.println();
        User a = new User("Ala","Kowalska",7);
        a.SetMoney(5000);
        a.PrintUser();
        a.AddAccount(1);
        a.AddAccount(2);
        a.TransferMoney_ToAccount(1,1000);
        a.TransferMoney_ToAccount(2,3000);
        a.TransferMoney_BetweenAccounts(2,1,1500);
        a.PrintAccounts();
        a.CloseAccount(2);
        a.PrintUser();
    }
}
