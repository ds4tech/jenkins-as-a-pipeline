package com.szymonhoma.pipelinedsl

import com.ofg.pipeline.core.JenkinsVariables
import com.ofg.pipeline.core.JobDefinition
import com.ofg.pipeline.core.JobType
import javaposse.jobdsl.dsl.Job


class SimpleJob extends JobDefinition<Job, ServiceProject> {

    private String name;

    SimpleJob(String name) {
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
                shell "echo $name"
                shell 'sleep 15'
            }
        }
    }

    @Override
    JobType getJobType() {
        return new JobType(name)
    }
}
