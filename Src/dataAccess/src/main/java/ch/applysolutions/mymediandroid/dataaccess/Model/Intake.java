package ch.applysolutions.mymediandroid.dataaccess.Model;

import java.io.Serializable;

/**
 * Created by Alessandro on 30.10.2014.
 */
public class Intake implements Serializable {

    private int _id;
    private String _date;
    private int _amount;
    private Medicine medicine;



    public Intake(){}

    public Intake(String date, int amount, Medicine medicine){
        super();
        _date=date;
        _amount = amount;
        this.medicine = medicine;
    }
    public int getId(){ return _id; }

    public void setId(int id){
        _id = id;
    }

    public String getDate(){
        return _date;
    }

    public void setDate(String date){
        _date = date;
    }

    public int getAmount(){
        return _amount;
    }

    public void setAmount(int amount){
        _amount = amount;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public String getMedicineName(){
        return medicine.getName();
    }

    @Override
    public String toString() {
        return "Intake [id=" + _id + ", date=" + _date + ", amount=" + _amount + "]";
    }
}
