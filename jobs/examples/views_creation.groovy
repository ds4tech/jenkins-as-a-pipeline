package examples

import javaposse.jobdsl.dsl.views.jobfilter.Status

listView('seed-jobs') {
    jobs {
        regex '.*-seed$'
    }
    columns {
        status()
        name()
        cronTrigger()
        lastSuccess()
        buildButton()
    }
}

listView('disabled-jobs') {
    jobFilters {
        status {
            status Status.DISABLED
        }
    }
    columns {
        name()
        lastSuccess()
    }
}
