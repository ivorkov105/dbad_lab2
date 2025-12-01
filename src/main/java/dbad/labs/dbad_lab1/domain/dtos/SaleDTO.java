package dbad.labs.dbad_lab1.domain.dtos;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class SaleDTO {
    private long id;
    private OffsetDateTime saleDateTimestamp;
    private BigDecimal quantitySold;
    private long shopId;
    private String shopName;
    private long productId;
    private String productName;
    private String productUnit;
    private long employeeId;
    private String employeeFullName;

    public SaleDTO() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeFullName() {
        return employeeFullName;
    }

    public void setEmployeeFullName(String employeeFullName) {
        this.employeeFullName = employeeFullName;
    }
}