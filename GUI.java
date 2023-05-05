import org.jetbrains.annotations.Nullable;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class GUI implements ActionListener, ListSelectionListener {
    String name = "Banking Friend";
    static ArrayList<User> Users = new ArrayList<>();
    static final int WIDTH = 2560;
    static final int HEIGHT = 1440;

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
//        CreateGUI_Login();
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

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    static class AccountRenderer1 extends DefaultListCellRenderer {      // here I change how the things will he displayed  on the  scrollpane
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);// this is needed to be able to chose Account and have the same formatting i have set
            Account account = (Account) value;
            label.setText("Account ID: " + account.GetID() + ", Money: " + account.GetMoney());
            return label;
        }

    }

    static class AccountRenderer2 extends DefaultListCellRenderer {      // here I change how the things will he displayed  on the  scrollpane
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);// this is needed to be able to chose Account and have the same formatting i have set
            Transaction transaction = (Transaction) value;
            label.setText("From : "+ transaction.GetID_A1() + "  To : "+ transaction.GetID_A2()+ "  Money : "+ transaction.GetMoney() + "  Type : " + transaction.GetType());
            return label;
        }

    }

    private static void CreateGUI_MainMenu(User user) {
        JFrame frame = new JFrame("Bank");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

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

        constraints.gridx = 0;
        constraints.gridy = 0;
        labelname.setFont(labelname.getFont().deriveFont(40f));
        panel.add(labelname, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        labelsurname.setFont(labelsurname.getFont().deriveFont(40f));
        panel.add(labelsurname, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        labelage.setFont(labelage.getFont().deriveFont(40f));
        panel.add(labelage, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        labelmoney.setFont(labelmoney.getFont().deriveFont(40f));
        panel.add(labelmoney, constraints);

        final Account[] selectedAccount = new Account[1];

        JList<Object> Accounts = new JList<>();
        Accounts.setFont(Accounts.getFont().deriveFont(40f));
        Accounts.setCellRenderer(new AccountRenderer1());
        Accounts.setListData(user.GetAccounts().toArray());
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
            double money=0;
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
            if(user.WithdrawMoney(selectedAccount[0].GetID(),money)){
                JOptionPane.showMessageDialog(null, "Money has been transfered", "Information", JOptionPane.INFORMATION_MESSAGE);
                selectedAccount[0].SetMoney(selectedAccount[0].GetMoney()-money);
                Transaction transaction = new Transaction(selectedAccount[0].GetID(), 0,money,"Withdraw");
                selectedAccount[0].AddTransaction(transaction);
                Accounts.setListData(user.GetAccounts().toArray());
                user.ShowUserData(panel);
            }
        };

        ActionListener deposit = e -> {
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
            user.SetMoney(user.GetMoney()+money);       //to jest ok, problem przy zamykaniu konta, musi być 0 i bediz eok
            user.ShowUserData(panel);
            frame.add(panel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
            Accounts.setListData(user.GetAccounts().toArray());
            JOptionPane.showMessageDialog(null, "Money has been transfered", "Information", JOptionPane.INFORMATION_MESSAGE);
        };

        JButton buttonCreateAccount = new JButton("Create Account");
        buttonCreateAccount.setFont(buttonCreateAccount.getFont().deriveFont(40f));
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
            }
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
            Accounts.setListData(user.GetAccounts().toArray());
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
                    Accounts.setListData(user.GetAccounts().toArray());
                }
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
            if(selectedAccount[0] == null){//just for now
                JOptionPane.showMessageDialog(null, "Not ready right now");
                return;
            }
            frame.dispose();
            //it should be on a Account or on a User
            CreateGUI_TransactionHistory(user,selectedAccount[0]);
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
        panel.add(buttonLogout, constraints);

        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.gridheight = 9;
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
        JFrame frame = new JFrame("Bank");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JList<Object> Transactions = new JList<>();
        Transactions.setFont(Transactions.getFont().deriveFont(40f));
        Transactions.setCellRenderer(new AccountRenderer2());
        Transactions.setListData(account.GetTransactions().toArray());

        JScrollPane scrollPane = new JScrollPane(Transactions);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(1000, 1000));

        JButton buttonlogout = new JButton("Log out");
        buttonlogout.setFont(buttonlogout.getFont().deriveFont(40f));
        buttonlogout.addActionListener(e -> {
            frame.dispose();
            CreateGUI_Login();
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

        //button back

        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
