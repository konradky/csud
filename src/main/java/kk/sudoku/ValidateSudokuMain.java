package kk.sudoku;

import kk.sudoku.application.OptionsParser;
import kk.sudoku.importer.Importer;
import kk.sudoku.solution.SudokuSolution;
import kk.sudoku.solution.ValidationResult;

public class ValidateSudokuMain {

    public static void main(String[] args) {

        OptionsParser optionsParser = new OptionsParser();

        Result<OptionsParser.Options, OptionsParser.Error> optionsParserResult = optionsParser.parseArguments(args);

        if (optionsParserResult.hasError()) {
            System.err.println("INVALID " + optionsParserResult.getErrorMessage());
        } else {
            // read file to lines and validate format

            Importer importer = new Importer();
            Result<Importer.ImportContent, Importer.Error> importResult = importer.importFromFile(optionsParserResult.getValue().getInputFile());

            if (importResult.hasError()) {
                System.err.println("INVALID " + importResult.getErrorMessage());
            } else {
                // validate solution

                SudokuSolution solution = new SudokuSolution();
                solution.load(importResult.getValue().getLines());

                ValidationResult validationResult = solution.validate();

                if (validationResult.isValid()) {
                    System.out.println("VALID");
                } else {
                    System.out.println("INVALID SOLUTION");
                    validationResult.getErrors().forEach(System.out::println);
                }
            }


        }
    }
}
