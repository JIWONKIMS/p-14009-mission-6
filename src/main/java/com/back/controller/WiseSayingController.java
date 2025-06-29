package com.back.controller;

import com.back.domain.WiseSaying;
import com.back.service.WiseSayingService;

import java.util.*;

//문자열 파싱 , 입출력 로직 실행
public class WiseSayingController {
    WiseSayingService wiseSayingService = new WiseSayingService();

    public void callAddServ(String author, String content) {
        // 명언에 특수문자가 있는지 확인
        if (!checkWiseSaying(author, content)) {
            System.out.println("잘못된 입력입니다. 특수문자는 입력 불가 입니다.");
            return ;
        }
        long id = wiseSayingService.addWiseSayingServ(author, content);
        System.out.println(id + "번 명언이 등록되었습니다.");
    }

    public void addDirect(String author, String content) {
        wiseSayingService.addWiseSayingServ(author, content);
    }

    public void callDeleteServ(String input) {
        String extractId = input.replaceAll("[a-z가-힣=?]", "");

        if(!extractId.matches("\\d+")) { return;}

        long deleteKey = Long.parseLong(extractId);
        boolean isDeleted =  wiseSayingService.deleteWiseSayingServ(deleteKey);
        System.out.println(isDeleted ? deleteKey + "번 명언이 삭제되었습니다." : deleteKey + "번 명언은 존재하지 않습니다.");
    }

    public void callModifyServ(String input, Scanner scanner) {
        String extractId = input.replaceAll("[a-z가-힣=?]", "");
        if (!extractId.matches("\\d+")) { return; }

        long modifyKey = Long.parseLong(extractId);

        // 아이디가 존재하는지 확인
        WiseSaying ws = wiseSayingService.getWiseSayingServ(modifyKey);

        if (ws == null) {
            System.out.println(modifyKey + "번 명언은 존재하지 않습니다.");
            return;
        }
        System.out.println("명언(기존) : " + ws.getContent());
        System.out.print("명언(수정) : ");
        String content = scanner.nextLine().trim();

        System.out.println("작가(기존) : " + ws.getAuthor());
        System.out.print("작가(수정) : ");
        String author = scanner.nextLine().trim();

        // 명언에 특수문자가 있는지 확인
        if (!checkWiseSaying(author, content)) {
            System.out.println("잘못된 입력입니다. 특수문자는 입력 불가 입니다.");
            return;
        }

        boolean isModified = wiseSayingService.modifyWiseSayingServ(modifyKey, author, content);
        System.out.println(isModified ? modifyKey + "번 명언이 수정되었습니다." : modifyKey + "번 명언은 존재하지 않습니다.");
    }

//    public void callBuildServ() {
//        wiseSayingService.buildAllWiseSayingServ();
//        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
//    }

    public void callGetPagedWiseSayingsServ(String input) {
        int page = 1;

        // 페이지 파라미터 파싱
        if (input.contains("?")) {
            String[] parts = input.split("\\?");
            String[] queryParams = parts[1].split("=");
            if (queryParams.length == 2 && queryParams[0].equals("page")) {
                page = Integer.parseInt(queryParams[1]);
            }
        }

        // 전체 명언 목록 (정렬)
        List<Map.Entry<Long, WiseSaying>> sortedList = new ArrayList<>(wiseSayingService.getAllWiseSayingsServ());
        sortedList.sort((e1, e2) -> Long.compare(e2.getKey(), e1.getKey())); // 역순 정렬

        int itemsPerPage = 5;
        int total = sortedList.size();
        int totalPages = (int) Math.ceil((double) total / itemsPerPage);

        if (page < 1 || page > totalPages) {
            System.out.println("잘못된 페이지 번호입니다.");
            return;
        }

        int start = (page - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage, total);

        System.out.println("번호 / 작가 / 명언 ");
        System.out.println("----------------------");

        for (int i = start; i < end; i++) {
            Map.Entry<Long, WiseSaying> entry = sortedList.get(i);
            System.out.printf("%d / %s / %s\n",
                    entry.getKey(), entry.getValue().getAuthor(), entry.getValue().getContent());
        }

        // 페이지 표시
        System.out.print("----------------------\n페이지 : ");
        for (int i = 1; i <= totalPages; i++) {
            if (i == page) {
                System.out.print("[" + i + "] ");
            } else {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

//    public void callGetAllWiseSayingsServ() {
//        System.out.println("번호 / 작가 / 명언 ");
//        System.out.println("----------------------");
//        Set<Map.Entry<Long, WiseSaying>> set = wiseSayingService.getAllWiseSayingsServ();
//        // List로 변환 후, 키 기준 역순 정렬
//        List<Map.Entry<Long, WiseSaying>> entryList = new ArrayList<>(set);
//        entryList.sort((e1, e2)
//                -> Long.compare(e2.getKey(), e1.getKey()));
//
//        for (Map.Entry<Long, WiseSaying> entry : entryList) {
//            Long id = entry.getKey();
//            WiseSaying ws = entry.getValue();
//            System.out.printf("%d / %s / %s\n", id, ws.getAuthor(), ws.getContent());
//        }
//    }

    public void callSearchWiseSayingsServ(String input) {
        String[] params = extractKeywordType(input);
        if (params == null) {
            return;
        }

        String keywordType = params[0];
        String keyword = params[1];

        Set<Map.Entry<Long, WiseSaying>> set = wiseSayingService.getAllWiseSayingsServ();
        List<Map.Entry<Long, WiseSaying>> entryList = new ArrayList<>(set);

        // 키 기준 역순 정렬
        entryList.sort((e1, e2) -> Long.compare(e2.getKey(), e1.getKey()));

        System.out.println("번호 / 작가 / 명언 ");
        System.out.println("----------------------");

        for (Map.Entry<Long, WiseSaying> entry : entryList) {
            Long id = entry.getKey();
            WiseSaying ws = entry.getValue();

            if (keywordType.equals("author") && ws.getAuthor().contains(keyword)) {
                System.out.printf("%d / %s / %s\n", id, ws.getAuthor(), ws.getContent());
            } else if (keywordType.equals("content") && ws.getContent().contains(keyword)) {
                System.out.printf("%d / %s / %s\n", id, ws.getAuthor(), ws.getContent());
            }
        }
    }

    public long callGetMapSizeServ() {
        return wiseSayingService.getMapSizeServ();
    }

    public boolean checkWiseSaying(String author, String content) {
        // 작가(author)는 영문/숫자/한글/공백만 허용
        if (author.matches(".*[^a-zA-Z0-9가-힣\\s].*")) {
            return false;
        }
        // 명언(content)은 마침표(.)도 허용
        return !content.matches(".*[^a-zA-Z0-9가-힣\\s.].*");
    }

    public WiseSaying callGetWiseSayingServ(long modifyKey) {
        return wiseSayingService.getWiseSayingServ(modifyKey);
    }

    public String[] extractKeywordType(String input) {
        String paramsPart = input.substring(input.indexOf("keywordType"));
        String[] params = paramsPart.split("&");

        String keywordType = null;
        String keyword = null;

        for (String param : params) {
            if (param.startsWith("keywordType=")) {
                keywordType = param.substring("keywordType=".length());
            } else if (param.startsWith("keyword=")) {
                keyword = param.substring("keyword=".length());
            }
        }

        if (keywordType == null || keyword == null) {
            System.out.println("검색 조건이 올바르지 않습니다.");
            return null;
        }

        return new String[]{keywordType, keyword};
    }
}
