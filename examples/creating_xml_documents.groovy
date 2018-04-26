import groovy.xml.MarkupBuilder

def writer = new StringWriter()
def xml = new MarkupBuilder(writer)

xml.project {
    builders {
        'hudson.tasks.Shell' {
            command 'echo Hello Sollers!'
        }
    }
}

println writer.toString()
