package DB_Context;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBContext extends SQLiteOpenHelper {
    //declaring database name
    private static String DB_NAME="rentalU";

    //declaring table name
    private static String USER_TABLE="user_table";
    private static String PROPERTY_TABLE="property_table";

    //declaring column names for user_table
    private static String USER_ID="id";
    private static String USER_NAME="name";
    //private static String USER_EMAIL="email";
    private static String USER_PASSWORD="password";
    //declaring column names for property_table
    private static String PROPERTY_REF_NO="ref_no";
    private static String PROPERTY_TYPE="property_type";
    private static String NO_OF_ROOMS="no_of_rooms";
    private static String DATE="date";
    private static String PRICE="rental_price";
    private static String FURNITURE_TYPE="type_of_furniture";
    private static String REMARK="remark";
    private static String REPORTER_NAME="reporter_name";

    public DBContext(Context context){
        super(context,DB_NAME,null,1);
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+PROPERTY_TABLE);

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
    public void addProperty(String property_type,String no_of_rooms,String date,String rental_price,String type_of_furniture,String remark,String reporter)
    {
        SQLiteDatabase database=this.getWritableDatabase();
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

    //delete property form property table
    public void deleteProperty(String ref_no)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(PROPERTY_TABLE,"ref_no=?",new String[]{ref_no});
        db.close();
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
