package antigo;

import java.io.File;

public class RedirectFileInterpreter extends FileInterpreter {
    public File file;
    public File dir;

    public RedirectFileInterpreter(File p) {
        super(p);

        if (props.get("folder") == null && props.get("file") == null) {
            new Exception("this file not have correct syntax").printStackTrace();
        } else {

            if (props.get("file") != null) file = new File(f.getParent()+"/"+props.get("file").replaceFirst("./", "/"));
            if (props.get("folder") != null) dir = new File(new File(f.getParent() + "/" + props.get("folder").replaceFirst("./", "/")).getAbsolutePath());

            if (props.get("folder") == null) {
                props.put("folder", file.getParent());
            }

            if (props.get("file") == null) {
                props.put("file", dir.getAbsolutePath());
            }

            if (file == null) file = new File(f.getParent()+"/"+props.get("file").replaceFirst("./", "/"));
            if (dir == null) dir = new File(new File(f.getParent() + "/" + props.get("folder").replaceFirst("./", "/")).getAbsolutePath());
        }

        //System.out.println(file);
        //System.out.println(dir);
    }

    @Override
    public String toString() {
        return "RedirectFileInterpreter {" +
                "file=" + file +
                ", dir=" + dir +
                '}';
    }
}
