import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class NeuralGas {
    PApplet pApplet;
    List<Node> referenceVectors;
    List<Edge> edges;
    List<PVector> dataNodes;
    Random random;
    int inputSignals;

    public NeuralGas(PApplet pApplet, List<PVector> dataNodes) {
        this.pApplet = pApplet;
        this.dataNodes = dataNodes;
        this.inputSignals = 1;
        this.referenceVectors = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.random = new Random();
    }

    public void initializeNeuralGas() {
        Node a = new Node((float) Math.random() * Main.WIDTH, (float) Math.random() * Main.HEIGHT);
        Node b = new Node((float) Math.random() * Main.WIDTH, (float) Math.random() * Main.HEIGHT);
        //Node c = new Node((float) Math.random() * Main.WIDTH, (float) Math.random() * Main.HEIGHT);
        Edge edge = new Edge(a, b);
        //Edge edge1 = new Edge(b, c);
        this.edges.add(edge);
        //this.edges.add(edge1);
        referenceVectors.add(a);
        referenceVectors.add(b);
    }

    public void adapt(PVector dataVector) {
//        this.pApplet.noFill();
//        this.pApplet.stroke(0,0,255);
//        this.pApplet.ellipse(dataVector.x, dataVector.y,15f,15f);
        Optional<Node> s1 = this.referenceVectors.stream().min((v1, v2) -> v1.dist(dataVector) > v2.dist(dataVector) ? 1 : v1.dist(dataVector) == v2.dist(dataVector) ? 0 : -1);
        if(s1.isPresent()){
            PVector s1Direction = new PVector(dataVector.x,dataVector.y).sub(s1.get());
            s1.get().increaseError((float) Math.pow(dataVector.dist(s1.get()),2));
            s1.get().add(s1Direction.mult(Main.E_B));

            getNeighbours(s1.get()).stream().forEach(neighbour -> {
                PVector dir = new PVector(dataVector.x, dataVector.y).sub(neighbour);
                neighbour.add(dir.mult(Main.E_N));
            });

//            this.pApplet.noFill();
//            this.pApplet.stroke(0,255,0);
//            this.pApplet.ellipse(s1.get().x, s1.get().y,15f,15f);
            Optional<Node> s2 = this.referenceVectors.stream().filter(v -> !s1.get().equals(v)).min((v1, v2) -> v1.dist(dataVector) > v2.dist(dataVector) ? 1 : v1.dist(dataVector) == v2.dist(dataVector) ? 0 : -1);
            if(s2.isPresent()){
//                this.pApplet.noFill();
//                this.pApplet.stroke(0,255,0);
//                this.pApplet.ellipse(s2.get().x, s2.get().y,15f,15f);

                Optional<Edge> winnersEdge = getNodesEdge(s1.get(),s2.get());
                if(winnersEdge.isPresent()){
                    winnersEdge.get().setAge(0.0f);
                }else{
                    edges.add(new Edge(s1.get(), s2.get()));
                }
            }

            getNodeEdges(s1.get()).stream().forEach(Edge::increaseAge);
        }
    }

    public List<Edge> getNodeEdges(Node node){
        return this.edges.stream().filter(edge -> edge.getPointA() == node || edge.getPointB() == node).collect(Collectors.toList());
    }

    public Optional<Edge> getNodesEdge(Node n1, Node n2){
        return this.edges.stream().filter(edge -> (edge.pointA == n1 && edge.pointB == n2) || edge.pointA == n2 && edge.pointB == n1).findFirst();
    }

    public List<Node> getNeighbours(Node node){
        List<Edge> nodeEdges = getNodeEdges(node);
        return nodeEdges.stream().flatMap(n -> n.getPoints().stream()).collect(Collectors.toList());
    }

    public void updateGas() {
        //this.determineWinners(this.dataNodes.get(random.nextInt(this.dataNodes.size())));
        this.dataNodes.stream().forEach(this::adapt);

        // Remove too old edges
        this.edges = this.edges.stream().filter(edge -> edge.getAge() < Main.A_MAX).collect(Collectors.toList());

        // Remove points with no edges
        this.referenceVectors = this.referenceVectors.stream().filter(n -> getNodeEdges(n).size() > 0).collect(Collectors.toList());

        if(this.inputSignals % Main.LAMBDA == 0){
            Node q = this.referenceVectors.stream().max((n1,n2) -> n1.error < n2.error ? 1 : -1).get();
            Node f = this.getNeighbours(q).stream().filter(n -> !n.equals(q)).max((n1,n2) -> n1.error < n2.error ? 1 : -1).get();
            Node r = new Node((q.x + f.x)/2, (q.y+ f.y)/2);

            System.out.println("q error: " + q.getError());
            System.out.println("f error: " + f.getError());
            System.out.println("New node pos: " + r);

            Edge q_r = new Edge(q,r);
            Edge f_r = new Edge(f,r);
            Edge oldEdge = getNodesEdge(q, f).get();
            this.edges.remove(oldEdge);

            this.edges.add(q_r);
            this.edges.add(f_r);
            this.referenceVectors.add(r);

            q.decreaseError();
            f.decreaseError();

           // r.setError(q.getError());
            r.setError((q.getError() + f.getError()) / 2);

//            this.pApplet.fill(255,0,0);
//            this.pApplet.ellipse(q.x,q.y,20f,20f);
//            this.pApplet.fill(0,255,0);
//            this.pApplet.ellipse(f.x,f.y,20f,20f);
        }

        this.referenceVectors.forEach(n -> n.decreaseError(n.getError() * Main.D));

        this.inputSignals++;
    }

    public void drawGas() {
        this.referenceVectors.forEach(pVector -> {
            this.pApplet.strokeWeight(5f);
            this.pApplet.stroke(255, 0, 0);
            this.pApplet.point(pVector.x, pVector.y);
        });

        edges.forEach(edge -> {
            this.pApplet.strokeWeight(1f);
            this.pApplet.line(edge.pointA.x, edge.pointA.y, edge.pointB.x, edge.pointB.y);
        });
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public List<PVector> getDataNodes() {
        return dataNodes;
    }

    public void setDataNodes(List<PVector> dataNodes) {
        this.dataNodes = dataNodes;
    }
}



