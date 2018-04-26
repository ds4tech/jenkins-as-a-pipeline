package examples

job('parametrized-hello') {
    parameters {
        stringParam 'name'
    }
    steps {
        shell 'echo ${name}!'
    }
}
