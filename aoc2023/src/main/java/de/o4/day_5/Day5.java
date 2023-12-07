package de.o4.day_5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.o4.utils.TextReader;

public class Day5 {

    public static void main(String[] args) {
        Day5 main = new Day5();
        List<String> puzzle_input = TextReader.read("aoc2023/src/main/java/de/o4/day_5/puzzle_input_5.txt");
        System.out.println("Smallest location number part 1: " + main.lowestLocationNumber_part_1(puzzle_input));
        System.out.println("Smallest location number part 2: " + main.lowestLocationNumber_part_2(puzzle_input));
    }

    public long lowestLocationNumber_part_1(List<String> puzzle_input) {

        List<Range> seedToSoil = new ArrayList<>();
        List<Range> soilToFertilizer = new ArrayList<>();
        List<Range> fertilizerToWater = new ArrayList<>();
        List<Range> waterToLight = new ArrayList<>();
        List<Range> lightToTemperature = new ArrayList<>();
        List<Range> temperatureToHumidty = new ArrayList<>();
        List<Range> humidityToLocation = new ArrayList<>();

        List<List<Range>> maps = new ArrayList<>();
        maps.add(0, seedToSoil);
        maps.add(1, soilToFertilizer);
        maps.add(0, fertilizerToWater);
        maps.add(3, waterToLight);
        maps.add(4, lightToTemperature);
        maps.add(5, temperatureToHumidty);
        maps.add(6, humidityToLocation);

        List<Long> seeds = (Arrays.asList(puzzle_input.get(0).split("seeds: ")[1].split(" ")))
                .stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());

        String puzzle_input_string = puzzle_input.subList(2, puzzle_input.size()).stream().reduce("",
                (a, b) -> (a + "\n" + b).strip());
        List<String> mapsString = Arrays.asList(puzzle_input_string.split("[a-z- ]+[:]")).stream().map(s -> s.strip())
                .toList();
        mapsString = mapsString.subList(1, mapsString.size());

        for (int mapIdx = 0; mapIdx < mapsString.size(); mapIdx++) {
            String[] lines = mapsString.get(mapIdx).split("\n");

            for (String line : lines) {
                List<Long> values = (Arrays.asList(line.split(" ")))
                        .stream()
                        .map(Long::valueOf)
                        .collect(Collectors.toList());

                Range range = new Range(values.get(0), values.get(1), values.get(2));
                maps.get(mapIdx).add(range);
            }
        }

        for (int seedIndex = 0; seedIndex < seeds.size(); seedIndex++) { // Seed
            long seedNumber = seeds.get(seedIndex);

            for (List<Range> map : maps) { // Maps

                for (Range range : map) { // Range

                    if (range.contains(seedNumber)) {
                        seedNumber = range.convert(seedNumber);
                        seeds.set(seedIndex, seedNumber);
                        break;
                    }

                }

            }

        }

        return seeds.stream().sorted().toList().get(0);

    }

    // Dont worry about runtime. Its probably only taking 15 minutes to give a
    // result ;)
    public long lowestLocationNumber_part_2(List<String> puzzle_input) {

        List<Range> seedToSoil = new ArrayList<>();
        List<Range> soilToFertilizer = new ArrayList<>();
        List<Range> fertilizerToWater = new ArrayList<>();
        List<Range> waterToLight = new ArrayList<>();
        List<Range> lightToTemperature = new ArrayList<>();
        List<Range> temperatureToHumidty = new ArrayList<>();
        List<Range> humidityToLocation = new ArrayList<>();

        List<List<Range>> maps = new ArrayList<>();
        maps.add(0, seedToSoil);
        maps.add(1, soilToFertilizer);
        maps.add(0, fertilizerToWater);
        maps.add(3, waterToLight);
        maps.add(4, lightToTemperature);
        maps.add(5, temperatureToHumidty);
        maps.add(6, humidityToLocation);

        List<Long> seedRanges = (Arrays.asList(puzzle_input.get(0).split("seeds: ")[1].split(" ")))
                .stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());

        String puzzle_input_string = puzzle_input.subList(2, puzzle_input.size()).stream().reduce("",
                (a, b) -> (a + "\n" + b).strip());
        List<String> mapsString = Arrays.asList(puzzle_input_string.split("[a-z- ]+[:]")).stream().map(s -> s.strip())
                .toList();
        mapsString = mapsString.subList(1, mapsString.size());

        for (int mapIdx = 0; mapIdx < mapsString.size(); mapIdx++) {
            String[] lines = mapsString.get(mapIdx).split("\n");

            for (String line : lines) {
                List<Long> values = (Arrays.asList(line.split(" ")))
                        .stream()
                        .map(Long::valueOf)
                        .collect(Collectors.toList());

                Range range = new Range(values.get(0), values.get(1), values.get(2));
                maps.get(mapIdx).add(range);
            }
        }

        List<List<Long>> seedRangeGroups = new ArrayList<>();

        for (int i = 0; i < seedRanges.size(); i += 2) {
            seedRangeGroups.add(new ArrayList<>(Arrays.asList(seedRanges.get(i), seedRanges.get(i + 1))));
        }

        long lowestLocationNumber = Long.MAX_VALUE;

        for (List<Long> rangeGroup : seedRangeGroups) {

            for (long i = rangeGroup.get(0); i < (rangeGroup.get(0) + rangeGroup.get(1)); i++) {

                long seedNumber = i;

                for (List<Range> map : maps) {
                    for (Range range : map) {
                        if (range.contains(seedNumber)) {
                            seedNumber = range.convert(seedNumber);
                            break;
                        }
                    }
                }

                if (lowestLocationNumber > seedNumber) {
                    lowestLocationNumber = seedNumber;
                }
            }
        }
        return lowestLocationNumber;
    }

    class Range {

        final long srcStart, destStart, step;

        public Range(long destStart, long srcStart, long step) {
            this.destStart = destStart;
            this.srcStart = srcStart;
            this.step = step;
        }

        public long getSourceStart() {
            return srcStart;
        }

        public long getSourceEnd() {
            return srcStart + (step - 1);
        }

        public long getDestinationStart() {
            return destStart;
        }

        public long getDestinationEnd() {
            return destStart + (step - 1);
        }

        public long getStep() {
            return step;
        }

        public boolean contains(long number) {
            if (number >= getSourceStart() && number <= getSourceEnd()) {
                return true;
            }
            return false;
        }

        public long convert(long number) {
            if (contains(number)) {
                return number + (getDestinationStart() - getSourceStart());
            } else {
                return number;
            }
        }
    }
}