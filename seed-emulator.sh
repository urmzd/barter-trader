#!/usr/bin/env bash
# Seed Firebase emulators with test data.
# Safe to run multiple times — creates users if missing, signs in if they exist.

set -euo pipefail

AUTH_URL="http://127.0.0.1:9099"
FIRESTORE_URL="http://127.0.0.1:8080"
PROJECT_ID="barter-trader-local"

get_uid() {
  local email="$1"
  local password="$2"
  local resp

  # Try sign up first
  resp=$(curl -s -X POST "${AUTH_URL}/identitytoolkit.googleapis.com/v1/accounts:signUp?key=fake-api-key" \
    -H 'Content-Type: application/json' \
    -d "{\"email\":\"${email}\",\"password\":\"${password}\",\"returnSecureToken\":true}")

  local uid
  uid=$(python3 -c "import json,sys; d=json.loads(sys.argv[1]); print(d.get('localId',''))" "$resp")

  if [ -n "$uid" ]; then
    echo "$uid"
    return
  fi

  # Already exists — sign in instead
  resp=$(curl -s -X POST "${AUTH_URL}/identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=fake-api-key" \
    -H 'Content-Type: application/json' \
    -d "{\"email\":\"${email}\",\"password\":\"${password}\",\"returnSecureToken\":true}")

  uid=$(python3 -c "import json,sys; d=json.loads(sys.argv[1]); print(d['localId'])" "$resp")
  echo "$uid"
}

echo "==> Creating/signing in test users..."

PROVIDER_UID=$(get_uid "provider@test.com" "Test1234!")
echo "    Provider UID: ${PROVIDER_UID}"

RECEIVER_UID=$(get_uid "receiver@test.com" "Test1234!")
echo "    Receiver UID: ${RECEIVER_UID}"

echo "==> Seeding Firestore with user profiles..."

curl -s -X PATCH \
  "${FIRESTORE_URL}/v1/projects/${PROJECT_ID}/databases/(default)/documents/users/${PROVIDER_UID}?updateMask.fieldPaths=role&updateMask.fieldPaths=email" \
  -H 'Content-Type: application/json' \
  -d "{
    \"fields\": {
      \"role\": {\"stringValue\": \"provider\"},
      \"email\": {\"stringValue\": \"provider@test.com\"}
    }
  }" > /dev/null

curl -s -X PATCH \
  "${FIRESTORE_URL}/v1/projects/${PROJECT_ID}/databases/(default)/documents/users/${RECEIVER_UID}?updateMask.fieldPaths=role&updateMask.fieldPaths=email" \
  -H 'Content-Type: application/json' \
  -d "{
    \"fields\": {
      \"role\": {\"stringValue\": \"receiver\"},
      \"email\": {\"stringValue\": \"receiver@test.com\"}
    }
  }" > /dev/null

echo "==> Seeding Firestore with sample posts..."

curl -s -X POST \
  "${FIRESTORE_URL}/v1/projects/${PROJECT_ID}/databases/(default)/documents/posts" \
  -H 'Content-Type: application/json' \
  -d "{
    \"fields\": {
      \"title\": {\"stringValue\": \"Guitar Lessons\"},
      \"description\": {\"stringValue\": \"Offering beginner guitar lessons in exchange for cooking lessons\"},
      \"category\": {\"stringValue\": \"Music\"},
      \"uid\": {\"stringValue\": \"${PROVIDER_UID}\"}
    }
  }" > /dev/null

curl -s -X POST \
  "${FIRESTORE_URL}/v1/projects/${PROJECT_ID}/databases/(default)/documents/posts" \
  -H 'Content-Type: application/json' \
  -d "{
    \"fields\": {
      \"title\": {\"stringValue\": \"Math Tutoring\"},
      \"description\": {\"stringValue\": \"Can tutor high school math, looking for language practice partner\"},
      \"category\": {\"stringValue\": \"Education\"},
      \"uid\": {\"stringValue\": \"${PROVIDER_UID}\"}
    }
  }" > /dev/null

echo ""
echo "=== Seeding complete ==="
echo ""
echo "Test accounts:"
echo "  Provider: provider@test.com / Test1234!"
echo "  Receiver: receiver@test.com / Test1234!"
echo ""
echo "Emulator UI: http://127.0.0.1:4000"
