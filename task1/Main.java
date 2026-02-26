import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {

    static final int MAX = 15;

    static String[] users = new String[MAX];
    static String[] passwords = new String[MAX];
    static int count = 0;

    static String[] forbidden = {"admin","pass","password","qwerty","ytrewq"};

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int menu;

        do {

            System.out.println("\n1 - реєстрація");
            System.out.println("2 - видалення");
            System.out.println("3 - аутентифікація");
            System.out.println("0 - вихід");

            try {
                menu = sc.nextInt();
                sc.nextLine();
            }
            catch (InputMismatchException e) {
                System.out.println("потрібно ввести число");
                sc.nextLine();
                menu = -1;
                continue;
            }

            if (menu == 1) register(sc);
            if (menu == 2) delete(sc);
            if (menu == 3) login(sc);

        } while (menu != 0);

        sc.close();
    }

    static void register(Scanner sc) {

        if (count >= MAX) {
            System.out.println("більше користувачів додати не можна");
            return;
        }

        System.out.print("ім’я: ");
        String name = sc.nextLine();

        try {
            checkName(name);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.print("пароль: ");
        String pass = sc.nextLine();

        try {
            checkPassword(pass);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        users[count] = name;
        passwords[count] = pass;
        count++;

        System.out.println("користувача додано");
    }


    static void delete(Scanner sc) {

        System.out.print("ім’я для видалення: ");
        String name = sc.nextLine();

        int index = -1;

        for (int i = 0; i < count; i++) {
            if (users[i].equals(name)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("користувача не знайдено");
            return;
        }

        for (int i = index; i < count - 1; i++) {
            users[i] = users[i + 1];
            passwords[i] = passwords[i + 1];
        }

        count--;
        System.out.println("користувача видалено");
    }


    static void login(Scanner sc) {

        System.out.print("ім’я: ");
        String name = sc.nextLine();

        System.out.print("пароль: ");
        String pass = sc.nextLine();

        for (int i = 0; i < count; i++) {
            if (users[i].equals(name) && passwords[i].equals(pass)) {
                System.out.println("користувача аутентифіковано");
                return;
            }
        }

        System.out.println("невірне ім’я або пароль");
    }


    static void checkName(String name) {

        if (name.length() < 5)
            throw new IllegalArgumentException("ім’я має бути не менше 5 символів");

        if (name.contains(" "))
            throw new IllegalArgumentException("і’я не повинно містити пробіли");
    }


    static void checkPassword(String pass) {

        if (pass.length() < 10)
            throw new IllegalArgumentException("пароль короткий");

        if (pass.contains(" "))
            throw new IllegalArgumentException("пароль не повинен містити пробіли");

        for (int i = 0; i < forbidden.length; i++) {
            if (pass.contains(forbidden[i]))
                throw new IllegalArgumentException("пароль містить заборонене слово");
        }

        int digits = 0;
        int special = 0;

        for (int i = 0; i < pass.length(); i++) {

            char c = pass.charAt(i);

            if (c >= '0' && c <= '9') digits++;
            else if (!(c >= 'A' && c <= 'Z') &&
                    !(c >= 'a' && c <= 'z'))
                special++;
        }

        if (digits < 3)
            throw new IllegalArgumentException("потрібно мінімум 3 цифри");

        if (special < 1)
            throw new IllegalArgumentException("потрібен спецсимвол");
    }
}