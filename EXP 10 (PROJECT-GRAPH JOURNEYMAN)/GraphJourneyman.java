import java.util.*;

class Edge {
    int src, dest, weight;
    Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}

public class GraphJourneyman {

    private int vertices;
    private List<List<Integer>> adjList;
    private List<Edge> edges;

    public GraphJourneyman(int vertices) {
        this.vertices = vertices;
        adjList = new ArrayList<>();
        edges = new ArrayList<>();
        for (int i = 0; i < vertices; i++)
            adjList.add(new ArrayList<>());
    }

    public void addEdge(int src, int dest) {
        adjList.get(src).add(dest);
        adjList.get(dest).add(src);
        edges.add(new Edge(src, dest, 1)); // default weight 1 for unweighted
    }

    public void addWeightedEdge(int src, int dest, int weight) {
        adjList.get(src).add(dest);
        adjList.get(dest).add(src);
        edges.add(new Edge(src, dest, weight));
    }

    // ---------------- DFS Traversal ----------------
    public void dfs(int start, boolean[] visited) {
        visited[start] = true;
        System.out.print(start + " ");
        for (int neighbor : adjList.get(start))
            if (!visited[neighbor])
                dfs(neighbor, visited);
    }

    // ---------------- BFS Traversal ----------------
    public void bfs(int start) {
        boolean[] visited = new boolean[vertices];
        Queue<Integer> q = new LinkedList<>();
        q.add(start);
        visited[start] = true;

        while (!q.isEmpty()) {
            int node = q.poll();
            System.out.print(node + " ");
            for (int neighbor : adjList.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    q.add(neighbor);
                }
            }
        }
    }

    // ---------------- Topological Sort ----------------
    public void topologicalSortUtil(int v, boolean[] visited, Stack<Integer> stack) {
        visited[v] = true;
        for (int neighbor : adjList.get(v))
            if (!visited[neighbor])
                topologicalSortUtil(neighbor, visited, stack);
        stack.push(v);
    }

    public void topologicalSort() {
        boolean[] visited = new boolean[vertices];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < vertices; i++)
            if (!visited[i])
                topologicalSortUtil(i, visited, stack);

        System.out.print("Topological Order: ");
        while (!stack.isEmpty())
            System.out.print(stack.pop() + " ");
        System.out.println();
    }

    // ---------------- Connected Components ----------------
    public void connectedComponents() {
        boolean[] visited = new boolean[vertices];
        int count = 0;
        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                count++;
                System.out.print("Component " + count + ": ");
                dfs(i, visited);
                System.out.println();
            }
        }
    }

    // ---------------- Graph Coloring ----------------
    public void greedyColoring() {
        int[] colors = new int[vertices];
        Arrays.fill(colors, -1);
        colors[0] = 0;

        boolean[] available = new boolean[vertices];
        Arrays.fill(available, true);

        for (int u = 1; u < vertices; u++) {
            Arrays.fill(available, true);

            for (int neighbor : adjList.get(u))
                if (colors[neighbor] != -1)
                    available[colors[neighbor]] = false;

            int color;
            for (color = 0; color < vertices; color++)
                if (available[color])
                    break;

            colors[u] = color;
        }

        System.out.println("Vertex -> Color");
        for (int u = 0; u < vertices; u++)
            System.out.println(u + " -> " + colors[u]);
    }

    // ---------------- Kruskal’s MST ----------------
    static class DisjointSet {
        int[] parent, rank;
        DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }
        int find(int x) {
            if (parent[x] != x)
                parent[x] = find(parent[x]);
            return parent[x];
        }
        void union(int x, int y) {
            int rootX = find(x), rootY = find(y);
            if (rootX == rootY) return;
            if (rank[rootX] < rank[rootY]) parent[rootX] = rootY;
            else if (rank[rootX] > rank[rootY]) parent[rootY] = rootX;
            else { parent[rootY] = rootX; rank[rootX]++; }
        }
    }

    public void kruskalMST() {
        Collections.sort(edges, Comparator.comparingInt(e -> e.weight));
        DisjointSet ds = new DisjointSet(vertices);

        List<Edge> mst = new ArrayList<>();
        int cost = 0;

        for (Edge e : edges) {
            if (ds.find(e.src) != ds.find(e.dest)) {
                mst.add(e);
                cost += e.weight;
                ds.union(e.src, e.dest);
            }
        }

        System.out.println("Kruskal’s MST:");
        for (Edge e : mst)
            System.out.println(e.src + " - " + e.dest + " (" + e.weight + ")");
        System.out.println("Total Cost: " + cost);
    }

    // ---------------- Prim’s MST ----------------
    public void primMST() {
        boolean[] visited = new boolean[vertices];
        int[] key = new int[vertices];
        int[] parent = new int[vertices];
        Arrays.fill(key, Integer.MAX_VALUE);
        key[0] = 0;
        parent[0] = -1;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{0, 0});

        while (!pq.isEmpty()) {
            int u = pq.poll()[0];
            visited[u] = true;

            for (int v : adjList.get(u)) {
                for (Edge e : edges) {
                    if ((e.src == u && e.dest == v) || (e.src == v && e.dest == u)) {
                        if (!visited[v] && e.weight < key[v]) {
                            key[v] = e.weight;
                            parent[v] = u;
                            pq.add(new int[]{v, key[v]});
                        }
                    }
                }
            }
        }

        System.out.println("Prim’s MST:");
        int totalCost = 0;
        for (int i = 1; i < vertices; i++) {
            System.out.println(parent[i] + " - " + i + " (" + key[i] + ")");
            totalCost += key[i];
        }
        System.out.println("Total Cost: " + totalCost);
    }

    // ---------------- Main ----------------
    public static void main(String[] args) {
        GraphJourneyman g = new GraphJourneyman(6);
        g.addWeightedEdge(0, 1, 4);
        g.addWeightedEdge(0, 2, 3);
        g.addWeightedEdge(1, 2, 1);
        g.addWeightedEdge(1, 3, 2);
        g.addWeightedEdge(2, 3, 4);
        g.addWeightedEdge(3, 4, 2);
        g.addWeightedEdge(4, 5, 6);

        System.out.println("DFS Traversal:");
        g.dfs(0, new boolean[g.vertices]);
        System.out.println("\n\nBFS Traversal:");
        g.bfs(0);

        System.out.println("\n\nConnected Components:");
        g.connectedComponents();

        System.out.println("\nGraph Coloring:");
        g.greedyColoring();

        System.out.println("\nKruskal’s MST:");
        g.kruskalMST();

        System.out.println("\nPrim’s MST:");
        g.primMST();
    }
}
