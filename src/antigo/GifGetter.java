package antigo;

import java.io.File;
import java.util.HashMap;

public class GifGetter {
    protected Gif gif;
    protected HashMap<String, String> props;
    protected RedirectFileInterpreter red;
    protected File f;

    public GifGetter(File f) {

        if (!f.exists() && !f.getAbsolutePath().endsWith(".redirect")) {
            f = new File(f.getAbsolutePath() + ".redirect");
        }

        if (!f.exists() && f.getAbsolutePath().endsWith(".redirect")) throw new java.lang.Error("file " + f.getAbsolutePath() + " not found!");

        this.f = f;

        String ex = FileInterpreter.getExtension(f);

        switch (ex) {
            case "redirect": {
                red = new RedirectFileInterpreter(f);

                FileInterpreter fi = getProps(red.file.getName(), red.dir.getAbsolutePath());

                props = fi == null ? null : fi.props;

                gif = new Gif(red.file.getAbsolutePath(), props);
                break;
            }

            case "gif": {
                FileInterpreter fi = getProps(f.getName(), f.getParent());

                props = fi == null ? null : fi.props;
                gif = new Gif(f.getAbsolutePath(), props);
                break;
            }
        }

        if (props == null) return;

        if (props.get("linkAudioAs") == null) return;
        if (gif == null) return;


        String dir = props.get("linkAudioAs");

        if (dir.startsWith("../")) dir = dir.replaceFirst("../", new File(f.getParent()).getAbsolutePath()+"/");

        //System.out.println(new File(dir));

        gif.attAudio(new AudioGetter(new File(dir)).getAudio());
    }

    private FileInterpreter getProps(String fileName, String dir) {
        File f = new File(dir + "/" + fileName + ".props");

        return (f.exists()) ? new FileInterpreter(f) : null;
    }

    public FileInterpreter getProps() {
        return getProps(f.getName(), f.getParent());
    }

    public Gif getGif() {
        return gif;
    }

    public GifGetter invertGif() {
        GifGetter gg = new GifGetter(this.f);

        gg.gif = gg.gif.flip();

        return gg;
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
