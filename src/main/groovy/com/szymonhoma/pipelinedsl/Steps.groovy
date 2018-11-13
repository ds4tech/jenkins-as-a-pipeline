package com.szymonhoma.pipelinedsl


class Steps {

    static BuildAndPublish BUILD_AND_PUBLISH = new BuildAndPublish("build-and-publish");
    static Deploy DEPLOY_TO_TEST = new Deploy("deploy-to-test", 'test');
    static SimpleJob RUN_ACCEPTANCE_TEST = new SimpleJob("run-acceptance-tests");
    static Deploy DEPLOY_TO_PRODUCTION = new Deploy("deploy-to-production", 'prod');
}
