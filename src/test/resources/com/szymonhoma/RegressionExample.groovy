package com.szymonhoma

import com.szymonhoma.simplestep.AcceptanceTest
import com.szymonhoma.simplestep.BuildGradle
import com.szymonhoma.simplestep.DeployToEnv
import javaposse.jobdsl.dsl.DslFactory

DslFactory dsl = this as DslFactory

def url = 'https://github.com/dev-trainings/simple-gradle-project.git'

new BuildGradle('simple-gradle-project', url)
        .next(new DeployToEnv('foo-env'))
        .next(new AcceptanceTest('url'))
        .next(new DeployToEnv('bar-env'))
        .execute(dsl)