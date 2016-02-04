package ch.applysolutions.mymediandroid.AdapterClasses;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.applysolutions.mymediandroid.R;

import ch.applysolutions.mymediandroid.dataaccess.Model.Medicine;

/**
 * Created by ap on 04.11.2014.
 */
public class MedicineAdapter extends ArrayAdapter<Medicine> {
    Context context;
    int layoutResourceId;
    Medicine data[] = null;

    public MedicineAdapter(Context context, int layoutResourceId, Medicine[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MedicineHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new MedicineHolder();
            holder.txtName = (TextView)row.findViewById(R.id.textViewName);
            holder.txtAmount = (TextView)row.findViewById(R.id.textViewAmount);

            row.setTag(holder);
        }
        else
        {
            holder = (MedicineHolder)row.getTag();
        }

        Medicine medicine = data[position];
        holder.txtName.setText(medicine.getName());
        holder.txtAmount.setText(String.valueOf(medicine.getStock()));

        return row;
    }

    static class MedicineHolder
    {
        TextView txtName;
        TextView txtAmount;
    }
}
