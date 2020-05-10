#!/bin/bash

cd ./src/
javac -d ../build/vfs/class ./vfs/Main.java -Xmaxerrs 1
cd ../
jar cfm ./build/vfs/jar/app.jar ./META-INF/vfs/MANIFEST.MF -C ./build/vfs/class .

cd ./src/
javac -d ../build/vfsmanager/class ./vfsmanager/Main.java -Xmaxerrs 1
cd ../
jar cfmv ./build/vfsmanager/jar/app.jar ./META-INF/vfsmanager/MANIFEST.MF -C ./build/vfsmanager/class/ .