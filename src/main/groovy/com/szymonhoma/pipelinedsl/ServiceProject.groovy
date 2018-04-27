package com.szymonhoma.pipelinedsl

import com.ofg.pipeline.core.Project
import groovy.transform.Canonical

@Canonical
class ServiceProject implements Project {

    String qualifiedName
}
