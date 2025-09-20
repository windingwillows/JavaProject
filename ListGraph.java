import java.util.*;

public class ListGraph implements Graph {
    private HashMap<String, LinkedList<String>> nodes = new HashMap<>();

    public boolean addNode(String n) {
        if(nodes.containsKey(n)) {
            return false;
        }
        nodes.put(n, new LinkedList<>());
        return true;
    }

    public boolean addEdge(String n1, String n2) {
        if(!nodes.containsKey(n1) || !nodes.containsKey(n2)) {
            throw new NoSuchElementException();
        }
        if(nodes.get(n1).contains(n2)) {
            return false;
        }
        nodes.get(n1).add(n2);
        return true;
    }

    public boolean hasNode(String n) {
        return nodes.containsKey(n);
    }

    public boolean hasEdge(String n1, String n2) {
	    if(!nodes.containsKey(n1) || !nodes.containsKey(n2)) {
            return false;
        }
        return nodes.get(n1).contains(n2);
    }

    public boolean removeNode(String n) {
	    if(!nodes.containsKey(n)) {
            return false;
        }
        nodes.remove(n);
        for(LinkedList<String> s : nodes.values()) {
            s.remove(n);
        }
        return true;
    }

    public boolean removeEdge(String n1, String n2) {
	    if(!nodes.containsKey(n1) || !nodes.containsKey(n2)) {
            throw new NoSuchElementException();
        } 
        return nodes.get(n1).remove(n2);
    }

    public List<String> nodes() {
        return new ArrayList<>(nodes.keySet());
    }

    public List<String> succ(String n) {
        if(!nodes.containsKey(n)) {
            throw new NoSuchElementException();
        }
        return new ArrayList<>(nodes.get(n));
    }

    public List<String> pred(String n) {
	    if(!nodes.containsKey(n)) {
            throw new NoSuchElementException();
        }
        List<String> predecessors = new ArrayList<>();
        for(String s : nodes.keySet()) {
            if(nodes.get(s).contains(n)) {
                predecessors.add(s);
            }
        }
        return predecessors;
    }

    public Graph union(Graph g) {
        Graph union = new ListGraph();
        for(String i : this.nodes()) {
            union.addNode(i);
        }
        for(String i : this.nodes()) {
            for(String j : this.succ(i)) {
                union.addEdge(i, j);
            }
        }
        for(String i : g.nodes()) {
            union.addNode(i);
        }
        for(String i : g.nodes()) {
            for(String j : g.succ(i)) {
                union.addEdge(i, j);
            }
        }
        return union;
    }

    public Graph subGraph(Set<String> sub) {
        Graph s = new ListGraph();
        for(String i : nodes()) {
            if(sub.contains(i)) {
                s.addNode(i);
            }
        }
        for(String i : nodes()) {
            if(sub.contains(i)) {
                for(String j : nodes.succ(i)) {
                    if(sub.contains(j)) {
                        s.addEdge(i, j);
                    }
                }
            }
        }
        return s;
    }

    public boolean connected(String n1, String n2) {
        if(!nodes.containsKey(n1) || !nodes.containsKey(n2)) {
            throw new NoSuchElementException();
        } 
        if(n1.equals(n2)) {
            return true;
        }

        Deque<String> search = new ArrayDeque<>();
        HashSet<String> visited = new HashSet<>();
        search.push(n1);
        visited.add(n1);

        while(!search.isEmpty()) {
            String a = search.pop();
            for(String i : nodes.get(a)) {
                if(i.equals(n2)) {
                    return true;
                }
                if(visited.add(i)) {
                    search.push(i);
                }
            }
        }
        return false;
    }
}
