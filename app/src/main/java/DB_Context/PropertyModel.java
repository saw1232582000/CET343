package DB_Context;

public class PropertyModel {
    private String type;
    private String rooms;
    private String date;
    private String price;
    private int ref_no;
    private String furniture;
    private String remark;
    private String name;

    public String getFurniture() {
        return furniture;
    }

    public void setFurniture(String furniture) {
        this.furniture = furniture;
    }

    // creating getter and setter methods
    public String getType() { return type; }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getRooms()
    {
        return rooms;
    }

    public void setRooms(String rooms)
    {
        this.rooms = rooms;
    }

    public String getDate() { return date; }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getPrice()
    {
        return price;
    }

    public void
    setPrice(String price)
    {
        this.price = price;
    }

    public int getRef_no() { return ref_no; }

    public void setRef_no(int ref_no) { this.ref_no = ref_no; }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // constructor
    public PropertyModel(int ref_no,String type,
                         String rooms,
                         String date,
                         String price, String furniture, String remark, String name)
    {
        this.ref_no=ref_no;
        this.type = type;
        this.rooms = rooms;
        this.date = date;
        this.price = price;
        this.furniture = furniture;
        this.remark = remark;
        this.name = name;
    }
}
