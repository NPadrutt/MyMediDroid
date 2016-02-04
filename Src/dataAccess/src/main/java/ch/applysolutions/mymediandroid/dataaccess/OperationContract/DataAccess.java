package ch.applysolutions.mymediandroid.dataaccess.OperationContract;

public interface DataAccess<T> {
    void save(T itemToSave);

    void delete(T itemToDelete);

    void loadList();

    void update(T itemToUpdate);
}
