import org.jetbrains.annotations.Nullable;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI implements ActionListener, ListSelectionListener {
    String name = "Banking Friend";
    static ArrayList<User> Users = new ArrayList<>();

    public static boolean CreateUser(String name, String surname, String login, String password, int age) {
        if(Users.stream().anyMatch(user -> user.GetLogin().equals(login))){
            JOptionPane.showMessageDialog(null, "Account was created earlier, Log in", "Information", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        if(name.equals("") || surname.equals("") || login.equals("") || password.equals("") || age < 18){
            JOptionPane.showMessageDialog(null, "One of the options in not filled or you are not 18 years old", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        User user = new User(name, surname, login, password, age);
        Users.add(user);
        return true;
    }

    public static void main(String[] args) {
        CreateUser("a","a","a","a",18); //just for testing
        User a = Users.get(0);
        a.AddAccount(1);
        a.AddAccount(2);
        System.out.println(a.GetMoney());
        CreateGUI_Login();
    }

    static @Nullable User CheckCredentials(String login, String password){
        for (User user : Users) {
            if (user.GetLogin().equals(login) && user.GetPassword().equals(password)) {
                return user;
            }
        }
        return null;
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
            User user = CheckCredentials(textlogin.getText(),textpassword.getText());
            if(user != null){
                frame.dispose();
                CreateGUI_MainMenu(user);
            }else{
                JOptionPane.showMessageDialog(null,"Login or password incorrect", "Login",JOptionPane.INFORMATION_MESSAGE);
            }
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

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    static class AccountRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);// this is needed to be able to chose Account and have the same formatting i have set
            Account account = (Account) value;
            label.setText("Account ID: " + account.GetID() + ", Money: " + account.GetMoney());
            return label;
        }
    }

    private static void CreateGUI_MainMenu(User user) {

        JFrame frame = new JFrame("Main Menu");
        frame.setSize(2560,1440);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel labelname = new JLabel();
        JLabel labelsurname = new JLabel();
        JLabel labelage =  new JLabel();
        JLabel labelmoney = new JLabel();

        user.ShowUserData(frame,labelname,labelsurname,labelage,labelmoney);

        final Account[] selectedAccount = new Account[1];

        JList<Object> Accounts = new JList<>();
        Accounts.setFont(Accounts.getFont().deriveFont(40f));
        Accounts.setCellRenderer(new AccountRenderer());
        Accounts.setListData(user.GetAccounts().toArray());
        Accounts.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedAccount[0] = (Account) Accounts.getSelectedValue();
            }
        });

        JScrollPane scrollPane = new JScrollPane(Accounts);
        scrollPane.setSize(600,1000);
        scrollPane.setLocation(1500,200);
        scrollPane.setVisible(true);

        JButton buttonCreateAccount = new JButton("Create Account");
        buttonCreateAccount.setSize(340,60);
        buttonCreateAccount.setLocation(1000,470);
        buttonCreateAccount.setFont(buttonCreateAccount.getFont().deriveFont(40f));
        buttonCreateAccount.setVisible(true);
        buttonCreateAccount.addActionListener(e -> {
            String userInput = (String) JOptionPane.showInputDialog(null, "Tell the ID", "Create Account", JOptionPane.PLAIN_MESSAGE, null, null, "");
            if(userInput != null){
                int id = Integer.parseInt(userInput);
                if(user.AddAccount(id)){
                    JOptionPane.showMessageDialog(null, "Account created successfully");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Account with this ID already exists");
                }
                Accounts.setListData(user.GetAccounts().toArray());
                scrollPane.revalidate();
                scrollPane.repaint();
            }
        });

        JButton buttonCloseAccount = new JButton("Close Account");
        buttonCloseAccount.setSize(340,60);
        buttonCloseAccount.setLocation(1000,550);
        buttonCloseAccount.setFont(buttonCloseAccount.getFont().deriveFont(40f));
        buttonCloseAccount.setVisible(true);
        buttonCloseAccount.addActionListener(e -> {
            if(selectedAccount[0]==null){
                JOptionPane.showMessageDialog(null, "You have to choose the account to want to close", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            user.CloseAccount(selectedAccount[0].GetID());
            JOptionPane.showMessageDialog(null,"The account has been closed","Information", JOptionPane.INFORMATION_MESSAGE);
            Accounts.setListData(user.GetAccounts().toArray());
            scrollPane.revalidate();
            scrollPane.repaint();
        });

        JButton buttonTransferMoneyIn = new JButton("Transfer money In");
        buttonTransferMoneyIn.setSize(500,60);
        buttonTransferMoneyIn.setLocation(900,630);
        buttonTransferMoneyIn.setFont(buttonTransferMoneyIn.getFont().deriveFont(40f));
        buttonTransferMoneyIn.setVisible(true);
        buttonTransferMoneyIn.addActionListener(e->{
            if(selectedAccount[0]==null){
                JOptionPane.showMessageDialog(null, "You have to choose the account to want to transfer money to", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            double money=0;
            try{
                money = Double.parseDouble(JOptionPane.showInputDialog(null,"How much money do you want to transefer from other bank?", "Transfer money", JOptionPane.PLAIN_MESSAGE));
            }catch(Exception exception){
                JOptionPane.showMessageDialog(null, "You have to write number", "Error", JOptionPane.ERROR_MESSAGE);
            }
            user.TransferMoney_ToAccount(selectedAccount[0].GetID(),money);
            System.out.println(user.AddMoney(money));
            user.ShowUserData(frame,labelname,labelsurname,labelage,labelmoney);
            Accounts.setListData(user.GetAccounts().toArray());
            scrollPane.revalidate();
            scrollPane.repaint();
        });

        JButton buttonTransferMoneyOut = new JButton("Transfer money Out");
        buttonTransferMoneyOut.setSize(500,60);
        buttonTransferMoneyOut.setLocation(900,710);
        buttonTransferMoneyOut.setFont(buttonTransferMoneyOut.getFont().deriveFont(40f));
        buttonTransferMoneyOut.setVisible(true);

        JButton buttonLogout = new JButton("Logout");
        buttonLogout.setSize(340,60);
        buttonLogout.setLocation(1000,790);
        buttonLogout.setFont(buttonLogout.getFont().deriveFont(40f));
        buttonLogout.setVisible(true);
        buttonLogout.addActionListener(e -> {
            frame.dispose();
            CreateGUI_Login();
        });

        frame.getContentPane().add(buttonCreateAccount);
        frame.getContentPane().add(buttonCloseAccount);
        frame.getContentPane().add(buttonTransferMoneyIn);
        frame.getContentPane().add(buttonTransferMoneyOut);
        frame.getContentPane().add(buttonLogout);
        frame.getContentPane().add(scrollPane);
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

        JLabel labellogin = new JLabel("Login");
        labellogin.setSize(240,60);
        labellogin.setLocation(1000,710);
        labellogin.setFont(labellogin.getFont().deriveFont(40f));
        labellogin.setVisible(true);

        JTextField textlogin = new JTextField();
        textlogin.setSize(240,40);
        textlogin.setLocation(1200,720);
        textlogin.setFont(textlogin.getFont().deriveFont(40f));
        textlogin.setVisible(true);

        JLabel labelpassword = new JLabel("Password");
        labelpassword.setSize(240,60);
        labelpassword.setLocation(1000,790);
        labelpassword.setFont(labelpassword.getFont().deriveFont(40f));
        labelpassword.setVisible(true);

        JTextField textpassword = new JTextField();
        textpassword.setSize(240,40);
        textpassword.setLocation(1200,800);
        textpassword.setFont(textpassword.getFont().deriveFont(40f));
        textpassword.setVisible(true);

        JLabel labelpassword2 = new JLabel("Password");
        labelpassword2.setSize(240,60);
        labelpassword2.setLocation(1000,870);
        labelpassword2.setFont(labelpassword2.getFont().deriveFont(40f));
        labelpassword2.setVisible(true);

        JTextField textpassword2 = new JTextField();
        textpassword2.setSize(240,40);
        textpassword2.setLocation(1200,880);
        textpassword2.setFont(textpassword2.getFont().deriveFont(40f));
        textpassword2.setVisible(true);

        JButton buttonregister = new JButton("Register");
        buttonregister.setSize(240,60);
        buttonregister.setLocation(1200,960);
        buttonregister.setFont(buttonregister.getFont().deriveFont(40f));
        buttonregister.setVisible(true);
        buttonregister.addActionListener(e -> {
            int age;

            try{
                age = Integer.parseInt(textage.getText());
            }catch(NumberFormatException ex){
                System.out.println("Age must be a number");
                age = 0;
            }
            if(!textpassword.getText().equals(textpassword2.getText())){
                JOptionPane.showMessageDialog(null, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(CreateUser(textname.getText(), textsurname.getText(), textlogin.getText(), textpassword.getText(), age)){
                System.out.println("Registered");
                frame.dispose();
                CreateGUI_Login();
            }

        });

        JButton buttonback = new JButton("Back");
        buttonback.setSize(240,60);
        buttonback.setLocation(1200,1040);
        buttonback.setFont(buttonback.getFont().deriveFont(40f));
        buttonback.setVisible(true);
        buttonback.addActionListener(e -> {
            frame.dispose();
            CreateGUI_Login();
        });

        frame.getContentPane().add(labelname);
        frame.getContentPane().add(textname);
        frame.getContentPane().add(labelsurname);
        frame.getContentPane().add(textsurname);
        frame.getContentPane().add(labelage);
        frame.getContentPane().add(textage);
        frame.getContentPane().add(labellogin);
        frame.getContentPane().add(textlogin);
        frame.getContentPane().add(labelpassword);
        frame.getContentPane().add(textpassword);
        frame.getContentPane().add(labelpassword2);
        frame.getContentPane().add(textpassword2);
        frame.getContentPane().add(buttonregister);
        frame.getContentPane().add(buttonback);
        frame.setLayout(null);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
