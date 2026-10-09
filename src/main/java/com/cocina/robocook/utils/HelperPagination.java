package com.cocina.robocook.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelperPagination {

    private HelperPagination(){}
    
    private static final int MIN_PAGE_SIZE =1;
    private static final int MAX_PAGE_SIZE =100;

    public static int validatePageSize(int size) {
        if (size < MIN_PAGE_SIZE) {
            log.warn("Page size {} is less than minimum {}, setting to minimum", size, MIN_PAGE_SIZE);
            return MIN_PAGE_SIZE;
        }
        if (size > MAX_PAGE_SIZE) {
            log.warn("Page size {} exceeds maximum {}, setting to maximum", size, MAX_PAGE_SIZE);
            return MAX_PAGE_SIZE;
        }
        return size;
    }

    public static String normalizeSortBy(String sortBy) {
        if (sortBy == null || sortBy.isEmpty())
            return "name";

        return switch (sortBy.toLowerCase()) {
            case "difficulty" -> "difficulty";
            case "season" -> "season";
            case "healthyscore" -> "healthyScore";
            case "tastescore" -> "tasteScore";
            case "preparationtime" -> "preparationTime";
            default -> "name";
        };
    }
}
