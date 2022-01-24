import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import util.Transaction;
import util.TxInput;
import util.TxOutput;

/**
 * TransactionProcessorMoreTest Class
 * 
 * @author Robert Lewis
 * @version 1.0
 */
public class TransactionProcessorMoreTest {

    /**
     * test 6 - 3 vertex with 2 edge graph
     */
    @Test
    public void testFiveVertexFourEdge1() {
        List<Transaction> list = new ArrayList<>();
        assertNotNull(list);
        Transaction trans1 = new Transaction("Tx1");
        assertNotNull(trans1);
        Transaction trans2 = new Transaction("Tx2");
        assertNotNull(trans2);
        Transaction trans3 = new Transaction("Tx3");
        assertNotNull(trans3);
        Transaction trans4 = new Transaction("Tx4");
        assertNotNull(trans4);
        Transaction trans5 = new Transaction("Tx5");
        assertNotNull(trans5);
        // tx1
        TxOutput tx1 = new TxOutput("tx1Out");
        assertNotNull(tx1);
        // tx2
        TxInput tx2 = new TxInput("tx1Out");
        assertNotNull(tx2);
        TxOutput tx3 = new TxOutput("tx2Out");
        assertNotNull(tx3);
        //tx3
        TxOutput tx4 = new TxOutput("tx3Out");
        assertNotNull(tx3);
        // tx4
        TxInput tx5 = new TxInput("tx3Out");
        assertNotNull(tx3);
        TxOutput tx6 = new TxOutput("tx4Out");
        assertNotNull(tx6);
        // tx5
        TxInput tx7 = new TxInput("tx2Out");
        assertNotNull(tx6);
        TxInput tx8 = new TxInput("tx4Out");
        assertNotNull(tx6);
        // add output to transaction1
        trans1.addOutput(tx1);
        // add input/output to transaction2
        trans2.addInput(tx2);
        trans2.addOutput(tx3);
        // add input/output to transaction3
        trans3.addOutput(tx4);
        // add input/output to transaction4
        trans4.addInput(tx5);
        trans4.addOutput(tx6);
        // add input/output to transaction5
        trans5.addInput(tx7);
        trans5.addInput(tx8);
        // add transaction to list
        assertTrue(list.add(trans1));
        assertTrue(list.add(trans2));
        assertTrue(list.add(trans3));
        assertTrue(list.add(trans4));
        assertTrue(list.add(trans5));
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
        assertEquals(5, list.size());
        assertEquals(1, copyList.size());
        assertEquals("Tx1", copyList.get(0).getId());
    }
    
    /**
     * test 7 - 3 vertex with 2 edge graph
     */
    @Test
    public void testFiveVertexFourEdge2() {
        List<Transaction> list = new ArrayList<>();
        assertNotNull(list);
        Transaction trans1 = new Transaction("Tx1");
        assertNotNull(trans1);
        Transaction trans2 = new Transaction("Tx2");
        assertNotNull(trans2);
        Transaction trans3 = new Transaction("Tx3");
        assertNotNull(trans3);
        Transaction trans4 = new Transaction("Tx4");
        assertNotNull(trans4);
        Transaction trans5 = new Transaction("Tx5");
        assertNotNull(trans5);
        // tx1
        TxOutput tx1 = new TxOutput("tx1Out");
        assertNotNull(tx1);
        // tx2
        TxInput tx2 = new TxInput("tx1Out");
        assertNotNull(tx2);
        TxOutput tx3 = new TxOutput("tx2Out");
        assertNotNull(tx3);
        //tx3
        TxOutput tx4 = new TxOutput("tx3Out");
        assertNotNull(tx3);
        // tx4
        TxInput tx5 = new TxInput("tx3Out");
        assertNotNull(tx3);
        TxOutput tx6 = new TxOutput("tx4Out");
        assertNotNull(tx6);
        // tx5
        TxInput tx7 = new TxInput("tx2Out");
        assertNotNull(tx6);
        TxInput tx8 = new TxInput("tx4Out");
        assertNotNull(tx6);
        // add output to transaction1
        trans1.addOutput(tx1);
        // add input/output to transaction2
        trans2.addInput(tx2);
        trans2.addOutput(tx3);
        // add input/output to transaction3
        trans3.addOutput(tx4);
        // add input/output to transaction4
        trans4.addInput(tx5);
        trans4.addOutput(tx6);
        // add input/output to transaction5
        trans5.addInput(tx7);
        trans5.addInput(tx8);
        // add transaction to list
        assertTrue(list.add(trans1));
        assertTrue(list.add(trans2));
        assertTrue(list.add(trans3));
        assertTrue(list.add(trans4));
        assertTrue(list.add(trans5));
        TransactionProcessor tp = null;
        boolean caught = false;
        // checks vertex 2
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
        assertEquals(5, list.size());
        assertEquals(2, copyList.size());
        assertEquals("Tx1", copyList.get(0).getId());
        assertEquals("Tx2", copyList.get(1).getId());
    }
    
