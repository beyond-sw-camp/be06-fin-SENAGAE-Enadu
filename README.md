
<br>
  
<h1 align="center"> 지식관리서비스 Enadu </h1>
<br>
<div align="center">
<img src="https://github.com/user-attachments/assets/08f19cfc-3976-4cc9-91b0-58d0b0224fc3" style="width: 80%;">
</div>

<br>



## 🎁팀원 소개
---
> **[한화시스템 BEYOND SW캠프 6기] Final Project** 

> **팀 SENAGAE 세나개**

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
- [🛠 기술 스택](#-기술-스택)
- [📚 Enadu 사이트 바로가기](#-enadu-사이트-바로가기)
- [🎁 Enadu 서비스 소개](#-enadu-서비스-소개)
- [🤳 프로젝트 목표](#-프로젝트-목표)
- [📈 프로젝트 설계](#-프로젝트-설계)
- [⌛프로젝트 기능 및 일정](#프로젝트-기능-및-일정)
<br><br>


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
## 🎁 Enadu 서비스 소개
---
어쩌구 저쩌구

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
## 📈 프로젝트 설계

어쩌구 <br>
저쩌구 <br>

### 1. ERD

### 2. 화면설계서

### 3. CI/CD

#### - 클러스터 노드 구성
💻 Master 1대<br>
💻 Worker 4대의 클러스터 구성
<br>

#### - 쿠버네티스 적용
-  운영 환경에서 유연성, 동일한 환경을 제공하여 버그를 최소화할 수 있다.
-  프라이빗 클라우드 인프라에서 안정적으로 실행될 수 있는 기능을 제공하여 서비스가 안정적으로 운영되고 개발 및 배포 과정을 더욱 효율적으로 만들어 주었다.

### 4. 시스템 아키텍처
<br>

<details>
<summary><b>시스템 아키텍처</b></summary>

<br>
  
<img src="https://github.com/user-attachments/assets/d8f033d6-3e75-4fca-a0b8-d532df8498fd">


<br>
## ⌛프로젝트 기능 및 일정
어쩌구 저쩌구

---

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

