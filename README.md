## AI Assist 활용 내역

본 프로젝트는 과제 요구 사항에 따라 ChatGPT(GPT-5.1)를 활용하여 개발을 진행하였습니다.  
AI Assist는 코드 생성, 구조 설계 보완, 오류 분석 및 해결 가이드 제공 등  
여러 단계에서 개발 효율을 높이기 위한 보조 도구로 활용되었습니다.

### AI Assist를 통해 참고하거나 생성한 주요 내용
- Clean Architecture 기반 모듈 구조 및 buildSrc 초기 템플릿 생성
- Hilt DI 구성 및 WorkManager 연동 과정에서 발생한 오류 해결
- 에뮬레이터 환경에서 FusedLocationProviderClient의 `lastLocation` 값이  
  null로 반환되는 문제 분석 및 해결 (requestLocationUpdates 기반 구조로 개선)
- Room 기반 위치 히스토리 저장 로직(변경된 위치만 저장) 설계
- Naver Map SDK를 활용한 마커/카메라 이동 로직 구성
- “같은 위치는 저장하지 않지만 버튼 클릭 시 지도는 항상 현재 위치로 이동해야 한다”는  
  요구사항에 맞춘 전체 동작 흐름 조정

### 사용된 주요 프롬프트 예시
- *“클린아키텍처, buildSrc, Hilt를 적용한 Android 프로젝트 템플릿 생성해줘.”*
- *“WorkManager에서 Hilt Worker가 생성되지 않는 오류를 해결해줘.”*
- *“현재 위치를 WorkManager로 받아서 Room에 저장하고 지도에 표시하는 전체 구조를 만들어줘.”*
- *“에뮬레이터에서 lastLocation이 null일 때 정상적으로 위치를 가져오는 방법은?”*
- *“동일 위치는 저장하지 않되, 지도는 항상 내 위치로 이동하도록 만들려면 어떻게 해야 해?”*

최종 구현은 AI Assist가 제안한 내용을 기반으로 직접 검증하고  
필요한 부분을 수정·통합하여 완성하였습니다.


전체 AI Assist 대화 기록
https://chatgpt.com/share/6919e20b-a450-8011-bc0c-19c893cd07d1



