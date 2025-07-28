# Maven 의존성 파일을 Nexus에서 가져오기 위한 스크립트입니다.
#
# 사전 필수 조건:
# 1. Nexus 서버가 설치되어 있고, 외부 Maven Central 접근이 제한되어 있어야 합니다.
# 2. 로컬 PC의 ~/.m2/settings.xml 파일이 존재해야 합니다.
# 3. settings.xml에는 <mirrors> 설정이 포함되어 있어야 하며, 중앙 저장소(maven central)가 
#    Nexus를 바라보도록 설정되어 있어야 합니다.
#
#    예시:
#    <mirrors>
#      <mirror>
#        <id>nexus</id>
#        <mirrorOf>*</mirrorOf>
#        <url>http://your-nexus-host:8081/repository/maven-public/</url>
#      </mirror>
#    </mirrors>
#
# 4. Nexus repository URL이 실제 운영 중이고 접근 가능한지 curl 등을 통해 확인해주세요.
#
# settings.xml 파일이 누락되었거나 Nexus 미연결 시, 의존성 다운로드가 실패하거나
# 외부 저장소 접근 오류가 발생할 수 있습니다.

#!/bin/bash

M2_DIR="$HOME/.m2"
SETTINGS_FILE="$M2_DIR/settings.xml"
TEMPLATE_FILE="./settings.xml.template"
BACKUP_FILE="$SETTINGS_FILE.backup.$(date +%Y%m%d%H%M%S)"

echo "## Maven settings.xml 설치 시작"

# .m2 디렉토리 생성
mkdir -p "$M2_DIR"

# 기존 settings.xml 백업
if [ -f "$SETTINGS_FILE" ]; then
  echo "## 기존 settings.xml 백업: $BACKUP_FILE"
  mv "$SETTINGS_FILE" "$BACKUP_FILE"
fi

# 템플릿 복사
cp "$TEMPLATE_FILE" "$SETTINGS_FILE"
echo "## settings.xml 설치 완료: $SETTINGS_FILE"