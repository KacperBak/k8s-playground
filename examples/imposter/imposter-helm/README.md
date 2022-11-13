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

Lint the Chart for error before deploy it on the cluster
```
helm lint imposter-mock
```

Render the template
```
helm template --debug . > test.yaml
```

Compare changes in large project files
```
helm template --debug . > testA.yaml
helm template --debug . > testB.yaml
diff testA.yaml testB.yaml
```

dry run  aka. render template with values using the name `my-imposter-mock`
```
helm install --dry-run --debug my-imposter-mock ./imposter-mock
```

install the chart in K8s using the name `my-imposter-mock`
```
helm install my-imposter-mock ./imposter-mock
```

upgrade, run install if not already done and also create namespace
```
helm upgrade --install --create-namespace --dry-run --debug imposter ./imposter-mock
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

## Optional: ConfigMap Auto Reload on helm `upgrade`
The mock data is defined in ConfigMap resources, each change needs the re-creation of ConfgMap resources and a re-deployment of the services.
[Fundamentally, there need to be multiple ConfigMap objects if we're going to have some pods referring to the new one and others referring to the old one(s)](https://github.com/kubernetes/kubernetes/issues/22368#issuecomment-203454883).
Brave approach using alpha state solution for this problem.

Reference GoPaddle Configurator
```
helm repo add gopaddle_configurator https://github.com/gopaddle-io/configurator/raw/v0.0.2/helm
```

Install GoPaddle Configurator
```
helm upgrade --install --create-namespace configurator gopaddle_configurator/configurator --version 0.4.0-alpha
```

This will also ensure re-deployments of the services when using `helm rollback` and the usage of the corresponding ConfigMaps, because the history of those is storred in CRD's:
```
kubectl get customresourcedefinitions                                                                               
NAME                                        CREATED AT
customconfigmaps.configurator.gopaddle.io   2022-10-26T06:16:10Z
customsecrets.configurator.gopaddle.io      2022-10-26T06:16:10Z
```

## Deployment

Deploy
```
helm upgrade --install --create-namespace imposter ./imposter-mock 
Release "imposter" does not exist. Installing it now.
NAME: imposter
LAST DEPLOYED: Sun Nov 13 08:33:31 2022
NAMESPACE: default
STATUS: deployed
REVISION: 1
TEST SUITE: None
```

Verfiy success
```
helm list
NAME            NAMESPACE       REVISION        UPDATED                                 STATUS          CHART                   APP VERSION
imposter        default         1               2022-11-13 08:33:31.968844 +0100 CET    deployed        imposter-mock-0.1.0     1.16.0
```

## Development

### Commands

Lint Helm Chart
```
helm lint ./imposter-mock
```

Optional: Dry-Run before deployment
```
helm upgrade --install --create-namespace --dry-run --debug imposter ./imposter-mock
```

Deploy
```
helm upgrade --install --create-namespace imposter ./imposter-mock 
NAME: imposter
LAST DEPLOYED: Sun Nov 13 08:33:31 2022
NAMESPACE: default
STATUS: deployed
REVISION: 1
TEST SUITE: None
```

Verfiy success
```
helm list
NAME            NAMESPACE       REVISION        UPDATED                                 STATUS          CHART                   APP VERSION
imposter        default         1               2022-11-13 08:33:31.968844 +0100 CET    deployed        imposter-mock-0.1.0     1.16.0
```

Uninstall
```
helm uninstall imposter
```

### K8s & Helm Setup
Each __file__ in the config folder/subfolder needs to be mounted in the mock container `/opt/imposter/config`.
For this two things are important:

#### 1. Configmap
The configmap needs a __Go__ template reference, where to find the `data` content. E.g.:
All files under `config/*` need to be referenced in the `templates/configmap.yaml` file.

```
config
├── named-example.groovy
├── openapi3-with-multiple-examples.yaml
├── scripted-config.json
└── tortoise.json
```

#### 2. Deployment
Each file under `config/*` needs to be referenced as `key` and `path` in `items` section to reference 

Deplyoment YAML
```
...
volumes:
  - name: imposter-mock-vol1
    configMap:
      name: imposter-mock-configmap
      items:
      - key: "named-example.groovy"
        path: "named-example.groovy"
      - key: "tortoise.json"
        path: "tortoise.json"
      - key: "scripted-config.json"
        path: "scripted-config.json"
      - key: "openapi3-with-multiple-examples.yaml"
        path: "openapi3-with-multiple-examples.yaml"
```

## Sources
## Sources
- [Imposter Mock](https://docs.imposter.sh/getting_started/)
- [K8s ConfigMaps](https://kubernetes.io/docs/concepts/configuration/configmap/)
- [Directory 2 Configmap](https://www.cloudytuts.com/tutorials/kubernetes/how-to-add-entire-directory-of-files-to-a-configmap/)
- [Configurator Plugin](https://github.com/gopaddle-io/configurator)
- [UTF-8 VS UTF-16 Helm file encoding](https://blog.kaniski.eu/2020/09/having-fun-with-helm-and-file-encoding/)
- [Operational aspects of using dependencies](https://helm.sh/docs/topics/charts/#operational-aspects-of-using-dependencies)
- [Hooks and the Release Lifecycle](https://helm.sh/docs/topics/charts_hooks/#hooks-and-the-release-lifecycle)
- [Go Template Language](https://pkg.go.dev/text/template)
- [Helm Version Support Policy](https://helm.sh/docs/topics/version_skew/)
- [K8s Service Types](https://kubernetes.io/docs/concepts/services-networking/service/#publishing-services-service-types)
- [Helm Doc's: Use files in ConfigMaps/Secrets](https://helm.sh/docs/chart_template_guide/accessing_files/#configmap-and-secrets-utility-functions)
- [Stackoverflow: Use files in ConfigMaps/Secrets](https://stackoverflow.com/questions/57219325/how-to-create-a-config-map-from-a-file-in-helm)