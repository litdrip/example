package java.util;

import java.util.Scanner;

/**
 * 控制台
 */
public class Console {

    public static void main(String[] args) {
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.print("console>");

            String cmd;
            while (scanner.hasNextLine()) {
                cmd = scanner.nextLine();
                switch (cmd) {
                    case "exit": {
                        System.exit(0);
                    }
                    break;
                    default: {
                        System.out.println("console>can't parse [" + cmd + "]");
                    }
                }
                System.out.print("console>");
            }
        }).start();
    }
}
