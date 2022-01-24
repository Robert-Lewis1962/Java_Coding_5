import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import util.Transaction;
import util.TxInput;
import util.TxOutput;

/**
 * TransactionProcessorTest Class
 * 
 * @author Robert Lewis
 * @version 1.0
 */
public class TransactionProcessorTest {

    /**
     * test 1 - empty graph
     */
    @Test
    public void testEmptyGraph() {
        List<Transaction> list = new ArrayList<>();
        assertNotNull(list);
        Transaction trans = new Transaction("1");
        TransactionProcessor tp = null;
        boolean caught = false;
        try {
            tp = new TransactionProcessor(list);
        } 
        catch (DuplicateOutputException e) {
            caught = true;
            e.printStackTrace();
        } 
        catch (OutputReusedException e) {
            caught = true;
            e.printStackTrace();
        }
        assertFalse(caught);
        assertNotNull(tp);

        List<Transaction> copyList = null;
        caught = false;
        try {
            copyList = tp.getUpstreamTransactions(trans);
        } 
        catch (MissingTxException e) {
            caught = true;
            e.printStackTrace();
        } 
        catch (CycleDetectedException e) {
            caught = true;
            e.printStackTrace();
        }
        assertFalse(caught);
        assertNotNull(copyList);
        assertEquals(0, copyList.size());
        assertEquals(0, list.size());
    }

    /**
     * test 2 - 1 vertex graph
     */
    @Test
    public void testOneVertex() {
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
        TransactionProcessor tp = null;
        boolean caught = false;
        try {
            tp = new TransactionProcessor(list);
        } 
        catch (DuplicateOutputException e) {
            caught = true;
            e.printStackTrace();
        } 
        catch (OutputReusedException e) {
            caught = true;
            e.printStackTrace();
        }
        assertFalse(caught);
        assertNotNull(tp);

        List<Transaction> copyList = null;
        caught = false;
        try {
            copyList = tp.getUpstreamTransactions(trans1);
        } 
        catch (MissingTxException e) {
            caught = true;
            e.printStackTrace();
        } 
        catch (CycleDetectedException e) {
            caught = true;
            e.printStackTrace();
        }
        assertFalse(caught);
        assertNotNull(copyList);
        assertEquals(1, list.size());
        assertEquals(1, copyList.size());
        assertEquals("Tx1", copyList.get(0).getId());
    }

    /**
     * test 3 - 2 vertex graph
     */
    @Test
    public void testTwoVertex() {
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
        TransactionProcessor tp = null;
        boolean caught = false;
        try {
            tp = new TransactionProcessor(list);
        } 
        catch (DuplicateOutputException e) {
            caught = true;
            e.printStackTrace();
        }
        catch (OutputReusedException e) {
            caught = true;
            e.printStackTrace();
        }
        assertFalse(caught);
        assertNotNull(tp);

        List<Transaction> copyList = null;
        caught = false;
        try {
            copyList = tp.getUpstreamTransactions(trans1);
        } 
        catch (MissingTxException e) {
            caught = true;
            e.printStackTrace();
        } 
        catch (CycleDetectedException e) {
            caught = true;
            e.printStackTrace();
        }
        assertFalse(caught);
        assertNotNull(copyList);
        assertEquals(2, list.size());
        assertEquals(1, copyList.size());
        assertEquals("Tx1", copyList.get(0).getId());

        // checks other vertex
        copyList = null;
        caught = false;
        try {
            copyList = tp.getUpstreamTransactions(trans2);
        } 
        catch (MissingTxException e) {
            caught = true;
            e.printStackTrace();
        } 
        catch (CycleDetectedException e) {
            caught = true;
            e.printStackTrace();
        }
        assertFalse(caught);
        assertNotNull(copyList);
        assertEquals(2, list.size());
        assertEquals(1, copyList.size());
        assertEquals("Tx2", copyList.get(0).getId());
    }

