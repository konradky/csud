package kk.sudoku.application;

import lombok.Value;

import java.io.File;

public class OptionsParser {

    public Result parseArguments(String[] args) {
        if (args == null || args.length < 1) {
            return Result.of(Error.ARGUMENTS_ARE_EMPTY);
        }

        File inputFile = new File(args[0]);

        if (!inputFile.isFile()) {
            return Result.of(Error.ARGUMENT_IS_NOT_AN_EXISTING_FILE);
        }

        Options options = new Options(inputFile);

        return Result.of(options);
    }

    public enum Error {
        ARGUMENTS_ARE_EMPTY,
        ARGUMENT_IS_NOT_AN_EXISTING_FILE
    }

    @Value
    public static class Options {
        File inputFile;
    }

    public static class Result {
        Options options;
        Error error;

        public static Result of(Error error) {
            Result result = new Result();
            result.error = error;

            return result;
        }

        public static Result of(Options options) {
            Result result = new Result();
            result.options = options;

            return result;
        }

        public boolean hasError() {
            return error != null;
        }

        public String getErrorMessage() {
            return error != null ? error.name() : null;
        }
    }


}
