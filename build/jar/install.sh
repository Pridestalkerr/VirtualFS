#!/bin/bash
echo "#! /usr/bin/env -S java -jar" > /bin/fs-java
cat app.jar >> /bin/fs-java
chmod +x /bin/fs-java