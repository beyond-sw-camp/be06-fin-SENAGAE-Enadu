
<br>
  
<h1 align="center"> 지식관리서비스 Enadu </h1>
<br>
<div align="center">
<img src="https://github.com/user-attachments/assets/3f6df515-9e11-4b63-b4e1-c35d22176721" style="width: 90%;">
</div>

<br>



## 🎁팀원 소개
---
> 6기 final project

> 팀 SENAGAE 세나개

<table align="center">
 <tr>
    <td align="center"><a href="https://github.com/dyun23"><img src="https://github.com/user-attachments/assets/99536e16-063c-4785-92d2-44b909ab7c84" width="150px;" alt=""></td>
    <td align="center"><a href="https://github.com/sue06004"><img src="https://github.com/user-attachments/assets/2dde57cc-aa14-410c-99f7-9d50841fde51" width="150px;" alt=""></td>
    <td align="center"><a href="https://github.com/jimnyy"><img src="https://github.com/user-attachments/assets/5ff5cb30-f7aa-4ab2-a18d-a31087ab9f80" width="150px;" alt=""></td>
    <td align="center"><a href="https://github.com/dlrkdms125"><img src="https://github.com/user-attachments/assets/42bed0f6-7d78-4eec-8906-e843495fca4f" width="150px;" alt=""></td>
    <td align="center"><a href="https://github.com/ashd89"><img src="https://github.com/user-attachments/assets/ce222453-33eb-450c-94a0-a46be9352506" width="150px;" alt=""></td>
  </tr>
  <tr>
    <td align="center">🐥<a href="https://github.com/dyun23"><b>김다윤</b></td>
    <td align="center">🦊<a href="https://github.com/sue06004"><b>김우혁</b></td>
    <td align="center">😼<a href="https://github.com/jimnyy"><b>도지민</b></td>
    <td align="center">🐰<a href="https://github.com/dlrkdms125"><b>이가은</b></td>
    <td align="center">🐻<a href="https://github.com/ashd89"><b>이재룡</b></td>
  </tr>
  </table>
<br>


### 목차
- [🤳 프로젝트 목표](#-프로젝트-목표)
- [🛠 기술 스택](#-기술-스택)
- [📚 Enadu 사이트 바로가기](#-enadu-사이트-바로가기)
- [DevOps 운영 환경](#-운영-환경)
- [배포 방식](#-bluegreen-배포-방식-사용-이유)
- [CI/CD 시나리오](#-cicd-시나리오)
<br><br>
---
## 🤳 프로젝트 목표
---
#### 1. 운영 중인 환경에 CI/CD 적용
  -  코드 변경 사항을 자동으로 빌드 및 배포하여 개발에만 집중 할 수 있다.
  -  서로의 작업이 자동으로 통합되며, 팀원들과 더 쉽게 협업할 수 있다. 
#### 2. 유연한 배포
  - 무중단 배포 방식인 <Blue-Green 배포 방식> 을 사용하여 서비스 중단 없이 새로운 기능을 배포할 수 있고, 사용자에게 지속적으로 안정적인 서비스를 제공 가능하게 함.
  - 새로운 버전(Green) 배포 후 문제가 발생할 경우, 기존 버전(Blue)으로 빠르게 롤백이 가능하므로 안정적인 롤백이 가능함.

<br>

---
## 🛠 기술 스택
---
<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://img.shields.io/badge/GitHub-181717?style=flat&logo=GitHub&logoColor=white&color=black"></a></a>
&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://img.shields.io/badge/Git-F05032?style=flat&logo=Git&logoColor=white&color=ffa500"></a></a>
&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://img.shields.io/badge/Docker-2496ED?style=flat&logo=Docker&logoColor=black&color=blue"/></a></a>
&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://img.shields.io/badge/Kubernetes-326CE5?style=flat&logo=Kubernetes&logoColor=blue&color=skyblue"/></a></a>
&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://img.shields.io/badge/Jenkins-D24939?style=flat&logo=jenkins&logoColor=white"/></a></a>
<br>
<br>
&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://img.shields.io/badge/vuejs-%2335495e.svg?style=flat&logo=vuedotjs&logoColor=%234FC08D"/></a>
&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://img.shields.io/badge/SpringBoot-181717?style=flat&logo=SpringBoot&logoColor=6DB33F&color=white"></a>
<br><br>

---
## 📚 Enadu 사이트 바로가기
---
📌Enadu <br>
https://www.enadu.kro.kr <br>
<br>

---
## 🌌 운영 환경
---
#### - 클러스터 노드 구성
💻 Master 1대<br>
💻 Worker 4대의 클러스터 구성
<br>

#### - 쿠버네티스 적용
-  운영 환경에서 유연성, 동일한 환경을 제공하여 버그를 최소화할 수 있다.
-  프라이빗 클라우드 인프라에서 안정적으로 실행될 수 있는 기능을 제공하여 서비스가 안정적으로 운영되고 개발 및 배포 과정을 더욱 효율적으로 만들어 주었다.


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
  
![image](https://github.com/user-attachments/assets/d8f033d6-3e75-4fca-a0b8-d532df8498fd)


<br>
<br>

---

## 📘📗 Blue/Green 배포 방식 사용 이유
---
우리팀은 다운 타임이 없는 무중단 배포 방식 중 Blue/Green 배포 방식을 적용하였다.
- 블루그린 배포 방식은 지속적 배포 방식 중 하나로 신 버전을 배포가 완료되면 구 버전을 바라보던 서비스가 신 버전으로 일제히 전환하도록 하는 방식이다.
- 신속한 업데이트와 동시에 서버 안정성이 좋기 때문에 이 방식을 선택했다.
<br><br><br>



---
## 🎞 CI/CD 시나리오
---

#### 1. github에 be-dev, fe-dev에 최신 버전 프로젝트를 머지
- a. 최신 버전 코드를 머지하면 이벤트 발생

#### 2. github는 젠킨스에게 webhook을 통해서 젠킨스에게 이벤트 전달

#### 3. 젠킨스는 파이프라인에 저장된 절차 실행
- a. 젠킨스는 github에서 최신 코드를 clone한다.
- b. 클론된 코드를 기반으로 빌드 작업 수행
- c. 빌드를 통해 도커 이미지 생성 및 도커 허브에 push
    
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

---
## 💻 CI/CD 테스트 및 결과
---

### 젠킨스 파이프라인
![](https://github.com/user-attachments/assets/ca2edd94-d1a3-4afc-9380-2426f8962d6a)

### K8S Blue/Green 배포 
![](https://github.com/user-attachments/assets/b8d2d959-975c-4892-8406-010534a62ed1)

### 무중단 배포 (Back - 쉘, Front - 테스트 페이지)
<img src="https://github.com/user-attachments/assets/25376253-1011-42bc-964f-3daec5e497b9" style="width:400px"></img>
<img src="https://github.com/user-attachments/assets/88c7b6d2-13f5-40da-8b6e-4ea040a989c5" style="width:350px"></img>

### 디스코드 알림
<img width="450" alt="스크린샷 2024-08-29 오후 2 36 56" src="https://github.com/user-attachments/assets/5c524497-12fc-4cad-acd3-ce86fd331641">



<br>
<br>

