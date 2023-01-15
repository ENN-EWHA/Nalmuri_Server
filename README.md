# Nalmuri_server
# 📘날무리
## 날무리 소개
- [날무리](http://nalmuri.site)는 일기를 작성하면 학습된 자연어 처리 모델이 일기 속에서 유저의 감정을 분석하고 그 감정에 어울리는 질문 카드를 제공합니다.
![image](https://user-images.githubusercontent.com/70185844/212279733-e33bd468-6668-4385-880d-5bf04121fccc.png)

## 사용 흐름
  -  유저는 자유롭게 오늘 하루에 대한 일기를 작성합니다. 
  - 일기가 저장되면, 직접 학습시킨 인공지능 모델이 하루 동안 유저의 마음속에서 가장 강렬했던 감정을 도출합니다. 
  - 사전에 구축된 질문 카드 데이터베이스에서 해당 감정에 어울리는 질문 카드가 유저에게 제공되고 유저는 이 질문 카드에 답하면서 하루를 마무리할 수 있습니다. 
  - 이때 질문 카드는 유저가 자신의 감정을 돌아보게 하거나, 감정을 추스르게 하는 질문들을 담고 있습니다.
![image](https://user-images.githubusercontent.com/70185844/212280228-ab211972-fc26-4686-a6d6-20d298ca49b0.png)
![image](https://user-images.githubusercontent.com/70185844/212356129-81dbc7be-8269-4aeb-b7f1-7b6c1a4091c4.png)
![image](https://user-images.githubusercontent.com/70185844/212356264-31cee02e-366f-4553-8738-3fe15d58b432.png)

## 서비스 이용 방법
- 날무리 도메인[(nalmuri.site)](http://nalmuri.site)을 통한 접속
- 또는 로컬에서 실행 후 접속[(localhost:3000)](http://localhost:3030)
```shell
$ git clone https://github.com/ENN-EWHA/NalMuri_FrontEnd
$ cd NalMuri_FrontEnd
$ npm install
$ npm run start
```

## 서버 실행 방법
- 도커 이미지를 이용해 백엔드 서버와 모델을 실행
```shell
$ docker pull gayeongpark/nalmuri-server-3
$ docker run --name nalmuri-server -d -p 8080:8080 gayeongpark/nalmuri-server-3

$ docker pull gayeongpark/nalmuri-nlp-1
$ docker run --name nalmuri-nlp -d -p 5000:5000 gayeongpark/nalmuri-nlp-1
```
- 이후 NalMuri_FrontEnd/package.json의 proxy 설정을 실행 중인 서버의 주소로 변경

## 포트 번호
| Service   | Port |
|-----------|------|
| Frontend  | 3000 |
| Server    | 8080 |
| Model Api | 5000 |

## 기술 스택
### Frontend
<img src="https://img.shields.io/badge/React-92CAFB?style=flat-square&logo=React&logoColor=black"/></a> 
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=JavaScript&logoColor=black"/></a>
<img src="https://img.shields.io/badge/CSS-1572B6?style=flat-square&logo=CSS3&logoColor=white"/></a> 
<img src="https://img.shields.io/badge/Axios-AE68D1?style=flat-square&logo=Axios&logoColor=white"/></a>
### Backend
<img src="https://img.shields.io/badge/Spring-6DB33F?style=flat&logo=Spring&logoColor=white"/></a>
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat&logo=SpringBoot&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Apache Tomcat-F8DC75?style=flat&logo=ApacheTomcat&logoColor=black"/></a>
<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=MySQL&logoColor=white"/></a>
### Model
<img src="https://img.shields.io/badge/PyTorch-EE4C2C?style=flat&logo=PyTorch&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Flask-000000?style=flat&logo=flask&logoColor=white"/></a>
### etc.
<img src="https://img.shields.io/badge/Amazon RDS-527FFF?style=flat&logo=AmazonRDS&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Google Cloud Platform-4285F4?style=flat&logo=GoogleCloud&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Docker-2496ED?style=flat&logo=Docker&logoColor=white"/></a> <br/>
<img src="https://img.shields.io/badge/Postman-FF6C37?style=flat&logo=Postman&logoColor=white"/></a>
<img src="https://img.shields.io/badge/npm-CB3837?style=flat&logo=npm&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Git-F05032?style=flat&logo=Git&logoColor=white"/></a>
<img src="https://img.shields.io/badge/GitHub-181717?style=flat&logo=Github&logoColor=white"/></a>


## Team ENN
| 권영경 | 김다희 | 김승언 | 김태영 | 박가영 |
|------|------|------|------|------|
| <img src="https://user-images.githubusercontent.com/70185844/212488683-2ee8af5c-ece2-4c4a-9bc3-dda25af9575e.png" width="150" height="150"/> | <img src="https://user-images.githubusercontent.com/70185844/212488720-4a2170db-7ae8-4335-9538-0c5249fd90d5.png" width="150" height="150"/> | <img src="https://user-images.githubusercontent.com/70185844/212488740-5bb3fb96-575f-4106-9274-f0d26a5af3a4.png" width="150" height="150"/> | <img src="https://user-images.githubusercontent.com/70185844/212504412-07df0960-ea30-41c4-a2c3-4c04ad13ec37.png" width="150" height="150"/> | <img src="https://user-images.githubusercontent.com/70185844/212488462-dc984efa-19e9-47ca-b731-b6f01afb2a59.png" width="150" height="150"/> |
| Frontend, AI | Frontend, AI | Backend, AI | Backend,  DevOps | Backend, Devops |
| [@asaei623](https://github.com/asaei623) | [@DAHEEKIM1](https://github.com/DAHEEKIM1) | [@seung-eon](https://github.com/seung-eon) | [@EHOia](https://github.com/EHOia) | [ParkIsComming](https://github.com/ParkIsComing) |


## Repositories
[날무리 프론트엔드](https://github.com/ENN-EWHA/Nalmuri_FrontEnd) <br>
[감정분류  NLP 모델](https://github.com/ENN-EWHA/Nalmuri_NLP)
