**Cloud Microservices Project — Run Guide**

- **Stack:** Spring Boot microservices, Eureka, Spring Cloud Gateway, MySQL, RabbitMQ, Keycloak
- **Orchestration:** Docker Compose

**Repo Layout**

- `docker-compose/default/docker-compose.yml`: Compose stack for infra + services
- `docker-compose/init-scripts/init.sql`: Creates required MySQL databases
- `eureka/`, `gateway-server/`, `userservice/`, `Bookingservice/`, `paymentMicroserviceforsaloonProject/`, `notifications/`, `categary-service/`, `saloon-service/`, `service-offering/`: Service source

**Quick Start (Docker Compose)**

- **Prerequisites:**
  - Docker Desktop 4.x or Docker Engine 20.10+
  - Free ports: `3307`, `5672`, `15672`, `3333`, `9000`, `8082-8089`, `9001`
- **Steps:**
  - `cd docker-compose/default`
  - Create a `.env` file with the following (adjust as needed):

    MYSQL_ROOT_PASSWORD=root

    ### Payment gateways 

    STRIPE_API_PUBLISHABLE=change-me \
    STRIPE_API_SECRET=change-me 

    ### Keycloak used by user-service and gateway

    KEYCLOAK_BASE_URL=http://keycloak:8080 \
    KEYCLOAK_CLIENT_ID=gateway-client \
    KEYCLOAK_CLIENT_SECRET=change-me \
    KEYCLOAK_ADMIN_USERNAME=admin \
    KEYCLOAK_ADMIN_PASSWORD=admin 

    ### JWK URI used by gateway 

    KEYCLOAK_JWK_URI=http://keycloak:8080/realms/master/protocol/openid-connect/certs

  - Bring the stack up:
    - `docker compose -f docker-compose.yml up -d`

- **Verify:**
  - Eureka: `http://localhost:8088`
  - API Gateway: `http://localhost:8089`
  - Keycloak Admin Console: `http://localhost:3333` (admin/admin)
  - RabbitMQ Management: `http://localhost:15672` (guest/guest)
  - MySQL: `localhost:3307` (user `root`, password from `.env`)

Notes:

- First run pulls images and may take several minutes.
- The compose file creates databases automatically using `docker-compose/init-scripts/init.sql`.
- If you don’t supply payment keys, the payment service may start but payment flows will not work.

**Service Ports (via Docker)**

- `gateway` → `8089`
- `eurekaserver` → `8088`
- `user` → `8082`
- `booking` → `8086`
- `payment` → `8087`
- `notification` → `9001`
- `category` → `8084`
- `saloon-service` → `8083`
- `service-offering` → `8085`



