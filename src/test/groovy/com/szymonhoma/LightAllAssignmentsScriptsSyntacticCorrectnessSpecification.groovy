package com.szymonhoma

import com.ofg.pipeline.test.util.JobSpecTrait
import groovy.io.FileType
import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.dsl.MemoryJobManagement
import spock.lang.Specification
import spock.lang.Unroll

//Can be executed directly from IDE
class LightAllAssignmentsScriptsSyntacticCorrectnessSpecification extends Specification implements JobSpecTrait {

    DslScriptLoader loader = new DslScriptLoader(new MemoryJobManagement())

    @Unroll
    def "generates jobs for #file.name script"(File file) {
        when:
            loader.runScript(file.text)
        then:
            noExceptionThrown()
        where:
            file << jobFiles
    }
    
    static List<File> getJobFiles() {
        List<File> files = []
        new File('jobs/assignments').eachFileRecurse(FileType.FILES) {
            if (it.name.endsWith('.groovy')) {
                files.add(it)
            }
        }
        return files
    }
}