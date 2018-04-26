package solutions

import javaposse.jobdsl.dsl.helpers.publisher.PublisherContext

job('01-build') {
    scm {
        git 'https://github.com/dev-trainings/simple-gradle-project.git'
    }
    triggers {
        scm '@daily'
    }
    steps {
        gradle 'publish'
    }
    publishers {
        triggerDownstreamJob(delegate, '02-deploy-on-test', [version: '$GIT_COMMIT'])
    }
}

job('02-deploy-on-test') {
    steps {
        shell 'echo deploying project v. $VERSION on test'
    }
    publishers {
        triggerDownstreamJob(delegate, '03-acceptance-tests')
    }
}

job('03-acceptance-tests') {
    scm {
        git('https://github.com/dev-trainings/simple-gradle-project.git', '$version')
    }
    steps {
        gradle 'acceptanceTests'
    }
    publishers {
        triggerDownstreamJob(delegate, '04-deploy-on-production')
    }
}

job('04-deploy-on-production') {
    steps {
        shell 'echo deploying project v. $VERSION on production'
    }
}

deliveryPipelineView('pipeline-delivery-view') {
    allowPipelineStart()
    enableManualTriggers()
    pipelines {
        component('Simple gradle project', '01-build')
    }
}

void triggerDownstreamJob(PublisherContext publisherContext, String downstreamJob, Map downstreamParameters = [:]) {
    publisherContext.downstreamParameterized {
        trigger(downstreamJob) {
            condition 'SUCCESS'
            parameters {
                currentBuild()
                if (downstreamParameters) {
                    predefinedProps downstreamParameters
                }
            }
        }
    }
}