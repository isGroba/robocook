package com.cocina.robocook.utils;

import com.cocina.robocook.entity.Recipe;

import java.util.*;

public class RecipeSorter {

    private RecipeSorter() {}

    private static final Map<String, Comparator<Recipe>> RECIPE_COMPARATORS = Map.of(
            "name", Comparator.comparing(
                    Recipe::getName,
                    Comparator.nullsLast(String::compareTo)),
            "difficulty", Comparator.comparing(Recipe::getDifficulty),
            "season", Comparator.comparing(
                    Recipe::getSeason,
                    Comparator.nullsLast(Comparator.comparing(Enum::name))),
            "healthyscore", Comparator.comparingInt(r -> parseScore(r.getHealthyScore())),
            "tastescore", Comparator.comparingInt(r -> parseScore(r.getTasteScore())),
            "preparationtime", Comparator.comparingInt(r -> parseScore(r.getTasteScore()))
    );

    private static final Comparator<Recipe> defaultComparator = RECIPE_COMPARATORS.get("name");

    public static List<Recipe> sortRecipes(List<Recipe> recipes, String sortBy, String sortDirection){
        if (recipes == null || recipes.isEmpty())
            return Collections.emptyList();

        String criterion = Optional.ofNullable(sortBy).orElse("name").toLowerCase();
        Comparator<Recipe> comparator = RECIPE_COMPARATORS.getOrDefault(criterion, defaultComparator);

        if (sortDirection != null && sortDirection.equalsIgnoreCase("desc"))
            comparator = comparator.reversed();

        return recipes.stream()
                .filter(Objects::nonNull)
                .sorted(comparator)
                .toList();
    }

    private static int parseScore(String score) {
        if (score == null) return 0;
        try {
            return Integer.parseInt(score.trim());
        } catch (NumberFormatException _) {
            return 0;
        }
    }

}
