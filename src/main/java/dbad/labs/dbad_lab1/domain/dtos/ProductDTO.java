package dbad.labs.dbad_lab1.domain.dtos;

public class ProductDTO {
    private long id;
    private String prodName;
    private String unit;

    public ProductDTO() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
}