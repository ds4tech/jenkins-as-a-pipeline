package examples

import javaposse.jobdsl.dsl.DslFactory

DslFactory factory = this as DslFactory

factory.job('github-projects-seed') {
    steps {
        dsl '''
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
            '''
    }
}

factory.job('bitbucket-projects-seed') {
    scm {
        git('https://github.com/dev-trainings/jenkins-jobdsl-scripts.git', 'master')
    }
    steps {
        dsl(['bitbucket_private_job.groovy'])
    }
}

queue('github-projects-seed')
queue('bitbucket-projects-seed')
