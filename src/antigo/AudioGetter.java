package antigo;

import java.io.File;
import java.util.HashMap;

public class AudioGetter {
    protected Audio audio;
    protected HashMap<String, String> props;
    protected RedirectFileInterpreter red;
    protected File f;

    public AudioGetter(File f) {

        if (!f.exists() && !f.getAbsolutePath().endsWith(".redirect")) {
            f = new File(f.getAbsolutePath() + ".redirect");
        }

        if (!f.exists() && f.getAbsolutePath().endsWith(".redirect")) throw new java.lang.Error("file " + f.getAbsolutePath() + " not found!");

        this.f = f;

        String ex = FileInterpreter.getExtension(f);

        switch (ex) {
            case "redirect" -> {
                red = new RedirectFileInterpreter(f);

                FileInterpreter fi = getProps(red.file.getName(), red.dir.getAbsolutePath());

                props = fi == null ? null : fi.props;

                audio = new Audio(red.file.getAbsolutePath(), props);
            }

            case "mp3", "wav" -> {
                FileInterpreter fi = getProps(f.getName(), f.getParent());

                props = fi == null ? null : fi.props;
                audio = new Audio(f.getAbsolutePath(), props);
            }
        }
    }

    private FileInterpreter getProps(String fileName, String dir) {
        File f = new File(dir + "/" + fileName + ".props");

        return (f.exists()) ? new FileInterpreter(f) : null;
    }

    public FileInterpreter getProps() {
        return getProps(f.getName(), f.getParent());
    }

    public Audio getAudio() {
        return audio;
    }

    @Override
    public String toString() {
        return "AudioGetter {" +
                "audio=" + audio +
                ", props=" + props +
                ", red=" + red +
                ", f=" + f +
                '}';
    }
}
