#!/bin/bash
color="blue"  # 기본값을 blue로 설정
flag=true
# 첫 번째 for 루프: pods를 순회하며 색상 변경 및 배포 적용
for line in $(kubectl get deployment -l type=backend | awk 'NR>1'); do
    flag=false
    if [[ "$line" != *green* ]]; then
        sed -i 's/color/green/g' /home/test/cicd/back/backend-deployment.yaml
        color="green"
    else
        sed -i 's/color/blue/g' /home/test/cicd/back/backend-deployment.yaml
    fi
    kubectl apply -f /home/test/cicd/back/backend-deployment.yaml
    break  # 첫 번째 pod를 처리한 후 루프를 종료합니다.
done
if [[ $flag == true ]]; then
    sed -i 's/color/blue/g' /home/test/cicd/back/backend-deployment.yaml
    kubectl apply -f /home/test/cicd/back/backend-deployment.yaml
fi
echo $color
# 두 번째 for 루프: Pod 상태 확인 (5회 반복)
result=true
for i in {1..20}; do

    kubectl rollout status deployment/be-$color-deployment
    rollout_status=$?
    echo $rollout_status
    if [[ $rollout_status -eq 0 ]]; then
        echo "Deployment is complete and all pods are ready and running."
        result=true
        break  # 모든 파드가 준비되면 for 루프 종료
    else
        echo "Waiting for deployment to complete..."
        result=false
        sleep 10  # 다음 시도 전에 대기
    fi

done

if [[ "$result" == "false" ]]; then
    kubectl delete deployment  -l type=backend,deployment="$color"
    exit 1
fi

# 서비스 업데이트 및 이전 배포 삭제
if [[ "$color" != "green" ]]; then
    sed -i 's/color/blue/g' /home/test/cicd/back/backend-service.yaml
    sed -i 's/color/blue/g' /home/test/cicd/back/backend-oauth-service.yaml
    kubectl apply -f /home/test/cicd/back/backend-service.yaml
    kubectl apply -f /home/test/cicd/back/backend-oauth-service.yaml
    kubectl delete deployment -l deployment=green,type=backend
else
    sed -i 's/color/green/g' /home/test/cicd/back/backend-service.yaml
    sed -i 's/color/green/g' /home/test/cicd/back/backend-oauth-service.yaml
    kubectl apply -f /home/test/cicd/back/backend-service.yaml
    kubectl apply -f /home/test/cicd/back/backend-oauth-service.yaml
    kubectl delete deployment -l deployment=blue,type=backend
fi