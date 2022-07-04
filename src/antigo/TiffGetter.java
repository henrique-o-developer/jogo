package antigo;

import antigo.Media.Tiff;

import java.io.File;
import java.util.HashMap;

public class TiffGetter {
    protected Tiff tiff;
    protected HashMap<String, String> props;
    protected RedirectFileInterpreter red;
    protected File f;

    public TiffGetter(File f) {

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

                tiff = new Tiff(red.file.getAbsolutePath(), props);
                break;
            }

            case "tiff": {
                FileInterpreter fi = getProps(f.getName(), f.getParent());

                props = fi == null ? null : fi.props;
                tiff = new Tiff(f.getAbsolutePath(), props);
                break;
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

    public Tiff getTiff() {
        return tiff;
    }

    public TiffGetter invertTiff() {
        TiffGetter gg = new TiffGetter(this.f);

        gg.tiff = gg.tiff.flip();

        return gg;
    }
}
