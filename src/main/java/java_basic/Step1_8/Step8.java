package java_basic.Step1_8;

import java.util.Scanner;

public class Step8 {
    public void method1() {
        System.out.println("== 명언 앱 ==");
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);
        WiseSayings wiseSaying = new WiseSayings();

        while (loop) {
            System.out.print("명령) ");
            String input = scanner.nextLine().trim();
            if(input.length() < 2) {
                continue;
            }
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

                    long num = wiseSaying.addWiseSaying(author, content);

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
                case "수정" -> {
                    String modifyId = input.substring(input.indexOf("=") + 1);
                    // 아이디가 숫자인지 확인
                    if (!modifyId.matches("\\d+")) {
                        continue;
                    }

                    long modifyKey = Long.parseLong(modifyId);

                    // 아이디가 존재하는지 확인
                    String[] value = wiseSaying.getWiseSaying(modifyKey);
                    if (value == null) {
                        continue;
                    }

                    System.out.println("명언(기존) : " + value[1]);
                    System.out.print("명언(수정) : ");
                    String content = scanner.nextLine().trim();

                    System.out.println("작가(기존) : " + value[0]);
                    System.out.print("작가(수정) : ");
                    String author = scanner.nextLine().trim();

                    if (wiseSaying.modifyWiseSaying(modifyKey, author, content)) {
                        System.out.printf("%d번 명언이 수정되었습니다.\n", modifyKey);
                    } else {
                        System.out.println("잘못된 입력입니다.");
                    }
                }
            }
        }

        scanner.close();
    }
}
