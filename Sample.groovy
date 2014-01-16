package my.testpackage;

@eu.mihosoft.vrl.instrumentation.VRLVisualization
public class MyFileClass {
    
    int value1;
    
    public int m1(int v1) {
        this.m2(this.m1(v1), this.m1(v1));
    }

    public int m2(int v1, int v2) {
      return m2(m1(v1), m1(v2))
    }
   
}
