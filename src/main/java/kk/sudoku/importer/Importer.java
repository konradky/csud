package kk.sudoku.importer;

import kk.sudoku.Result;
import lombok.Value;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static kk.sudoku.Result.error;

public class Importer {

    public Result<ImportContent, Error> importFromFile(File file) {

        if (file.length() > 200) {
            return error(Error.IMPORTED_FILE_IS_TOO_BIG);
        }

        try {
            List<String> strings = Files.readAllLines(file.toPath());

            if (strings.size() != 9) {
                return error(Error.IMPORTED_FILE_CONTAINS_WRONG_NUMBER_OF_LINES);
            }

            Optional<String> lineInWrongFormat = strings.stream()
                    .filter(line -> !line.matches("[1-9](,[1-9]){8}"))
                    .findAny();

            if (lineInWrongFormat.isPresent()) {
                return error(Error.IMPORTED_FILE_CONTAINS_WRONG_LINE_FORMAT);
            }

            return Result.value(new ImportContent(strings));
        } catch (IOException exception) {
            return error(Error.IMPORTED_FILE_CANNOT_BE_READ);
        }
    }

    public enum Error {
        IMPORTED_FILE_CANNOT_BE_READ,
        IMPORTED_FILE_IS_TOO_BIG,
        IMPORTED_FILE_CONTAINS_WRONG_NUMBER_OF_LINES,
        IMPORTED_FILE_CONTAINS_WRONG_LINE_FORMAT,
    }

    @Value
    public static class ImportContent {
        List<String> lines;
    }

}
