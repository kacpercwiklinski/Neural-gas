import org.jetbrains.annotations.NotNull;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.*;
import java.util.stream.Collectors;

public class NeuralGas {
    final PApplet pApplet;
    private final Random random;
    List<Node> referenceVectors;
    List<Edge> edges;
    int inputSignals;

    public NeuralGas(PApplet pApplet) {
        this.pApplet = pApplet;
        this.random = new Random();
        this.inputSignals = 1;
        this.referenceVectors = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public void initializeNeuralGas() {
        Node a = new Node((float) Math.random() * Main.WIDTH, (float) Math.random() * Main.HEIGHT);
        Node b = new Node((float) Math.random() * Main.WIDTH, (float) Math.random() * Main.HEIGHT);
        Edge edge = new Edge(a, b);
        this.edges.add(edge);
        referenceVectors.add(a);
        referenceVectors.add(b);
    }

    public void adapt(@NotNull PVector dataVector) {

        Node s1 = this.referenceVectors.stream().min((v1, v2) -> v1.dist(dataVector) >= v2.dist(dataVector) ? 1 : -1).get();
        Node s2 = this.referenceVectors.stream().filter(v -> !v.equals(s1)).min((v1, v2) -> v1.dist(dataVector) >= v2.dist(dataVector) ? 1 : -1).get();

        getNodeEdges(s1).forEach(Edge::increaseAge);

        float distance = s1.dist(dataVector);
        s1.increaseError((float) Math.pow(distance, 2));

        PVector s1Direction = new PVector(dataVector.x, dataVector.y).sub(s1);
        s1.add(s1Direction.mult(Main.menu.E_B));

        List<Node> s1Neighbours = getNeighbours(s1);

        s1Neighbours.forEach(neighbour -> {
            PVector dir = new PVector(dataVector.x, dataVector.y).sub(neighbour);
            neighbour.add(dir.mult(Main.menu.E_N));
        });

        Optional<Edge> winnersEdge = getNodesEdge(s1, s2);
        if (winnersEdge.isPresent()) {
            winnersEdge.get().setAge(0.0f);
        } else {
            edges.add(new Edge(s1, s2));
        }

        // Remove too old edges
        this.edges = this.edges.stream().filter(edge -> edge.getAge() < Main.menu.A_MAX).collect(Collectors.toList());

        // Remove points with no edges
        this.referenceVectors = this.referenceVectors.stream().filter(n -> getNodeEdges(n).size() > 0).collect(Collectors.toList());
    }

    public void nextIteration(List<PVector> input){
        this.adapt(input.get(random.nextInt(input.size())));
        this.newNodeCheck();

        // Decrease all nodes error
        this.referenceVectors.forEach(n -> n.decreaseError(n.getError() * Main.D));

        this.inputSignals++;
    }

    public void newNodeCheck() {
        if (this.inputSignals % Main.menu.LAMBDA == 0) {
            Node q = this.referenceVectors.stream().max(Comparator.comparing(Node::getError)).get();
            Node f = this.getNeighbours(q).stream().max(Comparator.comparing(Node::getError)).get();
            Node r = new Node((q.x + f.x) / 2, (q.y + f.y) / 2);

            Edge q_r = new Edge(q, r);
            Edge f_r = new Edge(f, r);
            Edge oldEdge = getNodesEdge(q, f).get();
            this.edges.remove(oldEdge);

            q.decreaseError();
            f.decreaseError();
            r.setError(q.getError());

            this.edges.add(q_r);
            this.edges.add(f_r);
            this.referenceVectors.add(r);
        }
    }

    public void drawGas() {
        this.referenceVectors.forEach(node -> {
            this.pApplet.strokeWeight(5f);
            this.pApplet.stroke(255, 0, 0);
            this.pApplet.point(node.x, node.y);
        });

        this.edges.forEach(edge -> {
            this.pApplet.strokeWeight(1f);
            this.pApplet.line(edge.pointA.x, edge.pointA.y, edge.pointB.x, edge.pointB.y);
        });
    }

    public List<Edge> getNodeEdges(Node node) {
        return this.edges.stream().filter(edge -> edge.getPointA() == node || edge.getPointB() == node).collect(Collectors.toList());
    }

    public @NotNull Optional<Edge> getNodesEdge(Node n1, Node n2) {
        return this.edges.stream().filter(edge -> (edge.pointA == n1 && edge.pointB == n2) || edge.pointA == n2 && edge.pointB == n1).findFirst();
    }

    public List<Node> getNeighbours(Node node) {
        List<Edge> nodeEdges = getNodeEdges(node);
        return nodeEdges.stream().map(n -> n.getOther(node)).collect(Collectors.toList());
    }
}