#!/bin/bash
echo "🔍 Running Semgrep scan (warn-only)..."
RESULTS=$(semgrep \
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

  .)

if [[ -z "$RESULTS" ]]; then
  echo "✅ No issues found by Semgrep."
else
  echo "$RESULTS"
fi

exit 0
