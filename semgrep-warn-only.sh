#!/bin/bash

RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color

echo "🔍 Running Semgrep scan (warn-only)..."

semgrep login
# Use Pro rules via --config=auto which pulls managed rules for your org
semgrep \
  --config=auto \
  --pro \
  --skip-unknown-extensions \
  --disable-version-check \
  --metrics=off \
  --quiet \
  .

echo -e "\n${GREEN}✔️ Semgrep Pro scan completed (warn-only).${NC}"
echo -e "${RED}📄 For any findings or support, contact the Security Team.${NC}"

exit 0  # Always exit 0 to avoid blocking commit
