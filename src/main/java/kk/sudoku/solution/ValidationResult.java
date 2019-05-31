
package kk.sudoku.solution;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ValidationResult {
    @Singular
    private List<String> errors;

    public boolean isValid() {
        return errors.isEmpty();
    }
}
