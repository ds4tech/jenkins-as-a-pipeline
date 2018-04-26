package examples

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
        downstreamParameterized {
            trigger('02-deploy-on-test') {
                condition 'SUCCESS'
                parameters {
                    predefinedProp('VERSION', '$GIT_COMMIT')
                }
            }
        }
    }
}

job('02-deploy-on-test') {
    steps {
        shell 'echo deploying project v. $VERSION on test'
    }
    publishers {
        downstreamParameterized {
            trigger('03-acceptance-tests') {
                condition 'SUCCESS'
                parameters {
                    currentBuild()
                }
            }
        }
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
        downstreamParameterized {
            trigger('04-deploy-on-production') {
                condition 'SUCCESS'
                parameters {
                    currentBuild()
                }
            }
        }
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