#!/usr/bin/env bash
# Seed Firebase emulators with test data.
# Run this AFTER the emulators are up: ./seed-emulator.sh

set -euo pipefail

AUTH_URL="http://127.0.0.1:9099"
FIRESTORE_URL="http://127.0.0.1:8080"
PROJECT_ID="barter-trader-local"

echo "==> Creating test users in Auth emulator..."

# Create a provider user
curl -s -X POST "${AUTH_URL}/identitytoolkit.googleapis.com/v1/accounts:signUp?key=fake-api-key" \
  -H 'Content-Type: application/json' \
  -d '{"email":"provider@test.com","password":"Test1234!","returnSecureToken":true}' \
  -o /tmp/provider_resp.json

PROVIDER_UID=$(python3 -c "import json; print(json.load(open('/tmp/provider_resp.json'))['localId'])")
echo "    Provider UID: ${PROVIDER_UID}"

# Create a receiver user
curl -s -X POST "${AUTH_URL}/identitytoolkit.googleapis.com/v1/accounts:signUp?key=fake-api-key" \
  -H 'Content-Type: application/json' \
  -d '{"email":"receiver@test.com","password":"Test1234!","returnSecureToken":true}' \
  -o /tmp/receiver_resp.json

RECEIVER_UID=$(python3 -c "import json; print(json.load(open('/tmp/receiver_resp.json'))['localId'])")
echo "    Receiver UID: ${RECEIVER_UID}"

echo "==> Seeding Firestore with user profiles..."

# Provider user doc
curl -s -X PATCH \
  "${FIRESTORE_URL}/v1/projects/${PROJECT_ID}/databases/(default)/documents/users/${PROVIDER_UID}?updateMask.fieldPaths=role&updateMask.fieldPaths=email" \
  -H 'Content-Type: application/json' \
  -d "{
    \"fields\": {
      \"role\": {\"stringValue\": \"provider\"},
      \"email\": {\"stringValue\": \"provider@test.com\"}
    }
  }"

# Receiver user doc
curl -s -X PATCH \
  "${FIRESTORE_URL}/v1/projects/${PROJECT_ID}/databases/(default)/documents/users/${RECEIVER_UID}?updateMask.fieldPaths=role&updateMask.fieldPaths=email" \
  -H 'Content-Type: application/json' \
  -d "{
    \"fields\": {
      \"role\": {\"stringValue\": \"receiver\"},
      \"email\": {\"stringValue\": \"receiver@test.com\"}
    }
  }"

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
  }"

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
  }"

echo ""
echo "=== Seeding complete ==="
echo ""
echo "Test accounts:"
echo "  Provider: provider@test.com / Test1234!"
echo "  Receiver: receiver@test.com / Test1234!"
echo ""
echo "Emulator UI: http://127.0.0.1:4000"
