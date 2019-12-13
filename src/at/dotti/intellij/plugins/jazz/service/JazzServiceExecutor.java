package at.dotti.intellij.plugins.jazz.service;

import at.dotti.intellij.plugins.jazz.exceptions.JazzServiceException;
import at.dotti.intellij.plugins.jazz.settings.SettingsBean;
import com.intellij.openapi.components.ServiceManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JazzServiceExecutor {

    private static final JazzServiceExecutor INSTANCE = new JazzServiceExecutor();

    public static JazzServiceExecutor getInstance() {
        return INSTANCE;
    }

    private JazzServiceExecutor() {
    }

    public String execute(String... commands) throws JazzServiceException {
        try {
            SettingsBean bean = ServiceManager.getService(SettingsBean.class);

            List<String> parameter = new ArrayList<>();
            Collections.addAll(parameter, bean.getLScmPath());
            Collections.addAll(parameter, commands);
            Collections.addAll(parameter, "-j", "-r", bean.getUrl(), "-u", bean.getUsername(), "-P", bean.getPassword());

            ProcessBuilder pb = new ProcessBuilder();
            pb.command(parameter);
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

}
