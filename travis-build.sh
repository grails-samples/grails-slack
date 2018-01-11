#!/bin/bash
set -e

export EXIT_STATUS=0

./gradlew check || EXIT_STATUS=$?

echo "Tag: $TRAVIS_TAG"

if [[ $EXIT_STATUS ]]; then

    if [[ -n $TRAVIS_TAG ]] || [[ $TRAVIS_BRANCH == 'master' && $TRAVIS_PULL_REQUEST == 'false' ]]; then

        if [[ -n $TRAVIS_TAG ]]; then

            echo "Publishing to PWS"

            ./gradlew assemble || EXIT_STATUS=$?

            if [[ $EXIT_STATUS ]]; then
                ./gradlew cf-push || EXIT_STATUS=$i
            fi
        fi
    fi
fi
exit $EXIT_STATUS
