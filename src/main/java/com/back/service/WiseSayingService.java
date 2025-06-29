package com.back.service;

import com.back.domain.WiseSaying;
import com.back.repository.WiseSayingRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WiseSayingService {
    private static long id;
    WiseSayingRepository repository;

    // 추후에 DI를 통해 리포지토리 객체를 주입받도록 수정
    public WiseSayingService() {
//        repository = new WiseSayingRepository(new HashMap<>());
        repository = new WiseSayingRepository();
        id = repository.getWiseSayingMapSize() + 1; // 초기 id 설정, 기존 명언의 개수 + 1
    }


    public long getMapSizeServ() {
        return repository.getWiseSayingMapSize();
    }

    // 명언이 등록 되었는지 확인
    public long addWiseSayingServ(String author, String content) {
        WiseSaying wiseSaying = new WiseSaying(id, author, content);
        repository.addWiseSaying(id, wiseSaying);

        return id++;
    }

    public boolean modifyWiseSayingServ(long id, String author, String content) {
        WiseSaying wiseSaying = new WiseSaying(id, author, content);
//        WiseSaying ws = repository.addWiseSaying(id, wiseSaying);
        return repository.modifyWiseSaying(id, wiseSaying);
        //null이면 추가 아니면 수정
//        return ws == null ? -1 : id;
    }

    // 명언이 삭제가 되었는지 확인
    public boolean deleteWiseSayingServ(long id) {
        return repository.deleteWiseSaying(id);

        // 명언이 제대로 삭제되었으면 true, 아니면 false
//        return ws != null;
    }

    // 명언 조회, 없으면 null
    public WiseSaying getWiseSayingServ(long id) {
        return repository.getWiseSaying(id);
    }

    // 모든 명언 조회
    // 없으면 빈 Set 반환
    public Set<Map.Entry<Long, WiseSaying>> getAllWiseSayingsServ() {
        return repository.getAllWiseSayings();
    }

//    public void buildAllWiseSayingServ() {
//        repository.callAllWiseSayingBuilder();
//    }
}
