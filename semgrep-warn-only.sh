#!/bin/bash
echo "🔍 Running Semgrep scan (warn-only)..."

semgrep \
  --config p/gitleaks \
  --config p/secrets \
  --config p/cwe-top-25 \
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

exit 0
