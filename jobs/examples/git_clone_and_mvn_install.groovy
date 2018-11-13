package examples

import javaposse.jobdsl.dsl.DslFactory

DslFactory dsl = this as DslFactory

dsl.job('service-discovery') {
    scm {
        git('https://github.com/dev-trainings/jobdsl-service-discovery.git', 'master')
    }
    steps {
        maven 'install'
    }
}

/*
// without convenience-shortcuts methods
job('service-discovery') {
    scm {
        git {
            branch 'master'
            remote {
                url 'https://github.com/dev-trainings/jobdsl-service-discovery.git'
            }
        }
    }
    steps {
        maven {
            goals 'install'
        }
    }
}
*/

/*
// using github-specific method
job('service-discovery') {
    scm {
        git {
            branch 'master'
            remote {
                github 'dev-trainings/jobdsl-service-discovery'
            }
        }
    }
    steps {
        maven {
            goals 'install'
        }
    }
}
*/

/*
// using git protocol instead of https
job('service-discovery') {
    scm {
        git {
            branch 'master'
            remote {
                github 'dev-trainings/jobdsl-service-discovery', 'git'
            }
        }
    }
    steps {
        maven {
            goals 'install'
        }
    }
}
*/