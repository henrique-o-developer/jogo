package antigo;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

public class FileInterpreter {
    protected File f;
    protected HashMap<String, String> props = new HashMap<>();

    public FileInterpreter(File p) {
        f = p;

        try {
            java.io.FileReader fr = new java.io.FileReader(p);

            int i;
            StringBuilder strB = new StringBuilder();
            while((i = fr.read()) != -1) {
                strB.append((char) i);
            }

            fr.close();

            String str = strB.toString();

            for (String s : str.split("\n")) {
                s = s.split("#")[0];

                if (!s.trim().equals("")) {
                    String[] a = s.split(":");

                    if (a.length < 2) {
                        throw new Exception("this file not have correct syntax");
                    }

                    props.put(a[0].trim(), String.join(" ", Arrays.copyOfRange(a, 1, a.length)).trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "FileInterpreter {" +
                "baseFile=" + f +
                ", props=" + props +
                '}';
    }

    public static String getExtension(String filename) {
        String extension = "";

        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i+1);
        }

        return extension;
    }

    public static String getExtension(File f) {
        return getExtension(f.getName());
    }
}
