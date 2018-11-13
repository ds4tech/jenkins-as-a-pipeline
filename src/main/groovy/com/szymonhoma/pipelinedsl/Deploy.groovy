package com.szymonhoma.pipelinedsl

import com.ofg.pipeline.core.JenkinsVariables
import com.ofg.pipeline.core.JobDefinition
import com.ofg.pipeline.core.JobType
import groovy.transform.CompileStatic
import javaposse.jobdsl.dsl.Job

class Deploy extends JobDefinition<Job, ServiceProject> {

    private String name;
    private String env

    Deploy(String name, String env) {
        this.env = env
        this.name = name
    }

    @Override
    Class<Job> getJobClass() {
        return Job
    }

    @Override
    void configure(Job job, ServiceProject microserviceProject, JenkinsVariables jenkinsVariables) {
        job.with {
            steps {
                shell 'echo deploying project v. $VERSION on ' + env
            }
        }
    }

    @Override
    JobType getJobType() {
        return new JobType(name)
    }
}
