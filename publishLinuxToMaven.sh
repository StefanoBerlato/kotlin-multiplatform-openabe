#!/bin/bash

. ../setupSonataPWS.sh # if this does not work, copy-paste in terminal and remove from here

./gradlew publishJvmPublicationToSnapshotRepository \
publishKotlinMultiplatformPublicationToSnapshotRepository publishLinuxX64PublicationToSnapshotRepository
