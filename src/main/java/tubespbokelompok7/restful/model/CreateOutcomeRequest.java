package tubespbokelompok7.restful.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CreateOutcomeRequest {
    @NotNull
    private BigDecimal balance;

    @Size(max = 100)
    private String outcome_name;

    @NotNull
    private BigDecimal outcome_total;
}
