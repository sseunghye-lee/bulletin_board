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
public enum PointStat implements IdEnum {
    ACCUMULATE("ACCUMULATE", "적립"),
    USE("USE", "사용"),
    EXPIRATION("EXPIRATION", "만료소멸");

    private final String id;
    private final String desc;

    @JsonValue
    public String getValue(){
        return this.name();
    }

    @JsonCreator
    public static PointStat of(String name) {
        for (PointStat obj : PointStat.values()) {
            if (obj.name().equalsIgnoreCase(name)) {
                return obj;
            }
        }
        return null;
    }

    public static List<Map<String, String>> codes(){

        List<Map<String, String>> codes = new ArrayList<>();

        for (PointStat obj : PointStat.values()) {
            Map<String, String> map = new HashMap<>();

            map.put("name", obj.name());
            map.put("id", obj.getId());
            map.put("desc", obj.getDesc());

            codes.add(map);
        }

        return codes;
    }
}
