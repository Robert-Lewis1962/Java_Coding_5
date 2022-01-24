import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import util.Transaction;
import util.TxInput;
import util.TxOutput;

/**
 * GrapghTeat Class
 * 
 * @author Robert Lewis
 * @version 1.0
 *
 */
public class GraphTest {
    /**
     * testing empty graph
     */
    @Test
    public void testEmptyGraph() {
        List<Transaction> list = new ArrayList<>();
        assertNotNull(list);
        Graph graph = null;
        boolean caught = false;
        try {
            graph = new Graph(list);
        } 
        catch (DuplicateOutputException e) {
            caught = true;
        } 
        catch (OutputReusedException e) {
            caught = true;
        }
        assertFalse(caught);
        assertNotNull(graph);
        assertNotNull(graph.getGraph());
        assertNotNull(graph.getOutputMap());
    }

    /**
     * testing with one vertex graph
     */
    @Test
    public void testGraphOneVertex() {
        List<Transaction> list = new ArrayList<>();
        assertNotNull(list);
        Transaction trans1 = new Transaction("Tx1");
        assertNotNull(trans1);
        TxOutput tx1 = new TxOutput("tx1Output");
        assertNotNull(tx1);
        // add output to transaction
        trans1.addOutput(tx1);
        // add transaction to list
        assertTrue(list.add(trans1));
        Graph graph = null;
        boolean caught = false;
        try {
            graph = new Graph(list);
        } 
        catch (DuplicateOutputException e) {
            caught = true;
        } 
        catch (OutputReusedException e) {
            caught = true;
        }
        assertFalse(caught);
        assertNotNull(graph);
        assertNotNull(graph.getGraph());
        assertNotNull(graph.getOutputMap());

        HashMap<Integer, List<Transaction>> gList = graph.getGraph();
        HashMap<Integer, Transaction> pList = graph.getOutputMap();

        // checks graph content
        assertEquals(1, gList.size());
        assertTrue(gList.containsKey(Math.abs(trans1.getId().hashCode())));
        List<Transaction> sList =
                gList.get(Math.abs(trans1.getId().hashCode()));
        assertTrue(sList.isEmpty());

        // checks pairMatching content
        assertEquals(1, pList.size());
        assertTrue(pList.containsKey(Math.abs(tx1.getAddress().hashCode())));
        Transaction value = pList.get(Math.abs(tx1.getAddress().hashCode()));
        assertEquals("Tx1", value.getId());
    }

    /**
     * testing with two vertex graph
     */
    @Test
    public void testGraphTwoVertex() {
        List<Transaction> list = new ArrayList<>();
        assertNotNull(list);
        Transaction trans1 = new Transaction("Tx1");
        assertNotNull(trans1);
        Transaction trans2 = new Transaction("Tx2");
        assertNotNull(trans2);
        TxOutput tx1 = new TxOutput("tx1Out");
        assertNotNull(tx1);
        TxOutput tx2 = new TxOutput("tx2Out");
        assertNotNull(tx2);
        // add output to transaction
        trans1.addOutput(tx1);
        // add output to transaction
        trans2.addOutput(tx2);
        // add transaction to list
        assertTrue(list.add(trans1));
        assertTrue(list.add(trans2));
        Graph graph = null;
        boolean caught = false;
        try {
            graph = new Graph(list);
        } 
        catch (DuplicateOutputException e) {
            caught = true;
        } 
        catch (OutputReusedException e) {
            caught = true;
        }
        assertFalse(caught);
        assertNotNull(graph);
        assertNotNull(graph.getGraph());
        assertNotNull(graph.getOutputMap());

        HashMap<Integer, List<Transaction>> gList = graph.getGraph();
        HashMap<Integer, Transaction> pList = graph.getOutputMap();

        // checks graph content
        assertEquals(2, gList.size());
        assertTrue(gList.containsKey(Math.abs(trans1.getId().hashCode())));
        assertTrue(gList.containsKey(Math.abs(trans2.getId().hashCode())));
        List<Transaction> sList = 
                gList.get(Math.abs(trans1.getId().hashCode()));
        assertTrue(sList.isEmpty());
        sList = gList.get(Math.abs(trans2.getId().hashCode()));
        assertTrue(sList.isEmpty());

        // checks pairMatching content
        assertEquals(2, pList.size());
        assertTrue(pList.containsKey(Math.abs(tx1.getAddress().hashCode())));
        assertTrue(pList.containsKey(Math.abs(tx2.getAddress().hashCode())));
        Transaction value = pList.get(Math.abs(tx1.getAddress().hashCode()));
        assertEquals("Tx1", value.getId());
        value = pList.get(Math.abs(tx2.getAddress().hashCode()));
        assertEquals("Tx2", value.getId());
    }

