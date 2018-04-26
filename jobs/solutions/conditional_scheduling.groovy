package solutions

job('env-dependant-scheduling') {
    triggers {
        if (isSchedulingEnabled()) {
            cron('@daily')
        }
    }
    steps {
        shell "echo Daily build scheduled"
    }
}

// your test environments are provisioned with `export JOB_SCHEDULING_DISABLED=true`
boolean isSchedulingEnabled() {
    !binding.variables.JOB_SCHEDULING_DISABLED
}