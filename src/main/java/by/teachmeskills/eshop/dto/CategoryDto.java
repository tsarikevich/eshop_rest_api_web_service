package by.teachmeskills.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private int id;
    private String name;
    private double rating;
    private List<ProductDto> products;
    private ImageDto image;
}
