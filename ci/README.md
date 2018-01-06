# Continuous Integration

## Jenkins 

## Pipeline Plugin

[Pipeline Plugin](https://jenkins.io/doc/book/pipeline/)

`scripts/pipeline.gdsl` defines the language for executing various functions in a pipeline 
(using the Pipeline Jenkins plugin)

## Job DSL Plugin

[Job DSL Plugin](https://github.com/jenkinsci/job-dsl-plugin)

`scripts/job_dsl.groovy` defines the Jenkins job layout 
(using the Job DSL Jenkins plugin)`

## Automating setup and deployment of Jenkins

### Using Docker

Jenkins comes in a prepackaged Docker image that can help automate the work of
setting up of a Jenkins instance as well as prepackaging pipelines.

To build and run the image, run:

```
> docker build . -t my-jenkins
> docker run -d -p 49002:8080 -v ~/jenkins_home:/var/jenkins_home -t my-jenkins
> docker logs -f [container-id]
```

To view in browser, go to `http://localhost:49002`

Run the seed job to spin up the preset jobs and Jenkins view

#### Details

`Dockerfile` fetches the `lts` image from Jenkins Docker repo, installs necessary
ruby and curl dependencies.

##### Plugins

Jenkins has the ability to pre-install plugins at start up, so we instruct Docker
to place the `plugins.txt` into a specific Jenkins directory where it can pick it up
and install them.

Please update `plugins.txt` with latest versions of plugins if outdated.

###### Seed Job

Seed Job groovy script (`seed_job.groovy`) defines the root job that will be created at start up time.
Seed Job defines all the other jobs, pipelines, and views to be built. It references
the `job_dsl.groovy` which is the blueprint for all the Jenkins entities to be created.

Docker copies the Seed Job script into `seed.groovy` script to be executed automatically at
runtime.

###### Init Groovy Script

Init groovy script is the arbiter of the whole of the Jenkins environment.

What it does is:
- Tell Jenkins where to find the Seed Job
- Tell Jenkins to load some initial user credentials
- Tell Jenkins to load the default JDK
- Tell Jenkins to allow macro processing