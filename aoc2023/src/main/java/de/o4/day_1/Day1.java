package de.o4.day_1;

import java.util.ArrayList;
import java.util.List;

import de.o4.utils.TextReader;

public class Day1 {

    public static void main(String[] args) {
        Day1 main = new Day1();
        List<String> output = TextReader.read("aoc2023\\src\\main\\java\\de\\o4\\day_1\\puzzle_input_1.txt");
        System.out.println(main.findNumbers(output).stream().reduce(0, (n1, n2) -> n1 + n2));
    }

    public List<Integer> findNumbers(List<String> output) {
        List<Integer> numbers = new ArrayList<>();

        for (String line : output) {
            List<Character> numberCharacters = new ArrayList<>();
            for (char c : line.toCharArray()) {
                if (Character.isDigit(c)) {
                    numberCharacters.add(c);
                }
            }
            int number = Integer
                    .valueOf(numberCharacters.get(0) + "" + numberCharacters.get(numberCharacters.size() - 1));
            numbers.add(number);
        }

        return numbers;
    }
}