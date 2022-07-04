package antigo;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

public class Audio {
    private Clip clip;
    private HashMap<String, String> props;
    private String path;

    public Audio(String path, HashMap<String, String> props) {
        this.props = props;
        this.path = path;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataInputStream dis = new DataInputStream(new FileInputStream(path));

            byte[] b = new byte[dis.available()];
            int read;

            while((read = dis.read(b)) >= 0) {
                baos.write(b, 0, read);
            }

            dis.close();

            byte[] buffer = baos.toByteArray();

            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(buffer)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    public void loop() {
        if (clip == null) return;

        clip.loop(Integer.MAX_VALUE);
    }

    public HashMap<String, String> getProps() {
        return props;
    }

    public String getPath() {
        return path;
    }
}
