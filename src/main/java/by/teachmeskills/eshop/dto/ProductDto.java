package by.teachmeskills.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private int categoryId;
    private List<ImageDto> images;

    @Override
    public String toString() {
        return "{id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", categoryId=" + categoryId +
                ", images=" + images +
                '}';
    }
}
