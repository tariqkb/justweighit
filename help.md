create database jwi
create schema public
create role jwi
alter role jwi with login password 'jwi'
manually run schema.sql
import:
	cd backend && ./build/install/justweighit/bin/backend import-all ../../src/main/resources/config.yml
	OR
	gradle backend:import-all


# Postgres
psql -h localhost -U postgres jwi
docker run --name postgres --rm -p 5432:5432 -v /Users/tariqbugrara/dev/pgdata:/var/lib/postgresql/data -e POSTGRES_PASSWORD=test -d postgres

# deployment
docker build -t registry.digitalocean.com/jwi/jwi . && \
 docker push registry.digitalocean.com/jwi/jwi && \
 kubectl rollout restart deployment/jwi-backend

docker push registry.digitalocean.com/jwi/jwi