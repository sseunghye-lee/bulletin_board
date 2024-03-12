package com.seung.pilot.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@RequiredArgsConstructor
public enum ResultCode {
    NONE(0, "NONE"),
    EXPIRED_TOKEN(100, "EXPIRED_TOKEN"),
    EXPIRED_REFRESH_TOKEN(100, "EXPIRED_REFRESH_TOKEN"),
    CONFLICT(200, "DUPLICATE ERROR"),
    NOT_EXISTS(300, "NOT EXISTS");

    private final int id;
    private final String desc;

    @JsonCreator
    public static ResultCode fromId(int id) {
        for (ResultCode type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return ResultCode.NONE;
    }

    public static List<Map<String, Object>> codes(){

        return Arrays.stream(ResultCode.values()).map(o-> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", o.getId());
            map.put("name", o.name());
            map.put("desc", o.getDesc());
            return map;
        }).collect(Collectors.toList());
    }
}
