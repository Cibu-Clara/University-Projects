package repository;
import model.iTrees;

public class Repository implements iRepository {
    iTrees[] trees;
    private int current_size;

    public Repository(int max_size){
        this.current_size = 0;
        trees = new iTrees[max_size];
    }

    public void add(iTrees t) throws Exception{
        if(this.current_size == this.trees.length)
            throw new Exception("The container is full!");
        else
        {
            this.trees[this.current_size] = t;
            ++this.current_size;
        }
    }

    public void remove(int index) throws Exception{
        if(index < 1 || index >this.current_size )
            throw new Exception("Index out of range!");
        else {
            for(int i = index - 1; i < this.current_size - 1; i++)
                trees[i] = trees[i + 1];
            trees[current_size - 1] = null;
            current_size--;
        }
    }
    public iTrees[] getAll() { return this.trees; }
}
