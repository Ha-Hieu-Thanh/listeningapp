package application;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<Result> resultList;

    
    // Constructor
    public History(){
        resultList = new ArrayList<Result>();
    }
    public History(Result result){
        resultList = new ArrayList<Result>();
        resultList.add(result);
    }
    public History(List<Result> results){
        resultList = new ArrayList<Result>();
        resultList = results;
    }
    // Add function
    public void addResult(Result result){
        resultList.add(result);
    }
    
    public void addAllResult (List<Result> results) {
    	resultList.addAll(results);
    }
    

    // Remove function
    public void removeResult (int index){
        if (index < 0 || index >= resultList.size())
            return;
        resultList.remove(index);
    }

    // getter
    public List<Result> getResultList(){
    	return resultList;
    }
}