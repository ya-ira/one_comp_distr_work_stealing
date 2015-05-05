package fjtest;

public class FJtest {

    public static void main(String[] args) {
        int n = 2, nC = 2;
        DEQueue q1 = new DEQueue(8);
        DEQueue q2 = new DEQueue(5);
        DEQueue q3 = new DEQueue(2);
        DEQueue q4 = new DEQueue(3);
        DEQueue[][] q12 = {{q1, q2}, {q3, q4}};
        Comp c0 = new Comp(q12, 0);
        Comp c1 = new Comp(q12, 1);
        Comp[] c = {c0, c1};
        for(int i = 0; i < nC; i++){
            c[i].start();
        }
    }

}
