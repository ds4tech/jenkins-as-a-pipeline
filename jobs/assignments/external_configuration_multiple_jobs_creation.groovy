package assignments

import groovy.json.JsonSlurper

def parseMetadata(String ghRepository) {
    URL metadata = "https://raw.githubusercontent.com/${ghRepository}/master/.pipelineMetadata".toURL()
    return new JsonSlurper().parseText(metadata.text)
}

URL projectsApi = 'https://api.github.com/users/dev-trainings/repos'.toURL()
List projects = new JsonSlurper().parseText(projectsApi.text)

projects.each { project ->
    if (project.name.startsWith('jobdsl-')) {
        def metadata = parseMetadata(project.full_name)

        job(project.name - 'jobdsl-') {
            quietPeriod 1
            scm {
                git(project.git_url, 'master')
            }
            wrappers {
                timestamps()
            }
            if (metadata.cronTrigger) {
                triggers {
                    cron(metadata.cronTrigger)
                }
            }
            steps {
                maven 'install'
            }
        }
    }
}

/*
// import projects from github
import groovy.json.JsonSlurper

URL projectsApi = 'https://api.github.com/users/dev-trainings/repos'.toURL()
List projects = new JsonSlurper().parseText(projectsApi.text)

projects.each { project ->
    if (project.name.startsWith('jobdsl-')) {
        job(project.name - 'jobdsl-') {
            quietPeriod 1
            scm {
                git(project.git_url, 'master')
            }
            wrappers {
                timestamps()
            }
            triggers {
                cron('@daily')
            }
            steps {
                maven 'install'
            }
        }
    }
}
*/

/*
// manually specified projects
[
    [jobName: 'service-discovery', repoUrl: 'https://github.com/dev-trainings/jobdsl-service-discovery.git'],
    [jobName: 'config-server', repoUrl: 'https://github.com/dev-trainings/jobdsl-config-server.git']
].each { config ->
    job(config.jobName) {
        scm {
            git(config.repoUrl, 'master')
        }
        wrappers {
            timestamps()
        }
        triggers {
            cron('@daily')
        }
        steps {
            maven 'install'
        }
        configure {
            // mind that there is support for quietPeriod: quietPeriod 1
            (it / 'quietPeriod').value = 1
        }
    }
}
*/