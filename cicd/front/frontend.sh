command=$(kubectl get pods -l type=frontend)
color="blue" 
flag=true

for line in $(kubectl get deployment -l type="frontend" | awk 'NR>1'); do
    flag=false
    if [[ "$line" != *green* ]]; then
        sed -i 's/color/green/g' /home/test/cicd/front/frontend-deployment.yaml
        color="green"
    else
        sed -i 's/color/blue/g' /home/test/cicd/front/frontend-deployment.yaml
    fi
    kubectl apply -f /home/test/cicd/front/frontend-deployment.yaml
    break
done

if [[ $flag == true ]]; then
    sed -i 's/color/blue/g' /home/test/cicd/front/frontend-deployment.yaml
    kubectl apply -f /home/test/cicd/front/frontend-deployment.yaml
fi

echo $color
result=true

for i in {1..10}; do
    kubectl rollout status deployment -l type=frontend | grep $color
    rollout_status=$?

    if [[ $rollout_status -eq 0 ]]; then
        echo "Deployment is complete and all pods are ready and running."
        result=true
        break 
    else
        echo "Waiting for deployment to complete..."
        result=false
        sleep 10 
    fi
done

if [[ "$result" == false ]]; then
    echo "Deployment did not complete successfully. Deleting deployment..."
    kubectl delete deployment -l deployment=$color
    exit 1
fi

if [[ "$color" != "green" ]]; then
    sed -i 's/green/blue/g' /home/test/cicd/front/frontend-service.yaml
    kubectl apply -f /home/test/cicd/front/frontend-service.yaml
    kubectl delete deployment -l deployment=green,type=frontend
else
    sed -i 's/blue/green/g' /home/test/cicd/front/frontend-service.yaml
    kubectl apply -f /home/test/cicd/front/frontend-service.yaml
    kubectl delete deployment -l deployment=blue,type=frontend
fi