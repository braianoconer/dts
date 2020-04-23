# Dummy Translation Service - DTS

This project is intended to be used as a PoC for Observability principles. If you want to read more about 
the concept of observability in distributed systems see the References section below.

## How to run the project using docker-compose

Install [Docker](https://www.docker.com/) v19.0+ in your machine and simply run 

    docker-compose up -d
     
## How to recreate the docker images for each of the services   

If you have modified the code and want to recreate the images for a particular service, simply run the following command:

    DOCKER_BUILDKIT=1 docker build -f $SERVICE-Dockerfile -t dts/$service .
   
Where you need to fill the right details for each service. For example, to rebuild the image of the english service use:

    DOCKER_BUILDKIT=1 docker build -f English-Dockerfile -t dts/english .

You can also use the convenience ``buildAll.sh`` script to rebuild all the images


## How to run the project in Kubernetes

TBD

## References


* [The three pillar of Observability](https://www.oreilly.com/library/view/distributed-systems-observability/9781492033431/ch04.html)
  Distributed Systems Observability by Cindy Sridharan, O'Reilly
  
### Events logs
* [Elastic Stack](https://www.elastic.co/elastic-stack)


### Metrics
* [Prometheus IO](https://prometheus.io/) 

* [Micrometer.io](https://micrometer.io/docs)


### Distributed Tracing
[The Open Tracing project](https://opentracing.io/)

[Zipkin IO](https://zipkin.io/)

[Spring Cloud Sleuth](https://spring.io/projects/spring-cloud-sleuth)
