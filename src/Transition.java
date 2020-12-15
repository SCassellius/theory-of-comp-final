public class Transition<start, end, value> {
    public final String start; 
    public final String end;
    public final int value; 
     
    public Transition(String start, String end, int value) { 
      this.start = start;
      this.end = end;
      this.value = value;
    }

    public boolean equals(Transition t){
        return this.start.equals(t.start) && this.end.equals(t.end) && this.value == t.value;
    }

    public String toString(){
        return this.start + " " + this.end+ " " + this.value;
    }

  } 