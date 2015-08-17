#!/bin/bash

if [ $TRAVIS_PULL_REQUEST == 'false' ]; then
  ./gradlew -Dgrails.env=production bintrayUpload
else
  ./gradlew check
fi
