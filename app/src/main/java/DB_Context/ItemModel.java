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
}
