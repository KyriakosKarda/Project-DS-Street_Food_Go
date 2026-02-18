# Street Food Go (DS Final Project)


---

## Clone the repository

```bash
git clone https://github.com/KyriakosKarda/Project-DS-Street_Food_Go.git
```

---

## Run the application

Navigate to the project directory:

```bash
cd Project-DS-Street_Food_Go
```

If you have Docker, run with Docker Compose:

```bash
docker compose up
```

If you don't have Docker installed, follow the instructions to install it:

### Ubuntu

```bash


https://docs.docker.com/engine/install/ubuntu/

# Add Docker's official GPG key:
sudo apt-get update
sudo apt-get install ca-certificates curl
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc

# Add the repository to Apt sources:
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "${UBUNTU_CODENAME:-$VERSION_CODENAME}") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update


install

sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin


No sudo docker run

sudo groupadd docker
sudo usermod -aG docker $USER


```
Docker Desktop

```
https://docs.docker.com/desktop/
```

---

## Access the application

Open in Browser:

```
http://localhost:8080
```


## Authors

Kyriakos Kardabikis

Spyros Xenos

Antzela Vassili
