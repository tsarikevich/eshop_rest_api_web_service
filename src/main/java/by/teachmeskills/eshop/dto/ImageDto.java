package by.teachmeskills.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {
    private int id;
    private boolean primaryFlag;
    private String imagePath;
    private int categoryId;
    private int productId;
}
