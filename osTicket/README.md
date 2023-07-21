# Docker OS Ticket

## Requirement

You must have a Docker! I you use Windows, please use WSL2.

## Setup

Run command below to build a custom OS Ticket Docker image.

```bash
docker build - t mycompany/osticket .
``` 

Next, run `docker compose up -d`. Then, open osTicket site in http://localhost:7777.

To open admin panel page, please go to http://localhost:7777/scp/login.php. The default credentials is:

- username: ostadmin
- password: Admin1

## Reference

[Provision OSticket with Docker (2023)](https://mpolinowski.github.io/docs/DevOps/Provisioning/2023-03-09--os-ticket-docker-2023/2023-03-09/).