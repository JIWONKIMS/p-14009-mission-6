package java_basic.Step1_8;

import java.util.Scanner;

public class Step1 {
    public void method1() {
        System.out.println("== 명언 앱 ==");
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);

        while (loop) {
            System.out.print("명령) ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "종료" -> {
                    loop = false;
                }
            }
        }

        scanner.close();
    }
}
