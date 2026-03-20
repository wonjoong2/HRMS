# HRMS

ERP 모듈 중 하나인 **인사관리 시스템(HRMS)** 의 일부 기능을 구현한 프로젝트 <br>
직원의 **근태 데이터를 관리하고 AI를 활용한 근태 분석 기능**을 제공

***

## 1. 프로젝트 소개


> 개발 기간 : **2026-02-23 ~ 2026-03-11** <br>
> 개발 인원 : **1명**

| 항목 | 링크 |
|---|---|
| 프로젝트 소개 | [HRMS](https://github.com/wonjoong2/HRMS/blob/master/docs/HRMS_Project.pdf) |
| 서비스 (운영) | https://myhrms.store |
| 개발 문서 | https://productive-season-630.notion.site/HRMS-320d34243c84808ab567f69ceb8757be |
| ERD | [https://github.com/wonjoong2/HRMS/blob/master/docs/ERD.png](https://github.com/wonjoong2/HRMS/blob/master/docs/HRMS_ERD.png) |

---

## 2. 기술 스택


### Backend
`Java 17` `Spring Boot 4.0.3` `Spring Security` `MySQL` `Redis` `WebSocket`

### Frontend
`Thymeleaf` `JavaScript` `Chart.js` `AdminLTE`

### Infra / 배포
`Docker` `Nginx` `Ubuntu` `Jenkins` 

### Observability
`Prometheus` `Grafana`

### AI
`OpenAI API`

---

## 3. 시스템 구성

<details>
<summary>Infra Architecture</summary>

<br>

<img src="https://github.com/user-attachments/assets/5ae4955c-346f-4d2e-af54-b9b2423907b0" width="80%">

</details>

---

## 4. 주요 기능

* 인증 : 이메일 인증 회원가입, Spring Security 기반 인증 처리
* 조직 관리 : 부서 생성·관리·삭제, 부서이력조회 
* 사원 관리 : 사원 생성·관리·삭제
* 근태 관리 : 근태 현황 조회, 공휴일 등록, 휴가 관리, 기준 근무시간 관리
* AI 근태 분석 : OpenAI API를 활용한 개인, 부서, 이상 근태 분석
* 부서 채팅 시스템 : 부서 단위 채팅방 구현

