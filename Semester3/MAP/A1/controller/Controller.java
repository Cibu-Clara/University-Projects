package controller;
import model.iTrees;
import repository.iRepository;

public class Controller {

    iRepository repo;

    public Controller(iRepository repo) {this.repo = repo;}

    public void add(iTrees t) throws Exception {this.repo.add(t);}

    public void remove(int index) throws Exception {this.repo.remove(index);}
    public void printSolution(int age) {
        iTrees[] data = this.repo.getAll();
        for (iTrees datum : data) {
            if (datum != null &&datum.problemSolver(age))
                System.out.print(datum);
        }
    }

    public void printAll() {
        iTrees[] data = this.repo.getAll();
        for(int i = 0; i < data.length; i++)
            if (data[i] != null)
                System.out.print(i + 1 + ". " + data[i].toString());
    }
}
