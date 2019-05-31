package kk.sudoku.importer

import com.google.common.io.Files
import spock.lang.Specification
import spock.lang.Unroll

import java.nio.charset.Charset

class ImporterTest extends Specification
{
    def importer = new Importer()

    def testFile = File.createTempFile("importer-test", "csv")

    def "should report error on unexisting file"()
    {
        given:
        testFile.delete()

        when:
        def result = importer.importFromFile(testFile)

        then:
        result.hasError()
        result.error == Importer.Error.IMPORTED_FILE_CANNOT_BE_READ
    }

    def "should report error on file which is too big"()
    {
        given:
        Files.write(new byte[250], testFile)

        when:
        def result = importer.importFromFile(testFile)

        then:
        result.hasError()
        result.error == Importer.Error.IMPORTED_FILE_IS_TOO_BIG
    }

    def "should report error on too much lines"()
    {
        given:
        def fileContent = (1..10).collect { it.toString() }.join("\n")
        Files.write(fileContent.getBytes(Charset.defaultCharset()), testFile)

        when:
        def result = importer.importFromFile(testFile)

        then:
        result.hasError()
        result.error == Importer.Error.IMPORTED_FILE_CONTAINS_WRONG_NUMBER_OF_LINES
    }

    @Unroll
    def "should report error on invalid line format: #sample"()
    {
        given:
        def fileContent = (1..9).collect { sample }.join("\n")
        Files.write(fileContent.getBytes(Charset.defaultCharset()), testFile)

        when:
        def result = importer.importFromFile(testFile)

        then:
        result.hasError()
        result.error == Importer.Error.IMPORTED_FILE_CONTAINS_WRONG_LINE_FORMAT

        where:
        sample << [
                "0,0,0,0,0,0,0,0,0",
                "1,2,3,4,5,6",
                "1,2,3,4,5,6,7,8,9,11",
                ",1,2,3,4,5,6,7,8,9",
                "1,2,3,4,5,6,7,8,9,",
                "# this is wrong"
        ]
    }

    @Unroll
    def "should import valid file format: #sample"()
    {
        given:
        def fileContent = (1..9).collect { sample }.join("\n")
        Files.write(fileContent.getBytes(Charset.defaultCharset()), testFile)

        when:
        def result = importer.importFromFile(testFile)

        then:
        !result.hasError()
        result.value.lines == (1..9).collect { sample }

        where:
        sample << [
                "1,2,3,4,5,6,7,8,9",
                "1,1,1,1,1,1,1,1,1",
        ]
    }


    def tearDown()
    {
        if (testFile.exists())
        {
            testFile.delete()
        }
    }
}
