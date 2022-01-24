import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import util.Transaction;

/**
 * TransactionProcessor Class
 * 
 * @author Robert Lewis
 * @version 1.0
 */

public class TransactionProcessor {
    private Graph adjacencyList;
    private HashSet<Transaction> cycle = 
            new HashSet<Transaction>();

    /**
     * takes in the graph
     * 
     * @param transactions is the graph that is being taken in
     * @throws DuplicateOutputException two outputs that have the same hash
     * @throws OutputReusedException when two inputs use the same output
     */
    public TransactionProcessor(List<Transaction> transactions) 
        throws DuplicateOutputException, OutputReusedException {
        adjacencyList = new Graph(transactions);
    }

    /**
     * does the typo sorting of the graph
     * 
     * @param tx the transaction that is starting the sort
     * @return a copy list of the graph
     * @throws MissingTxException missing transaction via input to output
     * @throws CycleDetectedException when there is a cycle in the graph
     */
    public List<Transaction> getUpstreamTransactions(Transaction tx) 
        throws MissingTxException, CycleDetectedException {
        List<Transaction> copyList = new ArrayList<Transaction>();
        
        if (this.adjacencyList.getGraph().isEmpty()) {
            return copyList;
        }

        // throw MissingTxException
        if (!this.adjacencyList.getInputMap().isEmpty()) {
            throw new MissingTxException();
        }
        
        int index = Math.abs(tx.hashCode());
        
        
        List<Transaction> tempList = this.adjacencyList.getGraph().get(index);

        // base case
        if (tempList.isEmpty()) {
            copyList.add(tx);
            return copyList;
        }  
        
        // throws CycleDetectedException
        if (!cycle.add(tx)) {
            throw new CycleDetectedException();
        }
        
        // calls recursive till it goes to base case
        for (int i = 0; i < tempList.size(); i++) {
            List<Transaction> temp =
                    this.getUpstreamTransactions(tempList.get(i));
            copyList.addAll(temp);
        }
        // adds last vertex to list
        copyList.add(tx);
        
        // clears hashSet once done
        cycle.clear();
        
        return copyList;
    }
}
