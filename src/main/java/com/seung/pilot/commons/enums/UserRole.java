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
public enum UserRole implements IdEnum {

    ROLE_MASTER("ROLE_MASTER", "마스터"),
    ROLE_USER("ROLE_USER", "사용자");

    private final String id;
    private final String desc;

    @JsonValue
    public String getValue(){
        return this.name();
    }

    @JsonCreator
    public static UserRole of(String name) {
        for (UserRole obj : UserRole.values()) {
            if (obj.id.equals(name)) {
                return obj;
            }
        }
        return null;
    }

    public static List<Map<String, String>> codes() {

        List<Map<String, String>> codes = new ArrayList<>();

        for (UserRole obj : UserRole.values()) {
            Map<String, String> map = new HashMap<>();

            map.put("name", obj.name());
            map.put("id", obj.getId());
            map.put("desc", obj.getDesc());

            codes.add(map);
        }

        return codes;
    }
}
