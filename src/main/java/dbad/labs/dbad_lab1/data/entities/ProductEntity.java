package dbad.labs.dbad_lab1.data.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "prod_name", nullable = false, length = 255)
    private String prodName;

    @Column(name = "unit", length = 50)
    private String unit;

    @OneToMany(mappedBy = "product")
    private List<SaleEntity> saleEntities;

    @OneToMany(mappedBy = "product")
    private List<InventoryEntity> inventories;

    public ProductEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<SaleEntity> getSales() {
        return saleEntities;
    }

    public void setSales(List<SaleEntity> saleEntities) {
        this.saleEntities = saleEntities;
    }

    public List<InventoryEntity> getInventories() {
        return inventories;
    }

    public void setInventories(List<InventoryEntity> inventories) {
        this.inventories = inventories;
    }
}