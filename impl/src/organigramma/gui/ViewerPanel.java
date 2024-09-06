package organigramma.gui;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import organigramma.main.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class ViewerPanel extends JPanel implements Observer {
    private SimpleDirectedGraph<UnitaIF, DefaultEdge> graph;
    private Frame mainWindow;
    private mxGraph mxGraph;
    mxGraphComponent graphComponent;
    private Object parent;
    private UnitaIF root;

    public ViewerPanel(Frame mainWindow, UnitaIF root) {
        // Configuro il JPanel (contenitore del grafico)
        super(new BorderLayout());
        this.mainWindow = mainWindow;
        setBorder(new EmptyBorder(10, 10, 10, 10));

        this.root = root;

        drawGraph();
    }//Costruttore

    public void setRoot(UnitaIF newRoot) {
        this.root = newRoot;
    }//setRoot

    public void drawGraph() {
        removeAll();

        // Costruzione
        graph = new SimpleDirectedGraph<>(DefaultEdge.class);
        mxGraph = new mxGraph();
        parent = mxGraph.getDefaultParent();

        // Creazione del grafo dall'organigramma
        buildGraph(root);

        // Configurazione del grafo JGraphX
        configureGraph();

        // Layout del grafo
        mxCompactTreeLayout layout = new mxCompactTreeLayout(mxGraph, false);
        layout.setHorizontal(false); // Imposta la disposizione verticale
        mxGraph.setCellsEditable(false); // Disabilito la modifica in-place
        layout.execute(mxGraph.getDefaultParent());

        // Aggiungi il componente grafico
        graphComponent = new mxGraphComponent(mxGraph);
        graphComponent.setConnectable(false);
        graphComponent.setBorder(null);

        // Aggiungere un mouse listener per intercettare il doppio click
        addDoubleClickListener();

        add(graphComponent, BorderLayout.CENTER);

        // Rinfresca il layout del pannello
        revalidate(); // Ricostruisce il layout con il nuovo grafico
        repaint(); // Ridipinge il pannello con i nuovi componenti
    }//drawGraph

    private void buildGraph(UnitaIF root) {
        Iterator<UnitaIF> iterator = new DepthFirstIterator(root);
        while (iterator.hasNext()) {
            UnitaIF current = iterator.next();
            graph.addVertex(current);

            // Aggiungo l'osservatore all'Unita
            current.attach(this);

            if (current.getClass().equals(OrganoGestione.class)) {
                for (UnitaIF child : ((OrganoGestione) current).getChildren()) {
                    graph.addVertex(child);
                    graph.addEdge(current, child);
                }
            }
        }
    }//buildGraph

    private void configureGraph() {
        // Imposta lo stile dei vertici con il padding interno
        mxStylesheet stylesheet = mxGraph.getStylesheet();
        Map<String, Object> style = stylesheet.getDefaultVertexStyle();

        // Imposta un padding di 10 pixel su tutti i lati
        style.put(mxConstants.STYLE_SPACING_TOP, 10);
        style.put(mxConstants.STYLE_SPACING_LEFT, 20);
        style.put(mxConstants.STYLE_SPACING_BOTTOM, 10);
        style.put(mxConstants.STYLE_SPACING_RIGHT, 20);

        mxGraph.getModel().beginUpdate();
        try {
            for (UnitaIF vertex : graph.vertexSet()) {
                Object cell = mxGraph.insertVertex(parent, vertex.getNome(), vertex.getNome(),
                        10, 10, 100, 40);

                mxGraph.updateCellSize(cell); // per aggiornare le dimensioni della cella in base al contenuto
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
    }//getCellByValue

    private UnitaIF getUnitByName(String name) {
        for (UnitaIF unit : graph.vertexSet()) {
            if (unit.getNome().equals(name)) {
                return unit;
            }
        }
        return null;
    }//getUnitByName

    private void addDoubleClickListener() {
        graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getClickCount() == 2) { // Doppio click
                    // Ottieni la cella cliccata
                    mxCell cell = (mxCell) graphComponent.getCellAt(e.getX(), e.getY());
                    if (cell != null) {
                        // Apri la finestra di modifica per l'unità selezionata
                        String cellValue = (String) cell.getValue();
                        UnitaIF unita = getUnitByName(cellValue);
                        if (unita != null)
                            new EditUnitaDialog(mainWindow, unita, root);
                    }
                }
            }
        });
    }// addDoubleClickListener

    // OBSERVER PATTERN
    @Override
    public void update(UnitaIF unita) {
        // Ricrea il grafo aggiornato
        drawGraph();
    }// update

}//Viewer
