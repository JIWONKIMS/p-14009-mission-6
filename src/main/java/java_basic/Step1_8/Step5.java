package java_basic.Step1_8;

import java.util.Scanner;

public class Step5 {
    public void method1() {
        System.out.println("== 명언 앱 ==");
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);
        WiseSayings wiseSaying = new WiseSayings();

        while (loop) {
            System.out.print("명령) ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "종료" -> {
                    loop = false;
                }
                case "등록" -> {
                    System.out.print("명언 : ");
                    String content = scanner.nextLine().trim();
                    System.out.print("작가 : ");
                    String author = scanner.nextLine().trim();

                    long num = wiseSaying.addWiseSaying(content, author);

                    System.out.printf("%d번 명언이 등록되었습니다.\n", num);
                }
                case "목록" -> {
                    wiseSaying.showWiseSaying();
                }
            }
        }

        scanner.close();
    }
}
