#!/bin/bash
# Este archivo nos ayuda a automatizar ejecuciones de comandos en windows

CONTAINER_NAME = mysql-server

.PHONY: build start restart stop shell help

help: ## Show this help message
	@echo 'usage: make [target]'
	@echo
	@echo 'targets:'
	@egrep '^(.+)\:\ ##\ (.+)' ${MAKEFILE_LIST} | column -t -c 2 -s ':#'

start: ## Inicia los contenedores
	@docker-compose up -d

stop: ## Detiene los contenedores
	docker-compose stop

restart: ## Reinicia los contenedores
	$(MAKE) stop && $(MAKE) start

build: ## Reconstruye los contenedores
	@docker-compose build

shell: ## Abre bash en el contenedor de mongo
	docker exec -it $(CONTAINER_NAME) /bin/bash