
# 🏦 Enterprise Banking Platform
[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Kafka](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apache-kafka&logoColor=white)](https://kafka.apache.org/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)](https://react.dev/)

A production-oriented, cloud-ready Enterprise Banking Platform engineered with a high-performance **Spring Boot Microservices Architecture**. This platform models resilient, distributed workflows typical of modern digital banking infrastructures—featuring independent domain databases, secure stateless authentication, and reactive event-driven streaming.

---

## 🚀 Key System Features

*   **Secure Identity Management:** Distributed authentication powered by Spring Security, stateless JWT verification, and encrypted database payloads using BCrypt.
*   **Decoupled Domain Services:** Completely isolated microservices executing core user onboarding, bank account provision, and transaction handling.
*   **Asynchronous Processing:** Asynchronous ledger updates, audit trails, and multi-channel notifications processed out-of-band using Apache Kafka.
*   **Enterprise Storage Topology:** Domain autonomy achieved by implementing a strict **Database-per-Service** design using containerized PostgreSQL clusters.
*   **Edge Routing & Discovery:** Single entry-point API gateway handling reverse proxying, token validation routing, and load balancing across internal service registries.

---

## 🛠️ Technology Stack

### Backend Infrastructure
*   **Core Execution:** Java 21 / Spring Boot 3
*   **Security Layers:** Spring Security, JSON Web Tokens (JWT), Cross-Origin Resource Sharing (CORS) configurations
*   **Data Integration:** Spring Data JPA, Hibernate ORM, Spring Validation Engine
*   **Service Mesh Communication:** Spring Cloud OpenFeign (Synchronous), Apache Kafka Broker (Asynchronous)
*   **Documentation standard:** Swagger / OpenAPI 3 Specification

### Storage & Message Broker
*   **Relational Storage:** PostgreSQL (Isolated service clusters)
*   **Distributed Logging & Streams:** Apache Kafka

### DevOps & Orchestration
*   **Container Runtime:** Docker Engine
*   **Multi-Container Orchestration:** Docker Compose
*   **Dependency Management:** Maven Multi-Module Project Structures

### Frontend Interface
*   **Single Page Application (SPA):** React.js (Scaffolded with Vite)
*   **UI Layout:** Tailwind CSS Core Engine
*   **HTTP Client:** Axios Interceptor Layers

---

# 🏗️ Enterprise Banking Platform Architecture

```text
                               ┌─────────────────────────────┐
                               │      React Frontend         │
                               │  (Vite + Tailwind CSS)      │
                               └──────────────┬──────────────┘
                                              │
                                              │ HTTP / REST
                                              ▼
                              ┌────────────────────────────────┐
                              │         API Gateway            │
                              │      Spring Cloud Gateway      │
                              └──────────────┬─────────────────┘
                                             │
        ┌────────────────────────────────────┼────────────────────────────────────┐
        │                                    │                                    │
        ▼                                    ▼                                    ▼
┌─────────────────┐                ┌─────────────────┐                ┌─────────────────┐
│   User Service  │                │ Account Service │                │Transaction Service│
│-----------------│                │-----------------│                │------------------│
│ • Register      │◄────Feign──────│ • Create Account│                │ • Deposit        │
│ • Login (JWT)   │                │ • View Account  │                │ • Withdraw       │
│ • User Profile  │                │ • Update/Delete │                │ • Transfer       │
└────────┬────────┘                └────────┬────────┘                └─────────┬────────┘
         │                                  │                                   │
         │                                  │                           Publishes Events
         │                                  │                                   │
         ▼                                  ▼                                   ▼
 ┌─────────────────┐                ┌─────────────────┐               ┌──────────────────┐
 │    users_db     │                │   accounts_db   │               │      Kafka       │
 │   PostgreSQL    │                │   PostgreSQL    │               │ Transaction Topic│
 └─────────────────┘                └─────────────────┘               └─────────┬────────┘
                                                                                 │
                              ┌──────────────────────────────────────────────────┼────────────────────────────────────────┐
                              │                                                  │                                        │
                              ▼                                                  ▼                                        ▼
                 ┌────────────────────────┐                     ┌────────────────────────┐                  ┌────────────────────────┐
                 │ Notification Service   │                     │  Statement Service     │                  │ Fraud Detection Service│
                 │------------------------│                     │------------------------│                  │------------------------│
                 │ Kafka Consumer         │                     │ Kafka Consumer         │                  │ Kafka Consumer         │
                 │ Sends Notifications    │                     │ Stores Statements      │                  │ Detects Fraud          │
                 └───────────┬────────────┘                     └───────────┬────────────┘                  └───────────┬────────────┘
                             │                                              │                                           │
                             ▼                                              ▼                                           ▼
                    PostgreSQL DB                                   PostgreSQL DB                              PostgreSQL DB


                              ┌──────────────────────────────┐
                              │      Shared Module           │
                              │------------------------------│
                              │ common-events               │
                              │ • TransactionEvent DTO      │
                              │ • Shared Kafka Models       │
                              └──────────────────────────────┘


                              ┌──────────────────────────────┐
                              │    Loan Service (Planned)    │
                              │------------------------------│
                              │ • Apply Loan                │
                              │ • EMI Calculation           │
                              │ • Loan Approval             │
                              │ • Loan History              │
                              └──────────────────────────────┘
```

## 📡 Distributed Communications Core

The transactional ecosystem minimizes service dependencies by treating transaction workflows as non-blocking state changes:

```text
Client
   │
   ▼
React Frontend
   │
   ▼
API Gateway
   │
   ├──────────── REST ───────────► User Service
   │
   ├──────────── REST ───────────► Account Service
   │
   └──────────── REST ───────────► Transaction Service
                                        │
                                        ▼
                               Apache Kafka Topic
                                        │
                     ┌──────────────────┼──────────────────┐
                     ▼                  ▼                  ▼
             Notification        Statement         Fraud Detection
                 Service            Service              Service

```

* **Synchronous Mechanics:** High-availability cross-domain validations (e.g., verifying User presence during Account setup) are managed reliably through **Spring Cloud OpenFeign**.
* **Asynchronous Mechanics:** Execution triggers like Deposits, Withdrawals, or Balances post event payloads immediately to specialized Kafka event logs to guarantee sub-millisecond API response targets.

---

## 📦 Microservices Topology

| Service Engine | Default Port | Registry Profile | Operational Status |
| --- | --- | --- | --- |
| **API Gateway** | `8080` | Internal Router | Production Ready |
| **User Service** | `8081` | Authentication Core | Production Ready |
| **Account Service** | `8082` | Core Banking Domain | Production Ready |
| **Transaction Service** | `8083` | Ledger Engine | Production Ready |
| **Notification Service** | `8084` | Event Broker Client | Production Ready |
| **Fraud Detection Service** | `8085` | Event Broker Client | Production Ready |
| **Statement Service** | `8086` | Event Broker Client | Production Ready |
| **Loan Service** | `8087` | Lending Domain | 🚧 Active Development |

---

## 🗄️ Database Per Service Topology

To eliminate tight schema coupling and ensure independent horizontal scaling, each domain service operates a private, sandboxed database instance:

* `users_db` — Profile metadata, authentication credentials, and permission matrices.
* `accounts_db` — Product configurations, routing information, and balance records.
* `transactions_db` — Append-only source ledger containing cryptographic transactions.
* `fraud_db` — Behavioral metrics and anomaly indicators.
* `statement_db` — High-read historical ledger views optimised for fast statements generation.

---

## 📖 API Directory (Swagger Integration)

Interactive technical documentation surfaces directly on local container endpoints. Browse payload structural models and execute requests against the runtime environment at:

* **User Engine Docs:** `http://localhost:8081/swagger-ui.html`
* **Account Engine Docs:** `http://localhost:8082/swagger-ui.html`
* **Transaction Engine Docs:** `http://localhost:8083/swagger-ui.html`

---

## 🐳 Container Setup & Deployment

Spin up the complete multi-module infrastructure—including databases, messaging brokers, gateways, and backend services—with a single terminal entry:

```bash
docker compose up --build -d

```

### Shared Project Monorepo Structure

```text
enterprise-banking-platform/
├── api-gateway/                 # Central routing configuration and ingress rules
├── user-service/                # Identity registration and authentication modules
├── account-service/             # Account configuration management engine
├── transaction-service/         # High-velocity financial transactions engine
├── notification-service/        # Event-driven communication client
├── statement-service/           # Analytical historical statement generator
├── fraud-detection-service/     # Transaction risk checking engine
├── loan-service/                # Debt processing engine (In Progress)
├── common-events/               # Shared Maven DTO library for Kafka schemas
├── frontend/                    # Single Page App UI (React + Tailwind)
├── docker-compose.yml           # Unified multi-service deployment spec
└── pom.xml                      # Global Maven Parent dependency tree

```

---

## 📌 Project Roadmap

### 🏁 Milestones Achieved

* [x] Distributed Spring Boot 3 Microservices Architecture
* [x] Cross-Service isolation utilizing isolated PostgreSQL clusters
* [x] Asynchronous messaging loops via Apache Kafka Brokers
* [x] Fully stateless API authorization filters via JWT layers
* [x] Reusable internal schema packaging (`common-events` core library)
* [x] Standardized exception parsing over all REST communication lines

### 🚧 Current Development Focus

* [ ] Finalizing the responsive React interface panels
* [ ] Delivering the Loan Application Workflow engine
* [ ] Injecting distributed memory performance optimization layers via Redis Cache
* [ ] Deploying system health monitoring tooling (Prometheus & Grafana)
* [ ] Migrating runtime deployments onto Kubernetes (EKS/GKE Target profiles)

---

## 👨‍💻 Author

**K S Vasanth Kumar**

*Java Full-Stack Engineer / Distributed Systems Enthusiast*

* **GitHub:** [github.com/vasanth8055](https://github.com/vasanth8055)
* **LinkedIn:** [linkedin.com/in/vasanth-kumar-505a13296](https://www.linkedin.com/in/vasanth-kumar-505a13296/)

---

## 📜 License

This system architecture is developed explicitly for academic, educational, and technical portfolio demonstration purposes. Feel free to fork, explore, or adapt it for your own microservices projects.

```

```
