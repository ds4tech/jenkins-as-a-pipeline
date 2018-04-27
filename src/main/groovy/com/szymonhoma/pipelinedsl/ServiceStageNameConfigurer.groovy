package com.szymonhoma.pipelinedsl

import javaposse.jobdsl.dsl.Job


/**
 * @author Artur Gajowy
 * @author Marek Kapowicki
 */
class ServiceStageNameConfigurer implements com.ofg.pipeline.core.StageNameConfigurer {

    @Override
    void configure(Job job, String stageName, String jobLabel) {
        job.with {
            deliveryPipelineConfiguration(stageName, jobLabel)
        }
    }
}