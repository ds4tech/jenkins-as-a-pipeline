package com.szymonhoma.pipelinedsl


class Steps {

    static SimpleJob BUILD_AND_PUBLISH = new SimpleJob("build-and-publish");
    static SimpleJob DEPLOY_TO_TEST = new SimpleJob("deploy-to-test");
    static SimpleJob RUN_ACCEPTANCE_TEST = new SimpleJob("run-acceptance-tests");
    static SimpleJob DEPLOY_TO_PRODUCTION = new SimpleJob("deploy-to-production");
}
