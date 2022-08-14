# Crawling Library

## CocoaPods Library

1. 라이브러리 프로젝트 생성

```
$ pod lib create 라이브러리 이름
```

2. 라이브러리 생성 시 질의 List

```
1. What platform do you want to use?? : iOS
2. What language do you want to use?? : Swift
3. Would you like to include edmo application with your library? :YES
(해당 라이브러리에 대한 화면 스크린샷이 필요하면 YES)
4. Which testing frameworks will you use?? None
(None의 경우 Apple 의 XCTest를 사용)
5. Would you like t odo view based testing ?? No
(Yes의 경우 페이스북 스냅샷 기반 테스트 오픈소스 FBSnapShot TestCase 포함)
```

## Carthage

- Carthage(카르타고) : 라이러리 관리 도구

1. Carthage 설치

```
$ brew update
$ brew install carthage
```

2. Cartfile 생성

```
$ touch Cartfile
```

3. Cartfile에 사용할 라이브러리 작성

```
// SwiftSoup
github "scinfu/SwiftSoup"
```

4. 작성 후 framework 생성

```
$ carthage update --use-xcframeworks
```

5. Carthage/Build/ 경로의 .xcframework 사용
