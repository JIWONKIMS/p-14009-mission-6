package java_basic.Step1_8;

import java.util.HashMap;
import java.util.Map;

//
public class WiseSayings {
    long id = 1L;
    private Map<Long, String[]> wiseSayingMap;

    public WiseSayings() {
        wiseSayingMap = new HashMap<>();
    }


    public long addWiseSaying( String author, String content) {
        if (author.matches(".*[^a-zA-Z0-9가-힣\\s].*") || content.matches(".*[^a-zA-Z0-9가-힣\\s].*")) {
            return -1;
        }

        String[] wiseSaying = {author, content};
        wiseSayingMap.put(id, wiseSaying);

        return id++;
    }

    public long deleteWiseSaying(long id) {
        if (wiseSayingMap.containsKey(id)) {
            wiseSayingMap.remove(id);
            return id;
        } else {
            return -1;
        }
    }

    public String[] getWiseSaying(long id) {
        if(!wiseSayingMap.containsKey(id)) {
            return null;
        }

        return wiseSayingMap.get(id);
    }

    public boolean modifyWiseSaying(long id, String author, String content) {
        if (wiseSayingMap.containsKey(id)) {
            if (author.matches(".*[^a-zA-Z0-9가-힣\\s].*") || content.matches(".*[^a-zA-Z0-9가-힣\\s].*")) {
                return false;
            }
            wiseSayingMap.put(id, new String[]{author, content});
            return true;
        } else {
            return false;
        }
    }

    public void showWiseSaying() {
        System.out.println("번호 / 작가 / 명언 ");
        System.out.println("----------------------");

        for (Map.Entry<Long, String[]> entry :
                wiseSayingMap.entrySet().stream()
                        .sorted((e1, e2) -> Long.compare(e2.getKey(), e1.getKey()))  // key 기준 역순 정렬
                        .toList()) {
            Long key = entry.getKey();
            String[] value = entry.getValue();
            System.out.printf("%d / %s / %s\n", key, value[0], value[1]);
        }
    }
}
