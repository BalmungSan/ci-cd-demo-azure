# CI / CD demo for Azure

Demo showing how to use **GitHub Actions** to configure a `CI / CD` pipeline deploying a **Dockerized** **Scala** app to **Azure** using **Terraform**.

-----

## Testing the app

You can test the application using:
* The **Swagger** web page: https://todo-service.orangeriver-980bfa97.eastus2.azurecontainerapps.io/docs/index.html.
* In the console using **curl**: `curl -X GET https://todo-service.orangeriver-980bfa97.eastus2.azurecontainerapps.io/list` ; or any other similar web client.
* Using the **Scala** CLI app: `sbt client/run`.
* Writing a client in any language that supports **smithy**.

-----

### Docker note

Note that for the sake of example, this project uses a `Dockerfile` and an assembly **JAR**.
That was done mostly for simplicity and since it could be more easily extrapolated to other languages.<br>
For real **Scala** apps, it is usually recommended to use [**sbt-native-packager**](https://github.com/sbt/sbt-native-packager) instead.
