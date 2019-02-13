package assignments

import com.szymonhoma.Manager
import com.szymonhoma.util.Scripts
import groovy.transform.Field
import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job
import javaposse.jobdsl.dsl.helpers.publisher.PublisherContext

DslFactory dsl = this as DslFactory

@Field
Manager manager = new Manager()
String gitUrl = 'https://github.com/dev-trainings/simple-gradle-project.git'

dsl.job('01-build') {
    scm {
        git gitUrl
    }
    triggers {
        scm '@daily'
    }
    steps {
        gradle 'publish'
    }
    manager.printToShell(delegate,'Building project.')
    manager.publishNextJob(delegate, '02-deploy-on-test', ['VERSION':'$GIT_COMMIT'])
}


dsl.job('02-deploy-on-test') {
    manager.printToShell(delegate,'deploying project version $VERSION on test')
    manager.publishNextJob(delegate, '03-acceptance-tests')
    Job thisJob = delegate
    2.times { int n ->
        manager.publishNextJob(thisJob, "03-0$n-long-running-job")
    }
}

dsl.job('03-acceptance-tests') {
    scm {
        git(gitUrl, '$version')
    }
    steps {
        gradle 'acceptanceTests'
    }
    concurrentBuild()
    manager.publishNextJob(delegate, '04-release-package')
}

2.times { int n ->
    dsl.job("03-0$n-long-running-job") {
        concurrentBuild()
        steps {
            shell 'sleep 50'
        }
    }
}

dsl.job('04-release-package') {
    publishers {
        groovyPostBuild(
                Scripts.loadScript('looking_for_a_job_execution_single_run.groovy'), PublisherContext.Behavior.MarkFailed
                //Scripts.loadScript('looking_for_a_job_execution_in_loop.groovy'), PublisherContext.Behavior.MarkFailed
        )
        downstreamParameterized {
            trigger('05-deploy-on-production') {
                parameters {
                    currentBuild()
                }
            }
        }
        naginatorPublisher {
            regexpForRerun ''
            rerunIfUnstable(true)
            checkRegexp false
            rerunMatrixPart false
            // Limits successive failed build retries.
            maxSchedule 100
            // Fixed delay in seconds before retrying build.
            delay {
                // Progressively delay before retrying build.
                progressiveDelay {
                    increment(3)
                    max(50)
                }
            }
         }
    }
}

dsl.job('05-deploy-on-production') {
    manager.printToShell(delegate,'deploying project version $VERSION on production')
}

dsl.deliveryPipelineView('pipeline-delivery-view') {
    //showAggregatedPipeline()
    allowPipelineStart()
    allowRebuild()
    updateInterval(60)
    pipelineInstances(2)
    enableManualTriggers()
    pipelines {
        component('Simple gradle project', '01-build')
    }
}