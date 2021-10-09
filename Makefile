
check:
	docker compose run --rm grpc-server mvn clean install
	docker compose run --rm grpc-client mvn clean install

.PHONY: check