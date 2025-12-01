package dbad.labs.dbad_lab1.data.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "sales")
public class SaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Long id;

    @Column(name = "sale_date", nullable = false)
    private OffsetDateTime saleDateTimestamp;

    @Column(name = "quantity_sold", nullable = false, precision = 10, scale = 3)
    private BigDecimal quantitySold;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private ShopEntity shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    public SaleEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OffsetDateTime getSaleDateTimestamp() {
        return saleDateTimestamp;
    }

    public void setSaleDateTimestamp(OffsetDateTime saleDateTimestamp) {
        this.saleDateTimestamp = saleDateTimestamp;
    }

    public BigDecimal getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(BigDecimal quantitySold) {
        this.quantitySold = quantitySold;
    }

    public ShopEntity getShop() {
        return shop;
    }

    public void setShop(ShopEntity shopEntity) {
        this.shop = shopEntity;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity productEntity) {
        this.product = productEntity;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employeeEntity) {
        this.employee = employeeEntity;
    }
}