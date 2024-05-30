package com.seung.pilot.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum PointHistoryType implements IdEnum {
    REGISTRATION("REGISTRATION", "게시글등록"),
    MODIFY("MODIFY", "게시글수정"),
    DELETE("DELETE", "게시글삭제");

    private final String id;
    private final String desc;

    @JsonValue
    public String getValue(){
        return this.name();
    }

    @JsonCreator
    public static PointHistoryType of(String name) {
        for (PointHistoryType obj : PointHistoryType.values()) {
            if (obj.name().equalsIgnoreCase(name)) {
                return obj;
            }
        }
        return null;
    }

    public static List<Map<String, String>> codes(){

        List<Map<String, String>> codes = new ArrayList<>();

        for (PointHistoryType obj : PointHistoryType.values()) {
            Map<String, String> map = new HashMap<>();

            map.put("name", obj.name());
            map.put("id", obj.getId());
            map.put("desc", obj.getDesc());

            codes.add(map);
        }

        return codes;
    }
}
