package de.o4.day_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.o4.utils.TextReader;

public class Day1 {

    public static void main(String[] args) {
        Day1 main = new Day1();
        List<String> output = TextReader.read("aoc2023\\src\\main\\java\\de\\o4\\day_1\\puzzle_input_1.txt");
        System.out.println(main.findNumbers(output).stream().reduce(0, (n1, n2) -> n1 + n2));
        System.out.println(main.findNumbersPart2(output).stream().reduce(0, (n1, n2) -> n1 + n2));
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
            
            int number;
            if(numberCharacters.size() > 1){
                number = Integer.valueOf(numberCharacters.get(0) + "" + numberCharacters.get(numberCharacters.size() - 1));
            } else{
                number = 0;
            }

            numbers.add(number);
        }

        return numbers;
    }

    public List<Integer> findNumbersPart2(List<String> output) {

        HashMap<String, String> numberMap = new HashMap<>();
            numberMap.put("one", "1");
            numberMap.put("two", "2");
            numberMap.put("three", "3");
            numberMap.put("four", "4");
            numberMap.put("five", "5");
            numberMap.put("six", "6");
            numberMap.put("seven", "7");
            numberMap.put("eight", "8");
            numberMap.put("nine", "9");
        
        List<Integer> numbers = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("(\\d)|(one)|(two)|(three)|(four)|(five)|(six)|(seven)|(eight)|(nine)");
        for (String line : output) {
            Matcher matcher = pattern.matcher(line);
            List<String> result = new ArrayList<String>();       
            int lastIndex = 0;     
            while(matcher.find(lastIndex)){
                result.add(matcher.group());
                lastIndex = matcher.start() >= matcher.end() ? matcher.end() : lastIndex+1;
            }
            String first = numberMap.containsKey(result.get(0)) ? numberMap.get(result.get(0)) : result.get(0);
            String last = numberMap.containsKey(result.get(result.size()-1)) ? numberMap.get(result.get(result.size()-1)) : result.get(result.size()-1);
            numbers.add(Integer.valueOf(first + "" + last));
        }
        return numbers;
    }
}