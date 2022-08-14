# simple-nginx-deployment

## Abstractions
`Deployment` -> `Replicaset` -> `Pod` -> `1..n Container`

## Deploy a pod: nginx
````
k create deployment nginx-depl --image=nginx 
````

## Verfiy deployment
````
k get deployment -o=wide
NAME         READY   UP-TO-DATE   AVAILABLE   AGE   CONTAINERS   IMAGES   SELECTOR
nginx-depl   1/1     1            1           10m   nginx        nginx    app=nginx-depl
````

## Verfiy replicaset
````
k get replicaset -o=wide
NAME                   DESIRED   CURRENT   READY   AGE   CONTAINERS   IMAGES   SELECTOR
nginx-depl-58458fcbd   1         1         1       13m   nginx        nginx    app=nginx-depl,pod-template-hash=58458fcbd
````

## Verfiy pod
````
k get pod -o=wide
NAME                         READY   STATUS    RESTARTS   AGE   IP           NODE       NOMINATED NODE   READINESS GATES
nginx-depl-58458fcbd-76n8c   1/1     Running   0          16m   172.17.0.5   minikube   <none>           <none>
````

## Describe a pod
````
k describe pod nginx-depl-84fd46788f-rx5fz
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

## View logs of a pod
````
k logs nginx-depl-84fd46788f-rx5fz
/docker-entrypoint.sh: /docker-entrypoint.d/ is not empty, will attempt to perform configuration
/docker-entrypoint.sh: Looking for shell scripts in /docker-entrypoint.d/
/docker-entrypoint.sh: Launching /docker-entrypoint.d/10-listen-on-ipv6-by-default.sh
10-listen-on-ipv6-by-default.sh: info: Getting the checksum of /etc/nginx/conf.d/default.conf
10-listen-on-ipv6-by-default.sh: info: Enabled listen on IPv6 in /etc/nginx/conf.d/default.conf
/docker-entrypoint.sh: Launching /docker-entrypoint.d/20-envsubst-on-templates.sh
/docker-entrypoint.sh: Launching /docker-entrypoint.d/30-tune-worker-processes.sh
/docker-entrypoint.sh: Configuration complete; ready for start up
2022/07/31 09:05:12 [notice] 1#1: using the "epoll" event method
2022/07/31 09:05:12 [notice] 1#1: nginx/1.22.0
2022/07/31 09:05:12 [notice] 1#1: built by gcc 10.2.1 20210110 (Debian 10.2.1-6)
2022/07/31 09:05:12 [notice] 1#1: OS: Linux 5.10.57
2022/07/31 09:05:12 [notice] 1#1: getrlimit(RLIMIT_NOFILE): 1048576:1048576
2022/07/31 09:05:12 [notice] 1#1: start worker processes
2022/07/31 09:05:12 [notice] 1#1: start worker process 31
2022/07/31 09:05:12 [notice] 1#1: start worker process 32
````

## Get a interactive shell inside a pod
````
k exec -it nginx-depl-84fd46788f-rx5fz -- bin/bash
````

## Delete a deployment
````
k delete deployment nginx-depl
````