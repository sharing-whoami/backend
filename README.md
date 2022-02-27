# WHOAMI backend


# API Documentation
## 1. USER
### 1.1. 사용자 회원가입
#### 1.1.1. URL
POST :/users

#### 1.1.2. Request
| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| userId | String | 회원 아이디 |
| password | String | 회원 비밀번호 |
| name | String | 이름 |
| registry_num  | String | 주민번호 |
| phone_num | String | 휴대전화번호 |
| email | String | 이메일 주소 |
| is_receive_notification | Boolean | 알림 설정 여부 |
| is_admin | Boolean | 관리자 여부 |
| profile | String | 프로필 사진 저장 경로 |

#### 1.1.3. Response
| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| userId | String | 회원 아이디 |
| email | String | 이메일 주소 |
| name | String | 이름 |

#### 1.1.4 Http code
- 201 : Created
- 400 : Bad Request (애초에 parameter를 잘못 전달하거나 없는 경우)
- 409 : Conflict (이미 존재하는 리소스(닉네임)를 중복 요청했을 경우)

### 1.2. 사용자 로그인 (토큰 발급)  
#### 1.2.1. URL
POST: /users/login/{userId}/{password}

#### 1.2.2. Request
| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| userId | String | 회원 아이디 |
| password | String | 회원 비밀번호 |

#### 1.2.3. Response
| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| refreshToken | String | 갱신을 위한 토큰 |
| accessToken | String | 인증을 위한 토큰 |

#### 1.2.4. Http code
- 201 : Created 
- 400 : Bad Request (애초에 parameter를 잘못 전달하거나 없는 경우)
- 401 : Unauthorized (로그인 실패)

### 1.3. 사용자 로그인 (토큰 갱신)  
#### 1.3.1. URL
PATCH :/users/login/refreshment/{refreshToken}/{accessToken}

#### 1.3.2. Request
| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| refreshToken | String | 갱신을 위한 토큰 |

#### 1.3.3. Response
| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| accessToken | String | 인증을 위한 토큰 |

#### 1.3.4. Http code
- 201 : Created 
- 400 : Bad Request (애초에 parameter를 잘못 전달하거나 없는 경우)

### 1.4. 사용자 로그인 (토큰 검증)  
#### 1.4.1. URL
POST: /users/login/verification/{accessToken}

#### 1.4.2. Request
| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| accessToken | String | 인증을 위한 토큰 |

#### 1.4.3. Response
없음

#### 1.4.4. Http code
- 201 : Created 
- 400 : Bad Request (애초에 parameter를 잘못 전달하거나 없는 경우)
- 401 : Unauthorized (로그인 실패)

### 1.5. 사용자 탈퇴 
#### 1.5.1 URL
DELETE :/users/{userId}

#### 1.5.2. Request
| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| userId | String | 회원 아이디 |

#### 1.5.3. Response
없음

#### 1.5.4. Http code
- 200 : Ok
- 409 : Conflict (리소스가 충돌 혹은 삭제 시 연관된 데이터가 남아있는 경우)

### 1.6. 사용자 비밀번호/휴대전화번호/이메일주소/알림여부 수정
#### 1.6.1. URL
PATCH: /users/{userId}

#### 1.6.2. Request
| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| userId | String | 회원 아이디 |
| password | String | 회원 비밀번호 |
| phone_num | String | 휴대전화번호 |
| email | String | 이메일 주소 |
| is_receive_notification | Boolean | 알림 설정 여부 |

#### 1.6.3. Response
없음

#### 1.6.4. Http code
- 201 : Created 
- 400 : Bad Request (애초에 parameter를 잘못 전달하거나 없는 경우)

### 1.7. 사용자 프로필 추가, 수정, 삭제
#### 1.7.1 URL
PATCH :/users/{userId}/{profile}

#### 1.7.2. Request
| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| userId | String | 회원 아이디 |
| profile | Boolean | 프로필 사진 저장 경로 |

#### 1.7.3. Response
없음

#### 1.7.4. Http code
- 201 : Created 
- 400 : Bad Request (애초에 parameter를 잘못 전달하거나 없는 경우)

### 1.8. 사용자 리스트 조회 (admin만 가능)
#### 1.8.1. URL
GET :/users/list/{userId}

