package com.szymonhoma.pipelinedsl

import com.ofg.pipeline.core.JenkinsVariables
import com.ofg.pipeline.core.JobConfigurer
import javaposse.jobdsl.dsl.Job


class ServiceJobConfigurer implements JobConfigurer<ServiceProject> {

    @Override
    void preConfigure(Job job, ServiceProject project, JenkinsVariables jenkinsVariables) {

    }

    @Override
    void postConfigure(Job job, ServiceProject project, JenkinsVariables jenkinsVariables) {
    }
}
