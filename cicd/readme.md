<br>
  
<h1 align="center"> 지식 관리 시스템   Enadu </h1>
<br>
<div align="center">
<img src="https://github.com/user-attachments/assets/3f6df515-9e11-4b63-b4e1-c35d22176721" style="width: 60%;">
</div>
<br>

### 목차
- [CI/CD 의 기대효과](#-cicd-의-기대효과)
- [Enadu 운영 환경](#-운영-환경)
- [배포 방식](#-bluegreen-배포-방식-사용-이유)
- [모니터링 방식](#-모니터링-시스템)
- [CI/CD 배포 시나리오](#-enadu-backend-cicd-시나리오)
- [CI/CD 테스트 및 결과](#-cicd-테스트-및-결과)
<br><br>



## 🛠 기술 스택
&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://img.shields.io/badge/kubernates-326CE5?style=for-the-badge&logo=Kubernates&logoColor=white" style="border-radius: 5px;">
&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" style="border-radius: 5px;">
&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://img.shields.io/badge/discord-326CE5?style=for-the-badge&logo=discord&logoColor=white" style="border-radius: 5px;">
&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://img.shields.io/badge/jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white" style="border-radius: 5px;">
&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white" style="border-radius: 5px;">
&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white" style="border-radius: 5px;">
<br><br>


## 🔎 CI/CD 의 기대효과 
전통적인 배포 파이프라인은 코드 변경 사항을 커밋-> 코드 통합 -> 빌드 -> 배포 준비 및 배포 -> 모니터링 방식이었다.

이렇듯 전통적인 배포 파이프라인은 수동 작업이 많고, 통합의 어려움, 개발자의 오류 리스크를 증가 시켰다.

현대의 CI/CD는 위와 같은 문제점들을 보완함과 동시에 더 빠르고 안정적인 소프트웨어 배포가 가능하다.

배포 프로세스의 대부분의 과정을 자동화하여 번거로운 배포 과정 간소화가 된다.

이를 통해 배포에 대한 부담감이 줄어들고, 기존에 수동으로 이루어지던 배포 프로세스를 자동화하여 개발자의 실수로 인한 에러를 방지할 수 있다.
<br><br><br><br>

## 🌌 운영 환경
#### - 클러스터 노드 구성
💻 Master 1대<br>
💻 Worker 4대의 클러스터 구성 (Galera Cluster)

> Calico CNI를 활용한 클러스터 내부 pod들의 통신망 구성
<br>


### 쿠버네티스 선택 이유  ⚙ 
#### 1. Automated Deployment
    
 애플리케이션의 자동 배포, 업데이트, 롤백을 지원하므로, 점진적 업데이트를 통해 무중단 배포 수행 가능하다
    
#### 2. Scaling and Autoscaling
    
 쿠버네티스는 클러스터 내의 리소스를 효율적으로 관리하고, 자동으로 Pod의 수를 조절할 수 있는 오토스케일링 기능을 제공하므로, 트래픽 변화에 동적으로 대응 가능하다.
    
#### 3. Service  and Load Balancing
    
 애플리케이션의 컴포넌트 간의 통신을 쉽게 설정하고 관리할 수 있다.

<br>


### 젠킨스 선택 이유 ⚙ 
#### 1. CI/CD
    
 코드 변경 사항을 자동으로 빌드하고 테스트하는 CI/CD 파이프라인을 쉽게 구성할 수 있는 기능을 제공하고, 배포를 자동화하여 개발 주기를 단축시킨다
    
#### 2. Flexibility
    
 다양한 빌드 도구와 배포 시스템을 지원하며, 파이프라인을 코드로 정의할 수 있어, 복잡한 배포 프로세스를 자동화하고 버전 관리를 할 수 있다

<br><br>


### 📁시스템 아키텍처
  
![시스템아키텍처 drawio](https://github.com/user-attachments/assets/d8be0030-19cc-432c-a73d-b89e5b1648a8)


<br>
<br>



## 📘📗 Blue/Green 배포 방식 사용 이유

우리팀은 다운 타임이 없는 무중단 배포 방식 중 Blue/Green 배포 방식을 적용하였다.
- 블루그린 배포 방식은 지속적 배포 방식 중 하나로 신 버전을 배포가 완료되면 구 버전을 바라보던 서비스가 신 버전으로 일제히 전환하도록 하는 방식이다.
- 신속한 업데이트와 동시에 서버 안정성이 좋기 때문에 이 방식을 선택했다.
<br><br><br>

## 💻 모니터링 시스템
[Prometheus와 Grafana를 이용한 모니터링 시스템 구축]

Prometheus는 k8s 클러스터 내부의 각 pod의 데이터를 수집하여 
Grafana를 통해 CPU 및 메모리 사용량, 네트워크 트래픽 등을 시각화하여 클러스터의 상태를 실시간으로 확인 가능하다.
<br><br><br> 

## 🎞 Enadu Backend CI/CD 시나리오


#### 1. github에 be-dev 최신 버전 프로젝트를 머지

#### 2. github는 젠킨스에게 webhook을 통해서 젠킨스에게 이벤트 전달

#### 3. 젠킨스는 파이프라인에 저장된 절차 실행
- a. 젠킨스는 github에서 최신 코드를 clone한다.
- b. backend 혹은 common(repository 관련 모듈) 모듈의 변화를 인식 
- c. 단위 테스트 실행 후 전체 테스트 성공 시 
- d. 클론된 코드를 기반으로 빌드 작업 수행
- e. 빌드를 통해 도커 이미지 생성 및 도커 허브에 push
    
#### 4. 쿠버네티스 마스터에 SSH 접속 후 쉘스크립트 실행
  1) 현재 실행 중인 디플로이먼트가 blue라면 green으로 디플로이먼트 생성
 - a. 젠킨스에서 도커 허브에 푸쉬한 이미지로 컨테이너 실행
  2) rollout명령어를 활용하여 생성한 디플로이먼트내의 프로그램이 정상 작동인지 확인
  3) 정상 작동중이라면 서비스를 신버전 디플로이먼트의 파드들과 연결하도록 수정
  4) 구버전 디플로이먼트 삭제

#### 5. webhook을 통해 Discode에게 파이프라인 결과 전달
- a. 젠킨스에 설치한 Discode 플러그인을 통해 파이프라인 제목, 결과, 실행 시간이 담긴 Post를 Discode에 보냄
- b. Discode봇이 데이터를 받아 지정한 Discode 서버에 실행 결과를 전송


## 🎞 Enadu Batch CI/CD 시나리오


#### 1. github에 be-dev 최신 버전 프로젝트를 머지

#### 2. github는 젠킨스에게 webhook을 통해서 젠킨스에게 이벤트 전달

#### 3. 젠킨스는 파이프라인에 저장된 절차 실행
- a. 젠킨스는 github에서 최신 코드를 clone한다.
- b. batch 혹은 common(repository 관련 모듈) 모듈의 변화를 인식 
- c. 클론된 코드를 기반으로 빌드 작업 수행
- d. 빌드를 통해 도커 이미지 생성 및 도커 허브에 push
    
#### 4. 쿠버네티스 마스터에 SSH 접속 후 쉘스크립트 실행
  1) 현재 실행 중인 디플로이먼트가 blue라면 green으로 디플로이먼트 생성
 - a. 젠킨스에서 도커 허브에 푸쉬한 이미지로 컨테이너 실행
  2) rollout명령어를 활용하여 생성한 디플로이먼트내의 프로그램이 정상 작동인지 확인
  3) 정상 작동중이라면 서비스를 신버전 디플로이먼트의 파드들과 연결하도록 수정
  4) 구버전 디플로이먼트 삭제

