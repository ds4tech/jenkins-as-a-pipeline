# Jobdsl exercises

 structure:
 - assignments: here are the consecutive tasks
 - examples: few examples of usefull code
 - jobs/assignments: here is where the jobdsl scripts with solutions should reside (they will be build automatically by master-seed job)
 - jobs/examples: that's the place where one can look when one feels lost.

# Running Jenkins

 - a one-command local jenkins setup: `./gradlew -b prepare_jenkins.gradle` (creates a `./runJenkins.{sh, bat}` script)
 (taken from https://github.com/4finance/sample-jenkins-microservice-pipeline)

# References

### Job-DSL

[Job-DSL wiki](https://github.com/jenkinsci/job-dsl-plugin/wiki)

[API Viewer](https://jenkinsci.github.io/job-dsl-plugin/)

[DSL Playground application](https://job-dsl.herokuapp.com/)

[Discussion group](https://groups.google.com/forum/#!forum/job-dsl-plugin)

### Groovy

[Groovy documentation](http://www.groovy-lang.org/documentation.html)

[Creating XML documents](http://www.groovy-lang.org/processing-xml.html#_creating_xml)

[Working with JSON documents](http://www.groovy-lang.org/json.html)

[Groovy for Java developers](http://www.groovy-lang.org/differences.html)
 
### Jenkins-Docker
[Jenkins Docker main repository](https://github.com/jenkinsci/docker)

[Jenkins Docker example](https://github.com/s2lomon/docker-jenkins)
