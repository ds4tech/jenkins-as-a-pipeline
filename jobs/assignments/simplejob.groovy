package assignments

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.views.jobfilter.Status

DslFactory dsl = this as DslFactory

dsl.job("delivery-pipeline") {
    scm {
        git {
            remote {
                name('origin')
                url('https://github.com/dev-trainings/simple-gradle-project.git')
            }
        }

    }
    steps {
        gradle{
            tasks('clean')
            tasks('publish')
        }
    }
    publishers {
        downstreamParameterized { //nazwa pluginu ktory uruchamia kolejne joby
            trigger('simple-job') {
                condition 'SUCCESS'
                parameters {
                    predefinedProp('Name', '$GIT_COMMIT')
                }
            }
        }
    }
}

dsl.job('simple-job') {
    parameters {
        stringParam('Name')
    }
    triggers {
        //cron('*/2 * * * *')
    }
    properties {
        rebuild {
            autoRebuild()
        }
    }
    steps {
        shell "echo Hello \$Name!"
    }
}

dsl.job('rebuild_with_param') {
    parameters {
        stringParam('Name')
    }
    steps {
        shell 'echo Hello $Name!'
    }
}