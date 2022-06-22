package lt.viko.vandraityte.saugumas.ketvirtas;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyFile {

    public void saveUserAndEncrypt(String username, String password) {
        try {
            FileWriter myWriter = new FileWriter("file.txt");
            String data = AES.encrypt(username + " " + password, "code");
            myWriter.write(data);
            myWriter.close();
            System.out.println("Sėkmingai įrašyta į failą.: " + data);
        } catch (IOException e) {
            System.out.println("Ivyko klaida");
            e.printStackTrace();
        }
    }

    public String getUserStringFromFileAndDecrypt() {

        String data = "";
        try {
            File obj = new File("file.txt");
            Scanner myReader = new Scanner(obj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                System.out.println("readed from file, encrypted: " + data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();

        }
        return AES.decrypt(data, "code");
    }

    public void savePasswordToCsv(Password save) {
        List<Password> passwords = getAllPasswords();
        passwords.add(save);
        System.out.println("Slaptazodis pridetas: " + save);
        saveAllPasswords(passwords);
    }

    private void saveAllPasswords(List<Password> passwords) {
        final char CSV_SEPARATOR = ',';

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("passwords.csv"))) {
            passwords.forEach(psw -> {
                try {
                    writer.append(psw.getName()).append(CSV_SEPARATOR)
                            .append(AES.encrypt(psw.getPassword(), "code")).append(CSV_SEPARATOR)
                            .append(psw.getApp()).append(CSV_SEPARATOR)
                            .append(psw.getComment()).append(System.lineSeparator());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private List<Password> getAllPasswords() {
        List<Password> passwords = new ArrayList<>();
        Path pathToFile = Paths.get("passwords.csv");

        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {


            String line = br.readLine();


            while (line != null) {


                String[] attributes = line.split(",");

                Password password = createPassword(attributes);


                passwords.add(password);

                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return passwords;
    }

    private static Password createPassword(String[] metadata) {
        String name = metadata[0];
        String password = AES.decrypt(metadata[1], "code");
        String app = metadata[2];
        String comment = metadata[2];


        return new Password(name, password, app, comment);
    }



    public void deletePasswordByName(String name) {
        List<Password> passwords = getAllPasswords();
       for (Password p : passwords) {
           if (p.getName().equals(name)) { passwords.remove(p);}

       }

        saveAllPasswords(passwords);
    }
}
