package DB_Context;

public class ItemModel {
    private String id;
    private String user_id;
    private String image_data;
    private String item_name;
    private String price;
    private String category;
    private String description;

    public ItemModel(String id,String user_id, String image_data, String item_name, String price, String category, String description) {
        this.id = id;
        this.user_id=user_id;
        this.image_data = image_data;
        this.item_name = item_name;
        this.price = price;
        this.category = category;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImage_data() {
        return image_data;
    }

    public void setImage_data(String image_data) {
        this.image_data = image_data;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
