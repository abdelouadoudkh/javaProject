package contacts;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class ContactForm extends Frame {
    private static ContactForm contactForm;
    private Database database;

    private Label nameLbl;
    private Label contactLbl;
    private Label emailLbl;
    private TextField nameTb;
    private TextField contactTb;
    private TextField emailField;
    private Button addBtn;

    private ContactForm(){
        database=Database.getInstance();
        init();
    }

    private void init() {
        this.setTitle("Contacts Application");
        this.setSize(500, 500);
        //window control button
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        addBtn =new Button("Add");
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAddBtnClicked();

            }
        });
        nameLbl =new Label();
        emailLbl=new Label("Email");
        nameLbl.setText("Name");
        nameLbl.setSize(10,10);
        contactLbl =new Label();
        contactLbl.setText("Contact");
        contactLbl.setSize(10,10);
        nameTb =new TextField();
        nameTb.setSize(10,10);
        emailField=new TextField();
        contactTb =new TextField();
        contactTb.setSize(10,10);
        //window design
        GridLayout gridLayout=new GridLayout(1,2);
        gridLayout.setHgap(2);
        Panel namePanel=new Panel();
        namePanel.setSize(10,10);
        namePanel.setBounds(80,190,300,20);
        namePanel.add(nameLbl);
        namePanel.add(nameTb);
        namePanel.setLayout(gridLayout);
        Panel emailPanel=new Panel();
        emailPanel.setSize(10,10);
        emailPanel.setBounds(80,270,300,20);
        emailPanel.add(emailLbl);
        emailPanel.add(emailField);
        emailPanel.setLayout(gridLayout);

        Container contactPanel=new Container();
        contactPanel.setSize(10,10);
        contactPanel.setBounds(80,230,300,20);
        contactPanel.add(contactLbl);
        contactPanel.add(contactTb);
        contactPanel.setLayout(gridLayout);
        addBtn.setBounds(200,290,40,20);
        Panel main=new Panel();
        main.setLayout(null);
        main.setSize(100,50);
        main.add(namePanel);
        main.add(contactPanel);
        main.add(emailPanel);
        main.add(addBtn);
        //cancel btn
        Button cancelBtn=new Button("Cancel");
        cancelBtn.setBounds(250,290,40,20);
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu mainMenu=new MainMenu();
                mainMenu.setVisible(true);
                dispose();
            }
        });
        main.add(cancelBtn);



        add(main);
        setLocationRelativeTo(null);
        setVisible(true);


    }

    private void onAddBtnClicked() {
        String name=nameTb.getText();
        String contact=contactTb.getText();
        String email=emailField.getText();

        database.addContact(name,email,contact);



        MainMenu mainMenu=new MainMenu();
        mainMenu.setVisible(true);
        dispose();
    }

    public static ContactForm getInstance(){
       //singleton object
            if (contactForm == null) {
                synchronized (Database.class) {
                    if (contactForm == null) {
                        contactForm = new ContactForm();
                    }
                }
            }
        return contactForm;
    }
}
