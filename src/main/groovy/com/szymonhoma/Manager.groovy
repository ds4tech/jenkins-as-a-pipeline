package com.szymonhoma

import javaposse.jobdsl.dsl.Job

class Manager {
    void printToShell(Job job, String txt){
        job.steps {
            shell("echo $txt")
        }
    }

    void publishNextJob(Job job, String name, Map<String, String> params = [:]) {
        job.publishers {
            downstreamParameterized {
                trigger(name) {
                    condition 'SUCCESS'
                    if(params.isEmpty()){
                        parameters {
                            currentBuild()
                        }
                    } else {
                        parameters {
                            params.each { key, value ->
                                predefinedProp(key, value)
                            }
                        }
                    }
                }
            }
        }
    }
}
