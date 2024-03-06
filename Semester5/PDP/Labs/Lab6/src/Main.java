import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

class HamiltonianCycleFinder {
    static class DirectedGraph {
        int V; // Number of vertices
        List<Integer>[] adj; // Adjacency list

        public DirectedGraph(int V) {
            this.V = V;
            adj = new ArrayList[V];
            for (int i = 0; i < V; i++) {
                adj[i] = new ArrayList<>();
            }
        }
        public void addEdge(int v, int w) {
            adj[v].add(w);
        }
    }

    static class HamiltonianCycleTask implements Callable<Boolean> {
        DirectedGraph graph;
        int[] path;
        int pos;

        public HamiltonianCycleTask(DirectedGraph graph, int[] path, int pos) {
            this.graph = graph;
            this.path = path;
            this.pos = pos;
        }

        private boolean isSafe(int v, int pos) {
            if (pos == 0) return true; // If it's the first vertex, it's always safe
            if (!graph.adj[path[pos - 1]].contains(v)) return false;

            for (int i = 0; i < pos; i++) {
                if (path[i] == v) return false;
            }
            return true;
        }

        @Override
        public Boolean call() {
            if (pos == graph.V) {
                // Hamiltonian cycle found
                return (graph.adj[path[pos - 1]].contains(path[0]));
            }

            for (int v : graph.adj[path[pos - 1]]) {
                if (isSafe(v, pos)) {
                    path[pos] = v;

                    // Recursively solve with the updated path
                    if (new HamiltonianCycleTask(graph, path, pos + 1).call()) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public static boolean findHamiltonianCycle(DirectedGraph graph) {
        int V = graph.V;
        int[] path = new int[V];
        for (int i = 0; i < V; i++) {
            path[i] = -1;
        }
        path[0] = 0; // Start from vertex 0

        List<Callable<Boolean>> tasks = new ArrayList<>();
        for (int i = 1; i < V; i++) {
            tasks.add(new HamiltonianCycleTask(graph, path, i));
        }

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        try {
            List<Future<Boolean>> results = executor.invokeAll(tasks);
            for (Future<Boolean> result : results) {
                if (result.get()) {
                    return true;
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        return false;
    }
}

public class Main {
    public static void main(String[] args) {
        HamiltonianCycleFinder.DirectedGraph graphHamiltonian = new HamiltonianCycleFinder.DirectedGraph(50);
        // Add edges to create a graph with a Hamiltonian cycle
        for (int i = 0; i < 49; i++) {
            graphHamiltonian.addEdge(i, i + 1);
        }
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            // Generate random integers in range 0 to 49
            int a = rand.nextInt(49);
            int b = rand.nextInt(49);
            graphHamiltonian.addEdge(a, b);
        }
        graphHamiltonian.addEdge(49, 0);

        boolean hasHamiltonianCycle = HamiltonianCycleFinder.findHamiltonianCycle(graphHamiltonian);
        if (hasHamiltonianCycle) {
            System.out.println("Hamiltonian cycle exists in the graph.");
        } else {
            System.out.println("No Hamiltonian cycle found in the graph.");
        }

        HamiltonianCycleFinder.DirectedGraph graph = new HamiltonianCycleFinder.DirectedGraph(50);
        // Add edges to create a graph without a Hamiltonian cycle
        for (int i = 0; i < 49; i++) {
            graph.addEdge(i, i + 1);
        }

        boolean hasHamiltonianCycleNot = HamiltonianCycleFinder.findHamiltonianCycle(graph);
        if (hasHamiltonianCycleNot) {
            System.out.println("Hamiltonian cycle exists in the graph.");
        } else {
            System.out.println("No Hamiltonian cycle found in the graph.");
        }
    }
}
