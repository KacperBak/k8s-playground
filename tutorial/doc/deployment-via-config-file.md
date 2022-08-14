# simple-cli-deployment

## Questions
- Q: Where do i get the basic template for a deployment?
- A: [k8s docs workload resources: Deployment](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/)

## Create a deployment via config
````
cd tutorial/deployment
k apply -f nginx-deployment.yaml
````

## Update a deployment via config
Edit file `nginx-deployment.yaml` and set replicas to `2` , than re-apply
````
k apply -f nginx-deployment.yaml
````

## Delete a deployment via config
````
k delete -f nginx-deployment.yaml
````
