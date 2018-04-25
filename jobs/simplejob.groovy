import javaposse.jobdsl.dsl.DslFactory

DslFactory dsl = this as DslFactory

dsl.job('a-very-simple-job') {

    steps {
        shell "echo Hello World!"
        shell "sleep 5"
    }
}