#### 1.8.2. Request
userId가 없을 경우 모든 data 반환
| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| userId | String | 회원 아이디 |

#### 1.8.3. Response
| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| userId | String | 회원 아이디 |
| name | String | 이름 |
| phone_num | String | 휴대전화번호 |
| email | String | 이메일 주소 |

#### 1.8.4. Http code
- 200 : Ok
- 400 : Bad Request (애초에 parameter를 잘못 전달하거나 없는 경우)
- 401 : Unauthorized (익명의 사용자의 접근을 차단함)
- 403 : Forbidden (관리자 권한이 없음)

### 1.9. 팔로잉 및 팔로워 추가
#### 1.9.1. URL
PUT :/follow/{followedId}/{followingId}

#### 1.9.2. Request
| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| followedId | String | 팔로우 되는 유저 아이디 |
| followingId | String | 팔로잉 하는 유저 아이디 |

#### 1.9.3. Response
| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| followId | Number | 팔로우 번호 |
| followedId | String | 팔로우 되는 유저 아이디 |
| followingId | String | 팔로잉 하는 유저 아이디 |

#### 1.9.4. Http code
- 201 : Created
- 401 : Unauthorized (로그인이 되어 있지 않아 팔로우 기능에 대한 접근을 차단함)

### 1.10. 팔로잉 및 팔로워 삭제
#### 1.10.1. URL
DELETE :/follow/{followedId}/{followingId}

#### 1.10.2. Request
| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| followedId | String | 팔로우 되는 유저 아이디 |
| followingId | String | 팔로잉 하는 유저 아이디 |

#### 1.10.3. Response
없음

#### 1.10.4. Http code
- 200 : Ok
- 401 : Unauthorized (로그인이 되어 있지 않아 팔로우 기능에 대한 접근을 차단함)

## 2. ARTICLE
## 3. QUESTION
### 3.1. GET : /question/:startDate/:endDate
#### 3.1.1. Request  
startDate와 endDate가 없을 경우 모든 data 반환

| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| startDate | Number | 검색 날짜 시작일   |
| endDate   | Number | 검색 날짜 마지막일  |

#### 3.1.2. Response  
data의 배열  

| Parameter |   Type   | Description |
|-----------|:--------:|-------------|
| articleId | Number | 질문 번호       |
| contents  | String | 질문 내용       |
| date      | datetime | 질문 날짜       |

#### 3.1.3. Http Code  
- 200 : 성공

### 3.2. POST : /question
#### 3.2.1. Request

| Parameter |   Type   | Description |
|-----------|:--------:|-------------|
| date      | Datetime | 질문 날짜       |
| contents  |  String  | 질문 내용       |

#### 3.2.2. Response
data의 배열   

| Parameter  |   Type   | Description |
|------------|:--------:|-------------|
| questionId |  Number  | 검색 날짜 시작일   |
| contents   |  String  | 검색 날짜 마지막일  |
| date       | Datetime | 질문 날짜       |

#### 3.2.3. Http Code
- 200 : 성공
- 400 : Bad Request (parameter를 잘못 전달하거나 없음)
- 401 : Unauthorized (익명의 사용자로인한 권한없음)
- 403 : Frobidden (admin 아닌 사용자로인한 권한없음)
- 
### 3.3. DELETE : /question/:questionId
#### 3.2.1. Request

| Parameter  |   Type   | Description |
|------------|:--------:|-------------|
| questionId |  Number  | 질문 id       |

#### 3.2.2. Response
없음

#### 3.2.3. Http Code
- 200 : 성공
- 400 : Bad Request (parameter를 잘못 전달하거나 없음)
- 401 : Unauthorized (익명의 사용자로인한 권한없음)
- 403 : Frobidden (admin 아닌 사용자로인한 권한없음)

### 3.4. PATCH : /question/:questionId
#### 3.2.1. Request

| Parameter  |   Type   | Description |
|------------|:--------:|-------------|
| questionId |  Number  | 질문 id       |
| date       | Datetime | 질문 날짜       |
| contents   |  String  | 질문 내용       |

#### 3.2.2. Response
없음

