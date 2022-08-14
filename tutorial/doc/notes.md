# notes

## minikube

### setup
install the following via brew:
- hyperkit
- minikube
- kubectl

### commands
Start cluster with hyperkit VM
````
minikube start --vm-driver=hyperkit
````

Get minikube status
````
minikube status
````

Stop minikube
````
minikube stop
````

## kubectl
### commands

Get kubectl version
````
kubectl version
````

Get all components and filter for `mongo`
````
kubectl get all | grep mongo
````

Get nodes
````
kubectl get nodes
````

Get services
````
kubectl get services
````

Describe a specific service like `mongodb-service`
````
kubectl describe service mongodb-service
````

Get pod with additional output
````
kubectl get pod -o wide
````

Describe a specific pod like `mongodb-service`
````
kubectl describe pod mongodb-service
````


#### Deploy a pod
````
kubectl create deployment NAME --image=image
````

## Issues
````
This VM is having trouble accessing https://k8s.gcr.io
````
Possible solution: Install Docker and use minikube with latest version
````
minikube start --driver=docker
````

Make docker the default driver
````
minikube config set driver docker
````

## Sources
- https://youtu.be/X48VuDVv0do
- https://gitlab.com/nanuchi/youtube-tutorial-series/-/blob/master/basic-kubectl-commands/cli-commands.md
- https://github.com/kubernetes/minikube/issues/7231
- https://minikube.sigs.k8s.io/docs/drivers/docker/