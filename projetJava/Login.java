package contacts;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Login extends Frame {
    private Label usernameLbl;
    private Label passwordLbl;
    private TextField usernameTv;
    private TextField passwordTv;
    private Button loginBtn;
    private User session;
    private Database database;
    public Login() throws HeadlessException {
        database=Database.getInstance();
        this.setTitle("Contacts Application");
        this.setSize(500, 500);
        //window control button
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        loginBtn=new Button("Login");
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onLoginBtnClicked();
            }
        });
        usernameLbl=new Label();
        usernameLbl.setText("Username");
        usernameLbl.setSize(10,10);
        passwordLbl=new Label();
        passwordLbl.setText("Password");
        passwordLbl.setSize(10,10);
        usernameTv=new TextField();
        usernameTv.setSize(10,10);
        passwordTv=new TextField();
        passwordTv.setSize(10,10);
        //window design
        GridLayout gridLayout=new GridLayout(1,2);
        gridLayout.setHgap(2);
        Panel usernamePanel=new Panel();
        usernamePanel.setSize(10,10);
        usernamePanel.setBounds(80,190,300,20);
        usernamePanel.add(usernameLbl);
        usernamePanel.add(usernameTv);
        usernamePanel.setLayout(gridLayout);
        Container passwordPanel=new Container();
        passwordPanel.setSize(10,10);
        passwordPanel.setBounds(80,230,300,20);
        passwordPanel.add(passwordLbl);
        passwordPanel.add(passwordTv);
        passwordPanel.setLayout(gridLayout);
        loginBtn.setBounds(200,260,40,40);
        Panel main=new Panel();
        main.setLayout(null);
        main.setSize(100,50);
        main.add(usernamePanel);
        main.add(passwordPanel);
        main.add(loginBtn);

        add(main);

        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void onLoginBtnClicked() {
        String username=usernameTv.getText();
        String password=passwordTv.getText();
        User user = database.checkCredentials(username, password);

        if (user.getUsername()==null||user.getPassword()==null){ //failed login

            //uses swing

            return;
        }

        //successful login
        MainMenu mainMenu=new MainMenu();
        mainMenu.setVisible(true);
        this.setVisible(false);
        dispose();


    }


    public static void main(String[] args) {
        Login login = new Login();
        login.setVisible(true);
    }


}
