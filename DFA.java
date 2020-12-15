import java.util.*; 

public class DFA{
    Set<String> listAllStates;
    String startState;
    Set<String> listAcceptStates;
    HashMap<List<String[]>, Integer> transitions;

    DFA(List<String> listStates, String startState, List<String> acceptStates, HashMap<List<String[]>, Integer> transitions) {
        this.listAllStates = cleanStates(listStates);
        this.startState = verifyStartState(startState);
        this.listAcceptStates = verifyAcceptStates(acceptStates);
        
        this.transitions = transitions;
        
    }

    private Set<String> cleanStates(List<String> listStates){
        listAllStates = new HashSet<String>();
        for(String str: listStates){
            listAllStates.add(str.toUpperCase());
        }
        return listAllStates;
    }

    private String verifyStartState(String startState){
        startState = startState.toUpperCase();
        if(!this.listAllStates.contains(startState)) throw new IllegalArgumentException("Start state does not exist in the given list of all states");
        return startState;
    }

    private Set<String> verifyAcceptStates(List<String> acceptStates){
        listAcceptStates = new HashSet<String>();
        for(String str: acceptStates){
            listAcceptStates.add(str.toUpperCase());
            if(!listAllStates.contains(str.toUpperCase())) throw new IllegalArgumentException("A given accept state does not exist in the given list of all states");
        }
        return listAcceptStates;
    }

}