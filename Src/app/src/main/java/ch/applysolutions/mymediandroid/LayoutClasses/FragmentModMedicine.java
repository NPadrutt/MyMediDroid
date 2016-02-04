package ch.applysolutions.mymediandroid.LayoutClasses;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.applysolutions.mymediandroid.R;
import ch.applysolutions.mymediandroid.ModificationsActivity;



import ch.applysolutions.mymediandroid.dataaccess.Model.Medicine;

public class FragmentModMedicine extends Fragment {

    private EditText txtMedicineName;
    private EditText txtMedicineStock;
    private EditText txtComment;
    private Medicine medicine;
    private Button buttonSaveEditMedicine;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_modification_medicine, container, false);
        final boolean isNew = getActivity().getIntent().getExtras().getBoolean("NEW");
        txtMedicineName = (EditText) view.findViewById(R.id.editTextMedicineName);
        txtMedicineStock = (EditText) view.findViewById(R.id.editTextStock);
        txtComment = (EditText) view.findViewById(R.id.editTextComment);

        buttonSaveEditMedicine = (Button) view.findViewById(R.id.buttonEditMedicine);

        if(!isNew) {
            medicine = (Medicine) getActivity().getIntent().getSerializableExtra("MEDICINE");
            txtMedicineName.setText(medicine.getName());
            txtMedicineStock.setText(String.valueOf(medicine.getStock()));
            txtComment.setText(medicine.getComment());

            getActivity().setTitle(R.string.title_edit_medi);
            buttonSaveEditMedicine.setText(R.string.button_save_changes);
        }
        else{
            getActivity().setTitle(R.string.title_add_medi);
            buttonSaveEditMedicine.setText(R.string.button_save_medicine);
        }

        buttonSaveEditMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtMedicineName.getText().toString();
                int amount = txtMedicineStock.getText().toString().isEmpty()
                        ?  0
                        : Integer.parseInt(txtMedicineStock.getText().toString());
                String comment = txtComment.getText().toString();

                if(!name.isEmpty() && amount != 0) {
                    if (!isNew) {
                        medicine.setName(name);
                        medicine.setStock(amount);
                        medicine.setComment(comment);
                        ((ModificationsActivity) getActivity()).updateMedicine(medicine);
                    } else {
                        ((ModificationsActivity) getActivity()).saveMedicine(new Medicine(name, amount, comment));
                    }
                    getActivity().finish();
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "Something is missing", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}
