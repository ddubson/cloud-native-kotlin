package jenkins

import javaposse.jobdsl.dsl.DslFactory

DslFactory dslFactory = this

dslFactory.job("seed-job") {
    triggers { githubPush() }
    scm { github("ddubson/cloud-native-kotlin") }
    steps {
        dsl {
            external("ci/scripts/jenkins/job_dsl.groovy")
            removeAction("DISABLE")
            removeViewAction("DELETE")
            ignoreExisting(false)
        }
    }
}