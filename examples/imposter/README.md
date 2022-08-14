# Imposter Mock
This example uses the [Imposter Mock Server](https://www.imposter.sh/) to demonstrate a minimal setup of a file based K8s deployment. A detailed imposter example is tracked in [imposter-playground](https://github.com/KacperBak/imposter-playground). This one is focusing on a simple K8s deployment use case.

## Mock Content
The mock itself uses a very simple openapi sample from the [Imposter Repo](https://github.com/outofcoffee/imposter/tree/main/examples/openapi/scripted-named-example-js).

Config Content
```
config/
├── named-example.groovy
├── openapi3-with-multiple-examples.yaml
├── scripted-config.json
└── tortoise.json
```

## Create a K8s ConfifMap based on the `config` directory content

Change dir:
```
cd examples/imposter
```

Get specific help
```
kubectl create configmap --help
```

- `from-file=[]`:
    Key file can be specified using its file path, in which case file basename will be used as configmap key, or
    optionally with a key and file path, in which case the given key will be used.  _Specifying a directory will
    iterate each named file in the directory whose basename is a valid configmap key_.

Create configmap
```
kubectl create configmap imposter-configmap --from-file=config
```

This generates the `imposter-configmap` resource file in the cluster.
```
kubectl get configmaps
NAME                 DATA   AGE
imposter-configmap   4      10m
```

Writes all files from the `from-file=/config` directory into the file `imposter-configmap.yaml` as `data` entries into the ConfigMap.
```
kubectl get configmap imposter-configmap -o yaml > imposter-configmap.yaml
```

Resulting ConfigMap
```
apiVersion: v1
data:
  named-example.groovy: |
  ...
  openapi3-with-multiple-examples.yaml: |
  ...
  scripted-config.json: |
  ...
  tortoise.json: '{ "id": 3, "name": "Tortoise" }'
```

Delete not important meta data and adjust the `name` and `namespace` if necessary in 
```
apiVersion: v1
kind: ConfigMap
metadata:
  name: config-files
  namespace: default
data:
  named-example.groovy:
    <generated-content-here>
```

## Reference data entries
Each file in the config folder needs to be mounted in the mock container using the path: `/opt/imposter/config`.
Use the `configMap` reference for this and list all files as `key`/`path` items.

imposter-deployment.yaml
```
...
containers:
  - name: imposter-openapi-petstore
    image: registry.docker.com/outofcoffee/imposter-openapi:3.0.4
    imagePullPolicy: Always
    ports:
      - containerPort: 8080
    volumeMounts:
      - name: imposter-config      
        mountPath: /opt/imposter/config
    resources:
      requests:
        memory: "128M"
        cpu: "500m"
      limits:
        memory: "256M"
        cpu: "1000m"
volumes:
  - name: imposter-config
    configMap:
      name: imposter-configmap
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

## Deployment Steps
Since the deployment is depending on the ConfigMap this K8s resource needs to exist first.

```
k apply -f imposter-configmap.yaml
k apply -f imposter-service.yaml
k apply -f imposter-deployment.yaml
```


