package repository;
import model.iTrees;

public interface iRepository {
     void add(iTrees t) throws Exception;
     void remove(int index) throws Exception;
     iTrees[] getAll();
}