#### 3.2.3. Http Code
- 200 : 성공
- 400 : Bad Request (parameter를 잘못 전달하거나 없음)
- 401 : Unauthorized (익명의 사용자로인한 권한없음)
- 403 : Frobidden (admin 아닌 사용자로인한 권한없음)


## 4. GUEST BOOK
### 4.1. GET : /guestbook/:startDate/:endDate
#### 4.1.1. Request
startDate와 endDate가 없을 경우 모든 data 반환

| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| startDate | Number | 검색 날짜 시작일   |
| endDate   | Number | 검색 날짜 마지막일  |

#### 4.1.2. Response
data의 배열

| Parameter |   Type   | Description |
|-----------|:--------:|-------------|
| articleId | Number | 질문 번호       |
| contents  | String | 질문 내용       |
| date      | datetime | 질문 날짜       |

#### 4.1.3. Http Code
- 200 : 성공

### 4.2. POST : /guestbook/
#### 4.2.1. Request

| Parameter   |  Type  | Description |
|-------------|:------:|-------------|
| contents    | String | 방명록 내용      |

#### 4.2.2. Response

| Parameter   |  Type  | Description |
|-------------|:------:|-------------|
| guestbookId | Number | 방명록 id      |

#### 4.2.3. Http Code
- 200 : 성공
- 400 : Bad Request (parameter를 잘못 전달하거나 없음)
- 401 : Unauthorized (익명의 사용자로인한 권한없음)

### 4.3. PATCH : /guestbook/:guestbookId
#### 4.3.1. Request

| Parameter |   Type   | Description |
|-----------|:--------:|-------------|
| commentId |  Number  | 댓글 id       |
| date      | Datetime | 질문 날짜       |
| contents  |  String  | 질문 내용       |

#### 4.3.2. Response
없음

#### 4.3.3. Http Code
- 200 : 성공
- 400 : Bad Request (parameter를 잘못 전달하거나 없음)
- 401 : Unauthorized (익명의 사용자로인한 권한없음)
- 403 : Frobidden (방명록 주인 아닌 사용자로인한 권한없음)

### 4.4. DELETE : /guestbook/:guestbookId
#### 4.4.1. Request

| Parameter |   Type   | Description |
|-----------|:--------:|-------------|
| guestbookId |  Number  | 방명록 id      |

#### 4.4.2. Response
없음

#### 4.4.3. Http Code
- 200 : 성공
- 400 : Bad Request (parameter를 잘못 전달하거나 없음)
- 401 : Unauthorized (익명의 사용자로인한 권한없음)
- 403 : Frobidden (방명록 주인 아닌 사용자로인한 권한없음)

### 4.5. POST : /guestbook/comment/:guestbookId
#### 4.5.1. Request

| Parameter   |  Type  | Description |
|-------------|:------:|-------------|
| guestbookId | Number | 방명록 id      |
| contents    | String | 댓글 내용       |

#### 4.5.2. Response

| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| commentId | Number | 댓글 id       |

#### 4.5.3. Http Code
- 200 : 성공
- 400 : Bad Request (parameter를 잘못 전달하거나 없음)
- 401 : Unauthorized (익명의 사용자로인한 권한없음)

### 4.6. PATCH : /guestbook/comment/:commentId
#### 4.6.1. Request

| Parameter |   Type   | Description |
|-----------|:--------:|-------------|
| commentId |  Number  | 댓글 id       |
| date      | Datetime | 질문 날짜       |
| contents  |  String  | 질문 내용       |

#### 4.6.2. Response
없음

#### 4.6.3. Http Code
- 200 : 성공
- 400 : Bad Request (parameter를 잘못 전달하거나 없음)
- 401 : Unauthorized (익명의 사용자로인한 권한없음)
- 403 : Frobidden (댓글 주인 아닌 사용자로인한 권한없음)


### 4.7. DELETE : /guestbook/comment/:commentId
#### 4.7.1. Request

| Parameter |   Type   | Description |
|-----------|:--------:|-------------|
| commentId |  Number  | 댓글 id       |

#### 4.7.2. Response
없음

#### 4.7.3. Http Code
- 200 : 성공
- 400 : Bad Request (parameter를 잘못 전달하거나 없음)
- 401 : Unauthorized (익명의 사용자로인한 권한없음)
- 403 : Frobidden (댓글 주인 아닌 사용자로인한 권한없음)



