package com.szymonhoma.pipelinedsl.view

import com.szymonhoma.pipelinedsl.ServiceProject
import com.szymonhoma.pipelinedsl.Steps
import javaposse.jobdsl.dsl.DslFactory

class ServiceViewBuilder {

    private final DslFactory dslFactory

    ServiceViewBuilder(DslFactory dslFactory) {
        this.dslFactory = dslFactory
    }

    void build(List<ServiceProject> projects) {
        projects.each { ServiceProject project ->
            dslFactory.deliveryPipelineView("${project.qualifiedName}-pipeline") {
                allowPipelineStart()
                enableManualTriggers()
                showAvatars()
                showChangeLog()
                enablePaging()
                configure {
                    (it / linkRelative).value = true
                    (it / showStaticAnalysisResults).value = true
                }
                pipelines {
                    component("Trigger pipeline for ${project.qualifiedName}", Steps.BUILD_AND_PUBLISH.getJobName(project))
                }
            }
        }
    }
}
