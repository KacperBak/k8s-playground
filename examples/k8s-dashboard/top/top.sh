#!/bin/bash

POD=$1
kubectl get pod "$POD" 2>&1 1>/dev/null
POD_EXISTS=$?

if [ "$POD_EXISTS" -eq 0 ]; then
    echo "Starting 'top' for pod '$POD'."
    while 'true' 
    do
      sleep 1
      kubectl top pod "$POD" --no-headers
    done
else
    echo "ERROR: Unable to fetch pod '$POD'."
    exit "$POD_EXISTS"
fi