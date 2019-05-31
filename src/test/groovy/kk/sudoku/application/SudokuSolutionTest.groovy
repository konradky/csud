package kk.sudoku.application

import kk.sudoku.solution.SudokuSolution
import spock.lang.Specification

class SudokuSolutionTest extends Specification
{

    def "should report valid solution"()
    {

        given:

        def sudoku = '''
4,3,5,2,6,9,7,8,1
6,8,2,5,7,1,4,9,3
1,9,7,8,3,4,5,6,2
8,2,6,1,9,5,3,4,7
3,7,4,6,8,2,9,1,5
9,5,1,7,4,3,6,2,8
5,1,9,3,2,6,8,7,4
2,4,8,9,5,7,1,3,6
7,6,3,4,1,8,2,5,9
'''.readLines()

        sudoku.remove(0)
        SudokuSolution solution = new SudokuSolution()
        solution.load(sudoku)

        when:
        def validationResult = solution.validate()

        then:
        validationResult.isValid()
    }

    def "should report error on two rows"()
    {

        given:

        def sudoku = '''
4,3,5,2,6,9,7,8,3
6,8,2,5,7,1,4,9,1
1,9,7,8,3,4,5,6,2
8,2,6,1,9,5,3,4,7
3,7,4,6,8,2,9,1,5
9,5,1,7,4,3,6,2,8
5,1,9,3,2,6,8,7,4
2,4,8,9,5,7,1,3,6
7,6,3,4,1,8,2,5,9
'''.readLines()

        sudoku.remove(0)
        SudokuSolution solution = new SudokuSolution()
        solution.load(sudoku)

        when:
        def validationResult = solution.validate()

        then:
        !validationResult.isValid()
        validationResult.errors == ["ERROR on row 1", "ERROR on row 2"]
    }

    def "should report all invalid columns and squares"()
    {

        given:

        def sudoku = '''
1,2,3,4,5,6,7,8,9
1,2,3,4,5,6,7,8,9
1,2,3,4,5,6,7,8,9
1,2,3,4,5,6,7,8,9
1,2,3,4,5,6,7,8,9
1,2,3,4,5,6,7,8,9
1,2,3,4,5,6,7,8,9
1,2,3,4,5,6,7,8,9
1,2,3,4,5,6,7,8,9
'''.readLines()

        sudoku.remove(0)
        SudokuSolution solution = new SudokuSolution()
        solution.load(sudoku)

        when:
        def validationResult = solution.validate()

        then:
        !validationResult.isValid()
        validationResult.errors.size() == 18
        validationResult.errors.findAll { it.startsWith("ERROR on column") }.size() == 9
        validationResult.errors.findAll { it.startsWith("ERROR on square") }.size() == 9
    }


    def "should report all invalid squares"()
    {

        given:

        def sudoku = '''
1,2,3,4,5,6,7,8,9
2,3,4,5,6,7,8,9,1
3,4,5,6,7,8,9,1,2
4,5,6,7,8,9,1,2,3
5,6,7,8,9,1,2,3,4
6,7,8,9,1,2,3,4,5
7,8,9,1,2,3,4,5,6
8,9,1,2,3,4,5,6,7
9,1,2,3,4,5,6,7,8
'''.readLines()

        sudoku.remove(0)
        SudokuSolution solution = new SudokuSolution()
        solution.load(sudoku)

        when:
        def validationResult = solution.validate()

        then:
        !validationResult.isValid()
        validationResult.errors.size() == 9
        validationResult.errors.findAll { it.startsWith("ERROR on square") }.size() == 9
    }


}
