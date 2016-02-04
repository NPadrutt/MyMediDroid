package ch.applysolutions.mymediandroid.dataaccess.Model;

import java.io.Serializable;

public class Medicine implements Serializable {
    private int id;
    private String _name;
    private double _stock;
    private String _comment;

    public Medicine(){}

    public Medicine(String name, int stock, String comment){
        _name = name;
        _stock = stock;
        _comment = comment;
    }

    public int getId(){ return id; }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return _name;
    }

    public void setName(String name){
        _name = name;
    }

    public double getStock() {
        return _stock;
    }

    public void setStock(double stock) {
        this._stock = stock;
    }

    public String getComment() {
        return _comment;
    }

    public void setComment(String comment) {
        _comment = comment;
    }

    @Override
    public String toString() {
        return _name + "(Amount: " + _stock + ")";
    }
}
