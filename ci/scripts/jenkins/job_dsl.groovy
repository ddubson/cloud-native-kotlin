package jenkins

import javaposse.jobdsl.dsl.DslFactory

DslFactory factory = this

factory.job("simple-gradle-boot-build") {
    deliveryPipelineConfiguration("Build")

    triggers {
        githubPush()
    }

    scm { github("ddubson/cloud-native-kotlin") }

    wrappers {
        colorizeOutput()
    }

    steps {
        shell("./gradlew clean")
        shell("./gradlew publishContractStubs")
        shell("./gradlew build")
    }

    publishers {
        archiveJunit("**/build/test-results/test/TEST-*.xml")
        archiveArtifacts("build/libs/*.jar")
        downstreamParameterized {
            trigger("simple-gradle-boot-deploy") {
                triggerWithNoParameters()
            }
        }
    }
}

factory.job("simple-gradle-boot-deploy") {
    deliveryPipelineConfiguration("Deployment")
    scm { github("ddubson/cloud-native-kotlin") }
    steps {
        shell('echo "Deploying Artifact!"')
    }
}

factory.deliveryPipelineView("simple-gradle-boot-pipeline-view") {
    pipelines {
        component("Deployment", "simple-gradle-boot-build")
    }
    allowPipelineStart()
    showChangeLog()
    showDescription()
    showTotalBuildTime()
    allowRebuild()
}