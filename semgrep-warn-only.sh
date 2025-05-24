#!/bin/bash
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color

echo "🔍 Running Semgrep scan (warn-only)..."


semgrep \
  --config p/gitleaks \
  --config p/secrets \
  --config p/cwe-top-25 \
  --config p/default \
  --config p/owasp-top-ten \
  --config p/java \
  --config p/dockerfile \
  --config p/docker-compose \
  --config p/findsecbugs \
  --skip-unknown-extensions \
  --disable-version-check \
  --metrics=off \
  --quiet \
  .
echo -e "\n${GREEN}✔️  Scanning process has been completed.${NC} ${RED}📄 Results shown above.${NC}"


exit 0
