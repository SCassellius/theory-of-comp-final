public class Transition<start, end, value> {
    public final String start; 
    public final String end;
    public final int value;

    /**
     * A tuple that denotes a transition for a DFA
     * @param start the start state
     * @param end the end state
     * @param value the binary value of the transition
     */
    public Transition(String start, String end, int value) { 
      this.start = start;
      this.end = end;
      this.value = value;
    }

    /**
     * Tests if 'this' Transition is equal to the parameter Transition
     * @param t the Transition to test against
     * @return true of the Transitions are the same, false otherwise
     */
    public boolean equals(Transition t){
        return this.start.equals(t.start) && this.end.equals(t.end) && this.value == t.value;
    }

    /**
     * Returns a String that is representative of 'this' Transition
     * @return a String that is representative of 'this' Transition
     */
    public String toString(){
        return this.start + " " + this.end+ " " + this.value;
    }

  } 