# README

## Using `hey` for load generation
In that case the imposter mock is exposed on port `52591`
````
hey -H 'accept: application/json' http://localhost:52591/pets/1
````

## Sources
- https://kubernetes.io/docs/tasks/access-application-cluster/web-ui-dashboard/
- https://github.com/rakyll/hey
- https://github.com/kubernetes/dashboard/issues/7337