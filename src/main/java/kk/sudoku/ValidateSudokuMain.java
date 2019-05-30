package kk.sudoku;

import kk.sudoku.application.OptionsParser;

public class ValidateSudokuMain {

    public static void main(String[] args) {

        OptionsParser optionsParser = new OptionsParser();

        OptionsParser.Result result = optionsParser.parseArguments(args);

        if (result.hasError()) {
            System.err.println("INVALID " + result.getErrorMessage());
        } else {
            // read file to lines

            // validate format

            // validate solution

        }
    }
}
