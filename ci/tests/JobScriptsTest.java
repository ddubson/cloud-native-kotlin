import javaposse.jobdsl.dsl.DslScriptLoader;
import javaposse.jobdsl.dsl.MemoryJobManagement;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Simulate running a Jenkins Job DSL script
 */
public class JobScriptsTest {
    @Test
    public void should_compile_scripts() throws IOException {
        MemoryJobManagement jm = new MemoryJobManagement();
        DslScriptLoader loader = new DslScriptLoader(jm);
        String scriptText = new String(
                Files.readAllBytes(new File("scripts/jenkins/job_dsl.groovy").toPath()));
        loader.runScript(scriptText);
    }
}