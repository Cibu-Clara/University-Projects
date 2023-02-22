package model.ADTs;

import exceptions.InterpreterException;
import java.util.List;

public interface MyIStack<T> {
    T pop() throws InterpreterException;
    void push(T element);
    boolean isEmpty();
    List<T> getReversed();
    String toString();
}
