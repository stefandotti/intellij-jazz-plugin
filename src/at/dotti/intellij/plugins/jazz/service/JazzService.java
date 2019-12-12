package at.dotti.intellij.plugins.jazz.service;

import at.dotti.intellij.plugins.jazz.exceptions.JazzServiceException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JazzService {

    public static void testConnection(String lscmPath, String url, String username, char[] password) throws JazzServiceException {
        if (StringUtils.isEmpty(lscmPath)) {
            throw new InvalidPathException(lscmPath, "lscm path must not empty");
        }
        Path lscm = Paths.get(lscmPath);
        if (!Files.exists(lscm)) {
            throw new InvalidPathException(lscmPath, "lscm path not found");
        }
        try {
            Process p = Runtime.getRuntime().exec(lscm.toString(), new String[]{ "login", "-r", url, "-u", username, "-P", new String(password) });
            List<String> linesError = IOUtils.readLines(p.getErrorStream(), StandardCharsets.UTF_8);
            List<String> linesOutput = IOUtils.readLines(p.getInputStream(), StandardCharsets.UTF_8);
            if (!linesError.isEmpty()) {
                throw new JazzServiceException(StringUtils.join(linesError.toArray()));
            }
        } catch (IOException e) {
            throw new JazzServiceException(e);
        }
    }
}
