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
public enum Gender {
    MAN("MAN", "남성"),
    WOMAN("WOMAN", "여성");

    private final String id;
    private final String desc;

    @JsonValue
    public String getValue(){
        return this.name();
    }

    @JsonCreator
    public static Gender of(String name) {
        for (Gender obj : Gender.values()) {
            if (obj.id.equals(name)) {
                return obj;
            }
        }
        return null;
    }

    public static List<Map<String, String>> codes() {

        List<Map<String, String>> codes = new ArrayList<>();

        for (Gender obj : Gender.values()) {
            Map<String, String> map = new HashMap<>();

            map.put("name", obj.name());
            map.put("id", obj.getId());
            map.put("desc", obj.getDesc());

            codes.add(map);
        }

        return codes;
    }
}
