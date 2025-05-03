#!/bin/bash

# Clean any previous compiled files
rm -rf bin
mkdir -p bin

# Compile all Java files including the new ones
echo "Compiling Java files..."
javac -d bin src/metro/model/*.java src/metro/service/*.java src/metro/Main.java src/metro/VisualizeMapMain.java

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo ""
    echo "To run the main Metro Management application:"
    echo "  java -cp bin metro.Main"
    echo ""
    echo "To generate the map visualization DOT file:"
    echo "  java -cp bin metro.VisualizeMapMain"
    echo ""
    # Optionally, run the main application directly after compiling
    # echo "Running Metro Management System..."
    # echo "---------------------------------"
    # java -cp bin metro.Main
else
    echo "Compilation failed. Please check for errors."
fi