    /**
     * test 8 - 3 vertex with 2 edge graph
     */
    @Test
    public void testFiveVertexFourEdge3() {
        List<Transaction> list = new ArrayList<>();
        assertNotNull(list);
        Transaction trans1 = new Transaction("Tx1");
        assertNotNull(trans1);
        Transaction trans2 = new Transaction("Tx2");
        assertNotNull(trans2);
        Transaction trans3 = new Transaction("Tx3");
        assertNotNull(trans3);
        Transaction trans4 = new Transaction("Tx4");
        assertNotNull(trans4);
        Transaction trans5 = new Transaction("Tx5");
        assertNotNull(trans5);
        // tx1
        TxOutput tx1 = new TxOutput("tx1Out");
        assertNotNull(tx1);
        // tx2
        TxInput tx2 = new TxInput("tx1Out");
        assertNotNull(tx2);
        TxOutput tx3 = new TxOutput("tx2Out");
        assertNotNull(tx3);
        //tx3
        TxOutput tx4 = new TxOutput("tx3Out");
        assertNotNull(tx3);
        // tx4
        TxInput tx5 = new TxInput("tx3Out");
        assertNotNull(tx3);
        TxOutput tx6 = new TxOutput("tx4Out");
        assertNotNull(tx6);
        // tx5
        TxInput tx7 = new TxInput("tx2Out");
        assertNotNull(tx6);
        TxInput tx8 = new TxInput("tx4Out");
        assertNotNull(tx6);
        // add output to transaction1
        trans1.addOutput(tx1);
        // add input/output to transaction2
        trans2.addInput(tx2);
        trans2.addOutput(tx3);
        // add input/output to transaction3
        trans3.addOutput(tx4);
        // add input/output to transaction4
        trans4.addInput(tx5);
        trans4.addOutput(tx6);
        // add input/output to transaction5
        trans5.addInput(tx7);
        trans5.addInput(tx8);
        // add transaction to list
        assertTrue(list.add(trans1));
        assertTrue(list.add(trans2));
        assertTrue(list.add(trans3));
        assertTrue(list.add(trans4));
        assertTrue(list.add(trans5));
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
        // checks vertex 3
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
        assertEquals(5, list.size());
        assertEquals(1, copyList.size());
        assertEquals("Tx3", copyList.get(0).getId());
    }
    
