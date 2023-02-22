package model.ADTs;

import exceptions.ADTException;
import java.util.List;

public interface MyIStack<T> {
    T pop() throws ADTException;
    void push(T element);
    boolean isEmpty();
    List<T> getReversed();
    String toString();
}
