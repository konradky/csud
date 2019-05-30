package kk.sudoku;

import kk.sudoku.application.OptionsParser;
import kk.sudoku.application.Result;

public class ValidateSudokuMain {

    public static void main(String[] args) {

        OptionsParser optionsParser = new OptionsParser();

        Result result = optionsParser.parseArguments(args);

        if (result.hasError()) {
            System.err.println("INVALID " + result.getErrorMessage());
        } else {
            // read file to lines

            // validate format

            // validate solution

        }
    }
}
