package at.dotti.intellij.plugins.jazz.service;

import at.dotti.intellij.plugins.jazz.beans.JazzProjectArea;
import at.dotti.intellij.plugins.jazz.exceptions.JazzServiceException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JazzService {

    public static String testConnection(String lscmPath, String url, String username, char[] password) throws JazzServiceException {
        if (StringUtils.isEmpty(lscmPath)) {
            throw new InvalidPathException(lscmPath, "lscm path must not empty");
        }
        Path lscm = Paths.get(lscmPath);
        if (!Files.exists(lscm)) {
            throw new InvalidPathException(lscmPath, "lscm path not found");
        }
        try {
            List<String> command = new ArrayList<>();
            Collections.addAll(command, lscm.toString(), "login", "-r", url, "-u", username, "-P", new String(password));
            ProcessBuilder pb = new ProcessBuilder();
            pb.command(command);
            Process p = pb.start();
            StringWriter error = new StringWriter();
            ReaderThread err = new ReaderThread(p.getErrorStream(), error);
            StringWriter output = new StringWriter();
            ReaderThread out = new ReaderThread(p.getInputStream(), output);
            err.start();
            out.start();
            p.waitFor();
            if (error.getBuffer().length() != 0) {
                throw new JazzServiceException(error.toString());
            }
            return output.toString();
        } catch (IOException | InterruptedException e) {
            throw new JazzServiceException(e);
        }
    }

    private static final JazzService INSTANCE = new JazzService();

    public static JazzService getInstance() {
        return INSTANCE;
    }

    private JazzService() {}

    public List<JazzProjectArea> listProjectAreas() throws JazzServiceException {
        String json = JazzServiceExecutor.getInstance().execute("list", "projectareas");

        return null;
    }
}
