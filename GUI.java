import org.jetbrains.annotations.Nullable;

import javax.swing.Timer;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;

public class GUI implements ActionListener, ListSelectionListener {
    private static final CurrencyExchange c = new CurrencyExchange();
    private final String name = "Banking Friend";
    private final static ArrayList<User> Users = new ArrayList<>();
    private static final int WIDTH = 2560;
    private static final int HEIGHT = 1440;
    private static HashMap<String,Double> Rates = new HashMap<>();

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
        a.AddAccount(1,"PLN");
        a.AddAccount(2,"USD");

        //CreateGUI_Login();
        CreateGUI_MainMenu(a);
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
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel, BorderLayout.CENTER);

        JLabel labellogin = new JLabel("Login");
        labellogin.setFont(labellogin.getFont().deriveFont(40f));

        JTextField textlogin = new JTextField();
        textlogin.setFont(textlogin.getFont().deriveFont(40f));

        JLabel labelpassword = new JLabel("Password");
        labelpassword.setFont(labelpassword.getFont().deriveFont(40f));

        JPasswordField textpassword = new JPasswordField();
        textpassword.setFont(textpassword.getFont().deriveFont(40f));

        JButton buttonlogin = new JButton("Login");
        buttonlogin.setFont(buttonlogin.getFont().deriveFont(40f));
        buttonlogin.addActionListener(e -> {
            User user = CheckCredentials(textlogin.getText(), textpassword.getText());
            if(user != null){
                frame.dispose();
                CreateGUI_MainMenu(user);
            }else{
                JOptionPane.showMessageDialog(null, "Login or password incorrect", "Login", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton buttonregister = new JButton("Register");
        buttonregister.setFont(buttonregister.getFont().deriveFont(40f));
        buttonregister.addActionListener(e -> {
            frame.dispose();
            CreateGUI_Registration();
        });

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(labellogin, constraints);

        constraints.gridx = 1;
        panel.add(textlogin, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(labelpassword, constraints);

        constraints.gridx = 1;
        panel.add(textpassword, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(buttonlogin, constraints);

        constraints.gridx = 1;
        panel.add(buttonregister, constraints);

        frame.setVisible(true);
    }

    private static void CreateGUI_MainMenu(User user) {
        JFrame frame = new JFrame("Bank");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel, BorderLayout.CENTER);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel labelname = new JLabel("Name: " + user.GetName());
        labelname.setName("labelname");
        JLabel labelsurname = new JLabel("Surname: " + user.GetSurname());
        labelsurname.setName("labelsurname");
        JLabel labelage =  new JLabel("Age: " + user.GetAge());
        labelage.setName("labelage");
        JLabel labelmoney = new JLabel("Money: " + user.GetMoney());
        labelmoney.setName("labelmoney");

        JLabel timelabel = new JLabel();
        timelabel.setName("timelabel");
        timelabel.setFont(timelabel.getFont().deriveFont(40f));

        Timer timer = new Timer(0, e -> {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            timelabel.setText(formattedDateTime);
        });
        timer.start();

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(timelabel,constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        labelname.setFont(labelname.getFont().deriveFont(40f));
        panel.add(labelname, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        labelsurname.setFont(labelsurname.getFont().deriveFont(40f));
        panel.add(labelsurname, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        labelage.setFont(labelage.getFont().deriveFont(40f));
        panel.add(labelage, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        labelmoney.setFont(labelmoney.getFont().deriveFont(40f));
        panel.add(labelmoney, constraints);

        final Account[] selectedAccount = new Account[1];


        JList<Object> Accounts = new JList<>();
        Accounts.setFont(Accounts.getFont().deriveFont(40f));
        Accounts.setCellRenderer(new AccountsCellRenderer());
        Accounts.setListData(user.GetAccountsPolish().toArray());
        Accounts.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedAccount[0] = (Account) Accounts.getSelectedValue();
            }
        });

        JScrollPane scrollPane = new JScrollPane(Accounts);
        scrollPane.setPreferredSize(new Dimension(600, 900));

        ActionListener withdrawmoney = e -> {
            if(selectedAccount[0]==null){
                JOptionPane.showMessageDialog(null, "You have to choose the account to want to transfer money from", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            double money;
            try{
                money = Double.parseDouble(JOptionPane.showInputDialog(null,"How much money do you want to transfer?", "Withdraw money", JOptionPane.PLAIN_MESSAGE));
            }catch(Exception exception){
                JOptionPane.showMessageDialog(null, "You have to write number", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(money<=0){
                JOptionPane.showMessageDialog(null, "You have to write number bigger than 0", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(Objects.requireNonNull(user.FindAccount(selectedAccount[0].GetID())).GetMoney()<money){
                JOptionPane.showMessageDialog(null, "You do not have enough money", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(user.WithdrawMoneyPolish(selectedAccount[0].GetID(),money)){
                JOptionPane.showMessageDialog(null, "Money has been transfered", "Information", JOptionPane.INFORMATION_MESSAGE);
                Accounts.setListData(user.GetAccountsPolish().toArray());
                user.ShowUserData(panel);
            }
        };

        ActionListener deposit = e -> {
            if(selectedAccount[0]==null){
                JOptionPane.showMessageDialog(null, "You have to choose the account to want to transfer money to", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            double money;
            String smoney = "";
            try{
               smoney = JOptionPane.showInputDialog(null,"How much money do you want to transefer from other bank?", "Transfer money", JOptionPane.PLAIN_MESSAGE);
               money = Double.parseDouble(smoney);
            }catch(Exception exception){
                if(smoney==null){
                    return;
                }
                JOptionPane.showMessageDialog(null, "You have to write number", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            user.TransferMoney_ToAccount(selectedAccount[0].GetID(),money);
            user.SetMoney(user.GetMoney()+money);
            Transaction transaction = new Transaction(0, selectedAccount[0].GetID(), money,"deposit");
            selectedAccount[0].AddTransaction(transaction);
            user.ShowUserData(panel);
            frame.add(panel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
            Accounts.setListData(user.GetAccountsPolish().toArray());
            JOptionPane.showMessageDialog(null, "Money has been transfered", "Information", JOptionPane.INFORMATION_MESSAGE);
        };

        JButton buttonCreateAccount = new JButton("Create Account");
        buttonCreateAccount.setFont(buttonCreateAccount.getFont().deriveFont(40f));
        buttonCreateAccount.addActionListener(e -> {
            String userInput = (String) JOptionPane.showInputDialog(null, "Tell the ID", "Create Account", JOptionPane.PLAIN_MESSAGE, null, null, "");
            int id;
            try{
                id = Integer.parseInt(userInput);
            }catch(Exception exception){
                JOptionPane.showMessageDialog(null,"You have to enter a number");
                return;
            }
            if(id<=0){
                JOptionPane.showMessageDialog(null,"Number must be bigger than 0");
                return;
            }
            if(user.AddAccount(id,"PLN")){
                JOptionPane.showMessageDialog(null, "Account created successfully");
            }
            else{
                JOptionPane.showMessageDialog(null, "Account with this ID already exists");
            }
            Accounts.setListData(user.GetAccountsPolish().toArray());
        });

        JButton buttonCloseAccount = new JButton("Close Account");
        buttonCloseAccount.setFont(buttonCloseAccount.getFont().deriveFont(40f));
        buttonCloseAccount.addActionListener(e -> {
            if(selectedAccount[0]==null){
                JOptionPane.showMessageDialog(null, "You have to choose the account to want to close", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if(!user.CloseAccount(selectedAccount[0].GetID())){
                return;
            }
            JOptionPane.showMessageDialog(null,"The account has been closed","Information", JOptionPane.INFORMATION_MESSAGE);
            Accounts.setListData(user.GetAccountsPolish().toArray());
        });

        JButton buttonTransferMoneyIn = new JButton("Transfer money In");
        buttonTransferMoneyIn.setFont(buttonTransferMoneyIn.getFont().deriveFont(40f));
        buttonTransferMoneyIn.addActionListener(deposit);

        JButton buttonTransferMoneyOut = new JButton("Transfer money Out");
        buttonTransferMoneyOut.setFont(buttonTransferMoneyOut.getFont().deriveFont(40f));
        buttonTransferMoneyOut.addActionListener(withdrawmoney);


        JButton buttonLogout = new JButton("Log out");
        buttonLogout.setFont(buttonLogout.getFont().deriveFont(40f));
        buttonLogout.addActionListener(e -> {
            frame.dispose();
            CreateGUI_Login();
        });

        JButton buttonTransferMoney = new JButton("Transfer money");
        buttonTransferMoney.setFont(buttonTransferMoney.getFont().deriveFont(40f));
        buttonTransferMoney.addActionListener(e -> {
            if(Accounts.getModel().getSize() == 0){
                JOptionPane.showMessageDialog(null, "You have to create at least one account", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            JTextField id1 = new JTextField();
            JTextField id2 = new JTextField();
            JTextField money = new JTextField();
            Object[] message = {
                    "ID of the account you want to transfer money from:", id1,
                    "ID of the account you want to transfer money to:", id2,
                    "How much money do you want to transfer:", money
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Transfer money", JOptionPane.OK_CANCEL_OPTION);

            if(option==JOptionPane.OK_OPTION) {
                int ID1 = Integer.parseInt(id1.getText());
                int ID2 = Integer.parseInt(id2.getText());
                double Money = Double.parseDouble(money.getText());

                if(user.FindAccount(ID1)==null){
                    JOptionPane.showMessageDialog(null, "One of the accounts does not exist", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(user.FindAccount(ID2)==null){
                    JOptionPane.showMessageDialog(null, "One of the accounts does not exist", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(Money <= 0){
                    JOptionPane.showMessageDialog(null, "Money must be bigger than 0", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(Objects.requireNonNull(user.FindAccount(ID1)).GetMoney() < Money){
                    JOptionPane.showMessageDialog(null, "You do not have enough money", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(user.TransferMoney_BetweenAccounts(ID1, ID2, Money)){
                    JOptionPane.showMessageDialog(null, "Money has been transfered", "Information", JOptionPane.INFORMATION_MESSAGE);
                    Accounts.setListData(user.GetAccountsPolish().toArray());
                }
                Transaction transaction = new Transaction(ID1,ID2,Money,"transfer");
                Objects.requireNonNull(user.FindAccount(ID1)).AddTransaction(transaction);
                Objects.requireNonNull(user.FindAccount(ID2)).AddTransaction(transaction);
            }
        });

        JButton buttonwithdraw = new JButton("Withdraw");
        buttonwithdraw.setFont(buttonwithdraw.getFont().deriveFont(40f));
        buttonwithdraw.addActionListener(withdrawmoney);

        JButton buttondeposit = new JButton("Deposit");
        buttondeposit.setFont(buttondeposit.getFont().deriveFont(40f));
        buttondeposit.addActionListener(deposit);

        JButton buttontransactionHistory = new JButton("Transaction History");
        buttontransactionHistory.setFont(buttontransactionHistory.getFont().deriveFont(40f));
        buttontransactionHistory.addActionListener(e -> {
            frame.dispose();
            //it should be on an Account or on a User
            CreateGUI_TransactionHistory(user,selectedAccount[0]);
        });


        JButton buttoncurrency = new JButton("Other currency");
        buttoncurrency.setFont(buttoncurrency.getFont().deriveFont(40f));
        buttoncurrency.addActionListener(e -> {
            frame.dispose();
            CreateGUI_OtherCurrency(user);
        });

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(buttonCreateAccount, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(buttonCloseAccount, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(buttonTransferMoneyIn, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(buttonTransferMoneyOut, constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        panel.add(buttonTransferMoney, constraints);

        constraints.gridx = 1;
        constraints.gridy = 5;
        panel.add(buttonwithdraw, constraints);

        constraints.gridx = 1;
        constraints.gridy = 6;
        panel.add(buttondeposit, constraints);

        constraints.gridx = 1;
        constraints.gridy = 7;
        panel.add(buttontransactionHistory, constraints);

        constraints.gridx = 1;
        constraints.gridy = 8;
        panel.add(buttoncurrency,constraints);

        constraints.gridx = 1;
        constraints.gridy = 9;
        panel.add(buttonLogout, constraints);

        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.gridheight = 10;
        panel.add(scrollPane, constraints);

        frame.add(panel);
        frame.setVisible(true);
    }


    public static void CreateGUI_Registration(){
        //i would like for this function to do the same as the one above but with a different layout using GridBagConstraints, it is great but can we have wider textfields

        JFrame frame = new JFrame("Bank");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JLabel labelname = new JLabel("Name");
        labelname.setFont(labelname.getFont().deriveFont(40f));

        JTextField textname = new JTextField();
        textname.setFont(textname.getFont().deriveFont(40f));

        JLabel labelsurname = new JLabel("Surname");
        labelsurname.setFont(labelsurname.getFont().deriveFont(40f));

        JTextField textsurname = new JTextField();
        textsurname.setFont(textsurname.getFont().deriveFont(40f));

        JLabel labelage = new JLabel("Age");
        labelage.setFont(labelage.getFont().deriveFont(40f));

        JTextField textage = new JTextField();
        textage.setFont(textage.getFont().deriveFont(40f));

        JLabel labellogin = new JLabel("Login");
        labellogin.setFont(labellogin.getFont().deriveFont(40f));

        JTextField textlogin = new JTextField();
        textlogin.setFont(textlogin.getFont().deriveFont(40f));

        JLabel labelpassword = new JLabel("Password");
        labelpassword.setFont(labelpassword.getFont().deriveFont(40f));

        JTextField textpassword = new JTextField();
        textpassword.setFont(textpassword.getFont().deriveFont(40f));

        JLabel labelpassword2 = new JLabel("Repeat password");
        labelpassword2.setFont(labelpassword2.getFont().deriveFont(40f));

        JTextField textpassword2 = new JTextField();
        textpassword2.setFont(textpassword2.getFont().deriveFont(40f));

        JButton buttonregister = new JButton("Register");
        buttonregister.setFont(buttonregister.getFont().deriveFont(40f));
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
        buttonback.setFont(buttonback.getFont().deriveFont(40f));
        buttonback.addActionListener(e -> {
            frame.dispose();
            CreateGUI_Login();
        });

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(labelname, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 1;
        constraints.weightx = 0.7; // set weightx to 0.7 for second column
        panel.add(textname, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0.3; // set weightx to 0.3 for first column
        constraints.gridwidth = 1;
        panel.add(labelsurname, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 1;
        panel.add(textsurname, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(labelage, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 1;
        panel.add(textage, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(labellogin, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 1;
        panel.add(textlogin, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(labelpassword, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 1;
        panel.add(textpassword, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(labelpassword2, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 1;
        panel.add(textpassword2, constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        panel.add(buttonregister, constraints);

        constraints.gridx = 1;
        panel.add(buttonback, constraints);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static void CreateGUI_TransactionHistory(User user, Account account){
        JFrame frame = new JFrame("Transaction History");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JList<Object> Transactions = new JList<>();
        Transactions.setFont(Transactions.getFont().deriveFont(40f));
        Transactions.setCellRenderer(new TransactionHistoryCellRenderer());
        if(account!=null){
            Transactions.setListData(account.GetTransactions().toArray());
        }else{
            Set<Transaction> CTransactions = new HashSet<>();
            List<Account> accounts = user.GetAccountsPolish();
            for(Account a : accounts){
                CTransactions.addAll(a.GetTransactions());
            }
            Transactions.setListData(CTransactions.toArray());
        }

        JScrollPane scrollPane = new JScrollPane(Transactions);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(1000, 1000));

        JButton buttonlogout = new JButton("Log out");
        buttonlogout.setFont(buttonlogout.getFont().deriveFont(40f));
        buttonlogout.addActionListener(e -> {
            frame.dispose();
            CreateGUI_Login();
        });

        JButton buttonback = new JButton("Back");
        buttonback.setFont(buttonback.getFont().deriveFont(40f));
        buttonback.addActionListener(e -> {
            frame.dispose();
            CreateGUI_MainMenu(user);
        });

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(scrollPane, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(buttonlogout, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(buttonback,constraints);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void CreateGUI_OtherCurrency(User user){
        UpdateRates();
        JFrame frame = new JFrame("Currency");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        final Account[] selectedAccount = new Account[1];
        final double[] price = new double[1];
        final String[] currency = new String[1];

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel labelname = new JLabel("Name: " + user.GetName());
        labelname.setName("labelname");
        JLabel labelsurname = new JLabel("Surname: " + user.GetSurname());
        labelsurname.setName("labelsurname");
        JLabel labelage =  new JLabel("Age: " + user.GetAge());
        labelage.setName("labelage");
        JLabel labelmoney = new JLabel("Money: " + user.GetMoney()+ " PLN");
        labelmoney.setName("labelmoney");

        JLabel timelabel = new JLabel();
        timelabel.setName("timelabel");
        timelabel.setFont(timelabel.getFont().deriveFont(40f));

        Timer timer = new Timer(0, e -> {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            timelabel.setText(formattedDateTime);
        });
        timer.start();

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(timelabel,constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        labelname.setFont(labelname.getFont().deriveFont(40f));
        panel.add(labelname, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        labelsurname.setFont(labelsurname.getFont().deriveFont(40f));
        panel.add(labelsurname, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        labelage.setFont(labelage.getFont().deriveFont(40f));
        panel.add(labelage, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        labelmoney.setFont(labelmoney.getFont().deriveFont(40f));
        panel.add(labelmoney, constraints);

        JList<Object> Currency = new JList<>();
        Currency.setFont(Currency.getFont().deriveFont(40f));
        Currency.setListData(Rates.entrySet().toArray());
        Currency.addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()){
                String [] split = Currency.getSelectedValue().toString().split("=");
                currency[0] = split[0];
                price[0] = Double.parseDouble(split[1]);
            }
        });

        JScrollPane scrollPane = new JScrollPane(Currency);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(400, 1000));

        JList<Object> Accounts = new JList<>();
        Accounts.setFont(Accounts.getFont().deriveFont(40f));
        Accounts.setListData(user.GetAccountsAll().toArray());
        Accounts.setCellRenderer(new AccountsCellRenderer());
        Accounts.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedAccount[0] = (Account) Accounts.getSelectedValue();
            }
        });

        JScrollPane scrollPane2 = new JScrollPane(Accounts);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane2.setPreferredSize(new Dimension(600, 1000));

        JButton buttoncreateAccount = new JButton("Create Account");
        buttoncreateAccount.setFont(buttoncreateAccount.getFont().deriveFont(40f));
        buttoncreateAccount.addActionListener(e -> {
            JTextField id = new JTextField();
            JTextField jcurrency = new JTextField();
            Object[] message = {
                    "Tell the id", id,
                    "Tell the currency",jcurrency
            };
            int decision = JOptionPane.showConfirmDialog(null,message,"Create Account",JOptionPane.OK_CANCEL_OPTION);
            if(decision == JOptionPane.OK_OPTION){
                if(id.getText().isEmpty() || jcurrency.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Something is empty");
                    return;
                }
                user.AddAccount(Integer.parseInt(id.getText()),jcurrency.getText().toUpperCase());
                Accounts.setListData(user.GetAccountsAll().toArray());
            }
        });

        List<Account> display = new ArrayList<>();

        JTextField textsearch = new JTextField();
        textsearch.setFont(textsearch.getFont().deriveFont(40f));
        textsearch.addActionListener(e -> {
            FilterText(user,textsearch, display);
            Accounts.setListData(display.toArray());
            panel.revalidate();
            panel.repaint();
        });

        textsearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                FilterText(user,textsearch, display);
                Accounts.setListData(display.toArray());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                FilterText(user,textsearch, display);
                Accounts.setListData(display.toArray());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                FilterText(user,textsearch, display);
                Accounts.setListData(display.toArray());
            }
        });

        JButton buttondeposit = new JButton("Deposit");
        buttondeposit.setFont(buttondeposit.getFont().deriveFont(40f));
        buttondeposit.addActionListener(e -> {
            if(selectedAccount[0] == null){
                JOptionPane.showMessageDialog(null,"Select an account");
                return;
            }
            JTextField amount = new JTextField();
            Object[] message = {
                    "Tell the amount", amount,
            };
            int decision = JOptionPane.showConfirmDialog(null,message,"Deposit",JOptionPane.OK_CANCEL_OPTION);
            if(decision == JOptionPane.OK_OPTION){
                if(amount.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Something is empty");
                    return;
                }
                selectedAccount[0].SetMoney(selectedAccount[0].GetMoney() + Double.parseDouble(amount.getText()));
                user.SetMoney(user.GetMoney() + Convert(Double.parseDouble(amount.getText()),selectedAccount[0].GetCurrency(),"PLN"));
                user.ShowUserData(panel);
                frame.add(panel, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                Accounts.setListData(user.GetAccountsAll().toArray());
                JOptionPane.showMessageDialog(null, "Money has been transfered", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton buttonlogout = new JButton("Log out");
        buttonlogout.setFont(buttonlogout.getFont().deriveFont(40f));
        buttonlogout.addActionListener(e -> {
            frame.dispose();
            CreateGUI_Login();
        });

        JButton buttonback = new JButton("Back");
        buttonback.setFont(buttonback.getFont().deriveFont(40f));
        buttonback.addActionListener(e -> {
            frame.dispose();
            CreateGUI_MainMenu(user);
        });


        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(buttoncreateAccount,constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(buttondeposit,constraints);

        constraints.gridx = 1;
        constraints.gridy = 8;
        panel.add(buttonback,constraints);

        constraints.gridx = 1;
        constraints.gridy = 9;
        panel.add(buttonlogout,constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        panel.add(textsearch,constraints);

        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.gridheight = 11;
        panel.add(scrollPane,constraints);

        constraints.gridx = 2;
        constraints.gridy = 1;
        panel.add(scrollPane2,constraints);

        frame.add(panel,BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static void FilterText(User user, JTextField search, List<Account> show){
        String query = search.getText().toUpperCase();

        show.clear();

        for(Account a : user.GetAccountsAll()){
            if(a.GetCurrency().contains(query)){
                show.add(a);
            }
        }
    }

    private static void UpdateRates(){
        c.UpdateRates();
        Rates = c.GetRates();
    }

    private static double Convert(double amount, String from, String to){
        return c.Convert(amount,from,to);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    static class AccountsCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);// this is needed to be able to chose Account and have the same formatting i have set
            Account account = (Account) value;
            label.setText(account.GetCurrency()+", ID: " + account.GetID() + ", Money: " + account.GetMoney());
            return label;
        }
    }


    static class TransactionHistoryCellRenderer extends DefaultListCellRenderer {      // here I change how the things will he displayed  on the  scrollpane
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);// this is needed to be able to chose Account and have the same formatting i have set
            Transaction transaction = (Transaction) value;
            label.setText("From : "+ transaction.GetID_A1() + "  To : "+ transaction.GetID_A2()+ "  Money : "+ transaction.GetMoney() + "  Type : " + transaction.GetType());
            return label;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
