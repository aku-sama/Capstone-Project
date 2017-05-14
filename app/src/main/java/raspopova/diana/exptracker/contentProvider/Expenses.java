package raspopova.diana.exptracker.contentProvider;

/**
 * Created by Diana.Raspopova on 5/9/2017.
 */

public class Expenses {

    private Integer categoryId;
    private Double amount;
    private String description;
    private String ownerId;
    private Long purchaseDate;
    private String attachment;


    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setPurchaseDate(Long purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public Long getPurchaseDate() {
        return purchaseDate;
    }

    public String getAttachment() {
        return attachment;
    }

}
