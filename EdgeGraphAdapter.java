import java.util.*;

public class EdgeGraphAdapter implements EdgeGraph {

    private Graph g;

    public EdgeGraphAdapter(Graph g) { this.g = g; }

    public boolean addEdge(Edge e) {
      String a = e.getSrc();
      String b = e.getDst();
      g.addNode(a);
      g.addNode(b);
      return g.addEdge(a, b);
    }

    public boolean hasNode(String n) {
      return g.hasNode(n);
    }

    public boolean hasEdge(Edge e) {
	    return g.hasEdge(e.getSrc(), e.getDst());
    }

    public boolean removeEdge(Edge e) {
      String a = e.getSrc();
      String b = e.getDst();
      if(!g.hasNode(a) || !g.hasNode(b)) {
        return false;
      }
	  boolean r = g.removeEdge(a, b);
      if(r) {
        if(g.hasNode(a) && g.succ(a).isEmpty() && g.pred(a).isEmpty()) {
          g.removeNode(a);
        }
        if(g.hasNode(b) && g.succ(b).isEmpty() && g.pred(b).isEmpty()) {
          g.removeNode(b);
        }
      }
      return r;
    }

    public List<Edge> outEdges(String n) {
      if(!g.hasNode(n)) {
        return new ArrayList<>();
      }
      List<Edge> outEdge = new ArrayList<>();
      for(String i : g.succ(n)) {
        outEdge.add(new Edge(n, i));
      }
      return outEdge;
    }

    public List<Edge> inEdges(String n) {
      if(!g.hasNode(n)) {
        return new ArrayList<>();
      }
      List<Edge> inEdge = new ArrayList<>();
      for(String i : g.pred(n)) {
        inEdge.add(new Edge(i, n));
      }
      return inEdge;
    }

    public List<Edge> edges() {
      List<Edge> allEdge = new ArrayList<>();
      for(String i : g.nodes()) {
        for(String j : g.succ(i)) {
          allEdge.add(new Edge(i, j));
        }
      }
      return allEdge;
    }

    public EdgeGraph union(EdgeGraph n) {
      Graph a = new ListGraph();
      EdgeGraphAdapter b = new EdgeGraphAdapter(a);
      for(String i : this.g.nodes()) {
        a.addNode(i);
        for(String j : this.g.succ(i)) {
          a.addEdge(i, j);
        }
      }
      for(Edge e : n.edges()) {
        b.addEdge(e);
      }
      return b;
    }

    public boolean hasPath(List<Edge> e) {
      if(e == null || e.isEmpty()) {
        return true;
      }
      for(Edge i : e) {
        if(!hasEdge(i)) {
          return false;
        }
      }
      for(int j = 0; j < e.size() - 1; j++) {
        if(!e.get(j).getDst().equals(e.get(j+1).getSrc())) {
          throw new BadPath();
        }
      }
      return true;
    }

}
