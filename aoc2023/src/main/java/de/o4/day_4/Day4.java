package de.o4.day_4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import de.o4.utils.TextReader;

public class Day4 {

    public static void main(String[] args) {
        Day4 main = new Day4();
        List<String> puzzle_input = TextReader.read("aoc2023/src/main/java/de/o4/day_4/puzzle_input_4.txt");
        System.out.println("Worth of cards: " + main.sumOfPoints(puzzle_input));
        System.out.println("Amount of cards: " + main.sumOfScratchcards(puzzle_input));
    }

    public int sumOfPoints(List<String> puzzle_input) {

        int result = 0;

        for (String line : puzzle_input) {

            String completeNumbers = line.split(":")[1];
            String[] numberStrings = completeNumbers.split("[|]");

            List<String> winningNumbers = new ArrayList<>(Arrays.asList(numberStrings[0].split(" ")));
            List<String> playerNumbers = new ArrayList<>(Arrays.asList(numberStrings[1].split(" ")));
            List<String> filteredWinningNumbers = winningNumbers.stream().filter(str -> !str.isEmpty()).toList();

            List<String> containedNumbers = playerNumbers.stream().filter(str -> filteredWinningNumbers.contains(str))
                    .toList();

            result += (int) Math.pow(2, containedNumbers.size() - 1);
        }

        return result;
    }

    public int sumOfScratchcards(List<String> puzzle_input) {

        HashMap<Integer, Integer> cards = new HashMap<>();
        HashMap<Integer, Integer> amountCards = new HashMap<>();

        for (int i = 0; i < puzzle_input.size(); i++) {

            String completeNumbers = puzzle_input.get(i).split(":", -1)[1].strip();
            String[] numberStrings = completeNumbers.split("[|]", -1);

            // Divide in winning and player numbers
            List<String> winningNumbers = new ArrayList<>(Arrays.asList(numberStrings[0].split("\\D+")));
            List<String> playerNumbers = new ArrayList<>(Arrays.asList(numberStrings[1].split("\\D+")));
            List<String> filteredWinningNumbers = winningNumbers.stream().filter(str -> !str.isEmpty()).toList();

            // Determine how many numbers are in the list.
            List<String> containedNumbers = playerNumbers.stream().filter(str -> filteredWinningNumbers.contains(str))
                    .toList();

            cards.put(i, containedNumbers.size());
            amountCards.put(i, 1);
        }

        for (Integer key : cards.keySet()) {
            for (int i = key + 1; i < (key + 1) + cards.get(key); i++) {
                amountCards.put(i, amountCards.get(i) + (amountCards.get(key)));
            }
        }

        int amount = amountCards.values().stream().reduce(0, (a, b) -> a + b);
        return amount;

        /*
         * If you're interested: First very much too slow and perhaps buggy version.
         * 
         * ArrayList<String> puzzle_input = new ArrayList<>();
         * for(String line : puzzle_input_reference){
         * puzzle_input.add(line);
         * }
         * 
         * for(int i = 0; i < puzzle_input.size(); i++){
         * 
         * //Get and format numbers
         * String line = puzzle_input.get(i) ;
         * String completeNumbers = line.split(":",-1)[1].strip();
         * String[] numberStrings = completeNumbers.split("[|]",-1);
         * 
         * //Divide in winning and player numbers
         * List<String> winningNumbers = new
         * ArrayList<>(Arrays.asList(numberStrings[0].split("\\D+")));
         * List<String> playerNumbers = new
         * ArrayList<>(Arrays.asList(numberStrings[1].split("\\D+")));
         * List<String> filteredWinningNumbers = winningNumbers.stream().filter(str ->
         * !str.isEmpty()).toList();
         * 
         * //Determine how many numbers are in the list.
         * List<String> containedNumbers = playerNumbers.stream().filter(str ->
         * filteredWinningNumbers.contains(str)).toList();
         * 
         * int currentCardNumber =
         * Integer.valueOf(puzzle_input.get(i).split(":")[0].split("\\D+")[1]);
         * List<String> newCards = puzzle_input_reference.subList(currentCardNumber,
         * currentCardNumber+containedNumbers.size());
         * 
         * //Avoid ConcurrentModificiationException
         * ArrayList<String> copy = new ArrayList<>();
         * for(String text : puzzle_input){
         * copy.add(text);
         * }
         * 
         * for(String card : newCards ){
         * String newCardNumber = card.split(":")[0].split("\\D+")[1];
         * int newIndex = copy.size();
         * 
         * for (int c = 0; c < copy.size(); c++) {
         * String oldCard = copy.get(c);
         * String cardNumber = oldCard.split(":")[0].split("\\D+")[1];
         * if(Integer.valueOf(newCardNumber) == Integer.valueOf(cardNumber)){
         * newIndex = c;
         * break;
         * }
         * }
         * 
         * copy.add(newIndex, card);
         * }
         * 
         * puzzle_input.clear();
         * puzzle_input.addAll(copy);
         * }
         * 
         * return puzzle_input.size();
         * 
         */
    }
}