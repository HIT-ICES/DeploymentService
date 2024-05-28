# DeploymentService
## Introduction
**DeploymentService** is an implementation based on the Springboot Java backend designed to provide management functionality for service deployment scenarios, allowing users to customise a scenario that contains a number of different containers, and to deploy and uninstall scenarios with a single click.

## Installation and Running
1. Clone the repository:
```
git clone https://github.com/your_username/your_microservice.git
```
2. Navigate to the project directory:
```
cd DeploymentService
```
3. Build the project using Maven:
```
mvn clean install
```
4. Build the Docker image:
```
docker build -t <you_image_url> .
```
5. Deploying to Kubernetes:
Make sure you have a Kubernetes cluster set up and kubectl configured to communicate with the cluster.
   Modify the image, namespace and other information in the deployment.yaml configuration file.
```
kubectl apply -f deployment.yaml
```
