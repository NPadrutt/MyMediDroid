package ch.applysolutions.mymediandroid.dataaccess.DataAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import ch.applysolutions.mymediandroid.dataaccess.AbstractDataAccess;
import ch.applysolutions.mymediandroid.dataaccess.Model.Intake;
import ch.applysolutions.mymediandroid.dataaccess.Model.Medicine;

public class IntakeDataAccess extends AbstractDataAccess<Intake> {

    public List<Intake> AllIntakes;
    public MedicineDataAccess medicineDataAccess;

    public IntakeDataAccess(Context context) {
        super(context);
        medicineDataAccess = new MedicineDataAccess(context);
    }

    @Override
    protected void saveToDb(Intake intake) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, intake.getDate());
        values.put(KEY_AMOUNT, intake.getAmount());
        values.put(KEY_MEDICINE_ID, intake.getMedicine().getId());

        db.insert(TABLE_INTAKE,
                null,
                values);

        db.close();
    }

    @Override
    protected void loadListFromDb() {
        AllIntakes = new LinkedList<Intake>();
        String query = "SELECT * FROM " + TABLE_INTAKE;
        List<Medicine> medicineList = medicineDataAccess.getAllMedicines();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Intake intake = null;
        if(cursor.moveToFirst()){
            do{
                intake = new Intake();
                intake.setId(Integer.parseInt(cursor.getString(0)));
                intake.setDate(cursor.getString(1));
                intake.setAmount(Integer.parseInt(cursor.getString(2)));
                for(Medicine medicine : medicineList){
                    if(medicine.getId() == Integer.parseInt(cursor.getString(3))){
                        intake.setMedicine(medicine);
                    }
                }
                AllIntakes.add(intake);
            } while (cursor.moveToNext());
        }
    }

    @Override
    protected void updateItemInDb(Intake intake) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, intake.getDate());
        values.put(KEY_AMOUNT, intake.getAmount());
        values.put(KEY_MEDICINE_ID, intake.getMedicine().getId());

        db.update(TABLE_INTAKE,
                values,
                KEY_ID + " = ?",
                new String[] {String.valueOf(intake.getId())});

        db.close();
    }

    @Override
    protected void deleteFromDb(Intake intake) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_INTAKE,
                KEY_ID + "=?",
                new String[] {String.valueOf(intake.getId())});

        db.close();
    }

    public List<Intake> getAllIntakes() {
        loadListFromDb();
        return AllIntakes;
    }
}
