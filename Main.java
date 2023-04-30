public class Main {
    public static void main(String[] args) {
        User a = new User("Ala","Kowalska",7);
        a.Print();
        a.SetMoney(5000);
        System.out.println(a.AddAccount());
        System.out.println(a.AddAccount());
    }
}
