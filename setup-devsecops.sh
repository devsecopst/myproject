#!/bin/bash

set -e

echo "🚀 Setting up Semgrep pre-commit hook for macOS..."

# Install Homebrew if missing
if ! command -v brew &>/dev/null; then
  echo "🍺 Homebrew not found. Installing..."
  /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
fi

# Install Python if needed
if ! command -v python3 &>/dev/null; then
  echo "🐍 Installing Python..."
  brew install python
fi

# Install pipx if needed
if ! command -v pipx &>/dev/null; then
  echo "📦 Installing pipx..."
  brew install pipx
  pipx ensurepath
fi

# Install pre-commit if missing
if ! command -v pre-commit &>/dev/null; then
  echo "📦 Installing pre-commit..."
  pipx install pre-commit
fi

# Install semgrep if missing
if ! command -v semgrep &>/dev/null; then
  echo "🔍 Installing Semgrep..."
  pipx install semgrep
fi

# Place pre-commit config if not already present
if [ ! -f .pre-commit-config.yaml ]; then
  echo "📄 Adding .pre-commit-config.yaml..."
  cat <<'EOF' > .pre-commit-config.yaml
repos:
  - repo: local
    hooks:
      - id: semgrep-warn-only
        name: Semgrep (Warn Only)
        entry: bash ./semgrep-warn-only.sh
        language: system
        pass_filenames: false
        verbose: true
EOF
fi

# Place semgrep-warn-only.sh if not already present
if [ ! -f semgrep-warn-only.sh ]; then
  echo "📄 Adding semgrep-warn-only.sh..."
  cat <<'EOF' > semgrep-warn-only.sh
#!/bin/bash
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color

echo "🔍 Running Semgrep scan (warn-only)..."


#!/bin/bash
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
  --config p/java \
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

EOF

  chmod +x semgrep-warn-only.sh
fi

# Enable pre-commit
echo "🪝 Installing pre-commit hook..."
pre-commit install

echo "✅ DevSecOps setup complete! Try making a commit to test the hook."

