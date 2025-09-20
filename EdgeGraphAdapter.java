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
      if(g.removeEdge(a, b)) {
        if(g.succ(a).isEmpty() && g.pred(a).isEmpty()) {
          g.removeNode(a);
        }
        if(g.hasNode(b) && g.succ(b).isEmpty() && g.pred(b).isEmpty()) {
          g.removeNode(b);
        }
      }
      return g.removeEdge(a, b);
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
        for(String j : g.succ()) {
          allEdge.add(new Edge(i, j));
        }
      }
      return allEdge;
    }

    public EdgeGraph union(EdgeGraph g) {
      Graph a = new ListGraph();
      EdgeGraphAdapter b = new EdgeGraphAdapter(a);
      for(String i : g.nodes()) {
        a.addNode(i);
        for(String j : g.succ(i)) {
          a.addEdge(i, j);
        }
      }
      for(Edge e : g.edges()) {
        b.addEdge(e);
      }
      return b;
    }

    public boolean hasPath(List<Edge> e) {
      if(e == null || e.isEmpty()) {
        return true;
      }
      for(Edge i : e) {
        if(!hasEdge(e)) {
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
