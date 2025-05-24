#!/bin/bash

echo "🔍 Semgrep scanning staged files..."

# get staged files
STAGED_FILES=$(git diff --cached --name-only --diff-filter=ACM | grep -E '\.js$|\.java$|\.kt$|\.py$|\.ts$|\.jsx$|\.tsx$|\.yaml$|\.yml$|\.json$|\.dockerfile$' || true)

if [[ -z "$STAGED_FILES" ]]; then
  echo "⚠️  No staged source files to scan."
  exit 0
fi

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
  --config p/java \
  --config p/kotlin \
  --config p/dockerfile \
  --config p/docker-compose \
  --config p/security-code-scan \
  --config p/findsecbugs \
  --severity=INFO \
  --skip-unknown-extensions \
  --disable-version-check \
  --metrics=off \
  $STAGED_FILES

exit 0
