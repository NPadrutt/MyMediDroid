package ch.applysolutions.mymediandroid.LayoutClasses;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.applysolutions.mymediandroid.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.applysolutions.mymediandroid.ModificationsActivity;
import ch.applysolutions.mymediandroid.dataaccess.Model.Intake;
import ch.applysolutions.mymediandroid.dataaccess.Model.Medicine;

/**
 * Created by Alessandro on 06.11.2014.
 */
public class FragmentModIntake extends Fragment {

    private int previousAmount;
    private Button buttonDate;
    private EditText txtAmount;
    private Intake intake;
    private Spinner spinner;
    private ModificationsActivity modificationsActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_modification_intake, container, false);
        modificationsActivity = (ModificationsActivity) getActivity();
        txtAmount = (EditText) view.findViewById(R.id.editText_Amount);
        buttonDate = (Button) view.findViewById(R.id.button_Date);
        Button buttonSaveIntake= (Button) view.findViewById(R.id.button_SaveIntake);
        spinner = (Spinner) view.findViewById(R.id.medicine_spinner);
        loadSpinnerData();
        final boolean isNew = modificationsActivity.getIntent().getExtras().getBoolean("NEW");


        //Control if it's a new intake or editing a exsiting intake
        if(!isNew) {
            intake = (Intake) modificationsActivity.getIntent().getSerializableExtra("INTAKE");
            txtAmount.setText(String.valueOf(intake.getAmount()));
            previousAmount = intake.getAmount();
            buttonDate.setText(intake.getDate());

            modificationsActivity.setTitle(R.string.title_edit_intake);
            buttonSaveIntake.setText(R.string.button_save_changes);
        }
        else{
            buttonDate.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
            modificationsActivity.setTitle(R.string.title_add_intake);
            buttonSaveIntake.setText(R.string.button_save_intake);
        }


        buttonSaveIntake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = buttonDate.getText().toString();
                int amount = txtAmount.getText().toString().isEmpty() ?  0 : Integer.parseInt(txtAmount.getText().toString());
                Medicine medicine = (Medicine) spinner.getSelectedItem();

                if(amount <= medicine.getStock()) {
                    if (!date.isEmpty() && amount != 0 && !spinner.getSelectedItem().equals(null)) {
                        if (!isNew) {
                            intake.setDate(date);
                            updateStock(amount, medicine);
                            intake.setAmount(amount);
                            intake.setMedicine(medicine);
                            modificationsActivity.updateIntake(intake);
                        }
                        else {
                            medicine.setStock(medicine.getStock() - amount);
                            modificationsActivity.saveIntake(new Intake(date, amount, medicine));
                        }
                        modificationsActivity.updateMedicine(medicine);
                        modificationsActivity.finish();
                    } else {
                        Toast.makeText(modificationsActivity, "Something is missing", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(modificationsActivity,"You have not so much medicine", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void updateStock(int amount, Medicine medicine) {
        if(previousAmount > amount){
            medicine.setStock(medicine.getStock() + (previousAmount - amount));
        }
        else{
            medicine.setStock(medicine.getStock() - (amount - previousAmount));
        }
    }

    private void loadSpinnerData(){
        List<Medicine> medicineList  = modificationsActivity.getMedicines();
        List<String> medicineNames = new ArrayList<String>();

        for(Medicine medicine : medicineList){
            medicineNames.add(medicine.getName());
        }

        // Creating adapter for spinner
        ArrayAdapter<Medicine> dataAdapter = new ArrayAdapter<Medicine>(modificationsActivity,
                android.R.layout.simple_spinner_item, medicineList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

}
