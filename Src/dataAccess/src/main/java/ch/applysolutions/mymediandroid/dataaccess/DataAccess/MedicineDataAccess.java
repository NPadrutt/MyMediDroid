package ch.applysolutions.mymediandroid.dataaccess.DataAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import ch.applysolutions.mymediandroid.dataaccess.Model.Medicine;
import ch.applysolutions.mymediandroid.dataaccess.AbstractDataAccess;

public class MedicineDataAccess extends AbstractDataAccess<Medicine> {

    public List<Medicine> AllMedicines;
    public MedicineDataAccess(Context context) {
        super(context);
    }


    @Override
    protected void saveToDb(Medicine medicine) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, medicine.getName());
        values.put(KEY_MED_AMOUNT, medicine.getStock());

        db.insert(TABLE_MEDICINE,
                null,
                values);

        db.close();
    }

    @Override
    protected void loadListFromDb() {
        AllMedicines = new LinkedList<Medicine>();
        String query = "SELECT * FROM " + TABLE_MEDICINE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Medicine medicine = null;
        if(cursor.moveToFirst()){
            do{
                medicine = new Medicine();
                medicine.setId(Integer.parseInt(cursor.getString(0)));
                medicine.setName(cursor.getString(1));
                medicine.setStock(Integer.parseInt(cursor.getString(2)));

                AllMedicines.add(medicine);
            } while (cursor.moveToNext());
        }
    }

    @Override
    protected void updateItemInDb(Medicine medicine) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, medicine.getName());
        values.put(KEY_MED_AMOUNT, medicine.getStock());

        db.update(TABLE_MEDICINE,
                values,
                KEY_ID + " = ?",
                new String[] {String.valueOf(medicine.getId())});

        db.close();
    }

    @Override
    protected void deleteFromDb(Medicine medicine) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_MEDICINE,
                KEY_ID + " = ? ",
                new String[] {String.valueOf(medicine.getId())});

        db.close();
    }

    public List<Medicine> getAllMedicines() {
        loadListFromDb();
        return AllMedicines;
    }
}
