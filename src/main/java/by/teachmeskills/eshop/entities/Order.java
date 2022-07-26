package by.teachmeskills.eshop.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Column(name = "PRICE")
    private BigDecimal price;
    @Column(name = "DATE")
    private LocalDateTime date;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyJoinColumn(name = "PRODUCT_ID")
    @Column(name = "QUANTITY", nullable = false)
    private Map<Product, Integer> products = new HashMap<>();

    public Map<Product, Integer> getProducts() {
        return Collections.unmodifiableMap(products);
    }

    public void addProducts(Product product, int quantity) {
        products.merge(product, quantity, Integer::sum);
    }

    public void removeItem(Product product) {
        products.computeIfPresent(product, (k, v) -> v > 1 ? v - 1 : null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Objects.equals(price, order.price) && Objects.equals(date, order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), price, date);
    }
}
