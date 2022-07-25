package by.teachmeskills.eshop.dto;

import by.teachmeskills.eshop.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Map<Object, Integer> products;
    private BigDecimal totalCost;
}
