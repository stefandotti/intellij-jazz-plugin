package at.dotti.intellij.plugins.jazz.service;

import at.dotti.intellij.plugins.jazz.beans.*;
import at.dotti.intellij.plugins.jazz.exceptions.JazzServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.CheckoutProvider;
import com.intellij.openapi.vcs.FilePath;
import com.intellij.openapi.vcs.changes.Change;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

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

    private JazzService() {
    }

    private boolean initialized = false;

    public synchronized void initialize() throws JazzServiceException {
        if (initialized) {
            return;
        }
    }


    public synchronized void shutdown() {
    }

    public JazzProjectAreaList listProjectAreas() throws JazzServiceException {
        String json = JazzServiceExecutor.getInstance().execute("list", "projectareas");
        ObjectMapper mapper = new ObjectMapper();
        try {
            JazzProjectArea[] list = mapper.readValue(json, JazzProjectArea[].class);
            JazzProjectAreaList jazzProjectAreaList = new JazzProjectAreaList();
            jazzProjectAreaList.setProjectAreas(Arrays.asList(list));
            return jazzProjectAreaList;
        } catch (IOException e) {
            System.out.println(json);
            throw new JazzServiceException(e);
        }
    }

    public void checkout(Project project, CheckoutProvider.Listener listener) {

    }

    public JazzStatusObject status(@NotNull Project project) throws JazzServiceException {
        String json = JazzServiceExecutor.getInstance().execute(project.getBasePath(), true, "show", "status");
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, JazzStatusObject.class);
        } catch (IOException e) {
            System.out.println(json);
            throw new JazzServiceException(e);
        }
    }

    public JazzStatusObject status(FilePath filePath) throws JazzServiceException {
        String json = JazzServiceExecutor.getInstance().execute(filePath.getPath(), true, "show", "status");
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, JazzStatusObject.class);
        } catch (IOException e) {
            System.out.println(json);
            throw new JazzServiceException(e);
        }
    }

    public String getContent(FilePath filePath, JazzChange change) throws JazzServiceException {
        if (!filePath.getIOFile().exists()) {
            throw new JazzServiceException("file does not exist");
        }
        if (filePath.isDirectory()) {
            throw new JazzServiceException("file is a directory");
        }
        String workspace = change.getComponent().getWorkspace().getName();
        String component = change.getComponent().getName();
        try {
            File tmpfile = File.createTempFile("bla", "blub");
            JazzServiceExecutor.getInstance().execute(filePath.getPath(), true, "get", "file", "-o", "-w", workspace, "-c", component, "-f", change.getPath(), tmpfile.getAbsolutePath());
            String content = FileUtils.readFileToString(tmpfile, StandardCharsets.UTF_8);
            Files.delete(tmpfile.toPath());
            return content;
        } catch (IOException e) {
            throw new JazzServiceException(e);
        }
    }

    public String checkin(Project project, List<Change> changes, String commitMessage) throws JazzServiceException {
        List<String> params = new ArrayList<>();
        Collections.addAll(params, "checkin", "--comment", commitMessage);
        params.addAll(changes.stream().map(Change::getVirtualFile).filter(Objects::nonNull).map(VirtualFile::getCanonicalPath).collect(Collectors.toList()));
        return JazzServiceExecutor.getInstance().execute(project.getBasePath(), true, params.toArray(new String[]{}));
    }

    public String deliver(Project project, JazzOutgoingChange uuid) throws JazzServiceException {
        return JazzServiceExecutor.getInstance().execute(project.getBasePath(), true, "deliver", "-c", "--source", uuid.getComponent().getWorkspace().getName(), uuid.getUuid());
    }
}
