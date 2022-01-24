import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import util.Transaction;
import util.TxInput;
import util.TxOutput;

/**
 * Graph Class
 * 
 * @author Robert Lewis
 * @version 1.0
 *
 */
public class Graph {
    private HashMap<Integer, List<Transaction>> adjacencyList =
            new HashMap<Integer, List<Transaction>>();
    private HashMap<Integer, Transaction> outputMap = 
            new HashMap<Integer, Transaction>();
    private HashMap<Integer, Transaction> inputMap = 
            new HashMap<Integer, Transaction>();
    /**
     * creates the graph
     * 
     * @param transactions list of transactions
     * @throws DuplicateOutputException two outputs that have the same hash
     * @throws OutputReusedException when two inputs use the same output 
     */
    public Graph(List<Transaction> transactions) 
        throws DuplicateOutputException, OutputReusedException {
        // goes through list of transactions for outputs
        for (int i = 0; i < transactions.size(); i++) {
            Transaction tx = transactions.get(i);
            List<TxOutput> opList = tx.getOutputs();
            for (int j = 0; j < opList.size(); j++) {
                TxOutput output = opList.get(j);
                int key = this.getIndex(output.getAddress());
                if (this.outputMap.containsKey(key)) {
                    // if there are two output address that have same hashcode
                    throw new DuplicateOutputException();
                }
                this.outputMap.put(key, tx);
            }
        }
        
        // goes through list of transactions for inputs 
        for (int i = 0; i < transactions.size(); i++) {
            Transaction tx = transactions.get(i);
            List<TxInput> inList = tx.getInputs();
            for (int j = 0; j < inList.size(); j++) {
                TxInput input = inList.get(j);
                int key = this.getIndex(input.getAddress());
                if (this.inputMap.put(key, tx) == null) {
                    this.inputMap.put(key, tx);
                }
                else {
                    // if there is more than one input with same address
                    throw new OutputReusedException();
                }
            }
        }
        
        // goes through list of transactions for inputs that match output key
        for (int i = 0; i < transactions.size(); i++) {
            Transaction tx = transactions.get(i);
            int vertex = this.getIndex(tx.getId());
            List<TxInput> ipList = tx.getInputs();
            List<Transaction> edgeList = new LinkedList<>();
            for (int j = 0; j < ipList.size(); j++) {
                TxInput input = ipList.get(j);
                int key = this.getIndex(input.getAddress());
                Transaction value = this.outputMap.get(key);
                if (this.outputMap.containsKey(key)) {
                    edgeList.add(value); 
                    this.inputMap.remove(key);
                }  
            }
            this.adjacencyList.put(vertex, edgeList);
        }
    }

    /**
     * gets the graph of vertex/ edegeList
     * 
     * @return the graph
     */
    public HashMap<Integer, List<Transaction>> getGraph() {
        return this.adjacencyList;
    }

    /**
     * gets the graph of Output address/ Transaction id
     * 
     * @return the pairMacthing
     */
    public HashMap<Integer, Transaction> getOutputMap() {
        return this.outputMap;
    }
    
    /**
     * gets the graph of Input address/ Transaction id
     * 
     * @return the InputMap
     */
    public HashMap<Integer, Transaction> getInputMap() {
        return this.inputMap;
    }

    /**
     * returns me the key index for the transaction vertex
     * 
     * @param id of transaction/input/output
     * @return key for graph/pairMatching
     */
    private int getIndex(String id) {
        return Math.abs(id.hashCode());
    }
}
