import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GUI implements ActionListener {
    String name = "Banking Friend";

    static JFrame frame;
    static JLabel labellogin;
    static JTextField textlogin;
    static JLabel labelpassword;
    static JPasswordField textpassword;
    static JButton buttonlogin;
    static JButton buttonregister;

    public Map<String, String> users = new HashMap<>();

    public static User CreateUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj imie: ");
        String name = scanner.nextLine();
        System.out.println("Podaj nazwisko: ");
        String surname = scanner.nextLine();
        System.out.println("Podaj wiek: ");
        int age = scanner.nextInt();
        return new User(name,surname,age);
    }
//    public static void main(String[] args) {
//        //sout == System.out.println();
//        User a = new User("Ala","Kowalska",7);
//        a.SetMoney(5000);
//        a.PrintUser();
//        a.AddAccount(1);
//        a.AddAccount(2);
//        a.TransferMoney_ToAccount(1,1000);
//        a.TransferMoney_ToAccount(2,3000);
//        a.TransferMoney_BetweenAccounts(2,1,1500);
//        a.PrintAccounts();
//        a.CloseAccount(2);
//        a.PrintUser();
//        User b = new User("Jan","Kowalski",17,100);
//        TransaferBetweenUsers(a,b,1000);
//        b.PrintUser();
//    }

    public static void main(String[] args) {
        CreateGUI_Login();
    }

    public static void CreateGUI_Login(){
        frame = new JFrame("Bank");
        frame.setSize(2560,1440);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        labellogin = new JLabel("Login");
        labellogin.setSize(240,60);
        labellogin.setLocation(1000,470);
        labellogin.setFont(labellogin.getFont().deriveFont(40f));
        labellogin.setVisible(true);

        textlogin = new JTextField();
        textlogin.setSize(240,40);
        textlogin.setLocation(1200,480);
        textlogin.setFont(textlogin.getFont().deriveFont(40f));
        textlogin.setVisible(true);

        labelpassword = new JLabel("Password");
        labelpassword.setSize(240,60);
        labelpassword.setLocation(1000,550);
        labelpassword.setFont(labelpassword.getFont().deriveFont(40f));
        labelpassword.setVisible(true);

        textpassword = new JPasswordField();
        textpassword.setSize(240,40);
        textpassword.setLocation(1200,560);
        textpassword.setFont(textpassword.getFont().deriveFont(40f));
        textpassword.setVisible(true);

        buttonlogin = new JButton("Login");
        buttonlogin.setSize(240,60);
        buttonlogin.setLocation(1200,640);
        buttonlogin.setFont(buttonlogin.getFont().deriveFont(40f));
        buttonlogin.addActionListener(new GUI());
        buttonlogin.setVisible(true);

        buttonregister = new JButton("Register");
        buttonregister.setSize(240,60);
        buttonregister.setLocation(1200,720);
        buttonregister.setFont(buttonregister.getFont().deriveFont(40f));
        buttonregister.addActionListener(new GUI());
        buttonregister.setVisible(true);

        frame.getContentPane().add(labellogin);
        frame.getContentPane().add(textlogin);
        frame.getContentPane().add(labelpassword);
        frame.getContentPane().add(textpassword);
        frame.getContentPane().add(buttonlogin);
        frame.getContentPane().add(buttonregister);
        frame.setLayout(null);
        frame.setVisible(true);

    }

    //From user a to user b
    public static void TransaferBetweenUsers(User a, User b, int money){
        if(a.GetMoney() >= money){
            a.SetMoney(a.GetMoney() - money);
            b.SetMoney(b.GetMoney() + money);
        }
        else{
            System.out.println("You don't have enough money");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonlogin){
            System.out.println("Login");
            return;
        }
        if (e.getSource() == buttonregister){
            System.out.println("Register");
            return;
        }

    }
}
