package com.szymonhoma


import com.ofg.pipeline.test.util.XmlComparator
import javaposse.jobdsl.dsl.GeneratedItems
import spock.lang.Shared
import spock.lang.Unroll

//Can be executed directly from IDE, but only by using the gradle execution - this is due to plugins preparation done by gradle script
@Unroll
class ExampleDSLScriptRegressionSpecification extends JenkinsTestHarnessSpecificationBase implements XmlComparator {

    String expectedJobXmlsPath = 'src/test/resources/jobs/expected'

    @Shared
    GeneratedItems jobsToTest;

    def setupSpec() {
        jobsToTest = generateJobsFor(ExampleDSLScriptRegressionSpecification.getResource('example_for_regression_dsl_script.groovy'))
    }

    def "generates expected job #job"() {
        expect:
            compareXmls(expectedJob(job), loadJobXmlConfiguration(job))
        where:
            job << jobsToTest.jobs*.jobName.toSet()
    }

    private File expectedJob(String job) {
        return new File(expectedJobXmlsPath, "${job}.xml")
    }
}
