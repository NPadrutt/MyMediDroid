package ch.applysolutions.mymediandroid.dataaccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ch.applysolutions.mymediandroid.dataaccess.OperationContract.DataAccess;

public abstract class AbstractDataAccess<T> extends SQLiteOpenHelper implements DataAccess<T> {

    private static final String DATABASE_NAME = "MyMediDB";

    private static final int DATABASE_VERSION = 1;

    //Tables
    public static final String TABLE_MEDICINE = "medicine";
    protected static final String TABLE_INTAKE = "intake";

    //Comon Columsn
    public static final String KEY_ID = "_id";

    //Columns Medicine
    public static final String KEY_NAME = "name";
    public static final String KEY_MED_AMOUNT = "medicine_amount";

    //Columns Intake
    protected static final String KEY_DATE = "date";
    protected static final String KEY_AMOUNT = "amount";
    protected static final String KEY_MEDICINE_ID = "medicine_id";

    public AbstractDataAccess(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    protected abstract void saveToDb(T itemToSave);

    protected abstract void deleteFromDb(T itemToDelete);

    protected abstract void loadListFromDb();

    protected abstract void updateItemInDb(T itemToUpdate);

    @Override
    public void save(T itemToSave){
        saveToDb(itemToSave);
    }

    @Override
    public void delete(T itemToDelete) {
        deleteFromDb(itemToDelete);
    }

    @Override
    public void loadList() {loadListFromDb(); }

    @Override
    public void update(T itemToUpdate) {
        updateItemInDb(itemToUpdate);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MEDICINE_TABLE);
        db.execSQL(CREATE_INTAKE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
    }

    private static String CREATE_MEDICINE_TABLE = "CREATE TABLE " + TABLE_MEDICINE + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT, " + KEY_MED_AMOUNT + " INTEGER )";

    private static String CREATE_INTAKE_TABLE = "CREATE TABLE " + TABLE_INTAKE + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_DATE + " DATE, " + KEY_AMOUNT + " INTEGER, " +
            KEY_MEDICINE_ID + " INTEGER, " + "FOREIGN KEY (" + KEY_MEDICINE_ID + ") REFERENCES "
            + TABLE_MEDICINE + " (" + KEY_ID + "))";
}
