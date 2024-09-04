package organigramma.gui;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import organigramma.main.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ViewerPanel extends JPanel implements Observer {
    SimpleDirectedGraph<UnitaIF, DefaultEdge> graph;
    private mxGraph mxGraph;
    private Object parent;

    public ViewerPanel() {
        // Configuro il JPanel (contenitore del grafico)
        super(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Costruzione
        graph = new SimpleDirectedGraph<>(DefaultEdge.class);
        mxGraph = new mxGraph();
        parent = mxGraph.getDefaultParent();

        // Creo un organo di gestione iniziale vuoto
        UnitaIF root = new OrganoGestione("Root", UnitaIF.Tipologia.DIREZIONE);

        drawGraph(root);
    }//Costruttore

    public void drawGraph(UnitaIF root) {
        // Creazione del grafo dall'organigramma
        buildGraph(root);

        // Configurazione del grafo JGraphX
        configureGraph();

        // Layout del grafo
        mxCompactTreeLayout layout = new mxCompactTreeLayout(mxGraph, false);
        layout.setHorizontal(false); // Imposta la disposizione verticale
        layout.execute(mxGraph.getDefaultParent());

        // Aggiungi il componente grafico
        mxGraphComponent graphComponent = new mxGraphComponent(mxGraph);
        add(graphComponent, BorderLayout.CENTER);
    }//drawGraph

    private void buildGraph(UnitaIF root) {
        Iterator<UnitaIF> iterator = new DepthFirstIterator(root);
        while (iterator.hasNext()) {
            UnitaIF current = iterator.next();
            graph.addVertex(current);

            if (current.getClass().equals(OrganoGestione.class)) {
                for (UnitaIF child : ((OrganoGestione) current).getChildren()) {
                    graph.addVertex(child);
                    graph.addEdge(current, child);
                }
            }
        }
    }//buildGraph

    private void configureGraph() {
        mxGraph.getModel().beginUpdate();
        try {
            for (UnitaIF vertex : graph.vertexSet()) {
                mxGraph.insertVertex(parent, vertex.getNome(), vertex.getNome(), 0, 0, 100, 40);
            }
            for (DefaultEdge edge : graph.edgeSet()) {
                mxGraph.insertEdge(parent, null, "",
                        getCellByValue(mxGraph, graph.getEdgeSource(edge).getNome()),
                        getCellByValue(mxGraph, graph.getEdgeTarget(edge).getNome()));
            }
        } finally {
            mxGraph.getModel().endUpdate();
        }
    }//configureGraph

    private Object getCellByValue(mxGraph graph, String value) {
        Object[] cells = graph.getChildVertices(graph.getDefaultParent());
        for (Object cell : cells) {
            if (value.equals(graph.getModel().getValue(cell).toString())) {
                return cell;
            }
        }
        return null;
    }

    // OBSERVER PATTERN
    @Override
    public void update(UnitaIF unita) {
        // TODO: 03/09/2024
    }// update

}//Viewer
