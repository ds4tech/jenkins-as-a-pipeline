package examples

import com.ofg.pipeline.core.JenkinsVariables
import com.ofg.pipeline.core.PipelineTemplateBuilder
import com.szymonhoma.pipelinedsl.ServicePipelineTemplate
import com.szymonhoma.pipelinedsl.ServiceProject
import com.szymonhoma.pipelinedsl.view.ServiceViewBuilder
import javaposse.jobdsl.dsl.DslFactory

List<ServiceProject> projects = [
        new ServiceProject('first-project'),
        new ServiceProject('second-project'),
        new ServiceProject('third-project'),
]

projects.each {
        new PipelineTemplateBuilder(this as DslFactory, JenkinsVariables.from(this))
                .build(ServicePipelineTemplate.INSTANCE, it)
}
new ServiceViewBuilder(this as DslFactory).build(projects)
