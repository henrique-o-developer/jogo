package antigo.Main;


import antigo.Error;
import antigo.Success;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KeyMap {

    List<Value> values = new ArrayList<>();
    KeyMap me;

    public man r = new man();

    public KeyMap() {
        me = this;
    }


    /*public KeyMap() {
        this("");
    }

    public KeyMap(String s) {
        me = this;

        check(s);
    }

    Value extract(int i, String[] arr) {
        String ant = "";
        String dps = "";
        int dpsA = 1;
        int antF = 1;

        for (int i2 = i-1; i2 >= 0; i2--) {

            if (Objects.equals(arr[i2], "}")) antF++;

            if (Objects.equals(arr[i2], "{") || Objects.equals(arr[i2], ",")) {
                antF--;

                if (antF == 0) {
                    System.out.println(arr[i2]);
                    break;
                }
            }

            ant = arr[i2] + ant;
        }

        for (int i2 = i+1; i2 < arr.length; i2++) {

            if (Objects.equals(arr[i2], "{")) dpsA++;

            dps += arr[i2];

            if (Objects.equals(arr[i2], "}")) {
                dpsA--;

                if (dpsA == 0)
                    break;
            }
        }

        ant = ant.trim();
        dps = dps.trim();

        if (dps)

        return new Value(ant, dps);
    }

    void check(String s) {
        System.out.println(s);

        String[] arr = s.split("");

        for (int i = 0; i < arr.length; i++) {
            String s1 = arr[i];

            String now = "";

            switch (s1) {
                case ":": {
                    /*String ant = "";
                    String dps = "";
                    int dpsA = 1;
                    int antF = 1;

                    for (int i2 = i-1; i2 >= 0; i2--) {

                        if (Objects.equals(arr[i2], "}")) antF++;

                        if (Objects.equals(arr[i2], "{") || Objects.equals(arr[i2], ",")) {
                            antF--;

                            if (antF == 0) {
                                System.out.println(arr[i2]);
                                break;
                            }
                        }

                        ant = arr[i2] + ant;
                    }

                    for (int i2 = i+1; i2 < arr.length; i2++) {

                        if (Objects.equals(arr[i2], "{")) dpsA++;

                        dps += arr[i2];

                        if (Objects.equals(arr[i2], "}")) {
                            dpsA--;

                            if (dpsA == 0)
                                break;
                        }
                    }

                    ant = ant.trim();
                    dps = dps.trim();

                    System.out.println(ant);
                    System.out.println(":");
                    System.out.println(dps);

                    System.out.println(new Value(ant, e));

                    System.out.println(extract(i, arr));

                    break;
                }
            }
        }
    }*/

    // a: { b: c }, d: 1

    public <T> Success add(String k, T v) {
        boolean e = false;

        for (Value va : values) {
            if (Objects.equals(va.key, k)) {
                e = true;
                break;
            }
        }

        if (!e) {
            values.add(new Value<T>(k, v));

            return new Success("addKey");
        } else {
            return new KeyError("cannot add 2 keys with equal key");
        }
    }

    public <T> Success set(String k, T v) {
        boolean s = false;

        for (int i = 0; i < values.size(); i++) {
            Value va = values.get(i);

            if (Objects.equals(va.key, k)) {
               values.set(i, new Value<T>(k, v));

               s = true;
            }
        }

        if (!s) add(k, v);

        return s ? new Success("settedExistingKey") : new Success("settedNoExistingKey");
    }

    public Success del(String k) {
        boolean s = false;

        for (int i = 0; i < values.size(); i++) {
            Value v = values.get(i);

            if (Objects.equals(v.key, k)) {
                values.remove(i);

                s = true;
            }
        }

        return s ? new Success("delkey") : new KeyError("cant delete what not exist");
    }

    public Value get(String k) {
        Value v = null;

        for (int i = 0; i < values.size(); i++) {
            Value v2 = values.get(i);

            if (Objects.equals(v2.key, k)) {
                v = v2;
            }
        }

        return v;
    }

    public List<Value> getAll() {
        return values;
    }

    @Override
    public String toString() {
        String r = "";

        for (KeyMap.Value value : values) {
            r += value.toString() + ", ";
        }

        if (r.length() >= 2) r = r.substring(0, r.length()-2);

        return "{" + r + '}';
    }

    public static class Value<T> {
        T val;
        String key;

        public Value(String key, T val) {
            this.val = val;
            this.key = key;
        }

        public String stringify() {
            String r = val.toString();

            if (val instanceof String) {
                r = "\"" + val + "\"";
            }

            return r;
        }

        @Override
        public String toString() {
            return key + ": " + stringify();
        }

        public T getValue() {
            return val;
        }

        public String getKey() {
            return key;
        }
    }

    private class man {

        public <T> man add(String k, T v) {
            boolean e = false;

            for (int i = 0; i < values.size(); i++) {
                Value va = values.get(i);

                if (Objects.equals(va.key, k)) {
                    e = true;
                }
            }

            if (!e) values.add(new Value<T>(k, v));

            return this;
        }

        public <T> man set(String k, T v) {
            boolean e = false;

            for (int i = 0; i < values.size(); i++) {
                Value va = values.get(i);

                if (Objects.equals(va.key, k)) {
                    e = true;

                    values.set(i, new Value<T>(k, v));
                }
            }

            if (!e) add(k, v);

            return this;
        }

        public man del(String k) {
            for (int i = 0; i < values.size(); i++) {
                Value v = values.get(i);

                if (Objects.equals(v.key, k)) {
                    values.remove(i);
                }
            }

            return this;
        }
    }

    private class KeyError extends Error {
        public KeyError(String msg) {
            super(msg, "KeyError");
        }
    }
}
