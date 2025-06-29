package java_basic.Step1_8;

import java.util.Scanner;

public class Step6_7 {
    public void method1() {
        System.out.println("== 명언 앱 ==");
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);
        WiseSayings wiseSaying = new WiseSayings();

        while (loop) {
            System.out.print("명령) ");
            String input = scanner.nextLine().trim();
            String order = input.substring(0, 2);

            switch (order) {
                case "종료" -> {
                    loop = false;
                }
                case "등록" -> {
                    System.out.print("명언 : ");
                    String content = scanner.nextLine().trim();
                    System.out.print("작가 : ");
                    String author = scanner.nextLine().trim();

                    long num = wiseSaying.addWiseSaying(content, author);

                    if (num == -1) {
                        System.out.println("잘못된 입력입니다.");
                        continue;
                    }
                    System.out.printf("%d번 명언이 등록되었습니다.\n", num);
                }
                case "목록" -> {
                    wiseSaying.showWiseSaying();
                }
                case "삭제" -> {
                    String deleteId = input.substring(input.indexOf("=") + 1);
                    if (!deleteId.matches("\\d+")) {
                        continue;
                    }

                    long deleteKey = Long.parseLong(deleteId);
                    long l = wiseSaying.deleteWiseSaying(deleteKey);
                    System.out.println(l == -1 ? deleteKey + "번 명언은 존재하지 않습니다" : l + "번 명언이 삭제되었습니다.");
                }
            }
        }

        scanner.close();
    }
}
