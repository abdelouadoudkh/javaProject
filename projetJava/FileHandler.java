package contacts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class FileHandler {
    private static FileHandler fileHandler;

    private FileHandler() {
    }

    public static FileHandler getInstance() {

        if (fileHandler == null) {
            synchronized (FileHandler.class) {
                if (fileHandler == null) {
                    fileHandler = new FileHandler();
                }
            }
        }
        return fileHandler;
    }

    public void writeToFile(String file, ArrayList<Contact> contacts) {
        try {
            FileWriter fw = new FileWriter(file);

            for (Contact contact : contacts) {
                String val = contact.getName() + "|" + contact.getEmail() + "|" + contact.getContact() + "\n";
                fw.write(val);
            }
            fw.close();
        } catch (Exception e) {
        }
    }


    public ArrayList<Contact> readContacts(String file) {
        ArrayList<Contact> contacts = new ArrayList<>();


        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                contacts.add(getContact(line));
            }

        } catch (Exception e) {

        }


        return contacts;
    }

    private Contact getContact(String line) {
        char[] chars = line.toCharArray();
        StringBuilder builder = new StringBuilder();
        String name=null;
        String email=null;
        String contact=null;

        int counter=0;

        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];

            if (ch == '|' || i == chars.length - 1) {

            switch (counter){
                case 0:
                    name=builder.toString();
                    builder.setLength(0);
                    counter++;
                    break;
                case 1:
                    email=builder.toString();
                    builder.setLength(0);
                    counter++;
                    break;
                case 2:
                    builder.append(ch);
                    contact=builder.toString();
                    builder.setLength(0);
                    counter++;
                    break;
            }
            } else {

                builder.append(ch);

            }


        }

        return new Contact(-1, name, email, contact);
    }


}
