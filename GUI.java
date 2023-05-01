import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GUI implements ActionListener {
    String name = "Banking Friend";

    public Map<String, String> users = new HashMap<>(); //here will be logins and passwords for users

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

        JFrame frame = new JFrame("Bank");
        frame.setSize(2560,1440);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel labellogin = new JLabel("Login");
        labellogin.setSize(240,60);
        labellogin.setLocation(1000,470);
        labellogin.setFont(labellogin.getFont().deriveFont(40f));
        labellogin.setVisible(true);

        JTextField textlogin = new JTextField();
        textlogin.setSize(240,40);
        textlogin.setLocation(1200,480);
        textlogin.setFont(textlogin.getFont().deriveFont(40f));
        textlogin.setVisible(true);

        JLabel labelpassword = new JLabel("Password");
        labelpassword.setSize(240,60);
        labelpassword.setLocation(1000,550);
        labelpassword.setFont(labelpassword.getFont().deriveFont(40f));
        labelpassword.setVisible(true);

        JPasswordField textpassword = new JPasswordField();
        textpassword.setSize(240,40);
        textpassword.setLocation(1200,560);
        textpassword.setFont(textpassword.getFont().deriveFont(40f));
        textpassword.setVisible(true);

        JButton buttonlogin = new JButton("Login");
        buttonlogin.setSize(240,60);
        buttonlogin.setLocation(1200,640);
        buttonlogin.setFont(buttonlogin.getFont().deriveFont(40f));
        buttonlogin.setVisible(true);
        buttonlogin.addActionListener(e -> {
                System.out.println("Login");
        });

        JButton buttonregister = new JButton("Register");
        buttonregister.setSize(240,60);
        buttonregister.setLocation(1200,720);
        buttonregister.setFont(buttonregister.getFont().deriveFont(40f));
        buttonregister.setVisible(true);
        buttonregister.addActionListener(e -> {
                frame.dispose();
                CreateGUIRegistration();
        });

        frame.getContentPane().add(labellogin);
        frame.getContentPane().add(textlogin);
        frame.getContentPane().add(labelpassword);
        frame.getContentPane().add(textpassword);
        frame.getContentPane().add(buttonlogin);
        frame.getContentPane().add(buttonregister);
        frame.setLayout(null);
        frame.setVisible(true);


    }

    public static void CreateGUIRegistration(){
        JFrame frame = new JFrame("Registration");
        frame.setSize(2560,1440);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel labelname = new JLabel("Name");
        labelname.setSize(240,60);
        labelname.setLocation(1000,470);
        labelname.setFont(labelname.getFont().deriveFont(40f));
        labelname.setVisible(true);

        JTextField textname = new JTextField();
        textname.setSize(240,40);
        textname.setLocation(1200,480);
        textname.setFont(textname.getFont().deriveFont(40f));
        textname.setVisible(true);

        JLabel labelsurname = new JLabel("Surname");
        labelsurname.setSize(240,60);
        labelsurname.setLocation(1000,550);
        labelsurname.setFont(labelsurname.getFont().deriveFont(40f));
        labelsurname.setVisible(true);

        JTextField textsurname = new JTextField();
        textsurname.setSize(240,40);
        textsurname.setLocation(1200,560);
        textsurname.setFont(textsurname.getFont().deriveFont(40f));
        textsurname.setVisible(true);

        JLabel labelage = new JLabel("Age");
        labelage.setSize(240,60);
        labelage.setLocation(1000,630);
        labelage.setFont(labelage.getFont().deriveFont(40f));
        labelage.setVisible(true);

        JTextField textage = new JTextField();
        textage.setSize(240,40);
        textage.setLocation(1200,640);
        textage.setFont(textage.getFont().deriveFont(40f));
        textage.setVisible(true);

        JButton buttonregister = new JButton("Register");
        buttonregister.setSize(240,60);
        buttonregister.setLocation(1200,720);
        buttonregister.setFont(buttonregister.getFont().deriveFont(40f));
        buttonregister.setVisible(true);

        frame.getContentPane().add(labelname);
        frame.getContentPane().add(textname);
        frame.getContentPane().add(labelsurname);
        frame.getContentPane().add(textsurname);
        frame.getContentPane().add(labelage);
        frame.getContentPane().add(textage);
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

    }
}