package kk.sudoku.solution;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.Table;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class SudokuSolution {

    private static final List<List<Integer>> RANGES = asList(asList(1, 2, 3), asList(4, 5, 6), asList(7, 8, 9));

    private static final List<Integer> POSITIONS = RANGES.stream()
            .flatMap(List::stream)
            .collect(Collectors.toList());

    private final Table<Integer, Integer, Integer> solution = ArrayTable.create(POSITIONS, POSITIONS);

    public void load(List<String> lines) {
        // validate
        for (int row = 1; row <= 9; row++) {
            String line = lines.get(row - 1);

            String[] numbers = line.split(",");
            for (int column = 1; column <= 9; column++) {
                solution.put(row, column, Integer.parseInt(numbers[column - 1]));
            }
        }
    }

    public ValidationResult validate() {
        ValidationResult.ValidationResultBuilder validationResultBuilder = ValidationResult.builder();

        validationResultBuilder.errors(
                POSITIONS.stream()
                        .filter(row -> !isRowValid(row))
                        .map(row -> "ERROR on row " + row)
                        .collect(Collectors.toList()));

        validationResultBuilder.errors(
                POSITIONS.stream()
                        .filter(column -> !isColumnValid(column))
                        .map(column -> "ERROR on column " + column)
                        .collect(Collectors.toList()));


        RANGES.forEach(rowRange ->
                RANGES.forEach(colRange -> {
                    if (!isSquareValid(rowRange, colRange)) {
                        validationResultBuilder.error("ERROR on square " + formatSquare(rowRange, colRange));
                    }
                }));

        return validationResultBuilder.build();
    }

    private boolean isRowValid(Integer pos) {
        return solution.row(pos).values().containsAll(POSITIONS);
    }

    private boolean isColumnValid(Integer pos) {
        return solution.column(pos).values().containsAll(POSITIONS);
    }

    private boolean isSquareValid(List<Integer> rowRange, List<Integer> colRange) {
        List<Integer> values = rowRange.stream()
                .flatMap(row -> colRange.stream()
                        .map(col -> solution.get(row, col)))
                .collect(Collectors.toList());

        return values.containsAll(POSITIONS);
    }

    private String formatSquare(List<Integer> rowRange, List<Integer> colRange) {
        return "[" +
                colRange.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(",")) +
                "]x[" +
                rowRange.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(",")) +
                "]";
    }


}
