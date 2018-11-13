package com.szymonhoma.pipelinedsl

import com.ofg.pipeline.core.JobConfigurer
import com.ofg.pipeline.core.PipelineBuilder
import com.ofg.pipeline.core.PipelineTemplate
import com.ofg.pipeline.core.StageNameConfigurer
import com.ofg.pipeline.core.link.AutoLink

import static com.ofg.pipeline.core.JobChain.of
import static com.ofg.pipeline.core.link.ManualLink.manual

class ServicePipelineTemplate implements PipelineTemplate<ServiceProject> {

    static final INSTANCE = new ServicePipelineTemplate()

    private ServicePipelineTemplate() {}

    @Override
    JobConfigurer<ServiceProject> createJobConfigurer() {
        new ServiceJobConfigurer()
    }

    @Override
    StageNameConfigurer createStageNameConfigurer() {
        new ServiceStageNameConfigurer()
    }

    @Override
    void configurePipeline(PipelineBuilder<ServiceProject> pipelineBuilder, ServiceProject project) {
        pipelineBuilder.configure {
            stage('Build') {
                job Steps.BUILD_AND_PUBLISH
            }
            stage('Testing') {
                job Steps.DEPLOY_TO_TEST
                job Steps.RUN_ACCEPTANCE_TEST
            }
            stage('Deploy to prod') {
                job Steps.DEPLOY_TO_PRODUCTION
            }

            chain(of(Steps.BUILD_AND_PUBLISH)
                    .then(
                        AutoLink.auto(Steps.DEPLOY_TO_TEST)
                            .withPredefinedProperties(['VERSION':'$GIT_COMMIT'])
                    )
                    .then(Steps.RUN_ACCEPTANCE_TEST)
                    .then(manual(Steps.DEPLOY_TO_PRODUCTION))
            )
        }
    }
}