    /**
     * test 2 vertex with an edge graph
     */
    @Test
    public void testTwoVertexOneEdge() {
        List<Transaction> list = new ArrayList<>();
        assertNotNull(list);
        Transaction trans1 = new Transaction("Tx1");
        assertNotNull(trans1);
        Transaction trans2 = new Transaction("Tx2");
        assertNotNull(trans2);
        TxOutput tx1 = new TxOutput("tx1Out");
        assertNotNull(tx1);
        TxInput tx2 = new TxInput("tx1Out");
        assertNotNull(tx2);
        TxOutput tx3 = new TxOutput("tx2Out");
        assertNotNull(tx3);
        // add output to transaction
        trans1.addOutput(tx1);
        // add input/output to transaction
        trans2.addInput(tx2);
        trans2.addOutput(tx3);
        // add transaction to list
        assertTrue(list.add(trans1));
        assertTrue(list.add(trans2));
        Graph graph = null;
        boolean caught = false;
        try {
            graph = new Graph(list);
        } 
        catch (DuplicateOutputException e) {
            caught = true;
        } 
        catch (OutputReusedException e) {
            caught = true;
        }
        assertFalse(caught);
        assertNotNull(graph);
        assertNotNull(graph.getGraph());
        assertNotNull(graph.getOutputMap());

        HashMap<Integer, List<Transaction>> gList = graph.getGraph();
        HashMap<Integer, Transaction> pList = graph.getOutputMap();

        // checks graph content
        assertEquals(2, gList.size());
        assertTrue(gList.containsKey(Math.abs(trans1.getId().hashCode())));
        assertTrue(gList.containsKey(Math.abs(trans2.getId().hashCode())));
        List<Transaction> sList = 
                gList.get(Math.abs(trans1.getId().hashCode()));
        assertTrue(sList.isEmpty());
        sList = gList.get(Math.abs(trans2.getId().hashCode()));
        assertEquals(1, sList.size());
        assertEquals("Tx1", sList.get(0).getId());

        // checks pairMatching content
        assertEquals(2, pList.size());
        assertTrue(pList.containsKey(Math.abs(tx3.getAddress().hashCode())));
        Transaction value = pList.get(Math.abs(tx1.getAddress().hashCode()));
        value = pList.get(Math.abs(tx3.getAddress().hashCode()));
        assertEquals("Tx2", value.getId());
        assertTrue(trans2.equals(value));
    }
    
    /**
     * testing DuplicateOutputException
     */
    @Test
    public void testDOE() {
        List<Transaction> list = new ArrayList<>();
        assertNotNull(list);
        Transaction trans1 = new Transaction("Tx1");
        assertNotNull(trans1);
        Transaction trans2 = new Transaction("Tx2");
        assertNotNull(trans2);
        TxOutput tx1 = new TxOutput("tx1Out");
        assertNotNull(tx1);
        TxOutput tx2 = new TxOutput("tx1Out");
        assertNotNull(tx2);
        // add output to transaction
        trans1.addOutput(tx1);
        // add input/output to transaction
        trans2.addOutput(tx2);
        // add transaction to list
        assertTrue(list.add(trans1));
        assertTrue(list.add(trans2));
        Graph graph = null;
        boolean caught = false;
        try {
            graph = new Graph(list);
        } 
        catch (DuplicateOutputException e) {
            caught = true;
        } 
        catch (OutputReusedException e) {
            caught = true;
        }
        assertTrue(caught);
        assertNull(graph);
    }
    
    /**
     * testing OutputReusedException
     */
    @Test
    public void testORE() {
        List<Transaction> list = new ArrayList<>();
        assertNotNull(list);
        Transaction trans1 = new Transaction("Tx1");
        assertNotNull(trans1);
        Transaction trans2 = new Transaction("Tx2");
        assertNotNull(trans2);
        Transaction trans3 = new Transaction("Tx3");
        assertNotNull(trans3);
        TxOutput tx1 = new TxOutput("tx1Out");
        assertNotNull(tx1);
        TxInput tx2 = new TxInput("tx1Out");
        assertNotNull(tx2);
        TxInput tx3 = new TxInput("tx1Out");
        assertNotNull(tx3);
        // add output to transaction
        trans1.addOutput(tx1);
        // add input to transaction
        trans2.addInput(tx2);
        // add input to transaction
        trans3.addInput(tx3);
        // add transaction to list
        assertTrue(list.add(trans1));
        assertTrue(list.add(trans2));
        assertTrue(list.add(trans3));
        Graph graph = null;
        boolean caught = false;
        try {
            graph = new Graph(list);
        } 
        catch (DuplicateOutputException e) {
            caught = true;
        } 
        catch (OutputReusedException e) {
            caught = true;
        }
        assertTrue(caught);
        assertNull(graph);
    }
}
