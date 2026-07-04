# 🍔 Street Food Go

**Street Food Go** είναι μια full-stack web εφαρμογή παραγγελιοληψίας φαγητού (food delivery / ordering platform) που συνδέει **πελάτες (Customers)** με **ιδιοκτήτες εστιατορίων (Owners)**. Υλοποιήθηκε ως τελική εργασία (Final Project) στο μάθημα **Distributed Systems (DS)**.

Η εφαρμογή προσφέρει:
- Ένα **server-rendered web UI** (Thymeleaf) για browsing εστιατορίων, καλάθι, checkout και διαχείριση παραγγελιών.
- Ένα **στατεless REST API** (JWT-protected) για προγραμματιστική πρόσβαση, τεκμηριωμένο μέσω **Swagger / OpenAPI**.
- Ενσωμάτωση με ένα εξωτερικό microservice (**DS-Lab-NOC**) για validation τηλεφωνικών αριθμών και αποστολή SMS.
- Πλήρες **CI/CD pipeline** (GitHub Actions + Jenkins + Ansible) και **containerization** με Docker.

---

## 📋 Πίνακας Περιεχομένων

- [Αρχιτεκτονική](#-αρχιτεκτονική)
- [Τεχνολογίες](#-τεχνολογίες)
- [Δομή Project](#-δομή-project)
- [Domain Model](#-domain-model)
- [Ρόλοι Χρηστών & Λειτουργίες](#-ρόλοι-χρηστών--λειτουργίες)
- [Ασφάλεια & Authentication](#-ασφάλεια--authentication)
- [REST API](#-rest-api)
- [Εκτέλεση με Docker (προτεινόμενο)](#-εκτέλεση-με-docker-προτεινόμενο)
- [Τοπική Εκτέλεση (χωρίς Docker)](#-τοπική-εκτέλεση-χωρίς-docker)
- [Το DS-Lab-NOC Microservice](#-το-ds-lab-noc-microservice)
- [CI/CD](#-cicd)
- [Συγγραφείς](#-συγγραφείς)

---

## 🏗 Αρχιτεκτονική

```
┌─────────────────────────────────────────────────────────────────┐
│                        Browser (Client)                         │
│        Thymeleaf Views  +  Bootstrap CSS/JS  +  jQuery          │
└───────────────────────────┬───────────────────────────────────--┘
                             │ HTTP (session-based form login)
                             ▼
┌───────────────────────────────────────────────────────────────--┐
│                      Street Food Go (Spring Boot)                │
│                                                                   │
│   Controllers (MVC)          Rest Controllers (JSON + JWT)        │
│   ├─ HomeController          ├─ AuthController  (/api/v1/auth)   │
│   ├─ RestaurantController    ├─ RegisterRestController           │
│   ├─ ProductController       ├─ RestaurantRestController         │
│   ├─ CartController          └─ CartRestController                │
│   ├─ OrderController                                              │
│   ├─ ViewOrderController      Security                            │
│   ├─ LoginController          ├─ SecurityConfig (Spring Security) │
│   └─ RegisterController       └─ JwtAuthenticationFilter/Service  │
│                                                                   │
│   Services (business logic)   Repositories (Spring Data JPA)     │
│   ├─ PersonService            ├─ PersonRepository                │
│   ├─ RestaurantService        ├─ RestaurantRepository            │
│   ├─ ProductService           ├─ ProductRepository                │
│   └─ OrderProcessService      ├─ OrderRequestsRepository          │
│                                └─ OrderPlacementRepository        │
└───────────────┬───────────────────────────┬──────────────────---┘
                │                            │
                ▼                            ▼
     ┌─────────────────┐          ┌────────────────────────────┐
     │   PostgreSQL 16   │          │  DS-Lab-NOC microservice   │
     │  (JPA / Hibernate) │          │  (phone lookup + SMS API)   │
     └─────────────────┘          └────────────────────────────┘
                │
                ▼
     ┌─────────────────┐
     │     MailHog       │  (SMTP sandbox για welcome emails)
     └─────────────────┘
```

---

## 🛠 Τεχνολογίες

| Κατηγορία | Τεχνολογία |
|---|---|
| Γλώσσα / Runtime | Java 21 |
| Framework | Spring Boot 3.5.7 |
| Web layer | Spring MVC + Thymeleaf (+ Layout Dialect) |
| Ασφάλεια | Spring Security, JWT (`jjwt` 0.11.5) |
| Persistence | Spring Data JPA / Hibernate |
| Βάση Δεδομένων | PostgreSQL 16 (H2 για tests) |
| Validation | Hibernate Validator, Jakarta Bean Validation |
| Τηλέφωνα | Google `libphonenumber` |
| API Docs | springdoc-openapi (Swagger UI) |
| Email (dev) | Spring Mail + MailHog |
| Build | Maven (Maven Wrapper) |
| Containerization | Docker, Docker Compose |
| CI/CD | GitHub Actions, Jenkins, Ansible (deploy σε VM) |
| Frontend assets | Bootstrap, jQuery, Popper |

---

## 📁 Δομή Project

```
Project-DS-Street_Food_Go/
├── src/main/java/SFG/Street_Food_Go/
│   ├── Controllers/       # MVC controllers (σελίδες, φόρμες)
│   ├── Entities/          # JPA entities (Person, Restaurant, Product, OrderRequest, ...)
│   ├── Repository/        # Spring Data JPA repositories
│   ├── Services/          # Business logic + DTOs/models
│   │   └── impl/          # Υλοποιήσεις services
│   ├── Rest/              # REST controllers + DTOs + mappers (JSON API)
│   ├── Security/          # Spring Security config + JWT filter/service
│   ├── Port/              # Θύρες προς εξωτερικές υπηρεσίες (SMS, phone validation, email)
│   └── config/            # OpenAPI/Swagger config
├── src/main/resources/
│   ├── templates/         # Thymeleaf HTML views (owner/customer dashboards, menus, orders...)
│   ├── static/             # CSS/JS (Bootstrap, jQuery)
│   └── application.properties
├── src/test/               # Unit/integration tests
├── DS-Lab-NOC/              # Ξεχωριστό Spring Boot microservice (lookup + SMS, βλ. παρακάτω)
├── Dockerfile               # Multi-stage build για την κύρια εφαρμογή
├── docker-compose.yml        # Ενορχήστρωση app + PostgreSQL + MailHog
├── Jenkinsfile               # Pipeline: test → docker build/push → Ansible deploy
├── .github/workflows/ci.yaml # GitHub Actions: build & push image σε GHCR
└── pom.xml
```

---

## 🗃 Domain Model

Οι βασικές οντότητες (JPA `@Entity`) του συστήματος:

| Entity | Περιγραφή |
|---|---|
| **Person** | Χρήστης του συστήματος (πελάτης ή ιδιοκτήτης), με `PersonType` (`CUSTOMER` / `OWNER`), στοιχεία επικοινωνίας και password hash. |
| **Restaurant** | Εστιατόριο, συνδεδεμένο με τον `owner` (Person) του και μια λίστα `Product`. |
| **Product** | Προϊόν του μενού (όνομα, τιμή, διαθεσιμότητα), ανήκει σε ένα `Restaurant`. |
| **OrderRequest** | Μια παραγγελία πελάτη προς εστιατόριο, με `OrderStatus` και timestamp δημιουργίας. |
| **OrderPlacement** | Γραμμή παραγγελίας (ποσότητα προϊόντος μέσα σε ένα `OrderRequest`). |
| **OrderStatus** (enum) | `PENDING → BEING_PREPARED → ON_THE_WAY → COMPLETED`, ή `DECLINED`. |
| **Client** | OAuth/JWT-style "μηχανικός" client (client-id/secret + ρόλοι) για machine-to-machine authentication στο API. |

Σχέσεις: `Person 1—N Restaurant`, `Restaurant 1—N Product`, `Restaurant 1—N OrderRequest`, `OrderRequest 1—N OrderPlacement N—1 Product`.

---

## 👥 Ρόλοι Χρηστών & Λειτουργίες

### 🧑‍🍳 Owner (Ιδιοκτήτης Εστιατορίου)
- Dashboard διαχείρισης (`/dashboard`, `/restaurant/**`).
- Δημιουργία/επεξεργασία εστιατορίου και μενού (`/product/**`, `/menu/*`).
- Διαχείριση παραγγελιών: ενεργές, εκκρεμείς, απορριφθείσες (`/order/restaurant/**`).
- Αποδοχή/απόρριψη παραγγελιών με μήνυμα αιτιολόγησης.

### 🧑 Customer (Πελάτης)
- Περιήγηση σε λίστα εστιατορίων (`/restaurants`) — δημόσια προσβάσιμη.
- Προβολή μενού και προσθήκη προϊόντων στο καλάθι (`/menu/{rest_id}/cart`).
- Υποβολή παραγγελίας (checkout) και παρακολούθηση κατάστασης παραγγελιών (`/viewOrder/**`).

### 🔌 API Client
- Machine-to-machine πρόσβαση μέσω `client_id` / `client_secret` → JWT token (`/api/v1/auth/client-tokens`).
- Ανάγνωση εστιατορίων/μενού μέσω REST endpoints, τεκμηριωμένα στο Swagger UI.

---

## 🔐 Ασφάλεια & Authentication

Η εφαρμογή χρησιμοποιεί **Spring Security** με δύο παράλληλα μονοπάτια αυθεντικοποίησης:

1. **Form login (session-based)** για το browser UI, με custom `AuthSuccessHandler` που δρομολογεί τον χρήστη ανάλογα με τον ρόλο του (`OWNER`/`CUSTOMER`) μετά το login.
2. **JWT Bearer tokens** για τα `/api/v1/**` endpoints, μέσω custom `JwtAuthenticationFilter` + `JwtService`.

Επιπλέον χαρακτηριστικά:
- Custom filter που ξεχωρίζει αιτήματα browser vs API client (βάσει `Accept` header) και επιστρέφει είτε HTML error page είτε δομημένο JSON `403` σφάλμα.
- Password encoding μέσω `PasswordEncoderFactories.createDelegatingPasswordEncoder()`.
- Method-level security ενεργοποιημένη (`@EnableMethodSecurity(securedEnabled = true)`).
- Role-based route protection (π.χ. `/orders/**`, `/dashboard` → `OWNER`, `/viewOrder/**` → `CUSTOMER`).
- Public endpoints: αρχική σελίδα, εγγραφή, login, στατικά αρχεία, Swagger UI, λίστα εστιατορίων/μενού.

---

## 📡 REST API

Τεκμηρίωση διαθέσιμη μέσω Swagger UI στο:

```
http://localhost:8080/swagger-ui.html
```

Βασικά endpoints:

| Method | Path | Περιγραφή | Auth |
|---|---|---|---|
| `POST` | `/api/v1/auth/client-tokens` | Login client (`client_id`/`client_secret`) → JWT | Public |
| `POST` | `/api/v1/register` | Εγγραφή νέου χρήστη | Public |
| `GET` | `/api/v1/restaurants` | Λίστα όλων των εστιατορίων | Public |
| `GET` | `/api/v1/restaurants/{rest_id}` | Στοιχεία συγκεκριμένου εστιατορίου | Public |
| `GET` | `/api/v1/menu/{rest_id}/cart` | Προϊόντα μενού ενός εστιατορίου | Public |
| `GET` | `/api/v1/auth/test-secure` | Δοκιμαστικό protected endpoint | Bearer JWT |

Global error handling για το API γίνεται μέσω `GlobalApiExceptionHandler` (πακέτο `Rest/ErrorPackage`).

---

## 🐳 Εκτέλεση με Docker (προτεινόμενο)

### Προαπαιτούμενα
- [Docker](https://docs.docker.com/engine/install/) & Docker Compose

### Βήματα

```bash
git clone https://github.com/KyriakosKarda/Project-DS-Street_Food_Go.git
cd Project-DS-Street_Food_Go
docker compose up
```

Αυτό εκκινεί 3 containers μέσω `docker-compose.yml`:

| Service | Image | Port | Ρόλος |
|---|---|---|---|
| `spring-app` | build από το κύριο `Dockerfile` (ή pre-built από GHCR) | `8080` | Η εφαρμογή Street Food Go |
| `db` | `postgres:16` | `5432` | Κύρια βάση δεδομένων |
| `mail-hog` | `mailhog/mailhog` | `1025` (SMTP) / `8025` (Web UI) | Sandbox για δοκιμαστικά emails |

### Πρόσβαση

- Εφαρμογή: **http://localhost:8080**
- Swagger UI: **http://localhost:8080/swagger-ui.html**
- MailHog Web UI (προβολή welcome emails): **http://localhost:8025**

> Το `spring-app` περιμένει να γίνει healthy το Postgres (`service_healthy`) πριν ξεκινήσει, χάρη στο `depends_on` + `healthcheck` στο compose file.

---

## 💻 Τοπική Εκτέλεση (χωρίς Docker)

Αν προτιμάς να τρέξεις μόνο την εφαρμογή Java τοπικά (χρειάζεσαι δικιά σου PostgreSQL 16 instance):

```bash
# 1. Ρύθμισε datasource στο application.properties ή μέσω env vars:
#    SPRING_DATASOURCE_URL / SPRING_DATASOURCE_USERNAME / SPRING_DATASOURCE_PASSWORD

# 2. Build & τρέξε
./mvnw clean package
./mvnw spring-boot:run

# ή απευθείας το jar
java -jar target/Street_Food_Go-0.0.1-SNAPSHOT.jar
```

Το `spring.jpa.hibernate.ddl-auto=update` δημιουργεί/ενημερώνει αυτόματα το schema στην εκκίνηση.

### Εκτέλεση tests

```bash
./mvnw test
```

---

## 🔗 Το DS-Lab-NOC Microservice

Ο φάκελος `DS-Lab-NOC/` περιέχει ένα **ξεχωριστό, ανεξάρτητο Spring Boot service** (module `gr.hua.dit:noc`) που παρέχει:

- **Lookup service** — αναζήτηση/επιβεβαίωση στοιχείων χρήστη.
- **Phone number validation** — μέσω Google `libphonenumber`.
- **SMS notification service** — με pluggable υλοποιήσεις (`MockSmsService` για ανάπτυξη, `RouteeSmsService` για πραγματική αποστολή μέσω του Routee provider), επιλεγμένες δυναμικά από `SmsServiceSelector`.
- Δικό του Swagger UI, τεκμηριωμένο ξεχωριστά.

Η κύρια εφαρμογή Street Food Go επικοινωνεί με αυτό μέσω των interfaces στο πακέτο `Port/` (`PhoneNumberService`, `SmsNotificationPort`) — ένα καθαρό παράδειγμα **Hexagonal / Ports & Adapters** αρχιτεκτονικής, όπου η υλοποίηση (`Port/impl/**`) καλεί το εξωτερικό microservice.

### Εκτέλεση του NOC ξεχωριστά

```bash
cd DS-Lab-NOC
./mvnw spring-boot:run
```

Προεπιλεγμένο port: **8081** — Swagger: `http://localhost:8081/swagger-ui/index.html`

Έχει επίσης δικό του `NOC-Dockerfile.Dockerfile` για containerized εκτέλεση.

---

## ⚙️ CI/CD

Το project διαθέτει διπλό pipeline:

1. **GitHub Actions** (`.github/workflows/ci.yaml`)
   - Trigger: push στο `main`.
   - Build Docker image → push στο **GitHub Container Registry (GHCR)**, με tag το commit SHA.

2. **Jenkins** (`Jenkinsfile`)
   - Στάδιο **Test**: `./mvnw test`.
   - Στάδιο **Docker build & push**: build image με tag `<short-commit>-<build-id>`, push στο GHCR.
   - Στάδιο **Deploy**: clone ξεχωριστού repo Ansible playbooks (`Ansible-Deployment-DevOps`) και εκτέλεση `ansible-playbook` για deployment σε remote VM.

Αυτό αντιπροσωπεύει ένα πλήρες **build → test → containerize → deploy** pipeline, ενδεικτικό των αρχών DevOps/Distributed Systems που καλύπτει το μάθημα.

---

## 👨‍💻 Συγγραφείς

- **Kyriakos Kardabikis**
- **Spyros Xenos**
- **Antzela Vassili**

*Distributed Systems — Τελική Εργασία (Final Project)*
