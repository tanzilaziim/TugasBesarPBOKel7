package tubespbokelompok7.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class UpdateIncomeRequest {
    @JsonIgnore
    @NotBlank
    private String id;

    @NotNull
    private BigDecimal balance;

    @NotBlank
    @Size(max = 100)
    private String item_name;

    @NotNull
    private BigDecimal price;

    @NotNull
    private int amount;

}
