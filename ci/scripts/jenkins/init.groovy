package jenkins

import com.cloudbees.plugins.credentials.CredentialsScope
import com.cloudbees.plugins.credentials.SystemCredentialsProvider
import com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl
import hudson.model.JDK
import hudson.plugins.groovy.Groovy
import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.dsl.JobManagement
import javaposse.jobdsl.plugin.JenkinsJobManagement
import jenkins.model.Jenkins
import net.sf.json.JSONObject

File jobScript = new File("/usr/share/jenkins/seed.groovy")
JobManagement jobManagement = new JenkinsJobManagement(System.out, [:], new File("."))

println("Creating seed job")
new DslScriptLoader(jobManagement).runScript(jobScript.text)

println("Adding credentials")
SystemCredentialsProvider.getInstance().getCredentials().add(
        new UsernamePasswordCredentialsImpl(CredentialsScope.GLOBAL,
                "my_id", "my desc", "user", "pass"
        )
)
SystemCredentialsProvider.getInstance().save()

println("Adding JDK")
Jenkins.getInstanceOrNull().getJDKs().add(new JDK("jdk8", "/usr/lib/jvm/java-8-openjdk-amd64"))

println("Marking allow macro token processing")
Groovy.DescriptorImpl descriptor = (Groovy.DescriptorImpl) Jenkins.getInstanceOrNull().getDescriptorOrDie(Groovy)
descriptor.configure(null, JSONObject.fromObject('{ "allowMacro": "true" }'))