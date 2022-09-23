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

Start cluster with Docker
````
minikube start --vm-driver=docker
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
For command simplification a simple alias `k` is used fur `kubectl`.

### commands

Get kubectl version
````
kubectl version
````

Get nodes
````
kubectl get nodes
````

Get services
````
kubectl get services
````

Get all pods
````
kubectl get pods
````

Get all components from all namespaces
```` 
kubectl get all -A
````

Get pod with additional output
````
kubectl get pod -o wide
NAME                         READY   STATUS    RESTARTS   AGE   IP           NODE       NOMINATED NODE   READINESS GATES
nginx-depl-58458fcbd-76n8c   1/1     Running   0          16m   172.17.0.5   minikube   <none>           <none>
````

Describe a specific pod like `mongodb-service`
````
kubectl describe pod mongodb-service
Name:         nginx-depl-84fd46788f-rx5fz
Namespace:    default
Priority:     0
Node:         minikube/192.168.235.3
Start Time:   Sun, 31 Jul 2022 11:04:56 +0200
Labels:       app=nginx-depl
              pod-template-hash=84fd46788f
Annotations:  <none>
Status:       Running
IP:           172.17.0.6
IPs:
  IP:           172.17.0.6
Controlled By:  ReplicaSet/nginx-depl-84fd46788f
Containers:
  nginx:
    Container ID:   docker://4ef1373cd105f65dec71f9217e1e33325943f3611986bd521b22438a6b79f65c
    Image:          nginx:1.22.0
    Image ID:       docker-pullable://nginx@sha256:685c35d9259ce180b5f0116a2989c45a9d56e8dc404cab56717d6338c0dc4cce
    Port:           <none>
    Host Port:      <none>
    State:          Running
      Started:      Sun, 31 Jul 2022 11:05:12 +0200
    Ready:          True
    Restart Count:  0
    Environment:    <none>
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from kube-api-access-p2lzb (ro)
Conditions:
  Type              Status
  Initialized       True
  Ready             True
  ContainersReady   True
  PodScheduled      True
Volumes:
  kube-api-access-p2lzb:
    Type:                    Projected (a volume that contains injected data from multiple sources)
    TokenExpirationSeconds:  3607
    ConfigMapName:           kube-root-ca.crt
    ConfigMapOptional:       <nil>
    DownwardAPI:             true
QoS Class:                   BestEffort
Node-Selectors:              <none>
Tolerations:                 node.kubernetes.io/not-ready:NoExecute op=Exists for 300s
                             node.kubernetes.io/unreachable:NoExecute op=Exists for 300s
Events:
  Type    Reason     Age    From               Message
  ----    ------     ----   ----               -------
  Normal  Scheduled  3m46s  default-scheduler  Successfully assigned default/nginx-depl-84fd46788f-rx5fz to minikube
  Normal  Pulling    3m45s  kubelet            Pulling image "nginx:1.22.0"
  Normal  Pulled     3m30s  kubelet            Successfully pulled image "nginx:1.22.0" in 14.784654893s
  Normal  Created    3m30s  kubelet            Created container nginx
  Normal  Started    3m30s  kubelet            Started container nginx
````


IMPORTANT: See content in `deployment` folder for further details

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