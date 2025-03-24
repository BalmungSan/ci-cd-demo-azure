# CI / CD demo for Azure

Demo showing how to use **Github Actions** to configure a `CI / CD` pipeline deploying a **Dockerized** **Scala** app to **Azure** using **Terraform**.

-----

### Docker note

Note that for the sake of example, this project uses a `Dockerfile` and an assembly **JAR**.
That was done mostly for simplicity and since it could be more easily extrapolated to other languages.<br>
For real **Scala** apps, it is usually recommended to use [**sbt-native-packager**](https://github.com/sbt/sbt-native-packager) instead.
