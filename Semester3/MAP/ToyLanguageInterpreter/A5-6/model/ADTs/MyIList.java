package model.ADTs;

import java.util.List;

public interface MyIList<T> {
    void add(T elem);
    List<T> getList();
    String toString();
}
