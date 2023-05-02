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
    static final int WIDTH = 1920;
    static final int HEIGHT = 1080;

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
            CreateGUIRegistration();
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
        Accounts.setCellRenderer(new AccountRenderer());
        Accounts.setListData(user.GetAccounts().toArray());
        Accounts.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedAccount[0] = (Account) Accounts.getSelectedValue();
            }
        });

        JScrollPane scrollPane = new JScrollPane(Accounts);
        scrollPane.setPreferredSize(new Dimension(600, 1000));

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
            user.CloseAccount(selectedAccount[0].GetID());
            JOptionPane.showMessageDialog(null,"The account has been closed","Information", JOptionPane.INFORMATION_MESSAGE);
            Accounts.setListData(user.GetAccounts().toArray());
        });

        JButton buttonTransferMoneyIn = new JButton("Transfer money In");
        buttonTransferMoneyIn.setFont(buttonTransferMoneyIn.getFont().deriveFont(40f));
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
            user.SetMoney(user.GetMoney()+money);       //to jest ok, problem przy zamykaniu konta, musi być 0 i bediz eok
            user.ShowUserData(panel);
            frame.add(panel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
            Accounts.setListData(user.GetAccounts().toArray());
            JOptionPane.showMessageDialog(null, "Money has been transfered", "Information", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton buttonTransferMoneyOut = new JButton("Transfer money Out");
        buttonTransferMoneyOut.setFont(buttonTransferMoneyOut.getFont().deriveFont(40f));

        JButton buttonLogout = new JButton("Logout");
        buttonLogout.setFont(buttonLogout.getFont().deriveFont(40f));
        buttonLogout.addActionListener(e -> {
            frame.dispose();
            CreateGUI_Login();
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
        panel.add(buttonLogout, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridheight = 5;
        panel.add(scrollPane, constraints);

        frame.add(panel);
        frame.setVisible(true);
    }


            public static void CreateGUIRegistration(){
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


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
