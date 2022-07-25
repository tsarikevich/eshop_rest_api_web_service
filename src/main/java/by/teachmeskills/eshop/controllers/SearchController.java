package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static by.teachmeskills.eshop.utils.EshopConstants.SEARCH;

@Tag(name = "search", description = "The Search API")
@RestController
@RequestMapping(value = "/search")
public class SearchController {
    private final ProductService productService;

    public SearchController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
            summary = "Find all products by request",
            description = "Search products in Eshop")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "All categories were found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Products not found - products is not exist"
            )})
    @PostMapping
    public ResponseEntity<List<ProductDto>> getSearchPage(@Valid @NotNull(message = "Field mustn't be null") @RequestParam(SEARCH) String request) {
        if (request.isBlank()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<ProductDto> productDtoList = productService.findAllProductsByRequest(request);
            return new ResponseEntity<>(productDtoList, HttpStatus.OK);
        }
    }
}
