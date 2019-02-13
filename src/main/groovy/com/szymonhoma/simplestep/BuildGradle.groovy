package com.szymonhoma.simplestep

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job


class BuildGradle extends PipelineStep {

    private String gitUrl

    BuildGradle(String name, String gitUrl) {
        super("build-and-publish-$name")
        this.gitUrl = gitUrl
    }

    Job applyOn(DslFactory dslFactory) {
        return dslFactory.job(name) {
            scm {
                git gitUrl
            }
            triggers {
                scm '@daily'
            }
            steps {
                gradle 'publish'
            }
        }
    }
}
