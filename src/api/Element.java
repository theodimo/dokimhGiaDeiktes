package api;

//this interface should be implemented by classes whose instances are going to be saved at the database
public interface Element<T> {
    public T getCopy();
}