    /**
     * test 9 - 3 vertex with 2 edge graph
     */
    @Test
    public void testFiveVertexFourEdge4() {
        List<Transaction> list = new ArrayList<>();
        assertNotNull(list);
        Transaction trans1 = new Transaction("Tx1");
        assertNotNull(trans1);
        Transaction trans2 = new Transaction("Tx2");
        assertNotNull(trans2);
        Transaction trans3 = new Transaction("Tx3");
        assertNotNull(trans3);
        Transaction trans4 = new Transaction("Tx4");
        assertNotNull(trans4);
        Transaction trans5 = new Transaction("Tx5");
        assertNotNull(trans5);
        // tx1
        TxOutput tx1 = new TxOutput("tx1Out");
        assertNotNull(tx1);
        // tx2
        TxInput tx2 = new TxInput("tx1Out");
        assertNotNull(tx2);
        TxOutput tx3 = new TxOutput("tx2Out");
        assertNotNull(tx3);
        //tx3
        TxOutput tx4 = new TxOutput("tx3Out");
        assertNotNull(tx3);
        // tx4
        TxInput tx5 = new TxInput("tx3Out");
        assertNotNull(tx3);
        TxOutput tx6 = new TxOutput("tx4Out");
        assertNotNull(tx6);
        // tx5
        TxInput tx7 = new TxInput("tx2Out");
        assertNotNull(tx6);
        TxInput tx8 = new TxInput("tx4Out");
        assertNotNull(tx6);
        // add output to transaction1
        trans1.addOutput(tx1);
        // add input/output to transaction2
        trans2.addInput(tx2);
        trans2.addOutput(tx3);
        // add input/output to transaction3
        trans3.addOutput(tx4);
        // add input/output to transaction4
        trans4.addInput(tx5);
        trans4.addOutput(tx6);
        // add input/output to transaction5
        trans5.addInput(tx7);
        trans5.addInput(tx8);
        // add transaction to list
        assertTrue(list.add(trans1));
        assertTrue(list.add(trans2));
        assertTrue(list.add(trans3));
        assertTrue(list.add(trans4));
        assertTrue(list.add(trans5));
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
        
        // checks vertex 4
        List<Transaction> copyList = null;
        caught = false;
        try {
            copyList = tp.getUpstreamTransactions(trans4);
        } 
        catch (MissingTxException e) {
            caught = true;
        }
        catch (CycleDetectedException e) {
            caught = true;
        }
        assertFalse(caught);
        assertNotNull(copyList);
        assertEquals(5, list.size());
        assertEquals(2, copyList.size());
        assertEquals("Tx3", copyList.get(0).getId());
        assertEquals("Tx4", copyList.get(1).getId());
    }
    
    
    /**
     * test 10 - 3 vertex with 2 edge graph
     */
    @Test
    public void testFiveVertexFourEdge5() {
        List<Transaction> list = new ArrayList<>();
        assertNotNull(list);
        Transaction trans1 = new Transaction("Tx1");
        assertNotNull(trans1);
        Transaction trans2 = new Transaction("Tx2");
        assertNotNull(trans2);
        Transaction trans3 = new Transaction("Tx3");
        assertNotNull(trans3);
        Transaction trans4 = new Transaction("Tx4");
        assertNotNull(trans4);
        Transaction trans5 = new Transaction("Tx5");
        assertNotNull(trans5);
        // tx1
        TxOutput tx1 = new TxOutput("tx1Out");
        assertNotNull(tx1);
        // tx2
        TxInput tx2 = new TxInput("tx1Out");
        assertNotNull(tx2);
        TxOutput tx3 = new TxOutput("tx2Out");
        assertNotNull(tx3);
        //tx3
        TxOutput tx4 = new TxOutput("tx3Out");
        assertNotNull(tx3);
        // tx4
        TxInput tx5 = new TxInput("tx3Out");
        assertNotNull(tx3);
        TxOutput tx6 = new TxOutput("tx4Out");
        assertNotNull(tx6);
        // tx5
        TxInput tx7 = new TxInput("tx2Out");
        assertNotNull(tx6);
        TxInput tx8 = new TxInput("tx4Out");
        assertNotNull(tx6);
        // add output to transaction1
        trans1.addOutput(tx1);
        // add input/output to transaction2
        trans2.addInput(tx2);
        trans2.addOutput(tx3);
        // add input/output to transaction3
        trans3.addOutput(tx4);
        // add input/output to transaction4
        trans4.addInput(tx5);
        trans4.addOutput(tx6);
        // add input/output to transaction5
        trans5.addInput(tx7);
        trans5.addInput(tx8);
        // add transaction to list
        assertTrue(list.add(trans1));
        assertTrue(list.add(trans2));
        assertTrue(list.add(trans3));
        assertTrue(list.add(trans4));
        assertTrue(list.add(trans5));
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
        
        // checks vertex 5
        List<Transaction> copyList = null;
        caught = false;
        try {
            copyList = tp.getUpstreamTransactions(trans5);
        } 
        catch (MissingTxException e) {
            caught = true;
        }
        catch (CycleDetectedException e) {
            caught = true;
        }
        assertFalse(caught);
        assertNotNull(copyList);
        assertEquals(5, list.size());
        assertEquals(5, copyList.size());
        assertEquals("Tx1", copyList.get(0).getId());
        assertEquals("Tx2", copyList.get(1).getId());
        assertEquals("Tx3", copyList.get(2).getId());
        assertEquals("Tx4", copyList.get(3).getId());
        assertEquals("Tx5", copyList.get(4).getId());
    }

}
