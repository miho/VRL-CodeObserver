package my.testpackage;

@eu.mihosoft.vrl.instrumentation.VRLVisualization
public class MyFileClass {
    
    int value1;
    
    public int m1(int v1) {
        this.m1(v1);
        this.m1(v1);
    }
    public int m2(double v1, my.testpackage.MyFileClass v2) {
        this.m2(v1, v2);
        for(int i = 1; i <= 3; i++) {
            for(int j = 10; j >= 9; j--) {
                this.m1(this.m1(j));
                System.out.println("Hello!\"");
            }
        }
    }
}
