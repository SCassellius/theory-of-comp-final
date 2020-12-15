import java.util.*;

public class DFA{
    public Set<String> listAllStates;
    public String startState;
    public Set<String> listAcceptStates;
    public Set<Transition> listTransitions;

    DFA(List<String> listStates, String startState, List<String> acceptStates, List<Transition> transitions) {
        this.listAllStates = cleanStates(listStates);
        this.startState = verifyStartState(startState);
        this.listAcceptStates = verifyAcceptStates(acceptStates);
        this.listTransitions = verifyBasicTransitions(transitions);
        testForIllegalTransitions(listTransitions);
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

    private Set<Transition> verifyBasicTransitions(List<Transition> transitions){
        Set<Transition> listTransitions = new HashSet<Transition>();
            for(Transition t : transitions){
                if(this.listAllStates.contains(t.start.toUpperCase()) &&
                this.listAllStates.contains(t.end.toUpperCase()) &&
                t.value >= 0 &&
                t.value <= 1) {
                    Transition newT = new Transition(t.start.toUpperCase(), t.end.toUpperCase(), t.value);
                    boolean canAdd = true;
                    for(Transition checkingT : listTransitions){
                        if(checkingT.equals(newT)){
                            canAdd = false;
                            break;
                        }
                    }
                    if(canAdd){
                        listTransitions.add(newT);
                    }else{
                        throw new IllegalArgumentException("There is a duplicate transition in the given list of transitions");
                    }
                }else{
                    throw new IllegalArgumentException("The given list of transitions contains an illegal transition");
                }
            }
        return listTransitions;
    }

    public boolean equals(DFA newDFA){
        if(this.listAllStates.equals(newDFA.listAllStates)){
            if(this.startState.equals(newDFA.startState)){
                if(this.listAcceptStates.equals(newDFA.listAcceptStates)){
                    if(this.listTransitions.size()!=newDFA.listTransitions.size()) return false;
                    Iterator<Transition> it1 = this.listTransitions.iterator();
                    while(it1.hasNext()){
                        Transition tToCheck = it1.next();
                        boolean contains = false;
                        for(Transition checkingT : newDFA.listTransitions){
                            if(checkingT.equals(tToCheck)){
                                contains = true;
                                break;
                            }
                        }
                        if(!contains) return false;
                    }
                }
            }
        }
        return true;
    }

    private void testForIllegalTransitions(Set<Transition> transitions){
        Iterator it = this.listAllStates.iterator();
        while(it.hasNext()){
            String testState = (String)it.next();
            Set<Integer> values = new HashSet<Integer>();
            for(Transition t: this.listTransitions){
                if(testState.equals(t.start)){
                    if(values.add(t.value) == false) throw new IllegalArgumentException("There is an illegal or duplicate transition in the given list of transitions");
                }
            }
        }
    }

//    private DFA minimize(DFA dfa){
//
//        return ;
//    }

}