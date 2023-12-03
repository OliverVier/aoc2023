package de.o4.day_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.o4.utils.TextReader;

public class Day2 {

    final int MAX_RED_CUBES = 12;
    final int MAX_GREEN_CUBES = 13;
    final int MAX_BLUE_CUBES = 14;

    public static void main(String[] args) {
        Day2 main = new Day2();
        List<String> output = TextReader.read("aoc2023\\src\\main\\java\\de\\o4\\day_2\\puzzle_input_2.txt");
        System.out.printf("Possible games: %s\n", main.sumOfPossibleGamesID(main.getGames(output)));
        System.out.printf("Sum of the power of sets: %s\n", main.sumOfPowOfCubes(main.getGames(output)));
    }

    public List<List<HashMap<String, Integer>>> getGames(List<String> output) {

        List<List<HashMap<String, Integer>>> games = new ArrayList<>();
        List<HashMap<String, Integer>> sets = new ArrayList<>();
        HashMap<String, Integer> cubes;

        for (String game : output) { // Game
            cubes = new HashMap<>();
            game = game.split(":")[1];
            String[] rounds = game.split(";");
            sets = new ArrayList<>();
            for (String round : rounds) { // Set
                String[] cubeString = round.split(",");
                for (String cube : cubeString) { // Würfel
                    String[] values = cube.split(" ");
                    cubes.put(values[2], Integer.valueOf(values[1]));
                }
                sets.add(cubes);
                cubes = new HashMap<String, Integer>();
            }
            games.add(sets);
        }
        return games;
    }

    public int sumOfPossibleGamesID(List<List<HashMap<String, Integer>>> games) {

        int possibleGamesSumID = 0;
        int index = 0;
        boolean invalid = false;

        for (List<HashMap<String, Integer>> game : games) {
            index++;
            for (HashMap<String, Integer> set : game) {
                if (set.getOrDefault("red", 0) > MAX_RED_CUBES ||
                        set.getOrDefault("blue", 0) > MAX_BLUE_CUBES ||
                        set.getOrDefault("green", 0) > MAX_GREEN_CUBES) {
                    invalid = true;
                    break;
                }
            }
            if (invalid == true) {
                invalid = false;
                continue;
            }
            possibleGamesSumID+=index;
        }
        return possibleGamesSumID;
    }

    public int sumOfPowOfCubes(List<List<HashMap<String, Integer>>> games) {

        int sumOfPowOfCubes = 0;


        for (List<HashMap<String, Integer>> game : games) {
            int maxBlue = 0, maxRed = 0, maxGreen = 0;
            for (HashMap<String, Integer> set : game) {
                if(set.getOrDefault("red", 0) > maxRed){
                    maxRed = set.getOrDefault("red", 0);
                }
                if(set.getOrDefault("blue", 0) > maxBlue){
                    maxBlue = set.getOrDefault("blue", 0);
                }
                if(set.getOrDefault("green", 0) > maxGreen){
                    maxGreen = set.getOrDefault("green", 0);
                }
            }
            sumOfPowOfCubes+=(maxBlue*maxGreen*maxRed);
        }
        return sumOfPowOfCubes;
    }
}

class Game {
    List<Set> set = new ArrayList<Set>();
}

class Set {
    HashMap<String, Integer> cubes = new HashMap<String, Integer>();
}