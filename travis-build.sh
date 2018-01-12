#!/bin/bash
set -e

export EXIT_STATUS=0

./gradlew app:check || EXIT_STATUS=$?

if [[ $EXIT_STATUS -ne 0 ]]; then
    echo "Project Check failed"
    exit $EXIT_STATUS
fi

./gradlew doc:asciidoctor || EXIT_STATUS=$?

if [[ $EXIT_STATUS -ne 0 ]]; then
    echo "Documentation generation failed"
    exit $EXIT_STATUS
fi

echo "Tag: $TRAVIS_TAG"

if [[ $EXIT_STATUS ]]; then

    if [[ -n $TRAVIS_TAG ]] || [[ $TRAVIS_BRANCH == 'master' && $TRAVIS_PULL_REQUEST == 'false' ]]; then

        if [[ -n $TRAVIS_TAG ]]; then

            echo "Publishing to PWS"

            ./gradlew app:assemble || EXIT_STATUS=$?

            if [[ $EXIT_STATUS ]]; then
                ./gradlew app:cf-push || EXIT_STATUS=$i
            fi
        fi
    fi
fi
exit $EXIT_STATUS
