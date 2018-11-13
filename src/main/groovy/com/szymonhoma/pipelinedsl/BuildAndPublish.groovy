package com.szymonhoma.pipelinedsl

import com.ofg.pipeline.core.JenkinsVariables
import com.ofg.pipeline.core.JobDefinition
import com.ofg.pipeline.core.JobType
import javaposse.jobdsl.dsl.Job


class BuildAndPublish extends JobDefinition<Job, ServiceProject> {

    private String name;

    BuildAndPublish(String name) {
        this.name = name
    }

    @Override
    Class<Job> getJobClass() {
        return Job
    }

    @Override
    void configure(Job job, ServiceProject microserviceProject, JenkinsVariables jenkinsVariables) {
        job.with {
            scm {
                git 'https://github.com/dev-trainings/simple-gradle-project.git'
            }
            triggers {
                scm '@daily'
            }
            steps {
                gradle 'publish'
            }
        }
    }

    @Override
    JobType getJobType() {
        return new JobType(name)
    }
}
