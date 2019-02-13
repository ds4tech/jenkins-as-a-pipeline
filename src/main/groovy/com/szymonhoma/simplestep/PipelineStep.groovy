package com.szymonhoma.simplestep

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job


abstract class PipelineStep {

    String name
    private PipelineStep nextStep
    protected PipelineStep previousStep
    private Map<String, String> params = [:]

    PipelineStep(String name) {
        this.name = name
    }

    void execute(DslFactory dsl) {
        Job current = applyOn(dsl)
        if (nextStep != null) {
            publishNextJobOn(current, nextStep.name, params)
        }
        if (previousStep != null) {
            previousStep.execute(dsl)
        }
    }

    abstract protected Job applyOn(DslFactory dsl);

    PipelineStep next(PipelineStep anotherStep, Map<String, String> params = [:]) {
        addNextStep(anotherStep, params)
        linkWithPrevious(anotherStep)
        return anotherStep
    }

    private void addNextStep(PipelineStep anotherStep, Map<String, String> params) {
        this.nextStep = anotherStep
        this.params = params
    }

    private void linkWithPrevious(PipelineStep anotherStep) {
        anotherStep.@previousStep = this
    }

    private void publishNextJobOn(Job job, String name, Map<String, String> params = [:]) {
        job.publishers {
            downstreamParameterized {
                trigger(name) {
                    condition 'SUCCESS'
                    if (params.isEmpty()) {
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
