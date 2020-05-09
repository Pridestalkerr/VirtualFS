#!/bin/bash
javac -d ./build/class/ ./src/main/VirtualFS/*.java -Xmaxerrs 1
jar cfm ./build/jar/app.jar MANIFEST.MF -C ./build/class .
