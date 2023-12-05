package de.o4.day_3;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.o4.utils.TextReader;

public class Day3 {

    public static void main(String[] args) {
        Day3 main = new Day3();
        List<String> puzzle_input = TextReader.read("aoc2023/src/main/java/de/o4/day_3/puzzle_input_3.txt");
        System.out.println(main.sumOfPartNumbers(puzzle_input));
        System.out.println(main.sumOfGearPartNumbers(puzzle_input));
    }

    public int sumOfPartNumbers(List<String> puzzle_input) {

        final String regex = "\\d+";

        List<Integer> parts = new ArrayList<Integer>();
        List<Symbol> numbers = findSymbols(regex, puzzle_input);

        for(Symbol number : numbers){
            if(checkAdjacentSymbol(puzzle_input, number.position)) {
                parts.add(Integer.valueOf(number.name));
            }
        }

        int sumOfPartsNumber = parts.stream().reduce(0, (a,b) -> a+b);

        return sumOfPartsNumber;
    }

    public int sumOfGearPartNumbers(List<String> puzzle_input) {

        List<Symbol> numbers = findSymbols("\\d+", puzzle_input);
        List<Symbol> asterisks = findSymbols("[*]", puzzle_input);

        int sumOfGearPartNumbers = 0;

        List<Symbol> adjacentSymbols = null; 

        for(Symbol asterisk : asterisks){

            adjacentSymbols = new ArrayList<>();

            final int y_idx = asterisk.position[0].y;
            final int startX = constrain(asterisk.position[0].x-1,0,puzzle_input.get(y_idx).length()-1);
            final int startY = constrain(asterisk.position[0].y-1,0,puzzle_input.size()-1);
            final int endX = constrain(asterisk.position[1].x+1,0,puzzle_input.get(y_idx).length()-1); 
            final int endY = constrain(asterisk.position[1].y+1,0,puzzle_input.size()-1);

            for(int y = startY; y <= endY; y++) {
                for(int x = startX; x <= endX; x++) {
                    char character = puzzle_input.get(y).charAt(x);
                    if(character != '.' && Character.isDigit(character)){
                        for(Symbol number : numbers){
                            if(number.position[0].y == y && number.position[0].x <= x && number.position[1].x >= x){
                                if(!adjacentSymbols.contains(number)){
                                    adjacentSymbols.add(number);
                                }
                            }
                        }
                    }
                }
            }

            if(adjacentSymbols.size() == 2){
                sumOfGearPartNumbers += Integer.valueOf(adjacentSymbols.get(0).name) * Integer.valueOf(adjacentSymbols.get(1).name); 
            }

        }
        return sumOfGearPartNumbers;
    }
    
    public List<Symbol> findSymbols(String regex, List<String> puzzle_input){
        
        Pattern pattern = Pattern.compile(regex);
        ArrayList<Symbol> symbols = new ArrayList<>();

        for(int y = 0; y < puzzle_input.size(); y++){
            String line = puzzle_input.get(y);
            Matcher matcher = pattern.matcher(line); 
            
            while(matcher.find()){
                Symbol symbol = new Symbol(matcher.group(), new Point[]{new Point(matcher.start(), y), new Point(matcher.end()-1, y)});
                symbols.add(symbol);
            }
        }
        
        return symbols;
    }

    //This method excludes digits
    public boolean checkAdjacentSymbol(List<String> puzzle_input, Point[] locations){

        final int y_idx = locations[0].y;
        final int startX = constrain(locations[0].x-1,0,puzzle_input.get(y_idx).length()-1);
        final int startY = constrain(locations[0].y-1,0,puzzle_input.size()-1);
        final int endX = constrain(locations[1].x+1,0,puzzle_input.get(y_idx).length()-1); 
        final int endY = constrain(locations[1].y+1,0,puzzle_input.size()-1);

        for(int y = startY; y <= endY; y++) {
            for(int x = startX; x <= endX; x++) {
                char character = puzzle_input.get(y).charAt(x);
                if(character != '.' && !Character.isDigit(character)){
                    return true;
                }
            }
        }
        return false;
    }

    public int constrain(int number, int min, int max){
        return number > max ? max : number < min ? min: number;
    }

    public String replaceChar(String text, char ch, int idx) {
        return text.substring(0, idx) + ch + text.substring(idx+1);
    }
}

class Symbol {
    String name;
    Point[] position;
    public Symbol(String name, Point[] position){
        this.name = name;
        this.position = position;
    }
}

class Point{
    int x;
    int y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
}