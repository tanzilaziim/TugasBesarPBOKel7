package tubespbokelompok7.restful.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class IncomeResponse {
    private String id;
    private BigDecimal balance;
    private String item_name;
    private BigDecimal price;
    private int amount;
}