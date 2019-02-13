import com.google.common.base.Predicate
import hudson.model.Job
import hudson.model.Run
import jenkins.model.Jenkins

PrintStream printStream = manager.listener.logger

def localEnv = manager.build.getEnvironment(manager.listener)

String versionVariable = 'VERSION'
String currentVersion = localEnv.get(versionVariable)
printStream.println("current version: $currentVersion")

def n = 0
String jobName = "03-0$n-long-running-job"

Job searchedJob = Jenkins.instance.getItem(jobName) as Job
Run searchedJobRun = searchedJob.getBuilds().filter({ Run run ->
    String version = run.getEnvironment(manager.listener).get(versionVariable)
    return currentVersion.equals(version)
} as Predicate<Run>)
        .getLastBuild()


if (searchedJobRun == null) {
    printStream.println("Can't find searched job with VERSION $currentVersion")
    manager.buildFailure()
    return
}

printStream.println("detected a job execution $searchedJobRun")

if (searchedJobRun.isBuilding()) {
    printStream.println("Searched job is still running")
    manager.buildUnstable()
    return
}

if (searchedJobRun.result.isBetterOrEqualTo(Result.SUCCESS)) {
    printStream.println("serached job execution has succeeded")
    manager.buildSuccess()
} else {
    printStream.println("serched job execution has failed")
    manager.buildFailure()
}