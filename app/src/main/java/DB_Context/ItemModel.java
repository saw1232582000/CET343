package DB_Context;

public class ItemModel {
    private String id;
    private String user_id;
    private String image_data;
    private String item_name;
    private String price;

    private String description;

    private String latitude;
    private String longitude;

    private int is_purchased;

    public ItemModel(String id, String user_id, String image_data, String item_name, String price, String description, int is_purchased, String latitude, String longitude) {
        this.id = id;
        this.user_id=user_id;
        this.image_data = image_data;
        this.item_name = item_name;
        this.price = price;

        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.is_purchased = is_purchased;
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





    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIs_purchased() {
        return is_purchased;
    }

    public void setIs_purchased(int is_purchased) {
        this.is_purchased = is_purchased;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
