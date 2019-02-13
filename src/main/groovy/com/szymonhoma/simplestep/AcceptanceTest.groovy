package com.szymonhoma.simplestep

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job


class AcceptanceTest extends PipelineStep {

    private String gitUrl

    AcceptanceTest(String gitUrl) {
        super("execute-acceptance-test")
        this.gitUrl = gitUrl
    }

    Job applyOn(DslFactory dslFactory) {
        return dslFactory.job(name) {
            scm {
                git(gitUrl, '$version')
            }
            steps {
                gradle 'acceptanceTests'
            }
        }
    }
}