#### 5. webhook을 통해 Discode에게 파이프라인 결과 전달
- a. 젠킨스에 설치한 Discode 플러그인을 통해 파이프라인 제목, 결과, 실행 시간이 담긴 Post를 Discode에 보냄
- b. Discode봇이 데이터를 받아 지정한 Discode 서버에 실행 결과를 전송


## 🎞 Enadu Frontend CI/CD 시나리오


#### 1. github에 fe-dev 최신 버전 프로젝트를 머지

#### 2. github는 젠킨스에게 webhook을 통해서 젠킨스에게 이벤트 전달

#### 3. 젠킨스는 파이프라인에 저장된 절차 실행
- a. 젠킨스는 github에서 최신 코드를 clone한다.
- b. depedencies 설치 (npm install)
- c. 클론된 코드를 기반으로 빌드 작업 수행
- d. 빌드를 통해 도커 이미지 생성 및 도커 허브에 push
    
#### 4. 쿠버네티스 마스터에 SSH 접속 후 쉘스크립트 실행
  1) 현재 실행 중인 디플로이먼트가 blue라면 green으로 디플로이먼트 생성
 - a. 젠킨스에서 도커 허브에 푸쉬한 이미지로 컨테이너 실행
  2) rollout명령어를 활용하여 생성한 디플로이먼트내의 프로그램이 정상 작동인지 확인
  3) 정상 작동중이라면 서비스를 신버전 디플로이먼트의 파드들과 연결하도록 수정
  4) 구버전 디플로이먼트 삭제

#### 5. webhook을 통해 Discode에게 파이프라인 결과 전달
- a. 젠킨스에 설치한 Discode 플러그인을 통해 파이프라인 제목, 결과, 실행 시간이 담긴 Post를 Discode에 보냄
- b. Discode봇이 데이터를 받아 지정한 Discode 서버에 실행 결과를 전송


 CI/CD 프로세스와 Blue-Green 배포 방식을 통해 빠르고 안전한 애플리케이션 배포를 가능하게 하며, 쿠버네티스의 기능을 활용하여 확장성 및 복구 능력이 극대화 가능하다.
<br>
<br>


## 💻 CI/CD 테스트 및 결과


### 젠킨스 파이프라인
![](https://github.com/user-attachments/assets/ca2edd94-d1a3-4afc-9380-2426f8962d6a)

### K8S Blue/Green 배포 
![](https://github.com/user-attachments/assets/b8d2d959-975c-4892-8406-010534a62ed1)

### 디스코드 알림
<img width="450" alt="스크린샷 2024-08-29 오후 2 36 56" src="https://github.com/user-attachments/assets/5c524497-12fc-4cad-acd3-ce86fd331641">
<br>
<br>
