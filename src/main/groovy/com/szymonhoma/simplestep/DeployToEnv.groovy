package com.szymonhoma.simplestep

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job


class DeployToEnv extends PipelineStep {

    private String envName

    DeployToEnv(String envName) {
        super("deploy-to-$envName")
        this.envName = envName
    }

    Job applyOn(DslFactory dslFactory) {
        return dslFactory.job(name) {
            steps {
                shell 'echo deploying project v. $VERSION on ' + envName
            }
        }
    }
}
