2020.03.28
개발환경에서 사용 시 프로필을 설정하여 실행합니다(VM Option)
-. 이클립스에서 실행 시(개발자) : 
     Server 탭에서 Tomcat 서버 더블클릭 
	 Overview 탭에서 Open Launch Configuration 클릭
	 Arguments탭에서 VM arguments에 "-Dspring.profiles.active=dev" 를 추가해줍니다.

-. Maven에서 빌드 시(배포 작업) :
     프로젝트 우클릭
	 Run AS -> Run Configuration
	 Maven Build -> Golas 안의 내용을 배포 환경에 맞게 
	 "clean install -Dspring.profiles.active=dev" 또는 "clean install -Dspring.profiles.active=product"로 합니다

-. 젠킨스를 이용한 배포 :
   Maven에서의 설정 내용을 그대로 사용합니다.
   개발서버 : clean install -Dspring.profiles.active=dev
   운영서버 : clean install -Dspring.profiles.active=product