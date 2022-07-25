package by.teachmeskills.eshop.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private int id;
    private BigDecimal price;
    private LocalDateTime date;
    private int userId;
    private Map<String, Integer> products;
}
