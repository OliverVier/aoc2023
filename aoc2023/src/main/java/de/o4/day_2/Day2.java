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

    public List<Game> getGames(List<String> output) {
        List<Game> games = new ArrayList<>();

        for (String gameString : output) {
            Game game = new Game();
            String[] rounds = gameString.split(":")[1].split(";");

            for (String round : rounds) {
                CubeSet set = new CubeSet();
                String[] cubesString = round.split(",");

                for (String cube : cubesString) {
                    String[] values = cube.split(" ");
                    set.cubes.put(values[2], Integer.valueOf(values[1]));
                }
                game.sets.add(set);
            }
            games.add(game);
        }
        return games;
    }

    public int sumOfPossibleGamesID(List<Game> games) {

        int possibleGamesSumID = 0;
        int index = 0;
        boolean invalid = false;

        for (Game game : games) {
            index++;
            for (CubeSet set : game.sets) {
                HashMap<String, Integer> cubes = set.cubes;
                if (cubes.getOrDefault("red", 0) > MAX_RED_CUBES ||
                        cubes.getOrDefault("blue", 0) > MAX_BLUE_CUBES ||
                        cubes.getOrDefault("green", 0) > MAX_GREEN_CUBES) {
                    invalid = true;
                    break;
                }
            }
            if (invalid == true) {
                invalid = false;
                continue;
            }
            possibleGamesSumID += index;
        }
        return possibleGamesSumID;
    }

    public int sumOfPowOfCubes(List<Game> games) {
        int sumOfPowOfCubes = 0;

        for (Game game : games) {
            int maxBlue = 0, maxRed = 0, maxGreen = 0;

            for (CubeSet set : game.sets) {
                HashMap<String, Integer> cubes = set.cubes;
                if (cubes.getOrDefault("red", 0) > maxRed) {
                    maxRed = cubes.getOrDefault("red", 0);
                }
                if (cubes.getOrDefault("blue", 0) > maxBlue) {
                    maxBlue = cubes.getOrDefault("blue", 0);
                }
                if (cubes.getOrDefault("green", 0) > maxGreen) {
                    maxGreen = cubes.getOrDefault("green", 0);
                }
            }
            sumOfPowOfCubes += (maxBlue * maxGreen * maxRed);
        }
        return sumOfPowOfCubes;
    }
}

class Game {
    List<CubeSet> sets = new ArrayList<CubeSet>();
}

class CubeSet {
    HashMap<String, Integer> cubes = new HashMap<String, Integer>();
}