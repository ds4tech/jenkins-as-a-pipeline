package com.szymonhoma.pipelinedsl.view

import com.szymonhoma.pipelinedsl.ServiceProject
import com.szymonhoma.pipelinedsl.Steps
import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.views.ListView

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

        dslFactory.nestedView('pipelines-dashboard') {
            views {
                listView("production-deployments") {
                    jobs {
                        for (ServiceProject project : projects) {
                            name(Steps.DEPLOY_TO_PRODUCTION.getJobName(project))
                        }
                    }
                    columns {
                        columns {
                            name()
                            weather()
                            status()
                            lastDuration()
                            lastBuildConsole()
                        }
                    }
                }
                nestedView("pipeline execution aggregated") {
                    views {
                        for (ServiceProject project : projects) {
                            deliveryPipelineView("${project.qualifiedName}-aggregated-pipeline") {
                                showAggregatedPipeline()
                                pipelineInstances(0)
                                columns(1)
                                updateInterval(5)
                                showAvatars()
                                showChangeLog()
                                configure {
                                    (it / linkRelative).value = true
                                    (it / showStaticAnalysisResults).value = true
                                }
                                pipelines {
                                    component("Aggregated pipeline for ${project.qualifiedName}", Steps.BUILD_AND_PUBLISH.getJobName(project))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
