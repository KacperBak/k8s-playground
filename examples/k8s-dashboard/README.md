# README

## Create a token to access web UI
https://kubernetes.io/docs/tasks/access-application-cluster/web-ui-dashboard/

## Using kubectl to get CPU/MEM usage

Get pod name
```
kubectl get pods
NAME                                        READY   STATUS    RESTARTS      AGE
imposter-mock-deployment-7df9ff5587-zsjgp   1/1     Running   1 (72m ago)   3h41m
```

Recurring calling `top` via kubectl
```
kubectl top pod imposter-mock-deployment-7df9ff5587-zsjgp
NAME                                        CPU(cores)   MEMORY(bytes)
imposter-mock-deployment-7df9ff5587-zsjgp   6m           239Mi
imposter-mock-deployment-7df9ff5587-zsjgp   50m          239Mi
imposter-mock-deployment-7df9ff5587-zsjgp   225m         241Mi
imposter-mock-deployment-7df9ff5587-zsjgp   6m           241Mi
```

## Using `hey` for load generation
In that case the imposter mock is exposed on port `52591`
````
hey -n 2000 -H 'accept: application/json' http://localhost:52591/pets/1

Summary:
  Total:	5.6514 secs
  Slowest:	3.0309 secs
  Fastest:	0.0051 secs
  Average:	0.2629 secs
  Requests/sec:	176.9485

  Total data:	26000 bytes
  Size/request:	26 bytes

Response time histogram:
  0.005 [1]	|
  0.308 [936]	|■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
  0.610 [13]	|■
  0.913 [0]	|
  1.215 [0]	|
  1.518 [0]	|
  1.821 [5]	|
  2.123 [6]	|
  2.426 [7]	|
  2.728 [15]	|■
  3.031 [17]	|■


Latency distribution:
  10% in 0.0705 secs
  25% in 0.1065 secs
  50% in 0.1490 secs
  75% in 0.1932 secs
  90% in 0.2454 secs
  95% in 1.5310 secs
  99% in 2.7877 secs

Details (average, fastest, slowest):
  DNS+dialup:	0.0001 secs, 0.0051 secs, 3.0309 secs
  DNS-lookup:	0.0000 secs, 0.0000 secs, 0.0016 secs
  req write:	0.0000 secs, 0.0000 secs, 0.0003 secs
  resp wait:	0.2627 secs, 0.0050 secs, 3.0283 secs
  resp read:	0.0000 secs, 0.0000 secs, 0.0003 secs

Status code distribution:
  [200]	1000 responses
````

## TODO
- Use a helm chart to deploy the board [Details](https://artifacthub.io/packages/helm/k8s-dashboard/kubernetes-dashboard).

## Sources
- https://kubernetes.io/docs/tasks/access-application-cluster/web-ui-dashboard/
- https://github.com/rakyll/hey
- https://github.com/kubernetes/dashboard/issues/7337