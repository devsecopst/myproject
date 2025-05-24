#!/bin/bash

echo "🔎 Running Semgrep in warn-only mode (will NOT block commits)..."

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
  --skip-unknown-extensions \
  --disable-version-check \
  --quiet
# Always allow the commit to continue
exit 0

