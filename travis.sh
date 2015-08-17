#!/bin/bash

if [ $TRAVIS_PULL_REQUEST == 'false' ]; then
  ./gradlew bintrayUpload
else
  ./gradlew check
fi
