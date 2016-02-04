package ch.applysolutions.mymediandroid.LayoutClasses;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.example.applysolutions.mymediandroid.R;
import ch.applysolutions.mymediandroid.MainActivity;
import ch.applysolutions.mymediandroid.dataaccess.Model.Medicine;


public class FragmentMedicine extends ListFragment {
    private boolean isMultipleFinished;
    private MainActivity mainActivity;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isMultipleFinished = true;
        mainActivity = (MainActivity) getActivity();

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> av, View v, int position, long id) {
                isMultipleFinished = false;
                return false;
            }
        });

        getListView().setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                final int checkedCount = getListView().getCheckedItemCount();
                actionMode.setSubtitle(checkedCount + " " + getString(R.string.title_item_selected));

            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate(R.menu.menu_multiple_selection, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                if (menuItem.getItemId() == mainActivity.findViewById(R.id.action_delete).getId()) {
                    List<Medicine> medicines = new ArrayList<>();
                    SparseBooleanArray checked = getListView().getCheckedItemPositions();
                    for (int i = 0; i < getListView().getCount(); i++)
                        if (checked.get(i)) {
                            medicines.add((Medicine) getListView().getItemAtPosition(i));
                        }
                    mainActivity.deleteMedicines(medicines);
                    actionMode.finish();
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                isMultipleFinished = true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_medicine, container, false);
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (isMultipleFinished) {
            Medicine medicine = (Medicine) l.getAdapter().getItem(position);
            mainActivity.startMedicineModificationsActivity(false, medicine);
        }

    }

}