package contacts;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class MainMenu extends Frame {
    private Database database;
    private User user;
    private MenuBar menuBar;
    private Menu viewMenu;
    private Menu add;
    private Menu deleteMenu;
    private Menu editMenu;
    private TextField input;
    private TextArea error;
    private Button operation;
    private FileHandler fileHandler;
    private Panel operationPanel;
    private int command;
    /*
    1=delete
    2=edit
    3=search
    */

    //using java swing tableview


    public MainMenu() throws HeadlessException {
        database = Database.getInstance();
        fileHandler=FileHandler.getInstance();
        user = database.getSession();
        init();
    }

    private void init() {
        setSize(600, 600);
        //menu bar
        viewMenu = new Menu("View");
        menuBar = new MenuBar();
        MenuItem view = new MenuItem("View Item");
        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onViewBtnClicked();
            }
        });
        add = new Menu("ADD");
        MenuItem single = new MenuItem("Single");
        MenuItem multiple = new MenuItem("Multiple");
        add.add(single);
        add.add(multiple);

        input=new TextField();
        input.setSize(200,40);
        input.setBounds(300,300,200,40);
        operation=new Button("Operation");
        operationPanel = new Panel();
        operationPanel.setLayout(new GridLayout(2,2));
        operationPanel.add(new Label("Enter"));
        operationPanel.add(input);
        operationPanel.add(operation);
        operationPanel.setBounds(200,200,300,40);
        operationPanel.setVisible(false);
        operation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onActionBtnclicked();
            }
        });
        add(operationPanel);




        single.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSingleBtnClicked();
            }
        });
        multiple.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onMultipleBtnClicked();
            }
        });
        deleteMenu = new Menu("Delete");
        MenuItem delete = new MenuItem("Delete contact");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onDeleteBtnClicked();
            }
        });
        editMenu = new Menu();
        MenuItem edit = new MenuItem("Edit item");
        editMenu.setLabel("EDIT");
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onEditBtnClicked();
            }
        });
        editMenu.add(edit);
        deleteMenu.add(delete);
        viewMenu.add(view);

        menuBar.add(viewMenu);
        menuBar.add(add);
        menuBar.add(deleteMenu);
        menuBar.add(editMenu);


        setMenuBar(menuBar);


        //close button
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        setLocationRelativeTo(null);
        setVisible(true);


    }



    private void onMultipleBtnClicked() {
        System.out.println("Multiple");
        String path = chooseFile();

        FileHandler fileHandler=FileHandler.getInstance();
        ArrayList<Contact> contacts = fileHandler.readContacts(path);


        for (Contact contact:contacts){
            database.addContact(contact.getName(),contact.getEmail(),contact.getContact());
        }
    }

    private String chooseFile() {

        final FileDialog fileDialog = new FileDialog(this, "Select file");

        fileDialog.setVisible(true);
        String str = ("File Selected :"
                + fileDialog.getDirectory() + fileDialog.getFile());
        System.out.println(str);
        this.setVisible(true);
        return fileDialog.getFile();
    }


    private void onSingleBtnClicked() {
        ContactForm contactForm = ContactForm.getInstance();
        contactForm.setVisible(true);
        dispose();
    }

    private void onEditBtnClicked() {
    command=2;
    operationPanel.setVisible(true);

    }

    private void onDeleteBtnClicked() {
    command=1;
    operation.setLabel("Delete");
    operationPanel.setVisible(true);

//        String contactName = (String)tableView.getValueAt(row, 0);


    }

    private void onViewBtnClicked() {
        command=3;
        operationPanel.setVisible(true);
        operation.setLabel("Search");

        System.out.println("View");

      /*  String path=chooseFile();
        if (!path.contains(".txt")){
            path+=".txt";
        }
        FileHandler fileHandler=FileHandler.getInstance();
        fileHandler.writeToFile(path,database.getContacts());

*/
    }

    private void onActionBtnclicked() {
        String filter=input.getText();
        MainMenu mainMenu;
        Contact contact;
        switch (command){
            case 1:
                contact=null;
                contact = database.getContact(filter);
                if (contact==null){
                    input.setText("");
                    return;
                }
                database.deleteContact(contact.getId());

                mainMenu = new MainMenu();
                mainMenu.setVisible(true);
                dispose();
                break;
            case 2:
                 contact=null;
                 contact=database.getContact(filter);
                if (contact==null){
                    input.setText("");
                    return;
                }
                ContactEditForm editForm=new ContactEditForm(contact);
                editForm.setVisible(true);
                dispose();
                break;
            case 3:
                ArrayList<Contact> contacts = database.getContacts(filter);
                String path = chooseFile();
                fileHandler.writeToFile(path,contacts);
                 mainMenu=new MainMenu();
                mainMenu.setVisible(true);
                dispose();
                break;
        }

    }
}


//updated
