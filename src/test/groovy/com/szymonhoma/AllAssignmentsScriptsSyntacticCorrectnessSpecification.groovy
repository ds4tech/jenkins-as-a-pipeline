package com.szymonhoma

import groovy.io.FileType
import spock.lang.Unroll

//Can be executed directly from IDE, but only by using the gradle execution - this is due to plugins preparation done by gradle script
class AllAssignmentsScriptsSyntacticCorrectnessSpecification extends JenkinsTestHarnessSpecificationBase {
    
    @Unroll
    def "generates jobs for #file.name script"(File file) {
        when:
            generateJobsFor file
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