package kk.sudoku.solution

import spock.lang.Specification


class ValidationResultTest extends Specification
{
    def "empty validation result should be valid"()
    {
        given:
        def validationResult = ValidationResult.builder().build()

        expect:
        validationResult.isValid()
    }

    def "validation result with errors should not be valid"()
    {
        given:
        def validationResult = ValidationResult.builder()
                .error("Invalid column")
                .build()

        expect:
        !validationResult.isValid()
    }
}
