package ch.applysolutions.mymediandroid.dataaccess.DataAccess;


import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import ch.applysolutions.mymediandroid.dataaccess.Model.Medicine;

public class MedicineDataAccessTest extends AndroidTestCase {
    private RenamingDelegatingContext context;

    public void setUp(){
        context = new RenamingDelegatingContext(getContext(), "test_");
    }

    public void crudTestMedicine() {
        String mediName = "fooMedi";

        MedicineDataAccess medicineDataAccess = new MedicineDataAccess(context);
        Medicine medicine = new Medicine();
        medicine.setName(mediName);

        medicineDataAccess.saveToDb(medicine);

        medicineDataAccess.loadList();

        assertEquals(1, medicineDataAccess.AllMedicines.size());
        assertEquals(mediName, medicineDataAccess.AllMedicines.get(0).getName());

        String newName = "newMediNameFoo";
        medicine.setName(newName);

        medicineDataAccess.update(medicine);

        medicineDataAccess.loadList();
        assertEquals(1, medicineDataAccess.AllMedicines.size());
        assertEquals(newName, medicineDataAccess.AllMedicines.get(0).getName());

        medicineDataAccess.deleteFromDb(medicine);
        medicineDataAccess.loadList();
        assertEquals(0, medicineDataAccess.AllMedicines.size());
    }
}
