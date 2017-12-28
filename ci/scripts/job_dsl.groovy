import javaposse.jobdsl.dsl.DslFactory

DslFactory factory = this

factory.job("job-dsl-simple-job") {
    steps {
        shell('echo "hello world"')
    }
}