# DC Crawler
웹 사이트 [dcinside](https://www.dcinside.com/) 이미지 크롤러

# Feature
- 갤러리 검색
  - 메인
  - 마이너
- 이미지 크롤링
  - 모든 게시글
  - 개념글
- ~~실시간 크롤링~~
  - 준비중
- ~~특정 주소 크롤링~~
  - 준비중
  
# How to run?
## Mac
```shell
git clone https://github.com/wjrmffldrhrl/dc_crawler.git
cd dc_crawler
sh gradlew build
cp ./build/libs/dc_crawler-1.0-SNAPSHOT.jar ./dc_crawler.jar
java -jar ./dc_crawler.jar 

```
## Window

  
# How to use?
### 첫 화면
![ex1](./example/ex1.png)
### 갤러리 검색
![ex1](./example/ex2.png)
- 아래 입력 공간에 갤러리 이름 입력
- search 버튼 클릭
### 검색된 갤러리 선택
![ex1](./example/ex3.png)
- 검색된 결과 중 원하는 갤러리 선택
- Start Crawling 클릭

### 크롤링 결과
![ex1](./example/ex4.png)
- 크롤링 결과는 현재 디렉터리 내부 img 내부에 생성


