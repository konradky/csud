package kk.sudoku.application;

import lombok.Value;

import java.io.File;

public class OptionsParser {

    public Result<Options, Error> parseArguments(String[] args) {
        if (args == null || args.length < 1) {
            return Result.error(Error.ARGUMENTS_ARE_EMPTY);
        }

        File inputFile = new File(args[0]);

        if (!inputFile.isFile()) {
            return Result.error(Error.ARGUMENT_IS_NOT_AN_EXISTING_FILE);
        }

        Options options = new Options(inputFile);

        return Result.value(options);
    }

    public enum Error {
        ARGUMENTS_ARE_EMPTY,
        ARGUMENT_IS_NOT_AN_EXISTING_FILE
    }

    @Value
    public static class Options {
        File inputFile;
    }


}
