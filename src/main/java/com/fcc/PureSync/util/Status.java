package com.fcc.PureSync.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Status {
    private static final Map<String, Integer> STATUS_MAP = Map.of(
            "all", -1,
            "disabled", 0,
            "rest", 1,
            "delete", 2,
            "block", 3,
            "user", 4
    );

    public static List<Integer> mapStatusList(String input) {
        if (input.equals("all") || input == null || input.isEmpty()) {
            // Querydsl에서 사용하기위한 emptyList 반환
            return Collections.emptyList();
        }

        return Arrays.stream(input.split("\\+"))
                .map(String::trim)
                .map(STATUS_MAP::get)
                .collect(Collectors.toList());
    }
}
