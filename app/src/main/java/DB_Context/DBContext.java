package DB_Context;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBContext extends SQLiteOpenHelper {
    //declaring database name
    private static String DB_NAME="BabyBuy";

    /*---------------declaring table name----------------------------*/
    private static String USER_TABLE="user_table";
    private static String PROPERTY_TABLE="property_table";
    private static String ITEM_TABLE="item_table";

    /*---------------declaring column names for user_table----------------------------*/
    private static String USER_ID="id";
    private static String USER_NAME="name";
    //private static String USER_EMAIL="email";
    private static String USER_PASSWORD="password";
    /*---------------declaring column names for property_table----------------------------*/
    private static String PROPERTY_REF_NO="ref_no";
    private static String PROPERTY_TYPE="property_type";
    private static String NO_OF_ROOMS="no_of_rooms";
    private static String DATE="date";
    private static String PRICE="rental_price";
    private static String FURNITURE_TYPE="type_of_furniture";
    private static String REMARK="remark";
    private static String REPORTER_NAME="reporter_name";

    /*---------------declaring column names for item_table----------------------------*/
    private static String ITEM_ID="item_id";
    private static String ITEM_USERID="user_id";
    private static String IMAGE_DATA="image_data";
    private static String ITEM_NAME="name";
    private static String ITEM_PRICE="price";
    private static String ITEM_CATEGORY="category";
    private static String ITEM_DESCRIPTION="description";
    private static String ITEM_LATITUDE = "latitude";
    private static String ITEM_LONGITUDE = "longitude";

    private static String ITEM_IS_PURCHASED="is_purchased";


    public DBContext(Context context){
        super(context,DB_NAME,null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //creating table for user
        String user_create="CREATE TABLE "+USER_TABLE+"("+
                USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                USER_NAME+" TEXT,"+USER_PASSWORD+" TEXT)";

        db.execSQL(user_create);

        //creating table for property
        String property_create="CREATE TABLE "+PROPERTY_TABLE+"("+
                PROPERTY_REF_NO+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                PROPERTY_TYPE+" TEXT,"+NO_OF_ROOMS+" TEXT,"+
                DATE+" TEXT,"+PRICE+" TEXT,"+
                FURNITURE_TYPE+" TEXT,"+REMARK+" TEXT,"+
                REPORTER_NAME+" TEXT)";
        db.execSQL(property_create);

        String item_create="CREATE TABLE "+ITEM_TABLE+"("+
                ITEM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                ITEM_USERID+" TEXT,"+
                IMAGE_DATA+" BLOB,"+
                ITEM_NAME+" TEXT,"+
                ITEM_PRICE+" TEXT,"+
                ITEM_DESCRIPTION+" TEXT,"+
                ITEM_IS_PURCHASED + " INTEGER," +
                ITEM_LATITUDE + " TEXT," +
                ITEM_LONGITUDE + " TEXT," +
                "FOREIGN KEY(" + ITEM_ID + ") REFERENCES " + USER_TABLE + "(" + USER_ID + "))";

        db.execSQL(item_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        db.execSQL("DROP TABLE IF EXISTS "+USER_TABLE);
//        db.execSQL("DROP TABLE IF EXISTS "+PROPERTY_TABLE);
//        db.execSQL("DROP TABLE IF EXISTS "+ITEM_TABLE);

//            String item_create="CREATE TABLE "+ITEM_TABLE+"("+
//                    ITEM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
//                    ITEM_USERID+" TEXT,"+
//                    IMAGE_DATA+" TEXT,"+
//                    ITEM_NAME+" TEXT,"+
//                    ITEM_PRICE+" TEXT,"+
//                    ITEM_DESCRIPTION+" TEXT,"+
//                    ITEM_CATEGORY + " TEXT," +
//                    ITEM_IS_PURCHASED + " INTEGER," +
//                    ITEM_LATITUDE + " REAL," +
//                    ITEM_LONGITUDE + " REAL," +
//                    "FOREIGN KEY(" + ITEM_ID + ") REFERENCES " + USER_TABLE + "(" + USER_ID + "))";
//            db.execSQL(item_create);


    }

    //adding a new user to user table
    public void addUser(String username,String password)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(USER_NAME,username);

        contentValues.put(USER_PASSWORD,password);

        database.insert(USER_TABLE,null,contentValues);
        database.close();
    }

    //reading user data to arraylist
    public ArrayList<UserModel> readUser()
    {
        SQLiteDatabase database=this.getReadableDatabase();
        Cursor user_cursor=database.rawQuery("SELECT * FROM "+USER_TABLE,null);

        ArrayList<UserModel> userModelArrayList=new ArrayList<>();

        if (user_cursor.moveToFirst())
        {
            do {
                userModelArrayList.add(new UserModel(user_cursor.getInt(0),
                        user_cursor.getString(1),
                        user_cursor.getString(2)));
            }while (user_cursor.moveToNext());
        }
        return userModelArrayList;
    }

    //adding a new property to property table
    public boolean addProperty(String property_type,String no_of_rooms,String date,String rental_price,String type_of_furniture,String remark,String reporter)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        try {

            ContentValues contentValues=new ContentValues();

            contentValues.put(PROPERTY_TYPE,property_type);
            contentValues.put(NO_OF_ROOMS,no_of_rooms);
            contentValues.put(DATE,date);
            contentValues.put(PRICE,rental_price);
            contentValues.put(FURNITURE_TYPE,type_of_furniture);
            contentValues.put(REMARK,remark);
            contentValues.put(REPORTER_NAME,reporter);

            database.insert(PROPERTY_TABLE,null,contentValues);
            database.close();
            return true;
        }catch (Error e){

            if(database.isOpen()){
                database.close();
            }
            return false;
        }

    }
    public boolean addItem(String user_id,String image_data,String name,String price,String description)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        try{
            byte[] imageData = Base64.decode(image_data, Base64.DEFAULT);
            ContentValues contentValues=new ContentValues();
            contentValues.put(ITEM_USERID,user_id);
            contentValues.put(IMAGE_DATA,imageData);
            contentValues.put(ITEM_NAME,name);
            contentValues.put(ITEM_PRICE,price);

            contentValues.put(ITEM_DESCRIPTION,description);
            contentValues.put(ITEM_IS_PURCHASED,0);
            contentValues.put(ITEM_LATITUDE, "");
            contentValues.put(ITEM_LONGITUDE, "");
            database.insert(ITEM_TABLE,null,contentValues);
            database.close();
            return true;
        }catch (Error e){
            if(database.isOpen()){
                database.close();
            }
            return false;
        }

    }

    public boolean addItem(String user_id,String image_data,String name,String price,String description,String latitude,String longitude)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        try{
            byte[] imageData = Base64.decode(image_data, Base64.DEFAULT);
            ContentValues contentValues=new ContentValues();
            contentValues.put(ITEM_USERID,user_id);
            contentValues.put(IMAGE_DATA,imageData);
            contentValues.put(ITEM_NAME,name);
            contentValues.put(ITEM_PRICE,price);

            contentValues.put(ITEM_DESCRIPTION,description);
            contentValues.put(ITEM_IS_PURCHASED,0);
            contentValues.put(ITEM_LATITUDE, latitude);
            contentValues.put(ITEM_LONGITUDE, longitude);
            database.insert(ITEM_TABLE,null,contentValues);
            database.close();
            return true;
        }catch (Error e){
            if(database.isOpen()){
                database.close();
            }
            return false;
        }

    }
    //reading data from property
    public ArrayList<PropertyModel> readProperty()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+PROPERTY_TABLE,null);
        ArrayList<PropertyModel> property_modelArrayList=new ArrayList<>();

        if(cursor.moveToFirst())
        {
            do{
                property_modelArrayList.add(new PropertyModel(cursor.getInt(0),cursor.getString(1),
                        cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),
                        cursor.getString(6),cursor.getString(7)));

            }while (cursor.moveToNext());
        }
        db.close();
        return property_modelArrayList;
    }

    public ArrayList<PropertyModel> readProperty_by_ref_no(String ref_no){
        SQLiteDatabase db=this.getReadableDatabase();
        String selection = PROPERTY_REF_NO+"=?";
        String[] selectionArgs = { ref_no };
        Cursor cursor = db.query(PROPERTY_TABLE, null, selection, selectionArgs, null, null, null);
        ArrayList<PropertyModel> property_modelArrayList=new ArrayList<>();

        if(cursor.moveToFirst())
        {
            do{
                property_modelArrayList.add(new PropertyModel(cursor.getInt(0),cursor.getString(1),
                        cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),
                        cursor.getString(6),cursor.getString(7)));

            }while (cursor.moveToNext());
        }
        db.close();
        return property_modelArrayList;
    }
    public ArrayList<ItemModel> read_item_by_item_id(String item_id){
        SQLiteDatabase db=this.getReadableDatabase();
        String selection = ITEM_ID+"=?";
        String[] selectionArgs = { item_id };
        Cursor cursor = db.query(ITEM_TABLE, null, selection, selectionArgs, null, null, null);
        ArrayList<ItemModel> item_modelArrayList=new ArrayList<>();

        if(cursor.moveToFirst())
        {
            do{
                byte[] imageData = cursor.getBlob(2);
                item_modelArrayList.add(new ItemModel(cursor.getString(0),cursor.getString(1),
                        Base64.encodeToString(imageData, Base64.DEFAULT),cursor.getString(3),
                        cursor.getString(4),cursor.getString(6),
                        cursor.getInt(6),cursor.getString(7),cursor.getString(8)));

            }while (cursor.moveToNext());
        }
        db.close();
        return item_modelArrayList;
    }
    public ArrayList<PropertyModel> search_Property_by_ref_no(String ref_no){
        SQLiteDatabase db=this.getReadableDatabase();
        String selection = PROPERTY_REF_NO+" LIKE ?";
        String[] selectionArgs = { "%" + ref_no + "%" };
        Cursor cursor = db.query(PROPERTY_TABLE, null, selection, selectionArgs, null, null, null);
        ArrayList<PropertyModel> property_modelArrayList=new ArrayList<>();

        if(cursor.moveToFirst())
        {
            do{
                property_modelArrayList.add(new PropertyModel(cursor.getInt(0),cursor.getString(1),
                        cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),
                        cursor.getString(6),cursor.getString(7)));

            }while (cursor.moveToNext());
        }
        db.close();
        return property_modelArrayList;
    }
    public ArrayList<ItemModel> search_items_by_name(String name,String user_id){
        SQLiteDatabase db=this.getReadableDatabase();
        String selection = ITEM_NAME+" LIKE ? AND "+ ITEM_USERID + " = ?";;
        String[] selectionArgs = { "%" + name + "%" ,user_id};
        Cursor cursor = db.query(ITEM_TABLE, null, selection, selectionArgs, null, null, null);
        ArrayList<ItemModel>  item_modelArrayList=new ArrayList<>();

        if(cursor.moveToFirst())
        {
            do{
                byte[] imageData = cursor.getBlob(2);
                item_modelArrayList.add(new ItemModel(cursor.getString(0),cursor.getString(1),
                        Base64.encodeToString(imageData, Base64.DEFAULT),cursor.getString(3),
                        cursor.getString(4),cursor.getString(6),
                        cursor.getInt(6),cursor.getString(7),cursor.getString(8)));

            }while (cursor.moveToNext());
        }
        db.close();
        return  item_modelArrayList;
    }

    //delete property form property table
    public void deleteProperty(String ref_no)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(PROPERTY_TABLE,"ref_no=?",new String[]{ref_no});
        db.close();
    }
    public boolean deleteItem(String item_id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        try{

            db.delete(ITEM_TABLE,"item_id=?",new String[]{item_id});
            db.close();
            return true;
        }catch(Error e){
            if(db.isOpen()){
                db.close();
            }
            return false;
        }

    }

    public void updateProperty(String original_ref_no,int new_ref_no,String property_type,String no_of_rooms,String date,String rental_price,String type_of_furniture,String remark,String reporter)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(PROPERTY_REF_NO,new_ref_no);
        contentValues.put(PROPERTY_TYPE,property_type);
        contentValues.put(NO_OF_ROOMS,no_of_rooms);
        contentValues.put(DATE,date);
        contentValues.put(PRICE,rental_price);
        contentValues.put(FURNITURE_TYPE,type_of_furniture);
        contentValues.put(REMARK,remark);
        contentValues.put(REPORTER_NAME,reporter);

        db.update(PROPERTY_TABLE,contentValues,"ref_no=?",new String[]{original_ref_no});

    }
    public boolean updateItem(String item_id,String image_data,String name,String price,String description,int is_purchased,String latitude,String longitude)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        try{
            byte[] imageData = Base64.decode(image_data, Base64.DEFAULT);
            ContentValues contentValues=new ContentValues();
//            contentValues.put(ITEM_USERID,user_id);
            contentValues.put(IMAGE_DATA,imageData);
            contentValues.put(ITEM_NAME,name);
            contentValues.put(ITEM_PRICE,price);

            contentValues.put(ITEM_DESCRIPTION,description);
            contentValues.put(ITEM_IS_PURCHASED,is_purchased);
            contentValues.put(ITEM_LATITUDE, latitude);
            contentValues.put(ITEM_LONGITUDE, longitude);
            database.update(ITEM_TABLE,contentValues,"item_id=?",new String[]{item_id});
            database.close();
            return true;
        }catch (Error e){
            if(database.isOpen()){
                database.close();
            }
            return false;
        }

    }

    public boolean updateItem(String item_id,String image_data,String name,String price,String description,int is_purchased)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        try{
            byte[] imageData = Base64.decode(image_data, Base64.DEFAULT);
            ContentValues contentValues=new ContentValues();
//            contentValues.put(ITEM_USERID,user_id);
            contentValues.put(IMAGE_DATA,imageData);
            contentValues.put(ITEM_NAME,name);
            contentValues.put(ITEM_PRICE,price);

            contentValues.put(ITEM_DESCRIPTION,description);
            contentValues.put(ITEM_IS_PURCHASED,is_purchased);
            database.update(ITEM_TABLE,contentValues,"item_id=?",new String[]{item_id});
            database.close();
            return true;
        }catch (Error e){
            if(database.isOpen()){
                database.close();
            }
            return false;
        }

    }
    public List<String> getTableList() {
        List<String> tableList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        try {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String tableName = cursor.getString(0);
                    if (!tableName.equals("android_metadata")) {
                        tableList.add(tableName);
                        Log.d("TableList", tableName);
                    }

                    cursor.moveToNext();
                }
            }
        } finally {
            cursor.close();
        }

        return tableList;
    }
}
