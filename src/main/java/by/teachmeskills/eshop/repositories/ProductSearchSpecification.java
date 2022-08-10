package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.dto.SearchParamsDto;
import by.teachmeskills.eshop.entities.Category;
import by.teachmeskills.eshop.entities.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductSearchSpecification implements Specification<Product> {
    private SearchParamsDto searchParamsDto;

    public ProductSearchSpecification(SearchParamsDto searchParamsDto) {
        this.searchParamsDto = searchParamsDto;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (Optional.ofNullable(searchParamsDto.getSearchKey()).isPresent() && !searchParamsDto.getSearchKey().isBlank()) {
            predicates.add(criteriaBuilder
                    .or(criteriaBuilder.like(root.get("name"), "%" + searchParamsDto.getSearchKey() + "%"),
                            criteriaBuilder.like(root.get("description"), "%" + searchParamsDto.getSearchKey() + "%")));
        }

        if (!searchParamsDto.getMinPrice().isBlank()) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), new BigDecimal(searchParamsDto.getMinPrice())));
        }

        if (!searchParamsDto.getMaxPrice().isBlank()) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), new BigDecimal(searchParamsDto.getMaxPrice())));
        }

        if (Optional.ofNullable(searchParamsDto.getCategoryName()).isPresent()
                && !searchParamsDto.getCategoryName().isBlank()) {
            Join<Product, Category> productCategoryJoin = root.join("category");
            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(productCategoryJoin.get("name"),
                    searchParamsDto.getCategoryName())));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
