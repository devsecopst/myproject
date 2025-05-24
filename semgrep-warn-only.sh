#!/bin/bash
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color

echo "🔍 Running Semgrep scan (warn-only)..."

semgrep \
  --config p/gitleaks \
  --config p/secrets \
  --config p/comment \
  --config p/cwe-top-25 \
  --config p/default \
  --config p/owasp-top-ten \
  --config p/security-audit \
  --config p/secure-defaults \
  --config p/javascript \
  --config p/react \
  --config p/swift \
  --config p/java \
  --config p/kotlin \
  --config p/dockerfile \
  --config p/docker-compose \
  --config p/security-code-scan \
  --config p/findsecbugs \
  --skip-unknown-extensions \
  --disable-version-check \
  --metrics=off \
  --quiet \
  .
echo -e "\n${GREEN}✔️Semgrep security code scan completed.\n\r${NC} ${RED}📄 For any questions or concerns, please contact the Security Team.${NC}"

exit 0
