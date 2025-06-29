package com.back.repository;

import com.back.domain.WiseSaying;
import com.back.simpleDb.SimpleDb;
import com.back.simpleDb.Sql;

import java.util.Map;
import java.util.Set;

//리포지토리는 데이터 저장소에 직접 접근하고 결과를 반환하는 역할
//저장, 조회, 삭제 수행
public class WiseSayingRepository {
//    private final Map<Long, WiseSaying> wiseSayingMap;

    SimpleDb simpleDb = new SimpleDb("localhost", "root", "lldj123414", "wiseSayings_Db");
    Sql sql = new Sql(simpleDb.getConnection());

//    public WiseSayingRepository(Map<Long, WiseSaying> wiseSayingMap) {
//        this.wiseSayingMap = wiseSayingMap;
//    }

    public long getWiseSayingMapSize() {
        return sql.getMapSizeServ();
//        return wiseSayingMap.size();
    }

    //추가, 수정
    public void addWiseSaying(long id, WiseSaying wiseSaying) {
//        return wiseSayingMap.put(id, wiseSaying);
//        key가 Map에 이미 존재하면(수정) → 기존 값을 반환
//        key가 처음 추가되는 것이라면 → null을 반환
        sql.insert(id, wiseSaying);
    }

    public boolean modifyWiseSaying(long id, WiseSaying wiseSaying) {
        // 수정은 addWiseSaying과 동일하게 동작
//        return wiseSayingMap.put(id, wiseSaying);
        return sql.update(id, wiseSaying);
    }


    //삭제
    public boolean deleteWiseSaying(long id) {
//        WiseSaying removed = wiseSayingMap.remove(id);
//        return removed;
        return sql.delete(id);
    }

    //조회
    public WiseSaying getWiseSaying(long id) {
//        return wiseSayingMap.get(id);//없다면 null
        return sql.getWiseSaying(id);
    }

    // 모든 명언 조회
    // 없으면 빈 Set 반환
    public Set<Map.Entry<Long, WiseSaying>> getAllWiseSayings() {
//        return wiseSayingMap.entrySet();
        return sql.getWiseSayings();
    }
}


// 데이터 파싱 양식이 다름
// 도메인에 id 추가