    /**
     * test 4 - 2 vertex with an edge graph
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
        TransactionProcessor tp = null;
        boolean caught = false;
        try {
            tp = new TransactionProcessor(list);
        } 
        catch (DuplicateOutputException e) {
            caught = true;
            e.printStackTrace();
        } 
        catch (OutputReusedException e) {
            caught = true;
            e.printStackTrace();
        }
        assertFalse(caught);
        assertNotNull(tp);

        List<Transaction> copyList = null;
        caught = false;
        try {
            copyList = tp.getUpstreamTransactions(trans1);
        } 
        catch (MissingTxException e) {
            caught = true;
            e.printStackTrace();
        } 
        catch (CycleDetectedException e) {
            caught = true;
            e.printStackTrace();
        }
        assertFalse(caught);
        assertNotNull(copyList);
        assertEquals(2, list.size());
        assertEquals(1, copyList.size());
        assertEquals("Tx1", copyList.get(0).getId());

        // checks other vertex
        copyList = null;
        caught = false;
        try {
            copyList = tp.getUpstreamTransactions(trans2);
        } 
        catch (MissingTxException e) {
            caught = true;
            
        }
        catch (CycleDetectedException e) {
            caught = true;
            e.printStackTrace();
        }
        assertFalse(caught);
        assertNotNull(copyList);
        assertEquals(2, list.size());
        assertEquals(2, copyList.size());
        assertEquals("Tx1", copyList.get(0).getId());
        assertEquals("Tx2", copyList.get(1).getId());
    }
    
    /**
     * test 5 - 3 vertex with 2 edge graph
     */
    @Test
    public void testThreeVertexTwoEdge1() {
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
        TxOutput tx3 = new TxOutput("tx2Out");
        assertNotNull(tx3);
        TxInput tx4 = new TxInput("tx2Out");
        assertNotNull(tx4);
        // add output to transaction
        trans1.addOutput(tx1);
        // add input/output to transaction
        trans2.addInput(tx2);
        trans2.addOutput(tx3);
        // add input/output to transaction
        trans3.addInput(tx4);
        // add transaction to list
        assertTrue(list.add(trans1));
        assertTrue(list.add(trans2));
        assertTrue(list.add(trans3));
        TransactionProcessor tp = null;
        boolean caught = false;
        try {
            tp = new TransactionProcessor(list);
        } 
        catch (DuplicateOutputException e) {
            caught = true;
        } 
        catch (OutputReusedException e) {
            caught = true;
        }
        assertFalse(caught);
        assertNotNull(tp);

        List<Transaction> copyList = null;
        caught = false;
        try {
            copyList = tp.getUpstreamTransactions(trans1);
        } 
        catch (MissingTxException e) {
            caught = true;
        } 
        catch (CycleDetectedException e) {
            caught = true;
        }
        assertFalse(caught);
        assertNotNull(copyList);
        assertEquals(3, list.size());
        assertEquals(1, copyList.size());
        assertEquals("Tx1", copyList.get(0).getId());

        // checks other vertex
        copyList = null;
        caught = false;
        try {
            copyList = tp.getUpstreamTransactions(trans2);
        } 
        catch (MissingTxException e) {
            caught = true;
        }
        catch (CycleDetectedException e) {
            caught = true;
        }
        assertFalse(caught);
        assertNotNull(copyList);
        assertEquals(3, list.size());
        assertEquals(2, copyList.size());
        assertEquals("Tx1", copyList.get(0).getId());
        assertEquals("Tx2", copyList.get(1).getId());
        
        // checks other vertex
        copyList = null;
        caught = false;
        try {
            copyList = tp.getUpstreamTransactions(trans3);
        } 
        catch (MissingTxException e) {
            caught = true;
        }
        catch (CycleDetectedException e) {
            caught = true;
        }
        assertFalse(caught);
        assertNotNull(copyList);
        assertEquals(3, list.size());
        assertEquals(3, copyList.size());
        assertEquals("Tx1", copyList.get(0).getId());
        assertEquals("Tx2", copyList.get(1).getId());
        assertEquals("Tx3", copyList.get(2).getId());
    }
    
