package models;

public abstract class Dao<T> {

    public abstract T find(int id);

    public abstract void create(T t);

    public abstract void udpate(T t);

    public abstract void delete(T t);
}
