package dbad.labs.dbad_lab1.data.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "shops")
public class ShopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long id;

    @Column(name = "shop_name", nullable = false, length = 255)
    private String shopName;

    @Column(name = "address", length = 255)
    private String address;

    @OneToMany(mappedBy = "shop")
    private List<EmployeeEntity> entities;

    @OneToMany(mappedBy = "shop")
    private List<SaleEntity> sales;

    @OneToMany(mappedBy = "shop")
    private List<InventoryEntity> inventories;

    public ShopEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<EmployeeEntity> getEmployees() {
        return entities;
    }

    public void setEmployees(List<EmployeeEntity> employeeEntities) {
        this.entities = employeeEntities;
    }

    public List<SaleEntity> getSales() {
        return sales;
    }

    public void setSales(List<SaleEntity> saleEntities) {
        this.sales = saleEntities;
    }

    public List<InventoryEntity> getInventories() {
        return inventories;
    }

    public void setInventories(List<InventoryEntity> inventories) {
        this.inventories = inventories;
    }
}
