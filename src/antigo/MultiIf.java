package antigo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MultiIf {

    protected List<List<Object>> test;
    public static final CC ANY = new CC("ANY");
    //public static final CC NOTUSED = new CC("NOTUSED");

    private static class CC {
        Object O;

        public CC(Object O) {
            this.O = O;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CC)) return false;
            CC cc = (CC) o;
            return Objects.equals(O, cc.O);
        }

        @Override
        public int hashCode() {
            return Objects.hash(O);
        }
    }

    public MultiIf() {
        test = new ArrayList<>();
    }

    public void add(List<Object> t, Lambdapp run) {
        List<Object> add = new ArrayList<>();

        add.add(t);
        add.add(run);

        test.add(add);
    }

    public void add(Object[] t, Lambdapp run) {
        add(new ArrayList<>(Arrays.asList(t)), run);
    }

    public Object test(List<Object> o, int i, Object p) {
        List<Object> t = test.get(i);

        boolean allPass = true;
        List<Object> notUsed = new ArrayList<>();
        List<int[]> notUsedTick = new ArrayList<>();

        for (int i1 = 0; i1 < ((List<Object>) t.get(0)).size(); i1++) {
            Object C = ((List<Object>) t.get(0)).get(i1);

            for (int i2 = 0; i2 < o.size(); i2++) {
                Object c = o.get(i2);

                if (i1 == i2) {
                    /*if (c == NOTUSED || C == NOTUSED) {
                        notUsedTick.add(new int[]{i1, i2});
                    } else*/ if (c != ANY && C != ANY) {
                        if (c != C) {
                            allPass = false;
                            break;
                        }
                    }
                }
            }
        }

        /*for (Object C : ((List<Object>) t.get(0))) {
            for (Object c : o) {
                if (!(C == c)) {
                    notUsed.add(c);
                }
            }
        }

        for (int[] ints : notUsedTick) {
            int i1 = ints[0];
            int i2 = ints[1];

            Object C = ((List<Object>) t.get(0)).get(i1);
            Object c = o.get(i2);

            boolean u = false;

            for (Object o1 : notUsed) {
                if (o1 == C || o1 == c) {
                    u = true;
                    break;
                }
            }

            if (c != ANY && C != ANY) {
                if (c != C && !u) {
                    allPass = false;
                    break;
                }
            }
        }*/

        if (allPass) {
            return ((Lambdapp) t.get(1)).run(p);
        }

        return null;
    }

    public Object test(Object[] o, int i, Object p) {
        return test(new ArrayList<>(Arrays.asList(o)), i, p);
    }

    public Object[] testAll(Object[] o, Object p) {
        return testAll(new ArrayList<>(Arrays.asList(o)), p);
    }

    public Object[] testAll(List<Object> o, Object p) {
        List<Object> os = new ArrayList<>();

        for (int i = 0; i < test.size(); i++) {
            os.add(test(o, i, p));
        }

        return os.toArray(new Object[]{});
    }
}
