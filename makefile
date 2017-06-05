PROJECT_NAME=game-of-life
VERSION_NAME=$(PROJECT_NAME)-0.0.1-SNAPSHOT

PID = /tmp/$(PROJECT_NAME).pid
LOG_FILE = /tmp/$(PROJECT_NAME).log

build-local:
	./gradlew clean build -x test

install:
	./gradlew clean build

start:
	@if [ ! -f $(PID) ]; \
	then \
		java -jar build/libs/$(VERSION_NAME).jar  2>&1 & echo "$$!" >> $(PID) | tee $(LOG_FILE); \
	fi

stop:
	@if [ -f $(PID) ]; \
	then \
		cat $(PID) | xargs kill; \
		rm $(PID); \
	fi

restart: stop start

help:
	@echo 'Available commands: help, build, install, start, stop, restart'
	@echo ''
	@echo 'Commands in this Makefile are top level commands to package the final version of the app. For low level commands, see subdirectories.'
	@echo ''

.PHONY: help
