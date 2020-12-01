package cooptool.models;

public abstract class DAO<T> {

    public abstract T find(int id);

    public abstract void create(T t);

    public abstract void update(T t);

    public abstract void delete(T t);
}
