package File;

import Graphics.Gif;
import java.io.File;
import java.util.HashMap;
import java.util.Objects;

public class GifGetter {
    protected Gif gif;
    protected HashMap<String, String> props;
    protected RedirectFileInterpreter red;

    public GifGetter(File f) {
        String ex = FileInterpreter.getExtension(f);

        switch (ex) {
            case "redirect": {
                red = new RedirectFileInterpreter(f);

                System.out.println(red.file.getName());

                gif = new Gif(red.file.getAbsolutePath());

                props = Objects.requireNonNull(getProps(red.file.getName(), red.dir.getAbsolutePath())).props;
                break;
            }

            case "gif": {
                gif = new Gif(f.getAbsolutePath());

                props = Objects.requireNonNull(getProps(f.getName(), f.getParent())).props;
                break;
            }
        }
    }

    private FileInterpreter getProps(String fileName, String dir) {
        File f = new File(dir + "/" + fileName + ".props");

        System.out.println(f);
        System.out.println((f.exists()) ? new FileInterpreter(f) : null);

        return (f.exists()) ? new FileInterpreter(f) : null;
    }

    @Override
    public String toString() {
        return "GifGetter {" +
                "gif=" + gif +
                ", props=" + props +
                ", red=" + red +
                '}';
    }


}
