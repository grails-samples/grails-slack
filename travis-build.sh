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

# If this is the master branch then update the snapshot
if [[ $TRAVIS_BRANCH == 'master' && $TRAVIS_PULL_REQUEST == 'false' ]]; then

    git config --global user.name "$GIT_NAME"
    git config --global user.email "$GIT_EMAIL"
    git config --global credential.helper "store --file=~/.git-credentials"
    echo "https://$GH_TOKEN:@github.com" > ~/.git-credentials

    git clone https://${GH_TOKEN}@github.com/${TRAVIS_REPO_SLUG}.git -b gh-pages gh-pages --single-branch > /dev/null
    cd gh-pages

    cp -r ../docs/build/asciidoc/html5/. ./snapshot/
    git add snapshot/*

    git commit -a -m "Updating docs for Travis build: https://travis-ci.org/$TRAVIS_REPO_SLUG/builds/$TRAVIS_BUILD_ID"
    git push origin HEAD

    cd ..
    rm -rf gh-pages
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
