package com.szymonhoma


import hudson.model.Item
import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.dsl.GeneratedItems
import javaposse.jobdsl.plugin.JenkinsJobManagement
import jenkins.model.Jenkins
import org.junit.ClassRule
import org.jvnet.hudson.test.JenkinsRule
import spock.lang.Shared
import spock.lang.Specification

abstract class JenkinsTestHarnessSpecificationBase extends Specification {
    
    @Shared
    @ClassRule
    JenkinsRule jenkinsRule = new JenkinsRule()
    
    GeneratedItems generateJobsFor(String seedScriptPath, Map<String, String> variables = [:]) {
        File script = new File(seedScriptPath)
        return generateJobsFor(script, variables)
    }
    
    GeneratedItems generateJobsFor(URL scriptUrl, Map<String, String> variables = [:]) {
        File script = new File(scriptUrl.toURI())
        return generateJobsFor(script, variables)
    }
    
    GeneratedItems generateJobsFor(File seedScript, Map<String, String> variables = [:]) {
        JenkinsJobManagement jobManagement = createJobManagement(variables)
        return new DslScriptLoader(jobManagement)
                .runScript(seedScript.text)
    }
    
    JenkinsJobManagement createJobManagement(Map<String, String> systemProperties) {
        return new JenkinsJobManagement(System.out, systemProperties, new File('.'))
    }

    Node loadJobXmlConfiguration(String job) {
        Item item = jenkins.getItemByFullName(job)
        String text = new URL(jenkins.rootUrl + item.url + 'config.xml').text
        return new XmlParser().parseText(text)
    }
    
    Jenkins getJenkins() {
        return jenkinsRule.jenkins;
    }
    
}
