package solutions

job('parametrized-hello') {
    properties {
        rebuild {
            autoRebuild()
        }
    }
    parameters {
        stringParam 'name'
    }
    steps {
        shell 'echo ${name}!'
    }
}
