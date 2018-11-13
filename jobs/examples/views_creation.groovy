package examples

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.views.jobfilter.Status

DslFactory dsl = this as DslFactory

dsl.listView('seed-jobs') {
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

dsl.listView('disabled-jobs') {
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
