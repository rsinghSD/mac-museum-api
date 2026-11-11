#!/usr/bin/env bash
# Creates labels, milestones and one issue per user story on GitHub.
#
# Requirements: gh CLI authenticated (`gh auth login`) with repo scope.
# Usage:        scripts/setup-github.sh [owner/repo]
#
# Idempotent: labels are upserted, existing milestones and issues (matched by
# title) are skipped, so the script is safe to re-run.
set -euo pipefail

REPO="${1:-$(gh repo view --json nameWithOwner -q .nameWithOwner)}"
STORIES_FILE="$(dirname "$0")/../docs/user-stories.md"

echo "==> Configuring $REPO"

# ---------------------------------------------------------------- labels ----
label() { # name color description
  gh label create "$1" --repo "$REPO" --color "$2" --description "$3" --force >/dev/null
  echo "    label: $1"
}

echo "==> Labels"
label "epic:browse"      "1d76db" "Browsing the catalog, details, timeline"
label "epic:search"      "0e8a16" "Filtering and free-text search"
label "epic:compare"     "5319e7" "Side-by-side comparison"
label "epic:admin"       "b60205" "Curating catalog data (CRUD)"
label "epic:ops"         "6f6f6f" "Deployment, health, CI"
label "type:feature"     "a2eeef" "New feature or enhancement"
label "type:bug"         "d73a4a" "Something is not working"
label "type:user-story"  "c5def5" "User story with acceptance criteria"
label "good first issue" "7057ff" "Good for newcomers"
label "priority:high"    "e11d21" "Must have for the milestone"
label "priority:medium"  "fbca04" "Should have"
label "priority:low"     "c2e0c6" "Nice to have"

# ------------------------------------------------------------ milestones ----
milestone() { # title description
  if gh api "repos/$REPO/milestones?state=all&per_page=100" -q '.[].title' | grep -Fxq "$1"; then
    echo "    milestone exists: $1"
  else
    gh api "repos/$REPO/milestones" -f title="$1" -f description="$2" >/dev/null
    echo "    milestone created: $1"
  fi
}

echo "==> Milestones"
milestone "v0.1" "Project setup: skeleton, CI, docs, user stories"
milestone "v0.2" "Domain, migrations, seed data, CRUD API with tests"
milestone "v0.3" "Filters, timeline, compare, random, Swagger, error handling"
milestone "v0.4" "Retro Platinum web frontend"
milestone "v0.5" "Docker, prod profile, health checks"
milestone "v1.0" "Public release: deployed on Render, polished README"

# ---------------------------------------------------------------- issues ----
# id | epic label | priority | milestone | good-first-issue (yes/no)
STORIES=(
  "US-01|epic:browse|priority:high|v0.2|no"
  "US-02|epic:browse|priority:high|v0.2|no"
  "US-03|epic:browse|priority:medium|v0.2|yes"
  "US-04|epic:browse|priority:medium|v0.3|no"
  "US-05|epic:browse|priority:low|v0.3|yes"
  "US-06|epic:search|priority:high|v0.3|no"
  "US-07|epic:search|priority:high|v0.3|no"
  "US-08|epic:search|priority:medium|v0.3|no"
  "US-09|epic:compare|priority:medium|v0.3|no"
  "US-10|epic:compare|priority:medium|v0.4|no"
  "US-11|epic:admin|priority:high|v0.2|no"
  "US-12|epic:admin|priority:high|v0.2|no"
  "US-13|epic:admin|priority:high|v0.3|no"
  "US-14|epic:ops|priority:medium|v0.5|yes"
  "US-15|epic:ops|priority:medium|v1.0|no"
)

# Prints the "### US-XX — Title" heading line of a story.
story_heading() {
  grep -m1 "^### $1 " "$STORIES_FILE" | sed 's/^### //'
}

# Prints the body of a story: everything after its heading up to the next
# "###"/"##" heading or horizontal rule.
story_body() {
  awk -v id="$1" '
    $0 ~ "^### " id " " { on = 1; next }
    on && (/^### / || /^## / || /^---/) { exit }
    on { print }
  ' "$STORIES_FILE"
}

echo "==> Issues"
existing_titles="$(gh issue list --repo "$REPO" --state all --limit 500 --json title -q '.[].title')"

for entry in "${STORIES[@]}"; do
  IFS='|' read -r id epic priority ms gfi <<<"$entry"
  title="$(story_heading "$id")"
  if [[ -z "$title" ]]; then
    echo "    !! $id not found in $STORIES_FILE" >&2
    exit 1
  fi
  if grep -Fxq "$title" <<<"$existing_titles"; then
    echo "    issue exists: $title"
    continue
  fi
  labels="type:user-story,$epic,$priority"
  [[ "$gfi" == "yes" ]] && labels="$labels,good first issue"
  body="$(story_body "$id")

---
_Source: [docs/user-stories.md](../blob/main/docs/user-stories.md)_"
  gh issue create --repo "$REPO" --title "$title" --body "$body" \
    --label "$labels" --milestone "$ms" >/dev/null
  echo "    issue created: $title [$ms]"
done

echo "==> Done"
