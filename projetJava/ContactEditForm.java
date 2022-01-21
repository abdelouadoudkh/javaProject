package contacts;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class ContactEditForm extends Frame {
    private static ContactEditForm contactForm;
    private Database database;

    private Label nameLbl;
    private Label contactLbl;
    private Label emailLbl;
    private TextField nameTb;
    private TextField contactTb;
    private TextField emailField;
    private Button editBtn;
    private Contact contact;
    

    public ContactEditForm(Contact contact){
        database=Database.getInstance();
        this.contact=contact;
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
        editBtn =new Button("Add");
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onUpdateBtnClicked();

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
        editBtn.setBounds(200,290,40,20);
        Panel main=new Panel();
        main.setLayout(null);
        main.setSize(100,50);
        main.add(namePanel);
        main.add(contactPanel);
        main.add(emailPanel);
        main.add(editBtn);
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
        initValues();
    }

    private void initValues() {
        nameTb.setText(contact.getName());
        contactTb.setText(contact.getContact());
        emailField.setText(contact.getEmail());
    }

    private void onUpdateBtnClicked() {
        String name=nameTb.getText();
        String contact=contactTb.getText();
        String email=emailField.getText();
        this.contact.setName(name);
        this.contact.setContact(contact);
        this.contact.setEmail(email);

        database.updateContact(this.contact);

        MainMenu mainMenu=new MainMenu();
        mainMenu.setVisible(true);
        dispose();
    }


}
