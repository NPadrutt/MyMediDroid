package ch.applysolutions.mymediandroid;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cocosw.undobar.UndoBarController.UndoListener;
import com.cocosw.undobar.UndoBarController.UndoBar;
import com.example.applysolutions.mymediandroid.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import ch.applysolutions.mymediandroid.AdapterClasses.IntakeAdapter;
import ch.applysolutions.mymediandroid.AdapterClasses.MedicineAdapter;
import ch.applysolutions.mymediandroid.dataaccess.DataAccess.IntakeDataAccess;
import ch.applysolutions.mymediandroid.dataaccess.DataAccess.MedicineDataAccess;
import ch.applysolutions.mymediandroid.dataaccess.Model.*;
import ch.applysolutions.mymediandroid.LayoutClasses.FragmentIntake;
import ch.applysolutions.mymediandroid.LayoutClasses.FragmentMedicine;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private FragmentMedicine frag_medicine;
    private FragmentIntake frag_intake;

    @Inject
    public MedicineDataAccess dataAccessMedicine;
    @Inject
    public IntakeDataAccess dataAccessIntake;

    private FragmentManager fragmentManager;
    private List<Medicine> medicineList;
    private List<Intake> intakeList;
    private UndoBar undoBar;


    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((App) getApplication()).inject(this);
        medicineList = new ArrayList<>();
        intakeList = new ArrayList<>();
        undoBar = new UndoBar(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        fragmentManager = getFragmentManager();
        frag_medicine = new FragmentMedicine();
        frag_intake = new FragmentIntake();

        setContentView(R.layout.layout_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.layout_mymedis));
    }

    @Override
    public void onResume(){
        super.onResume();
        loadIntakeDataToView();
        loadMedicineDataToView();
        if(getIntent().getBooleanExtra("WIDGET", false)){
            getIntent().removeExtra("WIDGET");
            startIntakeModificationActivity(true, null);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        onSectionAttached(position + 1);
        switch (position) {
            case 0:
                //For loading a fragment
                loadMedicineDataToView();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_mymedis, frag_medicine)
                        .commit();
                break;
            case 1:
                //To start a new activity
                loadIntakeDataToView();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_mymedis, frag_intake)
                        .commit();
                break;

            case 2:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
    }

   public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_add_medi) {
            startMedicineModificationsActivity(true, null);
            return true;
        }
        else if (id == R.id.action_add_intake) {
            if(!dataAccessMedicine.getAllMedicines().isEmpty()) {
                startIntakeModificationActivity(true, null);
                return true;
            }
            else{
                Toast.makeText(this, "You must first add a medicine", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadMedicineDataToView(){
        medicineList = dataAccessMedicine.getAllMedicines();
        Collections.sort(medicineList, new Comparator<Medicine>(){
            @Override
            public int compare(Medicine lhs, Medicine rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        MedicineAdapter adapterMedicine = new MedicineAdapter(this, R.layout.listview_item_medicine,
                medicineList.toArray(new Medicine[medicineList.size()]));
        frag_medicine.setListAdapter(adapterMedicine);
    }

    public void loadIntakeDataToView() {
        intakeList = dataAccessIntake.getAllIntakes();
        Collections.sort(intakeList, new Comparator<Intake>() {
            @Override
            public int compare(Intake lhs, Intake rhs) {
                int comparator = 0;
                DateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                try {
                    Date date1 = format.parse(lhs.getDate());
                    Date date2 = format.parse(rhs.getDate());
                    comparator = date1.compareTo(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return comparator;
            }
        });
        IntakeAdapter adapterIntake = new IntakeAdapter(this, R.layout.listview_item_intake,
                intakeList.toArray(new Intake[intakeList.size()]));
        frag_intake.setListAdapter(adapterIntake);
    }

    public void deleteIntakes(final List<Intake> intakes){
        for(Intake intake : intakes){
            resetMedicineStock(intake);
            dataAccessIntake.delete(intake);
        }
        loadIntakeDataToView();

        undoBar.message(intakes.size() + getString(R.string.deleted_items)).listener(new UndoListener() {
            @Override
            public void onUndo(@Nullable Parcelable parcelable) {
                for (Intake intake : intakes) {

                    Medicine medicine = intake.getMedicine();
                    medicine.setStock(medicine.getStock() - intake.getAmount());
                    dataAccessMedicine.update(medicine);

                    dataAccessIntake.save(intake);
                }
                loadIntakeDataToView();
            }
        }).show();
    }

    private void resetMedicineStock(Intake intake) {
        Medicine medicine = intake.getMedicine();

        double currentStock = medicine.getStock();
        double intakeAmount = intake.getAmount();

        medicine.setStock(currentStock + intakeAmount);

        dataAccessMedicine.update(medicine);
    }

    public void deleteMedicines(final List<Medicine> medicines) {
        final List<Intake> intakes = new ArrayList<>();

        MaterialDialog.Builder dialogBuilder = new MaterialDialog.Builder(this);
        dialogBuilder.content(R.string.alert_text);
        dialogBuilder.positiveText(R.string.alert_button_yes);
        dialogBuilder.positiveColorRes(R.color.PrimaryDarkBlue);
        dialogBuilder.negativeText(R.string.alert_button_no);
        dialogBuilder.negativeColorRes(R.color.PrimaryDarkBlue);
        dialogBuilder.show();

        dialogBuilder.callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                for (Medicine medicine : medicines) {
                    for (Intake intake : intakeList) {
                        if (intake.getMedicine().getId() == medicine.getId()) {
                            intakes.add(intake);
                            dataAccessIntake.delete(intake);
                        }
                    }
                    dataAccessMedicine.delete(medicine);
                }
                loadMedicineDataToView();

                undoBar.message(medicines.size() + getString(R.string.deleted_items)).listener(new UndoListener() {
                    @Override
                    public void onUndo(@Nullable Parcelable parcelable) {
                        for (Medicine medicine : medicines) {
                            dataAccessMedicine.save(medicine);
                        }
                        for (Intake intake : intakes) {
                            dataAccessIntake.save(intake);
                        }
                        loadMedicineDataToView();
                    }
                }).show();
            }
        });
    }

    public void startIntakeModificationActivity  (boolean isNew, Intake intake){
        Intent modActivity = new Intent(this, ModificationsActivity.class);
        modActivity.putExtra("FRAGMENT", "Intake");
        modActivity.putExtra("NEW", isNew);

        if(intake != null){
            modActivity.putExtra("INTAKE", intake);
        }
        startActivity(modActivity);
    }

    public void startMedicineModificationsActivity (boolean isNew,  Medicine medicine) {
        Intent modActivity = new Intent(this, ModificationsActivity.class);
        modActivity.putExtra("FRAGMENT", "Medicine");
        modActivity.putExtra("NEW", isNew);

        if(medicine != null){
            modActivity.putExtra("MEDICINE", medicine);
        }
        startActivity(modActivity);
    }
}
