# WHOAMI backend


# API Documentation
## 1. USER
### 1.1. 

## 2. ARTICLE
### 2.1. 답글 조회
#### 2.1.1. GET /answers/list/{userId}
#### 2.1.2. Request
userId가 없을 경우 모든 data 반환
| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| userId |  Number  | 회원 번호       |

#### 2.1.3. Response

| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| answerId |  Number  | 답글 번호       |
| updatedAt |  Datetime  | 날짜       |
| userId |  Number  | 회원 번호       |
| answerContents |  String  | 답글 내용       |
| tagId |  Number  | 태그 번호       |
| tagContents |  Number  | 태그 내용       |

#### 2.1.4. Http Code
- 200 : OK
- 400 : Bad Request (parameter를 잘못 전달하거나 없는 경우)

### 2.2. 답글 내용 작성
#### 2.2.1. POST /answers
#### 2.2.2. Request

| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| userId |  Number  | 회원 번호       |
| contents |  String  | 답글 내용       |
| tagContents |  Number  | 태그 내용       |

#### 2.2.3. Response

| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| answerId |  Number  | 답글 번호       |
| updatedAt |  Datetime  | 날짜       |
| userId |  Number  | 회원 번호       |
| answerContents |  String  | 답글 내용       |
| tagId |  Number  | 태그 번호       |
| tagContents |  Number  | 태그 내용       |

#### 2.2.4. Http Code
- 201 : Created
- 400 : Bad Request (parameter를 잘못 전달하거나 없는 경우)
- 401 : Unauthorized (로그인하지 않은 사용자가 답변을 작성하려고 한 경우)

### 2.3. 답글 내용 수정
#### 2.3.1. PATCH /answers/{answer}
#### 2.3.2. Request

| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| answerId |  Number  | 답글 번호       |
| answerContents |  String  | 답글 내용       |
| tagContents |  Number  | 태그 내용       |

#### 2.3.3. Response

없음

#### 2.3.4. Http Code
- 200 : OK
- 400 : Bad Request (parameter를 잘못 전달하거나 없는 경우)
- 401 : Unauthorized (로그인하지 않은 사용자가 답변을 수정하려고 한 경우)
- 403 : Forbidden (글작성자가 아닌 사용자가 답변을 수정하려고 한 경우)

### 2.4. 답글 내용 삭제
#### 2.4.1. DELETE /answers/{answerId}
#### 2.4.2. Request

| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| answerId |  Number  | 답글 번호       |

#### 2.4.3. Response

없음

#### 2.4.4. Http Code
- 200 : OK
- 400 : Bad Request (parameter를 잘못 전달하거나 없는 경우)
- 401 : Unauthorized (로그인하지 않은 사용자가 답변을 삭제하려고 한 경우)
- 403 : Forbidden (글작성자가 아닌 사용자가 답변을 삭제하려고 한 경우)

### 2.5. 답글 좋아요 추가
#### 2.5.1. POST /answers/likes
#### 2.5.2. Request

| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| answerId |  Number  | 답글 번호       |
| userId |  Number  | 회원 번호       |

#### 2.5.3. Response

| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| likeId |  Number  | 좋아요 번호       |
| answerId |  Number  | 답글 번호       |
| userId |  Number  | 회원 번호       |


#### 2.5.4. Http Code
- 200 : OK
- 400 : Bad Request (parameter를 잘못 전달하거나 없는 경우)
- 401 : Unauthorized (로그인하지 않은 사용자가 좋아요를 누른 경우)

### 2.6. 답글 좋아요 삭제
#### 2.6.1. DELETE /answers/likes/{likeId}
#### 2.6.2. Request

| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| likeId |  Number  | 좋아요 번호       |

#### 2.6.3. Response

없음

### 2.7. 댓글 좋아요 추가
#### 2.7.1. POST /comments/likes
#### 2.7.2. Request

| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| commentsId |  Number  | 댓글 번호       |
| userId |  Number  | 회원 번호       |

#### 2.7.3. Response

| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| likeId |  Number  | 좋아요 번호       |
| commentsId |  Number  | 댓글 번호       |
| userId |  Number  | 회원 번호       |


#### 2.7.4. Http Code
- 200 : OK
- 400 : Bad Request (parameter를 잘못 전달하거나 없는 경우)
- 401 : Unauthorized (로그인하지 않은 사용자가 좋아요를 누른 경우)

### 2.8. 댓글 좋아요 삭제
#### 2.8.1. DELETE /comments/likes/{likeId}
#### 2.8.2. Request

| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| likeId |  Number  | 좋아요 번호       |

#### 2.8.3. Response

없음

#### 2.8.4. Http Code
- 200 : OK
- 400 : Bad Request (parameter를 잘못 전달하거나 없는 경우)

### 2.9. 댓글 작성
#### 2.9.1. POST /comments
#### 2.9.2. Request

| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| userId |  Number  | 회원 번호       |
| comments |  String  | 댓글 내용       |
| answerId |  Number  | 답글 번호       |
| upperCommentsId |  String  | 윗댓글 번호       |

#### 2.9.3. Response

| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| commentsId |  Number  | 댓글 번호       |
| updatedAt |  Datetime  | 날짜       |
| userId |  Number  | 회원 번호       |
| answerId |  Number  | 답글 번호       |
| upperCommentsId |  String  | 윗댓글 번호       |

#### 2.9.4. Http Code
- 200 : OK
- 400 : Bad Request (parameter를 잘못 전달하거나 없는 경우)
- 401 : Unauthorized (로그인하지 않은 사용자가 댓글을 작성하려고 한 경우)

### 2.10. 댓글 삭제
#### 2.10.1. DELETE /comments/{commentId}
#### 2.10.2. Request

| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| commentsId |  Number  | 댓글 번호       |

#### 2.10.3. Response

없음

#### 2.10.4. Http Code
- 200 : OK
- 400 : Bad Request (parameter를 잘못 전달하거나 없는 경우)
- 401 : Unauthorized (로그인하지 않은 사용자가 댓글을 삭제하려고 한 경우)
- 403 : Forbidden (글작성자가 아닌 사용자가 댓글을 삭제하려고 한 경우)

### 2.10. 댓글 수정
#### 2.10.1 PATCH /comments/{commentId}
#### 2.10.2. Request

| Parameter |  Type  | Description |
|-----------|:------:|-------------|
| userId |  Number  | 회원 번호       |
| comments |  String  | 댓글 내용       |
| answerId |  Number  | 답글 번호       |
| upperCommentsId |  String  | 윗댓글 번호       |

#### 2.10.3. Response

없음

#### 2.10.4. Http Code
- 200 : OK
- 400 : Bad Request (parameter를 잘못 전달하거나 없는 경우)
- 401 : Unauthorized (로그인하지 않은 사용자가 댓글을 수정하려고 한 경우)
- 403 : Forbidden (글작성자가 아닌 사용자가 댓글을 수정하려고 한 경우)

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



