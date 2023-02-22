package model.ADTs;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    List<T> list;

    public MyList() {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(T elem) {
        this.list.add(elem);
    }

    @Override
    public List<T> getList() {
        return list;
    }

    @Override
    public String toString() {
        return this.list.toString();
    }

}
