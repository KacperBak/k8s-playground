# imposter mock as Helm chart

## Notes

Helm version needs to be compatible with used cluster verion.
```
3.9.x	1.24.x - 1.21.x
... 
```
More details - [Helm Version Support Policy](https://helm.sh/docs/topics/version_skew/).

## lifecycle commands

create a Helm chart
```
helm create imposter-mock
```

dry run  aka. render template with values using the name `my-imposter-mock`
```
helm install --dry-run --debug my-imposter-mock ./imposter-mock
```

install the chart in K8s using the name `my-imposter-mock`
```
helm install my-imposter-mock ./imposter-mock
```

uninstall the chart in K8s using the name `my-imposter-mock`
```
helm uninstall my-imposter-mock ./imposter-mock
```

install the chart in K8s using override of `service.type` by exposing the Service on each Node's IP at a static port
```
helm install my-imposter-mock ./imposter-mock --set service.type=NodePort
```

use the `status` values from `NOTES.txt` to get access to `NODE_IP` and `NODE_PORT` values.
```
helm status my-imposter-mock
NAME: my-imposter-mock
LAST DEPLOYED: Sun Sep 25 14:17:31 2022
NAMESPACE: default
STATUS: deployed
REVISION: 1
NOTES:
1. Get the application URL by running these commands:
  export POD_NAME=$(kubectl get pods --namespace default -l "app.kubernetes.io/name=imposter-mock,app.kubernetes.io/instance=my-imposter-mock" -o jsonpath="{.items[0].metadata.name}")
  export CONTAINER_PORT=$(kubectl get pod --namespace default $POD_NAME -o jsonpath="{.spec.containers[0].ports[0].containerPort}")
  echo "Visit http://127.0.0.1:8080 to use your application"
  kubectl --namespace default port-forward $POD_NAME 8080:$CONTAINER_PORT
```

update dependencies in `charts` folder
```
helm dependency update
```

## repo commands

list all local repos
```
helm repo list
```

search for a chart
```
healm search
```

## Sources
- [Operational aspects of using dependencies](https://helm.sh/docs/topics/charts/#operational-aspects-of-using-dependencies)
- [Hooks and the Release Lifecycle](https://helm.sh/docs/topics/charts_hooks/#hooks-and-the-release-lifecycle)
- [Go Template Language](https://pkg.go.dev/text/template)
- [Helm Version Support Policy](https://helm.sh/docs/topics/version_skew/)
- [K8s Service Types](https://kubernetes.io/docs/concepts/services-networking/service/#publishing-services-service-types)