    /**
     * test 6 - 3 vertex with 2 edge graph
     */
    @Test
    public void testThreeVertexTwoEdge2() {
        List<Transaction> list = new ArrayList<>();
        assertNotNull(list);
        Transaction trans1 = new Transaction("Tx1");
        assertNotNull(trans1);
        Transaction trans2 = new Transaction("Tx2");
        assertNotNull(trans2);
        Transaction trans3 = new Transaction("Tx3");
        assertNotNull(trans3);
        TxOutput tx1 = new TxOutput("tx1Out1");
        assertNotNull(tx1);
        TxInput tx2 = new TxInput("tx1Out1");
        assertNotNull(tx2);
        TxOutput tx3 = new TxOutput("tx1Out2");
        assertNotNull(tx3);
        TxInput tx4 = new TxInput("tx1Out2");
        assertNotNull(tx4);
        // add output to transaction
        trans1.addOutput(tx1);
        trans1.addOutput(tx3);
        // add input/output to transaction
        trans2.addInput(tx2);
        // add input/output to transaction
        trans3.addInput(tx4);
        // add transaction to list
        assertTrue(list.add(trans1));
        assertTrue(list.add(trans2));
        assertTrue(list.add(trans3));
        TransactionProcessor tp = null;
        boolean caught = false;
        try {
            tp = new TransactionProcessor(list);
        } 
        catch (DuplicateOutputException e) {
            caught = true;
        } 
        catch (OutputReusedException e) {
            caught = true;
        }
        assertFalse(caught);
        assertNotNull(tp);

        List<Transaction> copyList = null;
        caught = false;
        try {
            copyList = tp.getUpstreamTransactions(trans1);
        } 
        catch (MissingTxException e) {
            caught = true;
        } 
        catch (CycleDetectedException e) {
            caught = true;
        }
        assertFalse(caught);
        assertNotNull(copyList);
        assertEquals(3, list.size());
        assertEquals(1, copyList.size());
        assertEquals("Tx1", copyList.get(0).getId());

        // checks other vertex
        copyList = null;
        caught = false;
        try {
            copyList = tp.getUpstreamTransactions(trans2);
        } 
        catch (MissingTxException e) {
            caught = true;
        }
        catch (CycleDetectedException e) {
            caught = true;
        }
        assertFalse(caught);
        assertNotNull(copyList);
        assertEquals(3, list.size());
        assertEquals(2, copyList.size());
        assertEquals("Tx1", copyList.get(0).getId());
        assertEquals("Tx2", copyList.get(1).getId());
        
        // checks other vertex
        copyList = null;
        caught = false;
        try {
            copyList = tp.getUpstreamTransactions(trans3);
        } 
        catch (MissingTxException e) {
            caught = true;
        }
        catch (CycleDetectedException e) {
            caught = true;
        }
        assertFalse(caught);
        assertNotNull(copyList);
        assertEquals(3, list.size());
        assertEquals(2, copyList.size());
        assertEquals("Tx1", copyList.get(0).getId());
        assertEquals("Tx3", copyList.get(1).getId());
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
        TransactionProcessor tp = null;
        boolean caught = false;
        try {
            tp = new TransactionProcessor(list);
        } 
        catch (DuplicateOutputException e) {
            caught = true;
        } 
        catch (OutputReusedException e) {
            caught = true;
        }
        assertTrue(caught);
        assertNull(tp);
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
    
    /**
     * testing CycleDetectedException
     */
    @Test
    public void testCDE() {
        List<Transaction> list = new ArrayList<>();
        assertNotNull(list);
        Transaction trans1 = new Transaction("Tx1");
        assertNotNull(trans1);

        TxOutput tx1 = new TxOutput("tx1Out");
        assertNotNull(tx1);
        TxInput tx2 = new TxInput("tx1Out");
        assertNotNull(tx2);
        // add output/input to transaction
        trans1.addOutput(tx1);
        trans1.addInput(tx2);
        // add transaction to list
        assertTrue(list.add(trans1));
        TransactionProcessor tp = null;
        boolean caught = false;
        try {
            tp = new TransactionProcessor(list);
        } 
        catch (DuplicateOutputException e) {
            caught = true;
        } 
        catch (OutputReusedException e) {
            caught = true;
        }
        assertFalse(caught);
        assertNotNull(tp);
        
        List<Transaction> copyList = null;
        caught = false;
        try {
            copyList = tp.getUpstreamTransactions(trans1);
        } 
        catch (MissingTxException e) {
            caught = true;
        }
        catch (CycleDetectedException e) {
            caught = true;
        }
        assertTrue(caught);
        assertNull(copyList);
    }
    
    /**
     * testing MissingTxException
     */
    @Test
    public void testMTE() {
        List<Transaction> list = new ArrayList<>();
        assertNotNull(list);
        Transaction trans1 = new Transaction("Tx1");
        assertNotNull(trans1);
        TxInput tx2 = new TxInput("tx1Out");
        assertNotNull(tx2);
        // add output/input to transaction
        trans1.addInput(tx2);
        // add transaction to list
        assertTrue(list.add(trans1));
        TransactionProcessor tp = null;
        boolean caught = false;
        try {
            tp = new TransactionProcessor(list);
        } 
        catch (DuplicateOutputException e) {
            caught = true;
        } 
        catch (OutputReusedException e) {
            caught = true;
        }
        assertFalse(caught);
        assertNotNull(tp);
        
        List<Transaction> copyList = null;
        caught = false;
        try {
            copyList = tp.getUpstreamTransactions(trans1);
        } 
        catch (MissingTxException e) {
            caught = true;
        }
        catch (CycleDetectedException e) {
            caught = true;
        }
        assertTrue(caught);
        assertNull(copyList);
    }
}
