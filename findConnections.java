import java.util.*;

public class findConnections {
    // A class to represent an edge in the graph
    static class Edge {
        int u; // source node
        int v; // destination node
        public Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }
    }

    // A class to represent a graph
    static class Graph {
        int n; // number of nodes
        List<List<Integer>> adj; // adjacency list
        List<Edge> bridges; // list of critical connections

        public Graph(int n) {
            this.n = n;
            adj = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                adj.add(new ArrayList<>());
            }
            bridges = new ArrayList<>();
        }

        // A method to add an edge to the graph
        public void addEdge(int u, int v) {
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        // A method to find all critical connections in the graph using Tarjan's algorithm
        public List<Edge> findBridges() {
            boolean[] visited = new boolean[n]; // array to mark visited nodes
            int[] disc = new int[n]; // array to store discovery time of nodes
            int[] low = new int[n]; // array to store lowest reachable node from a node
            int[] parent = new int[n]; // array to store parent of a node in DFS tree
            Arrays.fill(parent, -1); // initialize parent as -1 for all nodes

            int time = 0; // variable to keep track of discovery time

            // loop through all nodes and perform DFS from each unvisited node
            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    dfs(i, visited, disc, low, parent, time);
                }
            }

            return bridges; // return the list of critical connections
        }

        // A helper method to perform DFS and find bridges
        private void dfs(int u, boolean[] visited, int[] disc, int[] low, int[] parent, int time) {
            visited[u] = true; // mark the current node as visited
            disc[u] = low[u] = ++time; // assign discovery and low values

            // loop through all adjacent nodes of the current node
            for (int v : adj.get(u)) {
                if (!visited[v]) { // if the adjacent node is not visited
                    parent[v] = u; // set the current node as its parent
                    dfs(v, visited, disc, low, parent, time); // recur for the adjacent node

                    low[u] = Math.min(low[u], low[v]); // update the low value of the current node

                    if (low[v] > disc[u]) { // if the low value of the adjacent node is greater than the discovery value of the current node
                        bridges.add(new Edge(u, v)); // then the edge between them is a bridge
                    }
                } else if (v != parent[u]) { // if the adjacent node is already visited and not the parent of the current node
                    low[u] = Math.min(low[u], disc[v]); // update the low value of the current node
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of servers: ");
        int n = sc.nextInt(); // read number of servers from input
        System.out.println("Enter number of connections: ");
        int m = sc.nextInt(); // read number of connections from input

        Graph g = new Graph(n); // create a graph with n nodes

        System.out.println("Enter connections: ");
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt(); // read source node of connection from input
            int v = sc.nextInt(); // read destination node of connection from input
            g.addEdge(u, v); // add edge to the graph
        }

        sc.close(); // close scanner

        List<Edge> bridges = g.findBridges(); // find all critical connections in the graph

        System.out.println("Critical connections are: ");
        for (Edge e : bridges) {
            System.out.println(e.u + " - " + e.v); // print each critical connection in any order
        }
    }
}