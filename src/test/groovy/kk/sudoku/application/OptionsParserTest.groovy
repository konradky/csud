package kk.sudoku.application

import spock.lang.Specification
import spock.lang.Unroll

import java.nio.file.Files

class OptionsParserTest extends Specification
{
    def "should return options on valid file provided"()
    {
        given:
        def testFile = File.createTempFile("test", "file")
        OptionsParser parser = new OptionsParser()

        when:
        def result = parser.parseArguments(testFile.absolutePath)

        then:
        result != null
        result.options != null
        result.options.inputFile == testFile

        cleanup:
        testFile.delete()

    }


    @Unroll
    def "should return error #expectedError on #invalidArguments"()
    {
        given:
        OptionsParser parser = new OptionsParser()

        String[] arguments = [] + invalidArguments

        when:
        def result = parser.parseArguments(arguments)

        then:
        result != null
        result.options == null
        result.error == expectedError

        where:
        invalidArguments || expectedError
        []               || OptionsParser.Error.ARGUMENTS_ARE_EMPTY
        ["ABC"]          || OptionsParser.Error.ARGUMENT_IS_NOT_AN_EXISTING_FILE
        [":"]            || OptionsParser.Error.ARGUMENT_IS_NOT_AN_EXISTING_FILE


    }

    def "should return error NOT_AN_EXISTING_FILE on directory provided"()
    {
        given:
        def testDir = Files.createTempDirectory("test")
        OptionsParser parser = new OptionsParser()

        when:
        def result = parser.parseArguments(testDir.toAbsolutePath().toString())

        then:
        result != null
        result.options == null
        result.error == OptionsParser.Error.ARGUMENT_IS_NOT_AN_EXISTING_FILE

        cleanup:
        Files.delete(testDir)

    }

}
