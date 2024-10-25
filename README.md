<br>
  
<h1 align="center"> 지식 관리 시스템   Enadu_ 백엔드 </h1>
<br>
<div align="center">
<img src="https://github.com/user-attachments/assets/3f6df515-9e11-4b63-b4e1-c35d22176721" style="width: 60%;">
</div>

<br>


---
## 🛠 기술 스택
---

#### &nbsp;　[ Backend ]
&nbsp;&nbsp;&nbsp;&nbsp;
![SpringBoot](https://img.shields.io/badge/SpringBoot-6DB33F?style=flat&logo=SpringBoot&logoColor=white)
![SpringSecurity](https://img.shields.io/badge/SpringSecurity-6DB33F?style=flat&logo=springsecurity&logoColor=white)
![SpringJPA](https://img.shields.io/badge/SpringJPA-6DB33F?style=flat&logo=spring&logoColor=white)
![SpringBatch](https://img.shields.io/badge/SpringBatch-6DB33F?style=flat&logo=spring&logoColor=white)
![Tomcat](https://img.shields.io/badge/Tomcat-F8DC75?style=flat&logo=apachetomcat&logoColor=black)
![AmazonS3](https://img.shields.io/badge/AmazonS3-569A31?style=flat&logo=amazons3&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=flat&logo=jsonwebtokens&logoColor=white)

---
📢주요기능
---

## 1. Search
<img width="617" alt="image" src="https://github.com/user-attachments/assets/fa399aaf-dbd0-4e43-8cf4-204a329442e1"> <br>
- 엘라스틱 서치는 역색인 방식으로, 키워드 기반으로 검색을 수행하여 검색을 매우 빠르게 할 수 있다 <br>
- 역색인 방식은 document들이 indexing되어 (text, documents)란 key, value 형태로 mapping 되는 방식이다 <br>
<img width="617" alt="image" src="https://github.com/user-attachments/assets/37483c40-01b4-48d7-81ae-552b3183a2fe"> <br>
- 기존의 db에서 logstash가 1분마다 데이터를 조회하고 elastic search에 저장한다
## 2. Chat <br>
<img width="613" alt="image" src="https://github.com/user-attachments/assets/4e3eb6f1-795a-4bab-980a-4607a6e54f50"> <br>
- Pub/Sub 구조 <br>
  - 서버 이중화로 인한 데이터 동기화 문제로 채팅에 외부 메시지 브로커를 추가하였다 <br>
  - 발행자가 메시지를 브로커에 보내면, 브로커가 이를 구독자에게 전달하는 비동기 패턴인 Pub/Sub 구조를 사용하였다 <br>
<img width="640" alt="image" src="https://github.com/user-attachments/assets/f17e3ba5-ea64-4a2a-871c-f67e29d6a32b"> <br>
- STOMP 프로토콜 <br>
  - 웹소켓 위에서 pub/sub 위에서 모델로 동작하는 문자 기반 메시징 프로토콜 <br>
<img width="640" alt="image" src="https://github.com/user-attachments/assets/68067bf5-fac9-438c-b25e-ecde5a4b5e18"> <br>
- 카프카
  - 메시지 브로커와 이벤트 스트리밍 플랫폼 모두 이벤트를 수신하고, 이것을 consumer에게 전달한다.
  - 고가용성의 설계를 가진 애플리케이션을 위해 트래픽이 많고, 장애가 생겼을때 대응할 수 있는 포인트가 조금 더 명확한 Kafka를 사용하기로 결정하였다.

## 3. AOP


## 4. Batch
<img width="617" alt="image" src="https://github.com/user-attachments/assets/b80ec394-995f-4ea4-9b49-2ce0854156b2"> <br>
- 외부 api로부터 가져온 데이터들을 데이터베이스에 저장하는 작업을 Spring Batch를 통해 구현하고, 해당 작업을 Kubernetes의 CronJob을 통해 주기적으로 수행한다.
  - 


