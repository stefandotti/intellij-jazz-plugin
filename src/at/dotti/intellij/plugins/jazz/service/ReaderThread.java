package at.dotti.intellij.plugins.jazz.service;

import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class ReaderThread extends Thread {
        private final InputStream is;
        private final Writer out;

        public ReaderThread(InputStream is, Writer out) {
            this.is = is;
            this.out = out;
        }

        @Override
        public void run() {
            try {
                byte[] buffer = new byte[1024];
                int len = -1;
                while ((len = is.read(buffer)) != -1) {
                    out.write(new String(buffer, 0, len, StandardCharsets.ISO_8859_1));
                }
            } catch (Exception e) {
                // ignore
            }
        }
    }
