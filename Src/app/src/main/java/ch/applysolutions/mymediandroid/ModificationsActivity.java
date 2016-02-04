package ch.applysolutions.mymediandroid;



import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.applysolutions.mymediandroid.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import ch.applysolutions.mymediandroid.dataaccess.DataAccess.IntakeDataAccess;
import ch.applysolutions.mymediandroid.dataaccess.DataAccess.MedicineDataAccess;
import ch.applysolutions.mymediandroid.dataaccess.Model.Intake;
import ch.applysolutions.mymediandroid.dataaccess.Model.Medicine;
import ch.applysolutions.mymediandroid.LayoutClasses.FragmentModIntake;
import ch.applysolutions.mymediandroid.LayoutClasses.FragmentModMedicine;


public class ModificationsActivity extends AppCompatActivity {

    @Inject
    MedicineDataAccess medicineDataAccess;
    @Inject
    IntakeDataAccess intakeDataAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_modifications);

        ((App) getApplication()).inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            if("Intake".equals(getIntent().getStringExtra("FRAGMENT"))){
                getFragmentManager().beginTransaction()
                        .add(R.id.container, new FragmentModIntake())
                        .commit();
            }
            else if("Medicine".equals(getIntent().getStringExtra("FRAGMENT"))){
                getFragmentManager().beginTransaction()
                        .add(R.id.container, new FragmentModMedicine())
                        .commit();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modifications, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveMedicine(Medicine medicine){
        medicineDataAccess.save(medicine);
    }

    public void updateMedicine(Medicine medicine){
        medicineDataAccess.update(medicine);
    }

    public List<Medicine> getMedicines(){
        List<Medicine> medicineList = medicineDataAccess.getAllMedicines();
        Collections.sort(medicineList, new Comparator<Medicine>() {
            @Override
            public int compare(Medicine lhs, Medicine rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        return medicineList;
    }

    public void saveIntake(Intake intake){
        intakeDataAccess.save(intake);
    }

    public void updateIntake(Intake intake){
        intakeDataAccess.update(intake);
    }

    public List<Intake> getIntakes(){
        return intakeDataAccess.getAllIntakes();
    }
}
