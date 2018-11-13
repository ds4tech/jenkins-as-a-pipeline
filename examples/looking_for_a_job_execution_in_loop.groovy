import Predicate
import Job
import Run
import jenkins.model.*

PrintStream printStream = manager.listener.logger

def localEnv = manager.build.getEnvironment(manager.listener)

String versionVariable = 'VERSION'
String currentVersion = localEnv.get(versionVariable)
printStream.println("current version: $currentVersion")

Run searchedJobRun
while (true) {
    Job searchedJob = Jenkins.instance.getItem('put-your-job-name-here') as Job

    searchedJobRun = searchedJob.getBuilds().filter({ Run run ->
        String version = run.getEnvironment(manager.listener).get(versionVariable)
        return currentVersion.equals(version)
    } as Predicate<Run>)
            .getLastBuild()

    if (searchedJobRun != null) {
        printStream.println("found the searched job and it's status is ${searchedJobRun.result}")
    } else {
        printStream.println("couldn't find the job")
    }

    if (searchedJobRun != null && !searchedJobRun.isBuilding()) {
        break
    }
    Thread.sleep(1000)
}

printStream.println("found the searched job and it's status is ${searchedJobRun.result}")
