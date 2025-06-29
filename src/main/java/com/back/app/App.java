package com.back.app;

import com.back.controller.WiseSayingController;
import com.back.domain.WiseSaying;
import java.util.*;

public class App {
    WiseSayingController controller = new WiseSayingController();

    public void run() {
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);
        initSampleData();

        System.out.println("== 명언 앱 ==");

        while (loop) {
            System.out.print("명령) ");
            String input = scanner.nextLine().trim();

            // input 검증은 추후에 좀 더 강화
            if (input.length() < 2) {
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
                    controller.callAddServ(author, content);
                }
                case "목록" -> {
                    if (input.startsWith("목록?keywordType=")) {
                        controller.callSearchWiseSayingsServ(input);
                    }else if( input.startsWith("목록")) {
                        controller.callGetPagedWiseSayingsServ(input);
//                        controller.callGetAllWiseSayingsServ();
                    }
                }
                case "삭제" -> {
                    if(input.startsWith("삭제?id=")){
                        controller.callDeleteServ(input);
                    }
                }
                case "수정" -> {
                    controller.callModifyServ(input, scanner);
                }
//                case "빌드" -> {
//                    controller.callBuildServ();
//                }
            }
        }
        scanner.close();
    }

    private void initSampleData() {
        if (controller.callGetMapSizeServ() == 0) {
            for (int i = 1; i <= 10; i++) {
                controller.addDirect("작자미상 " + i, "명언 " + i);
            }
        }
    }

}
