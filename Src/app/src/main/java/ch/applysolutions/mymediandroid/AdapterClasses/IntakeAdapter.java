package ch.applysolutions.mymediandroid.AdapterClasses;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.applysolutions.mymediandroid.R;

import ch.applysolutions.mymediandroid.dataaccess.Model.Intake;

public class IntakeAdapter extends ArrayAdapter<Intake> {
    Context context;
    int layoutResourceId;
    Intake data[] = null;

    public IntakeAdapter(Context context, int layoutResourceId, Intake[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        IntakeHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new IntakeHolder();
            holder.txtDate = (TextView)row.findViewById(R.id.textViewDate);
            holder.txtMedicineName = (TextView) row.findViewById(R.id.textViewMedicineName);
            holder.txtAmount = (TextView)row.findViewById(R.id.textViewAmount);

            row.setTag(holder);
        }
        else
        {
            holder = (IntakeHolder)row.getTag();
        }

        Intake intake = data[position];
        holder.txtDate.setText(intake.getDate());
        holder.txtMedicineName.setText(intake.getMedicineName());
        holder.txtAmount.setText(String.valueOf(intake.getAmount()));

        return row;
    }

    static class IntakeHolder
    {
        TextView txtDate;
        TextView txtMedicineName;
        TextView txtAmount;
    }
}
