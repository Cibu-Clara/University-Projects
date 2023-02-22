package model.ADTs;

import exceptions.ADTException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface MyIDictionary<T1,T2> {
    boolean isDefined(T1 key);
    void put(T1 key, T2 value);
    T2 lookUp(T1 key) throws ADTException;
    void update(T1 key, T2 value) throws ADTException;
    Collection<T2> values();
    Set<T1> keySet();
    Map<T1, T2> getContent();
    void remove(T1 value) throws ADTException;
    MyIDictionary<T1, T2> deepCopy() throws ADTException;
    String toString();
}
