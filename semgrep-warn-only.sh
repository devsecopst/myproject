#!/bin/bash
echo "🔍 Running Semgrep scan (warn-only)..."

semgrep \
  --config p/gitleaks \
  --config p/secrets \
  --config p/comment \
  --config p/cwe-top-25 \
  --config p/default \
  --config p/owasp-top-ten \
  --config p/java \
  --config p/dockerfile \
  --config p/docker-compose \
  --config p/security-code-scan \
  --skip-unknown-extensions \
  --disable-version-check \
  --metrics=off \
  --quiet \
  .

exit 0
